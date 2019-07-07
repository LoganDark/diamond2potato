package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.entity.EntityBakedPotatoGrenade
import net.logandark.diamond2potato.util.modid
import net.logandark.diamond2potato.util.replaceEntityItem
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Items
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object EntityBakedPotatoGrenadeSpawner {
	@JvmStatic
	@SubscribeEvent
	fun onEntityJoinedWorld(event: EntityJoinWorldEvent) {
		if (!event.world.isRemote) {
			val entity = event.entity

			if (entity is EntityItem) {
				if (entity !is EntityBakedPotatoGrenade && entity.item.item == Items.BAKED_POTATO) {
					val bakedPotatoGrenade = EntityBakedPotatoGrenade(entity)

					replaceEntityItem(entity, bakedPotatoGrenade)

					val velocityMultiplier = 1.0 + (bakedPotatoGrenade.item.count.toDouble() / 32)

					bakedPotatoGrenade.setVelocity(
						bakedPotatoGrenade.motionX * velocityMultiplier,
						bakedPotatoGrenade.motionY * velocityMultiplier,
						bakedPotatoGrenade.motionZ * velocityMultiplier
					)
				}
			}
		}
	}
}