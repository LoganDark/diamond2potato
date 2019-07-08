package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.inventory.InventoryProtectedStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

class GuiPotatoFurnace(
	player: EntityPlayer,
	inventory: IInventory,
	protectedStack: ItemStack
) : GuiFurnaceIInventory(player, InventoryProtectedStack(player.inventory, protectedStack), inventory)