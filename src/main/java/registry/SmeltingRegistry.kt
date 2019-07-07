package net.logandark.diamond2potato.registry

import net.logandark.diamond2potato.item.*
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

object SmeltingRegistry {
	fun registerSmelting() {
		GameRegistry.addSmelting(Items.DIAMOND, ItemStack(Items.POTATO), 0F)
		GameRegistry.addSmelting(ItemPotatoPickaxe, ItemStack(ItemBakedPotatoPickaxe), 0F)
		GameRegistry.addSmelting(ItemFrenchFry, ItemStack(ItemBakedFrenchFry), 0F)
		GameRegistry.addSmelting(ItemFrenchFryHilt, ItemStack(ItemBakedFrenchFryHilt), 0F)
	}
}