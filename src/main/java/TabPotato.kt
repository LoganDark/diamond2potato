package net.logandark.diamond2potato

import net.logandark.diamond2potato.item.ItemPotatoPickaxe
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object TabPotato : CreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.size, "diamond2potato") {
	override fun createIcon(): ItemStack {
		return ItemStack(ItemPotatoPickaxe)
	}
}