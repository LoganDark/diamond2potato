package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.item.ItemPotatoPickaxe
import net.logandark.diamond2potato.item.ItemPotatoShovel
import net.logandark.diamond2potato.util.modid
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Suppress("unused")
@Mod.EventBusSubscriber(modid = modid)
object Tooltips {
	@SideOnly(Side.CLIENT)
	@JvmStatic
	@SubscribeEvent
	fun onItemTooltip(event: ItemTooltipEvent) {
		when (event.itemStack.item) {
			Diamond2Potato.potato -> {
				event.toolTip[0] = "§d" + event.toolTip[0]
			}
			Diamond2Potato.diamond -> {
				event.toolTip.add(1, "Drop or smelt for a prize. Go on...")
			}
			ItemPotatoPickaxe -> {
				event.toolTip.add(1, "Delicious.")
			}
			ItemPotatoShovel -> {
				event.toolTip.add(1, "Just so happens to work very effectively as a shovel...")
			}
			Diamond2Potato.bakedPotato -> {
				event.toolTip.add(1, "§4Be very careful around primed potato grenades... or you just might get yourself hurt.")
			}
		}
	}
}