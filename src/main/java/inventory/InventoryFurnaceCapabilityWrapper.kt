package net.logandark.diamond2potato.inventory

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.item.ItemPotatoFurnace
import net.minecraft.util.text.translation.I18n

class InventoryFurnaceCapabilityWrapper(
	private val furnaceCapability: IFurnaceCapability
) : InventoryItemHandlerWrapper(
	furnaceCapability,
	I18n.translateToLocal("${ItemPotatoFurnace.translationKey}.name")
) {
	override fun getFieldCount() = 4

	override fun getField(id: Int) = when (id) {
		0    -> furnaceCapability.furnaceBurnTime
		1    -> furnaceCapability.currentItemBurnTime
		2    -> furnaceCapability.cookTime
		3    -> furnaceCapability.totalCookTime
		else -> 0
	}

	override fun setField(id: Int, value: Int) {
		when (id) {
			0 -> furnaceCapability.furnaceBurnTime = value
			1 -> furnaceCapability.currentItemBurnTime = value
			2 -> furnaceCapability.cookTime = value
			3 -> furnaceCapability.totalCookTime = value
		}
	}
}