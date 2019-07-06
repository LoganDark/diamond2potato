package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.item.ItemBakedPotatoPickaxe
import net.logandark.diamond2potato.item.ItemPotatoPickaxe
import net.logandark.diamond2potato.util.modid
import net.logandark.diamond2potato.util.replaceDroppedItem
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.item.ItemTossEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object DroppedDiamondToPotato {
	@JvmStatic
	//@SubscribeEvent
	fun onEntityJoinedWorld(event: EntityJoinWorldEvent) {
		if (!event.world.isRemote) {
			if (event.entity is EntityItem) {
				val drop = event.entity as EntityItem

				if (drop.item.item == Diamond2Potato.diamond) {
					replaceDroppedItem(drop, Diamond2Potato.potato)
				}
			}
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun onBlockDrop(event: BlockEvent.HarvestDropsEvent) {
		val player = event.harvester ?: return
		val tool = player.getHeldItem(EnumHand.MAIN_HAND).item

		when (event.state.block) {
			Blocks.DIAMOND_ORE -> {
				if (tool != ItemPotatoPickaxe && tool != ItemBakedPotatoPickaxe) {
					for (index in event.drops.indices) {
						val drop = event.drops[index]

						if (drop.item == Diamond2Potato.diamond) {
							event.drops[index] = ItemStack(Diamond2Potato.potato, drop.count)
						}
					}
				}
			}
		}
	}

	@JvmStatic
	@SubscribeEvent
	fun onBlockDrop(event: ItemTossEvent) {
		val entityItem = event.entityItem

		when (entityItem.item.item) {
			Diamond2Potato.diamond -> {
				if (!entityItem.world.isRemote) {
					replaceDroppedItem(entityItem, Diamond2Potato.potato)
				}
			}
		}
	}
}