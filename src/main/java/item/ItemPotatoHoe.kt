package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.PotatoMaterial
import net.logandark.diamond2potato.TabPotato
import net.logandark.diamond2potato.`class`.ItemHoeFood
import net.logandark.diamond2potato.util.modid
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object ItemPotatoHoe : ItemHoeFood(
	PotatoMaterial,
	Diamond2Potato.potato.getHealAmount(ItemStack(Diamond2Potato.potato)) * 2,
	Diamond2Potato.potato.getSaturationModifier(ItemStack(Diamond2Potato.potato)) * 2,
	false
) {
	init {
		translationKey = "$modid.potato_hoe"
		registryName = ResourceLocation(modid, "potato_hoe")
		creativeTab = TabPotato
		setAlwaysEdible()
	}

	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		this.replacementItemStack = ItemStack(Diamond2Potato.stick)

		return super.onItemUseFinish(stack, worldIn, entityLiving)
	}

	override fun onItemUse(player: EntityPlayer?, worldIn: World?, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
		if (super.onItemUse(player!!, worldIn!!, pos!!, hand!!, facing!!, hitX, hitY, hitZ) != EnumActionResult.SUCCESS) {
			return EnumActionResult.FAIL
		}

		if (!player.isCreative) {
			player.setHeldItem(hand, ItemStack.EMPTY)
		}

		if (!worldIn.isRemote) {
			worldIn.createExplosion(player, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 5F, true)
		}

		return EnumActionResult.SUCCESS
	}
}