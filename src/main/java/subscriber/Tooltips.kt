package net.logandark.diamond2potato.subscriber

import net.logandark.diamond2potato.util.modid
import net.minecraft.init.Items
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

@Suppress("unused")
@Mod.EventBusSubscriber(value = [Side.CLIENT], modid = modid)
object Tooltips {
	@JvmStatic
	@SubscribeEvent
	fun onItemTooltip(event: ItemTooltipEvent) {
		when (event.itemStack.item) {
			Items.POTATO       -> {
				event.toolTip[0] = "§d" + event.toolTip[0]
			}
			Items.DIAMOND      -> {
				event.toolTip.add(1, "Drop or smelt for a prize. Go on...")
			}
			Items.BAKED_POTATO -> {
				event.toolTip.add(1, "§4Be very careful around primed potato grenades... or you just might get yourself hurt.")
			}
		}
	}
}