package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.util.modid
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

object ItemFrenchFry : ItemFood(
	Diamond2Potato.potatoFood.getHealAmount(ItemStack(Diamond2Potato.potatoFood)) * 3,
	Diamond2Potato.potatoFood.getSaturationModifier(ItemStack(Diamond2Potato.potatoFood)) * 3,
	false
) {
	init {
		translationKey = "$modid.french_fry"
		registryName = ResourceLocation(modid, "french_fry")
		creativeTab = TabPotato
	}
}