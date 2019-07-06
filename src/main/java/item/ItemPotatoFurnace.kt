package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.Diamond2Potato
import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.capability.FurnaceCapabilityProvider
import net.logandark.diamond2potato.gui.GuiHandler
import net.logandark.diamond2potato.util.modid
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilityProvider

object ItemPotatoFurnace : Item() {
	@JvmStatic
	@CapabilityInject(IFurnaceCapability::class)
	lateinit var FURNACE_CAPABILITY: Capability<IFurnaceCapability>

	init {
		translationKey = "$modid.potato_furnace"
		registryName = ResourceLocation(modid, "potato_furnace")
		maxStackSize = 1
	}

	override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
		if (!worldIn.isRemote) {
			playerIn.openGui(Diamond2Potato, GuiHandler.GUIs.PotatoFurnace.ordinal, worldIn, 0, 0, 0)
		}

		return super.onItemRightClick(worldIn, playerIn, handIn)
	}

	override fun initCapabilities(stack: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
		return FurnaceCapabilityProvider()
	}

	override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
		val furnaceCapability = stack.getCapability(FURNACE_CAPABILITY, null)

		furnaceCapability!!.update()
	}
}