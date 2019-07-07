package net.logandark.diamond2potato.`class`

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.SoundEvents
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.world.World

open class ItemPickaxeFood(
	toolMaterial: ToolMaterial,
	/** The amount this food item heals the player.  */
	amount: Int,
	saturation: Float,
	/** Whether wolves like this food (true for raw and cooked porkchop).  */
	isWolfFood: Boolean
) : ItemPickaxe(toolMaterial) {
	private val actualFood = ItemFood(amount, saturation, isWolfFood)
	var replacementItemStack: ItemStack? = null

	@Suppress("unused_parameter")
	private fun onFoodEaten(stack: ItemStack, worldIn: World, player: EntityPlayer) {
		// ahaha
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	 * the Item before the action is complete.
	 */
	override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
		if (entityLiving is EntityPlayer) {
			entityLiving.foodStats.addStats(this.actualFood, stack)
			worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, worldIn.rand.nextFloat() * 0.1f + 0.9f)

			val method = this.actualFood::class.java.getDeclaredMethod("onFoodEaten", ItemStack::class.java, World::class.java, EntityPlayer::class.java)
			method.isAccessible = true
			method.invoke(this.actualFood, stack, worldIn, entityLiving)

			this.onFoodEaten(stack, worldIn, entityLiving)

			if (entityLiving is EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger(entityLiving, stack)
			}
		}

		if (this.replacementItemStack != null) {
			return this.replacementItemStack!!
		}

		stack.shrink(1)
		return stack
	}

	/**
	 * How long it takes to use or consume an item
	 */
	override fun getMaxItemUseDuration(stack: ItemStack) = this.actualFood.getMaxItemUseDuration(stack)

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	override fun getItemUseAction(stack: ItemStack): EnumAction = this.actualFood.getItemUseAction(stack)

	/**
	 * Called when the equipped item is right clicked.
	 */
	override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> = this.actualFood.onItemRightClick(worldIn, playerIn, handIn)

	@Suppress("unused")
	fun getHealAmount(stack: ItemStack) = this.actualFood.getHealAmount(stack)

	@Suppress("unused")
	fun getSaturationModifier(stack: ItemStack) = this.actualFood.getSaturationModifier(stack)

	/**
	 * Whether wolves like this food (true for raw and cooked porkchop).
	 */
	@Suppress("unused")
	fun isWolfsFavoriteMeat() = this.actualFood.isWolfsFavoriteMeat

	@Suppress("unused")
	fun setPotionEffect(effect: PotionEffect, probability: Float): ItemPickaxeFood {
		this.actualFood.setPotionEffect(effect, probability)

		return this
	}

	/**
	 * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
	 */
	fun setAlwaysEdible(): ItemPickaxeFood {
		this.actualFood.setAlwaysEdible()

		return this
	}
}