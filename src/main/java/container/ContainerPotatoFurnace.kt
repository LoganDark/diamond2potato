package net.logandark.diamond2potato.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ContainerFurnace
import net.minecraft.inventory.IInventory

class ContainerPotatoFurnace(
	private val player: EntityPlayer,
	inventory: IInventory
) : ContainerFurnace(player.inventory, inventory) {
	override fun canInteractWith(playerIn: EntityPlayer) = playerIn == player
}