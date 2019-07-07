package net.logandark.diamond2potato.capability

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntityFurnace.getItemBurnTime
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.MathHelper
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.items.ItemStackHandler

class FurnaceCapability : ItemStackHandler(3), IFurnaceCapability, Capability.IStorage<IFurnaceCapability> {
	companion object {
		fun register() {
			CapabilityManager.INSTANCE.register(IFurnaceCapability::class.java, FurnaceCapability()) { FurnaceCapability() }
		}
	}

	override var furnaceBurnTime = 0
	override var currentItemBurnTime = 0
	override var cookTime = 0
	override var totalCookTime = 0

	override fun readNBT(capability: Capability<IFurnaceCapability>, instance: IFurnaceCapability, side: EnumFacing, nbt: NBTBase) {
		if (nbt is NBTTagCompound) {
			instance.deserializeNBT(nbt)
			instance.furnaceBurnTime = nbt.getInteger("BurnTime")
			instance.currentItemBurnTime = nbt.getInteger("ItemBurnTime")
			instance.cookTime = nbt.getInteger("CookTime")
			instance.totalCookTime = nbt.getInteger("CookTimeTotal")
		}
	}

	override fun writeNBT(capability: Capability<IFurnaceCapability>, instance: IFurnaceCapability, side: EnumFacing): NBTBase {
		val tagCompound = instance.serializeNBT()
		tagCompound.setInteger("BurnTime", instance.furnaceBurnTime)
		tagCompound.setInteger("ItemBurnTime", instance.currentItemBurnTime)
		tagCompound.setInteger("CookTime", instance.cookTime)
		tagCompound.setInteger("CookTimeTotal", instance.totalCookTime)

		return tagCompound
	}

	override fun isBurning() = furnaceBurnTime > 0

	/**
	 * Returns true if this furnace can smelt an item
	 */
	override fun canSmelt(): Boolean {
		if (getStackInSlot(0).isEmpty) {
			return false
		} else {
			val resultStack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0))
			val resultStackLimit = getStackLimit(2, resultStack)

			return if (resultStack.isEmpty) {
				false
			} else {
				val currentResultStack = getStackInSlot(2)

				if (currentResultStack.isEmpty) {
					true
				} else if (!currentResultStack.isItemEqual(resultStack)) {
					false
				} else {
					currentResultStack.count + resultStack.count <= resultStackLimit
				}
			}
		}
	}

	/**
	 * Gets how long it will take to cook an item
	 */
	override fun getCookTime(stack: ItemStack) = 200

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
	 */
	override fun smeltItem() {
		if (this.canSmelt()) {
			val itemStack = getStackInSlot(0)
			val newResultStack = FurnaceRecipes.instance().getSmeltingResult(itemStack)
			val currentResultStack = getStackInSlot(2)

			if (currentResultStack.isEmpty) {
				setStackInSlot(2, newResultStack.copy())
			} else if (currentResultStack.item === newResultStack.item) {
				currentResultStack.grow(newResultStack.count)
			}

			if (itemStack.item === Item.getItemFromBlock(Blocks.SPONGE) && itemStack.metadata == 1 && !getStackInSlot(1).isEmpty && getStackInSlot(1).item == Items.BUCKET) {
				setStackInSlot(1, ItemStack(Items.WATER_BUCKET))
			}

			itemStack.shrink(1)
		}
	}

	override fun update(isRemote: Boolean) {
		if (isRemote) return

		if (isBurning()) {
			furnaceBurnTime--
		}

		val itemstack = getStackInSlot(1)

		if (isBurning() || !itemstack.isEmpty && !getStackInSlot(0).isEmpty) {
			if (!isBurning() && canSmelt()) {
				furnaceBurnTime = getItemBurnTime(itemstack)
				currentItemBurnTime = furnaceBurnTime

				if (isBurning()) {
					if (!itemstack.isEmpty) {
						val item = itemstack.item
						itemstack.shrink(1)

						if (itemstack.isEmpty) {
							val item1 = item.getContainerItem(itemstack)
							setStackInSlot(1, item1)
						}
					}
				}
			}

			if (isBurning() && canSmelt()) {
				cookTime++

				if (cookTime == totalCookTime) {
					cookTime = 0
					totalCookTime = getCookTime(getStackInSlot(0))
					smeltItem()
				}
			} else {
				cookTime = 0
			}
		} else if (!isBurning() && cookTime > 0) {
			cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime)
		}
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack = if (slot != 1) {
		super.insertItem(slot, stack, simulate)
	} else {
		val oldStack = getStackInSlot(slot)
		val remainder = super.insertItem(slot, stack, simulate)
		val newStack = getStackInSlot(slot)

		if (newStack.isEmpty || !newStack.isItemEqual(oldStack) || !ItemStack.areItemStackTagsEqual(newStack, oldStack)) {
			totalCookTime = getCookTime(newStack)
			// cookTime = 0
		}

		remainder
	}
}