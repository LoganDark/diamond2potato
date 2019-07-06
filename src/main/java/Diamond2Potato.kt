package net.logandark.diamond2potato

import net.logandark.diamond2potato.gui.GuiHandler
import net.logandark.diamond2potato.util.*
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemFood
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry

@Mod(
	modid = modid,
	name = name,
	version = version,
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	dependencies = "required-after: forgelin"
)
@Mod.EventBusSubscriber()
object Diamond2Potato {
	val potatoFood: ItemFood = Items.POTATO as ItemFood
	val bakedPotatoFood: ItemFood = Items.BAKED_POTATO as ItemFood

	@Suppress("unused")
	@JvmStatic
	@Mod.EventHandler
	fun onPreInitializationStep(event: FMLPreInitializationEvent) {
		logger = event.modLog

		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandler)
	}

	@Suppress("unused")
	@JvmStatic
	@Mod.EventHandler
	fun onInitializationStep(
		@Suppress("unused_parameter")
		event: FMLInitializationEvent
	) {
		registerRecipes()
	}
}