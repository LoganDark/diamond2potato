package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.util.modid
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

object ItemBakedFrenchFry : ItemFood(
	Diamond2Potato.bakedPotatoFood.getHealAmount(ItemStack(Diamond2Potato.bakedPotatoFood)) * 3,
	Diamond2Potato.bakedPotatoFood.getSaturationModifier(ItemStack(Diamond2Potato.bakedPotatoFood)) * 3,
	false
) {
	init {
		translationKey = "$modid.baked_french_fry"
		registryName = ResourceLocation(modid, "baked_french_fry")
		creativeTab = TabPotato
	}
}