package net.logandark.diamond2potato

import net.logandark.diamond2potato.capability.FurnaceCapability
import net.logandark.diamond2potato.gui.GuiHandler
import net.logandark.diamond2potato.util.*
import net.minecraft.init.Items
import net.minecraft.item.ItemFood
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

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

		GuiHandler.register()
		FurnaceCapability.register()
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