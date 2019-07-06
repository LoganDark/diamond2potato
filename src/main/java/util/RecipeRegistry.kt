package net.logandark.diamond2potato.util

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.item.*
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry

fun registerRecipes() {
	GameRegistry.addSmelting(Diamond2Potato.diamond, ItemStack(Diamond2Potato.potato), 0F)

	GameRegistry.addShapedRecipe(
		ItemPotatoPickaxe.registryName,
		ItemPotatoPickaxe.registryName,
		ItemStack(ItemPotatoPickaxe),
		"PPP",
		" S ",
		" S ",
		'P', Diamond2Potato.potato,
		'S', Items.STICK
	)

	GameRegistry.addShapedRecipe(
		ItemBakedPotatoPickaxe.registryName,
		ItemBakedPotatoPickaxe.registryName,
		ItemStack(ItemBakedPotatoPickaxe),
		"PPP",
		" S ",
		" S ",
		'P', Diamond2Potato.bakedPotato,
		'S', Items.STICK
	)

	GameRegistry.addSmelting(ItemPotatoPickaxe, ItemStack(ItemBakedPotatoPickaxe), 0F)

	GameRegistry.addShapedRecipe(
		ItemPotatoShovel.registryName,
		ItemPotatoShovel.registryName,
		ItemStack(ItemPotatoShovel),
		" P ",
		" S ",
		" S ",
		'P', Diamond2Potato.potato,
		'S', Items.STICK
	)

	GameRegistry.addShapedRecipe(
		ItemPotatoHoe.registryName,
		ItemPotatoHoe.registryName,
		ItemStack(ItemPotatoHoe),
		"PP ",
		" S ",
		" S ",
		'P', Diamond2Potato.potato,
		'S', Items.STICK
	)

	GameRegistry.addSmelting(ItemFrenchFry, ItemStack(ItemBakedFrenchFry), 0F)

	GameRegistry.addShapedRecipe(
		ItemFrenchFry.registryName,
		ItemFrenchFry.registryName,
		ItemStack(ItemFrenchFry),
		"PPP",
		'P', Diamond2Potato.potato
	)

	GameRegistry.addShapedRecipe(
		ItemFrenchFryHilt.registryName,
		ItemFrenchFryHilt.registryName,
		ItemStack(ItemFrenchFryHilt),
		"FSF",
		" S ",
		'F', ItemFrenchFry,
		'S', Items.STICK
	)

	GameRegistry.addSmelting(ItemFrenchFryHilt, ItemStack(ItemBakedFrenchFryHilt), 0F)
}