package net.logandark.diamond2potato.container

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.slot.SlotItemHandlerFurnaceFuel
import net.logandark.diamond2potato.slot.SlotItemHandlerFurnaceOutput
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.items.SlotItemHandler

open class ContainerFurnaceCapability(
	private val player: EntityPlayer,
	playerInventory: IInventory,
	private val furnace: IFurnaceCapability
) : Container() {
	var cookTime: Int = 0
	var totalCookTime: Int = 0
	var furnaceBurnTime: Int = 0
	var currentItemBurnTime: Int = 0

	init {
		addSlotToContainer(SlotItemHandler(furnace, 0, 56, 17))
		addSlotToContainer(SlotItemHandlerFurnaceFuel(furnace, 1, 56, 53))
		addSlotToContainer(SlotItemHandlerFurnaceOutput(player, furnace, 2, 116, 35))

		for (i in 0..2) {
			for (j in 0..8) {
				addSlotToContainer(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
			}
		}

		for (k in 0..8) {
			addSlotToContainer(Slot(playerInventory, k, 8 + k * 18, 142))
		}
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	override fun detectAndSendChanges() {
		super.detectAndSendChanges()

		for (i in listeners.indices) {
			val containerListener = listeners[i]

			if (cookTime != furnace.cookTime) {
				containerListener.sendWindowProperty(this, 2, furnace.cookTime)
			}

			if (furnaceBurnTime != furnace.furnaceBurnTime) {
				containerListener.sendWindowProperty(this, 0, furnace.furnaceBurnTime)
			}

			if (currentItemBurnTime != furnace.currentItemBurnTime) {
				containerListener.sendWindowProperty(this, 1, furnace.currentItemBurnTime)
			}

			if (totalCookTime != furnace.totalCookTime) {
				containerListener.sendWindowProperty(this, 3, furnace.totalCookTime)
			}
		}

		cookTime = furnace.cookTime
		furnaceBurnTime = furnace.furnaceBurnTime
		currentItemBurnTime = furnace.currentItemBurnTime
		totalCookTime = furnace.totalCookTime
	}

	@SideOnly(Side.CLIENT)
	override fun updateProgressBar(id: Int, data: Int) {
		when (id) {
			2 -> furnace.cookTime = data
			0 -> furnace.furnaceBurnTime = data
			1 -> furnace.currentItemBurnTime = data
			3 -> furnace.totalCookTime = data
		}
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	override fun canInteractWith(playerIn: EntityPlayer) = playerIn == player

	/**
	 * Handle when the stack in slot `index` is shift-clicked. Normally this
	 * moves the stack between the player inventory and the other inventory(s).
	 */
	override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
		var currentStack = ItemStack.EMPTY
		val slot = inventorySlots[index]

		if (slot != null && slot.hasStack) {
			val stackInSlot = slot.stack
			currentStack = stackInSlot.copy()

			if (index == 2) {
				if (!mergeItemStack(stackInSlot, 3, 39, true)) {
					return ItemStack.EMPTY
				}

				slot.onSlotChange(stackInSlot, currentStack)
			} else if (index > 2) {
				if (!FurnaceRecipes.instance().getSmeltingResult(stackInSlot).isEmpty) {
					if (!mergeItemStack(stackInSlot, 0, 1, false)) {
						return ItemStack.EMPTY
					}
				} else if (TileEntityFurnace.isItemFuel(stackInSlot)) {
					if (!mergeItemStack(stackInSlot, 1, 2, false)) {
						return ItemStack.EMPTY
					}
				} else if (index in 3..29) {
					if (!mergeItemStack(stackInSlot, 30, 39, false)) {
						return ItemStack.EMPTY
					}
				} else if (index in 30..38 && !mergeItemStack(stackInSlot, 3, 30, false)) {
					return ItemStack.EMPTY
				}
			} else if (!mergeItemStack(stackInSlot, 3, 39, false)) {
				return ItemStack.EMPTY
			}

			if (stackInSlot.isEmpty) {
				slot.putStack(ItemStack.EMPTY)
			} else {
				slot.onSlotChanged()
			}

			if (stackInSlot.count == currentStack.count) {
				return ItemStack.EMPTY
			}

			slot.onTake(playerIn, stackInSlot)
		}

		return currentStack
	}
}