package net.logandark.diamond2potato.slot

import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class SlotItemHandlerFurnaceFuel(itemHandler: IItemHandler, index: Int,
                                 xPosition: Int, yPosition: Int)
	: SlotItemHandler(itemHandler, index, xPosition, yPosition) {
	private fun isBucket(stack: ItemStack) = stack.item === Items.BUCKET

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor
	 * slots as well as furnace fuel.
	 */
	override fun isItemValid(stack: ItemStack) =
		TileEntityFurnace.isItemFuel(stack) || isBucket(stack)

	override fun getItemStackLimit(stack: ItemStack) =
		if (isBucket(stack)) 1
		else super.getItemStackLimit(stack)
}