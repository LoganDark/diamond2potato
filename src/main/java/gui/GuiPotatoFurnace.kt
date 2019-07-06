package net.logandark.diamond2potato.gui

import net.minecraft.client.gui.inventory.GuiFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory

class GuiPotatoFurnace(
	player: EntityPlayer,
	inventory: IInventory
) : GuiFurnace(player.inventory, inventory)