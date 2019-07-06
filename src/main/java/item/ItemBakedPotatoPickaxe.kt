package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.PotatoMaterial
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.`class`.ItemPickaxeFood
import net.logandark.diamond2potato.util.modid
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ItemBakedPotatoPickaxe : ItemPickaxeFood(
	PotatoMaterial,
	Diamond2Potato.bakedPotatoFood.getHealAmount(ItemStack(Diamond2Potato.bakedPotatoFood)) * 3,
	Diamond2Potato.bakedPotatoFood.getSaturationModifier(ItemStack(Diamond2Potato.bakedPotatoFood)) * 3,
	false
) {
	init {
		translationKey = "$modid.baked_potato_pickaxe"
		registryName = ResourceLocation(modid, "baked_potato_pickaxe")
		creativeTab = TabPotato
		setAlwaysEdible()
	}

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		this.replacementItemStack = ItemStack(Items.STICK)

		return super.onItemUseFinish(stack, worldIn, entityLiving)
	}
}