package net.logandark.diamond2potato

import net.logandark.diamond2potato.capability.FurnaceCapability
import net.logandark.diamond2potato.gui.GuiHandler
import net.logandark.diamond2potato.registry.CommandRegistry
import net.logandark.diamond2potato.registry.SmeltingRegistry
import net.logandark.diamond2potato.util.logger
import net.logandark.diamond2potato.util.modid
import net.logandark.diamond2potato.util.name
import net.logandark.diamond2potato.util.version
import net.minecraft.command.CommandHandler
import net.minecraft.crash.CrashReport
import net.minecraft.init.Items
import net.minecraft.item.ItemFood
import net.minecraft.util.ReportedException
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent

@Mod(
	modid = modid,
	name = name,
	version = version,
	acceptedMinecraftVersions = "[1.12.2]",
	modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
	dependencies = "required:forge@[14.23.5.2768,);required-after:forgelin"
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
	fun onServerStarting(event: FMLServerStartingEvent) {
		val commandManager = event.server.getCommandManager()

		if (commandManager is CommandHandler) {
			CommandRegistry.registerCommands(commandManager)
		} else {
			throw ReportedException(CrashReport.makeCrashReport(ClassCastException(), "Command manager isn't CommandHandler (can't register commands)"))
		}
	}

	@Suppress("unused")
	@JvmStatic
	@Mod.EventHandler
	fun onInitializationStep(
		@Suppress("unused_parameter")
		event: FMLInitializationEvent
	) {
		SmeltingRegistry.registerSmelting()
	}
}