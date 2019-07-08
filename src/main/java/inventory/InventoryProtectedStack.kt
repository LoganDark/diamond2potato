package net.logandark.diamond2potato.inventory

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

class InventoryProtectedStack(private val playerInventory: InventoryPlayer, val protectedStack: ItemStack) : IInventory {
	override fun clear() = throw NotImplementedError("This would remove the protected item")
	override fun closeInventory(player: EntityPlayer) = playerInventory.closeInventory(player)
	override fun decrStackSize(index: Int, count: Int): ItemStack =
		if (getStackInSlot(index) == protectedStack) ItemStack.EMPTY
		else playerInventory.decrStackSize(index, count)

	override fun getDisplayName(): ITextComponent = playerInventory.displayName
	override fun getField(id: Int) = playerInventory.getField(id)
	override fun getFieldCount() = playerInventory.fieldCount
	override fun getInventoryStackLimit() = playerInventory.inventoryStackLimit
	override fun getName(): String = playerInventory.name
	override fun getSizeInventory() = playerInventory.sizeInventory
	override fun getStackInSlot(index: Int): ItemStack = playerInventory.getStackInSlot(index)
	override fun hasCustomName() = playerInventory.hasCustomName()
	override fun isEmpty() = playerInventory.isEmpty
	override fun isItemValidForSlot(index: Int, stack: ItemStack) = when (protectedStack) {
		stack                 -> false
		getStackInSlot(index) -> stack == protectedStack
		else                  -> playerInventory.isItemValidForSlot(index, stack)
	}

	override fun isUsableByPlayer(player: EntityPlayer) = playerInventory.isUsableByPlayer(player)
	override fun markDirty() = playerInventory.markDirty()
	override fun openInventory(player: EntityPlayer) = playerInventory.openInventory(player)
	override fun removeStackFromSlot(index: Int): ItemStack =
		if (getStackInSlot(index) == protectedStack) ItemStack.EMPTY
		else playerInventory.removeStackFromSlot(index)

	override fun setField(id: Int, value: Int) = playerInventory.setField(id, value)
	override fun setInventorySlotContents(index: Int, stack: ItemStack) {
		if (stack != protectedStack && getStackInSlot(index) != protectedStack) {
			playerInventory.setInventorySlotContents(index, stack)
		}
	}

	override fun equals(other: Any?): Boolean = playerInventory == other
	override fun hashCode(): Int = playerInventory.hashCode()
	override fun toString(): String = playerInventory.toString()
}