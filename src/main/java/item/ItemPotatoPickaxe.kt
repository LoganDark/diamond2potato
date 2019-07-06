package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.BakedPotatoMaterial
import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.`class`.ItemPickaxeFood
import net.logandark.diamond2potato.util.modid
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ItemPotatoPickaxe : ItemPickaxeFood(
	BakedPotatoMaterial,
	Diamond2Potato.potato.getHealAmount(ItemStack(Diamond2Potato.potato)) * 3,
	Diamond2Potato.potato.getSaturationModifier(ItemStack(Diamond2Potato.potato)) * 3,
	false
) {
	init {
		translationKey = "potato_pickaxe"
		registryName = ResourceLocation(modid, "potato_pickaxe")
		creativeTab = TabPotato
		setAlwaysEdible()
	}

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		this.replacementItemStack = ItemStack(Diamond2Potato.stick)

		return super.onItemUseFinish(stack, worldIn, entityLiving)
	}
}