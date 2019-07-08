package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.`class`.ItemPickaxeFood
import net.logandark.diamond2potato.material.PotatoMaterial
import net.logandark.diamond2potato.util.modid
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object ItemPotatoPickaxe : ItemPickaxeFood(
	PotatoMaterial,
	Diamond2Potato.potatoFood.getHealAmount(ItemStack(Diamond2Potato.potatoFood)) * 3,
	Diamond2Potato.potatoFood.getSaturationModifier(ItemStack(Diamond2Potato.potatoFood)) * 3,
	false
) {
	init {
		translationKey = "$modid.potato_pickaxe"
		registryName = ResourceLocation(modid, "potato_pickaxe")
		creativeTab = TabPotato
		setAlwaysEdible()
	}

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		this.replacementItemStack = ItemStack(Items.STICK)

		return super.onItemUseFinish(stack, worldIn, entityLiving)
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
		super.addInformation(stack, worldIn, tooltip, flagIn)

		tooltip.add(1, "Delicious.")
	}
}