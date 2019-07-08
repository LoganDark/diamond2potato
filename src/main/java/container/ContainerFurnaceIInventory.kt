package net.logandark.diamond2potato.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.*
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class ContainerFurnaceIInventory(player: EntityPlayer, private val playerInventory: IInventory, furnaceInventory: IInventory) : Container() {
	private var tileFurnace: IInventory = furnaceInventory
	private var cookTime: Int = 0
	private var totalCookTime: Int = 0
	private var furnaceBurnTime: Int = 0
	private var currentItemBurnTime: Int = 0

	init {
		addSlotToContainer(Slot(furnaceInventory, 0, 56, 17))
		addSlotToContainer(SlotFurnaceFuel(furnaceInventory, 1, 56, 53))
		addSlotToContainer(SlotFurnaceOutput(player, furnaceInventory, 2, 116, 35))

		for (i in 0..2) {
			for (j in 0..8) {
				addSlotToContainer(Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
			}
		}

		for (k in 0..8) {
			addSlotToContainer(Slot(playerInventory, k, 8 + k * 18, 142))
		}
	}

	override fun addListener(listener: IContainerListener) {
		super.addListener(listener)
		listener.sendAllWindowProperties(this, tileFurnace)
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	override fun detectAndSendChanges() {
		super.detectAndSendChanges()

		for (i in listeners.indices) {
			val containerListener = listeners[i]

			if (cookTime != tileFurnace.getField(2)) {
				containerListener.sendWindowProperty(this, 2, tileFurnace.getField(2))
			}

			if (furnaceBurnTime != tileFurnace.getField(0)) {
				containerListener.sendWindowProperty(this, 0, tileFurnace.getField(0))
			}

			if (currentItemBurnTime != tileFurnace.getField(1)) {
				containerListener.sendWindowProperty(this, 1, tileFurnace.getField(1))
			}

			if (totalCookTime != tileFurnace.getField(3)) {
				containerListener.sendWindowProperty(this, 3, tileFurnace.getField(3))
			}
		}

		cookTime = tileFurnace.getField(2)
		furnaceBurnTime = tileFurnace.getField(0)
		currentItemBurnTime = tileFurnace.getField(1)
		totalCookTime = tileFurnace.getField(3)
	}

	@SideOnly(Side.CLIENT)
	override fun updateProgressBar(id: Int, data: Int) {
		tileFurnace.setField(id, data)
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	override fun canInteractWith(playerIn: EntityPlayer): Boolean {
		return tileFurnace.isUsableByPlayer(playerIn)
	}

	/**
	 * Handle when the stack in slot `index` is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
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