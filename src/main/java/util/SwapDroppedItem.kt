package net.logandark.diamond2potato.util

import net.minecraft.entity.item.EntityItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

fun replaceEntityItem(old: EntityItem, new: EntityItem) {
	new.setPosition(old.posX, old.posY, old.posZ)
	new.motionX = old.motionX
	new.motionY = old.motionY
	new.motionZ = old.motionZ
	new.setPickupDelay(40)
	old.world.spawnEntity(new)
	old.setDead()
}

fun replaceDroppedItem(old: EntityItem, newItem: Item, transferDamage: Boolean = false, transferNBT: Boolean = false) {
	val oldStack = old.item
	val damage = if (transferDamage) oldStack.itemDamage else 0

	val newStack = ItemStack(newItem, oldStack.count, damage)

	if (transferNBT) newStack.tagCompound = oldStack.tagCompound

	val new = EntityItem(old.world, .0, .0, .0, newStack)

	replaceEntityItem(old, new)
}