package net.logandark.diamond2potato.container

import net.logandark.diamond2potato.util.logger
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ContainerFurnace
import net.minecraft.inventory.IInventory

class ContainerPotatoFurnace(
	private val player: EntityPlayer,
	inventory: IInventory
) : ContainerFurnace(player.inventory, inventory) {
	override fun canInteractWith(playerIn: EntityPlayer) = playerIn == player

	override fun onContainerClosed(playerIn: EntityPlayer) {}

	override fun detectAndSendChanges() {
		super.detectAndSendChanges()

		if (player.world.isRemote) {
			logger!!.info("Player: ${player.name}")
			logger!!.info("Detected and saved changes!!")
			logger!!.info("World is remote: ${player.world.isRemote}")
		}
	}
}