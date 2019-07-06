package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.item.*
import net.logandark.diamond2potato.util.modid
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object RegisterItems {
	private val items = arrayListOf(
		ItemPotatoPickaxe,
		ItemBakedPotatoPickaxe,
		ItemPotatoShovel,
		ItemPotatoHoe,
		ItemFrenchFry,
		ItemBakedFrenchFry,
		ItemFrenchFryHilt,
		ItemBakedFrenchFryHilt,
		ItemChaosFragment,
		ItemPotatoFurnace
	)

	@JvmStatic
	@SubscribeEvent
	fun registerItems(
		@Suppress("unused_parameter")
		event: RegistryEvent.Register<Item>
	) {
		for (item in items) {
			event.registry.register(item)
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun registerRenders(
		@Suppress("unused_parameter")
		event: ModelRegistryEvent
	) {
		for (item in items) {
			val resourceLocation = item.registryName!!
			val modelResourceLocation = ModelResourceLocation(resourceLocation, "inventory")

			ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation)
		}
	}
}