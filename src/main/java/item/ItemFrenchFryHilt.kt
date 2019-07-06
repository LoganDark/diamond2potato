package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.util.modid
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object ItemFrenchFryHilt : Item() {
	init {
		translationKey = "$modid.french_fry_hilt"
		registryName = ResourceLocation(modid, "french_fry_hilt")
		creativeTab = TabPotato
	}
}