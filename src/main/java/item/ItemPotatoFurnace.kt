package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.`class`.PotatoFurnaceState
import net.logandark.diamond2potato.gui.GuiHandler
import net.logandark.diamond2potato.inventory.InventoryPotatoFurnace
import net.logandark.diamond2potato.util.logger
import net.logandark.diamond2potato.util.modid
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.inventory.IInventory
import net.minecraft.item.*
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.*
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

object ItemPotatoFurnace : Item() {
	init {
		translationKey = "$modid.potato_furnace"
		registryName = ResourceLocation(modid, "potato_furnace")
		maxStackSize = 1
	}

	override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
		if (!worldIn.isRemote) {
			playerIn.openGui(Diamond2Potato, GuiHandler.GUIs.PotatoFurnace.ordinal, worldIn, 0, 0, 0)
		}

		return super.onItemRightClick(worldIn, playerIn, handIn)
	}

	/**
	 * Furnace isBurning
	 */
	private fun isBurning(potatoFurnaceState: PotatoFurnaceState) = potatoFurnaceState.furnaceBurnTime > 0

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	 */
	private fun canSmelt(inventory: IInventory): Boolean {
		if ((inventory.getStackInSlot(0) as ItemStack).isEmpty) {
			return false
		} else {
			val itemStack = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0))

			return if (itemStack.isEmpty) {
				false
			} else {
				val outputStack = inventory.getStackInSlot(2)

				if (outputStack.isEmpty) {
					true
				} else if (itemStack.item != outputStack.item) {
					false
				} else if (outputStack.count + itemStack.count <= inventory.inventoryStackLimit && outputStack.count + itemStack.count <= outputStack.maxStackSize) {
					true
				} else {
					outputStack.count + itemStack.count <= itemStack.maxStackSize
				}
			}
		}
	}

	@Suppress("unused_parameter")
	fun getCookTime(stack: ItemStack) = 200

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	private fun smeltItem(inventory: IInventory) {
		if (canSmelt(inventory)) {
			val itemStack = inventory.getStackInSlot(0)
			val fuelStack = inventory.getStackInSlot(1)
			val outputStack = inventory.getStackInSlot(2)

			val smeltingResult = FurnaceRecipes.instance().getSmeltingResult(itemStack)

			if (outputStack.isEmpty) {
				inventory.setInventorySlotContents(2, smeltingResult.copy())
			} else if (outputStack.item === smeltingResult.item) {
				outputStack.grow(smeltingResult.count)
			}

			if (itemStack.item === Item.getItemFromBlock(Blocks.SPONGE) && itemStack.metadata == 1 && !fuelStack.isEmpty && fuelStack.item === Items.BUCKET) {
				inventory.setInventorySlotContents(1, ItemStack(Items.WATER_BUCKET))
			}

			itemStack.shrink(1)
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
	 * fuel
	 */
	private fun getItemBurnTimeForFurnace(stack: ItemStack): Int {
		if (stack.isEmpty) {
			return 0
		}

		val burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack)
		if (burnTime >= 0) return burnTime

		val item = stack.item

		return if (item === Item.getItemFromBlock(Blocks.WOODEN_SLAB)) {
			150
		} else if (item === Item.getItemFromBlock(Blocks.WOOL)) {
			100
		} else if (item === Item.getItemFromBlock(Blocks.CARPET)) {
			67
		} else if (item === Item.getItemFromBlock(Blocks.LADDER)) {
			300
		} else if (item === Item.getItemFromBlock(Blocks.WOODEN_BUTTON)) {
			100
		} else if (Block.getBlockFromItem(item).defaultState.material === Material.WOOD) {
			300
		} else if (item === Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
			16000
		} else if (item is ItemTool && "WOOD" == item.toolMaterialName) {
			200
		} else if (item is ItemSword && "WOOD" == item.toolMaterialName) {
			200
		} else if (item is ItemHoe && "WOOD" == item.materialName) {
			200
		} else if (item === Items.STICK) {
			100
		} else if (item !== Items.BOW && item !== Items.FISHING_ROD) {
			if (item === Items.SIGN) {
				200
			} else if (item === Items.COAL) {
				1600
			} else if (item === Items.LAVA_BUCKET) {
				20000
			} else if (item !== Item.getItemFromBlock(Blocks.SAPLING) && item !== Items.BOWL) {
				if (item === Items.BLAZE_ROD) {
					2400
				} else if (item is ItemDoor && item !== Items.IRON_DOOR) {
					200
				} else {
					if (item is ItemBoat) 400 else 0
				}
			} else {
				100
			}
		} else {
			300
		}
	}

	override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
		if (worldIn.isRemote) return

		val inventory = InventoryPotatoFurnace(stack)
		val state = PotatoFurnaceState(stack)

		val itemStack = inventory.getStackInSlot(0)
		val fuelStack = inventory.getStackInSlot(1)
		var inventoryDirty = false

		if (isBurning(state)) {
			state.furnaceBurnTime--
		}

		if (isBurning(state) || !fuelStack.isEmpty && !itemStack.isEmpty) {
			if (!isBurning(state) && canSmelt(inventory)) {
				state.furnaceBurnTime = getItemBurnTimeForFurnace(fuelStack)
				state.currentItemBurnTime = state.furnaceBurnTime

				if (isBurning(state)) {
					if (!fuelStack.isEmpty) {
						val item = fuelStack.item
						fuelStack.shrink(1)
						inventoryDirty = true

						if (fuelStack.isEmpty) {
							inventory.setInventorySlotContents(1, item.getContainerItem(fuelStack))
						}
					}
				}
			}

			if (isBurning(state) && canSmelt(inventory)) {
				state.cookTime++

				if (state.cookTime == state.totalCookTime) {
					state.cookTime = 0
					state.totalCookTime = getCookTime(itemStack)
					smeltItem(inventory)
					inventoryDirty = true
				}
			} else {
				state.cookTime = 0
			}
		} else if (!isBurning(state) && state.cookTime > 0) {
			state.cookTime = MathHelper.clamp(state.cookTime - 2, 0, state.totalCookTime)
		}

		state.saveNBT(stack)

		if (inventoryDirty) {
			inventory.markDirty()
		}
	}

	override fun shouldCauseReequipAnimation(oldStack: ItemStack, newStack: ItemStack, slotChanged: Boolean) = slotChanged || oldStack.item != newStack.item
}