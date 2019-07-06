package net.logandark.diamond2potato.`class`

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class PotatoFurnaceState(itemStack: ItemStack) {
	private val nbtKey = "PotatoFurnaceState"

	var furnaceBurnTime = 0
	var currentItemBurnTime = 0
	var cookTime = 0
	var totalCookTime = 0

	init {
		val tagCompound = itemStack.tagCompound

		if (tagCompound is NBTTagCompound) {
			val potatoFurnaceState = tagCompound.getCompoundTag(nbtKey)

			if (potatoFurnaceState is NBTTagCompound) {
				furnaceBurnTime = potatoFurnaceState.getInteger("furnaceBurnTime")
				currentItemBurnTime = potatoFurnaceState.getInteger("currentItemBurnTime")
				cookTime = potatoFurnaceState.getInteger("cookTime")
				totalCookTime = potatoFurnaceState.getInteger("totalCookTime")
			}
		}
	}

	fun saveNBT(stack: ItemStack) {
		var tagCompound = stack.tagCompound

		if (tagCompound !is NBTTagCompound) {
			tagCompound = NBTTagCompound()
			stack.tagCompound = tagCompound
		}

		val potatoFurnaceState = tagCompound.getCompoundTag(nbtKey)

		potatoFurnaceState.setInteger("furnaceBurnTime", furnaceBurnTime)
		potatoFurnaceState.setInteger("currentItemBurnTime", currentItemBurnTime)
		potatoFurnaceState.setInteger("cookTime", cookTime)
		potatoFurnaceState.setInteger("totalCookTime", totalCookTime)

		tagCompound.setTag(nbtKey, potatoFurnaceState)
	}
}