package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.container.ContainerPotatoFurnace
import net.logandark.diamond2potato.inventory.InventoryProtectedStack
import net.logandark.diamond2potato.item.ItemPotatoFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.common.network.NetworkRegistry

object GuiHandler : IGuiHandler {
	@JvmStatic
	@CapabilityInject(IFurnaceCapability::class)
	lateinit var FURNACE_CAPABILITY: Capability<IFurnaceCapability>

	enum class GUIs {
		PotatoFurnace
	}

	override fun getClientGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == GUIs.PotatoFurnace.ordinal) {
			val heldItemStack = player!!.getHeldItem(EnumHand.MAIN_HAND)

			if (heldItemStack.item is ItemPotatoFurnace) {
				val furnace = heldItemStack.getCapability(FURNACE_CAPABILITY, null)!!

				return GuiPotatoFurnace(player, furnace, heldItemStack)
			}
		}

		return null
	}

	override fun getServerGuiElement(ID: Int, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == GUIs.PotatoFurnace.ordinal) {
			val heldItemStack = player!!.getHeldItem(EnumHand.MAIN_HAND)

			if (heldItemStack.item is ItemPotatoFurnace) {
				val furnace = heldItemStack.getCapability(FURNACE_CAPABILITY, null)!!
				val playerInventory = InventoryProtectedStack(player.inventory, heldItemStack)

				return ContainerPotatoFurnace(player, playerInventory, furnace)
			}
		}

		return null
	}

	fun register() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Diamond2Potato, this)
	}
}