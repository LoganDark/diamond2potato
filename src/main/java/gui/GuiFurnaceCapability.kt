package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.`interface`.IFurnaceCapability
import net.logandark.diamond2potato.container.ContainerFurnaceCapability
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

open class GuiFurnaceCapability(
	player: EntityPlayer,
	private val playerInv: IInventory,
	private val furnace: IFurnaceCapability,
	private val name: String = "Furnace Capability",
	private val furnaceContainer: ContainerFurnaceCapability = ContainerFurnaceCapability(player, playerInv, furnace)
) : GuiContainer(furnaceContainer) {
	private val furnaceGuiTextures = ResourceLocation("textures/gui/container/furnace.png")

	/**
	 * Draws the screen and all the components in it.
	 */
	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		drawDefaultBackground()
		super.drawScreen(mouseX, mouseY, partialTicks)
		renderHoveredToolTip(mouseX, mouseY)
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040)
		fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 96 + 2, 0x404040)
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
		mc.textureManager.bindTexture(furnaceGuiTextures)
		val i = (width - xSize) / 2
		val j = (height - ySize) / 2
		drawTexturedModalRect(i, j, 0, 0, xSize, ySize)

		if (furnace.isBurning()) {
			val k = getBurnLeftScaled(13)
			drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1)
		}

		val l = getCookProgressScaled(24)
		drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16)
	}

	private fun getCookProgressScaled(pixels: Int): Int {
		val i = furnaceContainer.cookTime
		val j = furnaceContainer.totalCookTime
		return if (j != 0 && i != 0) i * pixels / j else 0
	}

	private fun getBurnLeftScaled(pixels: Int): Int {
		var i = furnaceContainer.currentItemBurnTime

		if (i == 0) {
			i = 200
		}

		return furnaceContainer.furnaceBurnTime * pixels / i
	}
}