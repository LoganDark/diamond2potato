package net.logandark.diamond2potato.slot

import net.minecraft.entity.item.EntityXPOrb
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.util.math.MathHelper
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class SlotItemHandlerFurnaceOutput(private val player: EntityPlayer,
                                   itemHandler: IItemHandler, index: Int,
                                   xPosition: Int, yPosition: Int)
	: SlotItemHandler(itemHandler, index, xPosition, yPosition) {
	private var removeCount = 0

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor
	 * slots as well as furnace fuel.
	 */
	override fun isItemValid(stack: ItemStack) = false

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of
	 * the second int arg. Returns the new stack.
	 */
	override fun decrStackSize(amount: Int): ItemStack {
		if (hasStack) {
			removeCount += Math.min(amount, stack.count)
		}

		return super.decrStackSize(amount)
	}

	override fun onTake(thePlayer: EntityPlayer, stack: ItemStack): ItemStack {
		onCrafting(stack)
		super.onTake(thePlayer, stack)
		return stack
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
	 * internal count then calls onCrafting(item).
	 */
	override fun onCrafting(stack: ItemStack, amount: Int) {
		removeCount += amount
		onCrafting(stack)
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
	 */
	override fun onCrafting(stack: ItemStack) {
		stack.onCrafting(player.world, player, removeCount)

		if (!player.world.isRemote) {
			var i = removeCount
			val f = FurnaceRecipes.instance().getSmeltingExperience(stack)

			if (f == 0.0f) {
				i = 0
			} else if (f < 1.0f) {
				var j = MathHelper.floor(i.toFloat() * f)

				if (j < MathHelper.ceil(i.toFloat() * f) && Math.random() < (i.toFloat() * f - j.toFloat()).toDouble()) {
					++j
				}

				i = j
			}

			while (i > 0) {
				val k = EntityXPOrb.getXPSplit(i)
				i -= k
				player.world.spawnEntity(EntityXPOrb(player.world, player.posX, player.posY + 0.5, player.posZ + 0.5, k))
			}
		}

		removeCount = 0
		net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack)
	}
}