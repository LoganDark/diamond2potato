package net.logandark.diamond2potato.`interface`

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

interface IFurnaceCapability : IItemHandler {
	var furnaceBurnTime: Int
	var currentItemBurnTime: Int
	var cookTime: Int
	var totalCookTime: Int

	/**
	 * Returns true if the furnace is currently burning an item
	 */
	fun isBurning(): Boolean

	/**
	 * Returns true if the furnace can currently smelt an item
	 */
	fun canSmelt(): Boolean

	/**
	 * Gets how long it will take to cook the item in this stack
	 */
	fun getCookTime(stack: ItemStack): Int

	/**
	 * Smelt the current item right now
	 */
	fun smeltItem()

	/**
	 * Should be called every tick. Updates all the state
	 */
	fun update()
}