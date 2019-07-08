package net.logandark.diamond2potato.container

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory

class ContainerPotatoFurnace(
	private val player: EntityPlayer,
	playerInventory: IInventory,
	furnace: IFurnaceCapability
) : ContainerFurnaceCapability(player, playerInventory, furnace) {
	override fun canInteractWith(playerIn: EntityPlayer) = playerIn == player
}