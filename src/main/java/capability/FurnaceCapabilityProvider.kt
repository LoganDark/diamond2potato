package net.logandark.diamond2potato.capability

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilityProvider

class FurnaceCapabilityProvider : ICapabilityProvider {
	companion object {
		@JvmStatic
		@CapabilityInject(IFurnaceCapability::class)
		lateinit var FURNACE_CAPABILITY: Capability<IFurnaceCapability>
	}

	private val furnaceCapability: IFurnaceCapability = FurnaceCapability()

	@Suppress("unchecked_cast")
	override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
		return when (capability) {
			FURNACE_CAPABILITY -> furnaceCapability as T
			else               -> null
		}
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability == FURNACE_CAPABILITY
	}
}