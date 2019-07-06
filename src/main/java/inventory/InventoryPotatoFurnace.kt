package net.logandark.diamond2potato.inventory

import net.logandark.diamond2potato.`class`.PotatoFurnaceState
import net.logandark.diamond2potato.item.ItemPotatoFurnace
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.ItemStackHelper
import net.minecraft.inventory.SlotFurnaceFuel
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntityFurnace.isItemFuel
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.translation.I18n

class InventoryPotatoFurnace(
	private val potatoFurnaceStack: ItemStack
) : IInventory {
	private val potatoFurnace: ItemPotatoFurnace = when (potatoFurnaceStack.item is ItemPotatoFurnace) {
		true -> potatoFurnaceStack.item as ItemPotatoFurnace
		false -> {
			throw IllegalArgumentException("Potato furnace stack must be a potato furnace")
		}
	}

	private val itemStacks: NonNullList<ItemStack> = NonNullList.withSize(3, ItemStack.EMPTY)

	private fun loadInventory() {
		val tagCompound = potatoFurnaceStack.tagCompound

		if (tagCompound is NBTTagCompound) {
			ItemStackHelper.loadAllItems(tagCompound, itemStacks)
		}
	}

	init {
		loadInventory()
	}

	override fun clear() = itemStacks.fill(ItemStack.EMPTY)

	override fun closeInventory(player: EntityPlayer) {}

	override fun decrStackSize(index: Int, count: Int): ItemStack {
		return ItemStackHelper.getAndSplit(itemStacks, index, count)
	}

	override fun getDisplayName(): ITextComponent {
		return TextComponentString(name)
	}

	override fun getField(id: Int): Int {
		val state = PotatoFurnaceState(potatoFurnaceStack)

		return when (id) {
			0 -> state.furnaceBurnTime
			1 -> state.currentItemBurnTime
			2 -> state.cookTime
			3 -> state.totalCookTime
			else -> 0
		}
	}

	override fun getFieldCount() = 0

	override fun getInventoryStackLimit() = 64

	override fun getName(): String = I18n.translateToLocal("${potatoFurnace.translationKey}.name")

	override fun getSizeInventory() = 3

	override fun getStackInSlot(index: Int) = itemStacks[index]

	override fun hasCustomName() = false

	override fun isEmpty() = itemStacks.any { !it.isEmpty }

	override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean {
		return when (index) {
			0, 2 -> true
			1 -> isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemStacks[1].item !== Items.BUCKET
			else -> false
		}
	}

	override fun isUsableByPlayer(player: EntityPlayer) = true

	override fun markDirty() {
		var tagCompound = potatoFurnaceStack.tagCompound

		if (tagCompound !is NBTTagCompound) {
			tagCompound = NBTTagCompound()
			potatoFurnaceStack.tagCompound = tagCompound
		}

		ItemStackHelper.saveAllItems(tagCompound, itemStacks)
	}

	override fun openInventory(player: EntityPlayer) {}

	override fun removeStackFromSlot(index: Int): ItemStack = ItemStackHelper.getAndRemove(itemStacks, index)

	override fun setField(id: Int, value: Int) {
		val state = PotatoFurnaceState(potatoFurnaceStack)

		when (id) {
			0 -> state.furnaceBurnTime = value
			1 -> state.currentItemBurnTime = value
			2 -> state.cookTime = value
			3 -> state.totalCookTime = value
		}

		state.saveNBT(potatoFurnaceStack)
	}

	override fun setInventorySlotContents(index: Int, stack: ItemStack) {
		val oldStack = itemStacks[index]
		itemStacks[index] = stack

		if (stack.count > this.inventoryStackLimit) {
			stack.count = this.inventoryStackLimit
		}

		if (index == 0 && (stack.isEmpty || stack.item != oldStack.item || !ItemStack.areItemStackTagsEqual(stack, oldStack))) {
			val state = PotatoFurnaceState(potatoFurnaceStack)
			state.totalCookTime = ItemPotatoFurnace.getCookTime(stack)
			state.cookTime = 0
			state.saveNBT(potatoFurnaceStack)
		}
	}
}