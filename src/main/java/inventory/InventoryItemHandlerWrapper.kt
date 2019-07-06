package net.logandark.diamond2potato.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.items.IItemHandler

open class InventoryItemHandlerWrapper(private val capabilityInstance: IItemHandler, private val displayName: String = "Container") : IInventory {
	override fun clear() {
		for (slot in 0 until capabilityInstance.slots) {
			removeStackFromSlot(slot)
		}
	}

	override fun closeInventory(player: EntityPlayer) {}

	override fun decrStackSize(index: Int, count: Int): ItemStack {
		return capabilityInstance.extractItem(index, count, false)
	}

	override fun getDisplayName(): ITextComponent = TextComponentString(displayName)

	override fun getField(id: Int) = 0

	override fun getFieldCount() = 0

	override fun getInventoryStackLimit() = 64

	override fun getName() = displayName

	override fun getSizeInventory() = capabilityInstance.slots

	override fun getStackInSlot(index: Int): ItemStack = capabilityInstance.getStackInSlot(index).copy()

	override fun hasCustomName() = false

	override fun isEmpty(): Boolean {
		for (slot in 0 until capabilityInstance.slots) {
			if (!capabilityInstance.getStackInSlot(slot).isEmpty) return false
		}

		return true
	}

	override fun isItemValidForSlot(index: Int, stack: ItemStack) = capabilityInstance.isItemValid(index, stack)

	override fun isUsableByPlayer(player: EntityPlayer) = true

	override fun markDirty() {}

	override fun openInventory(player: EntityPlayer) {}

	override fun removeStackFromSlot(index: Int) = capabilityInstance.extractItem(index, capabilityInstance.getSlotLimit(index), false)

	override fun setField(id: Int, value: Int) {}

	override fun setInventorySlotContents(index: Int, stack: ItemStack) {
		removeStackFromSlot(index)
		capabilityInstance.insertItem(index, stack, false)
	}
}