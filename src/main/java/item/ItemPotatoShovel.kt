package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.`class`.ItemSpadeFood
import net.logandark.diamond2potato.material.PotatoMaterial
import net.logandark.diamond2potato.util.modid
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ItemPotatoShovel : ItemSpadeFood(
	PotatoMaterial,
	Diamond2Potato.potatoFood.getHealAmount(ItemStack(Diamond2Potato.potatoFood)),
	Diamond2Potato.potatoFood.getSaturationModifier(ItemStack(Diamond2Potato.potatoFood)),
	false
) {
	init {
		translationKey = "$modid.potato_shovel"
		registryName = ResourceLocation(modid, "potato_shovel")
		creativeTab = TabPotato
		setAlwaysEdible()
	}

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		this.replacementItemStack = ItemStack(Items.STICK)

		return super.onItemUseFinish(stack, worldIn, entityLiving)
	}
}