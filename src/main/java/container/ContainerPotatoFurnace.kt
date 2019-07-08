package net.logandark.diamond2potato.container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory

class ContainerPotatoFurnace(
	private val player: EntityPlayer,
	playerInventory: IInventory,
	furnaceInventory: IInventory
) : ContainerFurnaceIInventory(player, playerInventory, furnaceInventory) {
	override fun canInteractWith(playerIn: EntityPlayer) = playerIn == player
}