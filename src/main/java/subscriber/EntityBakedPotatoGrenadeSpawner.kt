package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.entity.EntityBakedPotatoGrenade
import net.logandark.diamond2potato.util.modid
import net.logandark.diamond2potato.util.replaceEntityItem
import net.minecraft.init.Items
import net.minecraftforge.event.entity.item.ItemTossEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object EntityBakedPotatoGrenadeSpawner {
	@JvmStatic
	@SubscribeEvent
	fun onItemToss(event: ItemTossEvent) {
		val item = event.entityItem
		val world = item.world

		if (!world.isRemote) {
			if (item !is EntityBakedPotatoGrenade && item.item.item == Items.BAKED_POTATO) {
				val bakedPotatoGrenade = EntityBakedPotatoGrenade(item)

				replaceEntityItem(item, bakedPotatoGrenade)

				val velocityMultiplier = 1.0 + (bakedPotatoGrenade.item.count.toDouble() / 32)
				bakedPotatoGrenade.motionX *= velocityMultiplier
				bakedPotatoGrenade.motionY *= velocityMultiplier
				bakedPotatoGrenade.motionZ *= velocityMultiplier
			}
		}
	}
}