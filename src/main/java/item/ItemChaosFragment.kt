package net.logandark.diamond2potato.item

import net.logandark.diamond2potato.util.modid
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

// This item will not be a final part of the mod and is ONLY for TESTING
//
// I made it for a friend who wanted to change the icon of an item based on how
// many items were in the stack, like Extra Utils 2 does with Ender Shards
// except XU2 is abstraction on top of abstraction on top of low-level render
// code and this uses Minecraft's convenient model predicate system
// I just had to make it for myself before I knew what to tell him
object ItemChaosFragment : Item() {
	init {
		translationKey = "$modid.chaos_fragment"
		registryName = ResourceLocation(modid, "chaos_fragment")
		maxStackSize = 4

		this.addPropertyOverride(ResourceLocation(modid, "chaos_fragment")) { stack, _, _ -> stack.count / 4F }
	}

	override fun hasEffect(stack: ItemStack) = true
}