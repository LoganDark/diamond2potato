package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.inventory.InventoryProtectedStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class GuiPotatoFurnace(
	player: EntityPlayer,
	furnace: IFurnaceCapability,
	protectedStack: ItemStack,
	containerName: String = "Potato Furnace"
) : GuiFurnaceCapability(player, InventoryProtectedStack(player.inventory, protectedStack), furnace, containerName)