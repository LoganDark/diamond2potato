package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.util.modid
import net.minecraft.block.Block
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object DestroyPotatoPlants {
	@JvmStatic
	@SubscribeEvent
	fun onBlockPlaced(event: BlockEvent.PlaceEvent) {
		if (event.placedBlock.block == Block.getBlockFromName("minecraft:potatoes")) {
			event.world.setBlockToAir(event.pos)
			event.world.createExplosion(null, event.pos.x.toDouble(), event.pos.y.toDouble(), event.pos.z.toDouble(), 5F, true)
		}
	}
}