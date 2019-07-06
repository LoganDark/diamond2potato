package net.logandark.diamond2potato.util

import net.minecraft.entity.item.EntityItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

fun replaceEntityItem(old: EntityItem, new: EntityItem) {
	new.setPosition(old.posX, old.posY, old.posZ)
	new.setVelocity(old.motionX, old.motionY, old.motionZ)
	new.setPickupDelay(40)
	old.world.spawnEntity(new)
	old.setDead()
}

fun replaceDroppedItem(old: EntityItem, newItem: Item, transferDamage: Boolean = false, transferNBT: Boolean = false) {
	val oldStack = old.item

	val damage = when (transferDamage) {
		true -> oldStack.itemDamage
		false -> 0
	}

	val nbt = when (transferNBT) {
		true -> oldStack.tagCompound
		false -> NBTTagCompound()
	}

	val newStack = ItemStack(newItem, oldStack.count, damage, nbt)
	val new = EntityItem(old.world, .0, .0, .0, newStack)

	replaceEntityItem(old, new)
}