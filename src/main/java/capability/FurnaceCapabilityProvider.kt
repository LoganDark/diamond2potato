package net.logandark.diamond2potato.capability

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class FurnaceCapabilityProvider(
	private val furnaceCapability: IFurnaceCapability
) : ICapabilitySerializable<NBTTagCompound> {
	companion object {
		@JvmStatic
		@CapabilityInject(IFurnaceCapability::class)
		lateinit var FURNACE_CAPABILITY: Capability<IFurnaceCapability>
	}

	@Suppress("unchecked_cast")
	override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? = when (capability) {
		FURNACE_CAPABILITY -> furnaceCapability as T?
		else               -> null
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability == FURNACE_CAPABILITY
	}

	override fun deserializeNBT(nbt: NBTTagCompound?) {
		FURNACE_CAPABILITY.readNBT(furnaceCapability, EnumFacing.UP, nbt)
	}

	override fun serializeNBT(): NBTTagCompound {
		return FURNACE_CAPABILITY.writeNBT(furnaceCapability, EnumFacing.UP) as NBTTagCompound
	}
}