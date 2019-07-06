package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.`class`.PotatoFurnaceState
import net.logandark.diamond2potato.container.ContainerPotatoFurnace
import net.logandark.diamond2potato.inventory.InventoryPotatoFurnace
import net.logandark.diamond2potato.item.ItemPotatoFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object GuiHandler : IGuiHandler {
	enum class GUIs {
		PotatoFurnace
	}

	override fun getClientGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == GUIs.PotatoFurnace.ordinal) {
			val heldItemStack = player!!.getHeldItem(EnumHand.MAIN_HAND)

			if (heldItemStack.item is ItemPotatoFurnace) {
				val inventory = InventoryPotatoFurnace(heldItemStack)

				return GuiPotatoFurnace(player, inventory)
			}
		}

		return null
	}

	override fun getServerGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == GUIs.PotatoFurnace.ordinal) {
			val heldItemStack = player!!.getHeldItem(EnumHand.MAIN_HAND)

			if (heldItemStack.item is ItemPotatoFurnace) {
				val inventory = InventoryPotatoFurnace(heldItemStack)

				return ContainerPotatoFurnace(player, inventory)
			}
		}

		return null
	}
}