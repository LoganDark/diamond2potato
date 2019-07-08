package net.logandark.diamond2potato.gui

import net.logandark.diamond2potato.container.ContainerFurnaceIInventory
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.ResourceLocation

open class GuiFurnaceIInventory(
	player: EntityPlayer,
	private val playerInv: IInventory,
	private val furnaceInv: IInventory
) : GuiContainer(ContainerFurnaceIInventory(player, playerInv, furnaceInv)) {
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
		val s = furnaceInv.displayName.unformattedText
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752)
		fontRenderer.drawString(playerInv.displayName.unformattedText, 8, ySize - 96 + 2, 4210752)
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

		if (TileEntityFurnace.isBurning(furnaceInv)) {
			val k = getBurnLeftScaled(13)
			drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1)
		}

		val l = getCookProgressScaled(24)
		drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16)
	}

	private fun getCookProgressScaled(pixels: Int): Int {
		val i = furnaceInv.getField(2)
		val j = furnaceInv.getField(3)
		return if (j != 0 && i != 0) i * pixels / j else 0
	}

	private fun getBurnLeftScaled(pixels: Int): Int {
		var i = furnaceInv.getField(1)

		if (i == 0) {
			i = 200
		}

		return furnaceInv.getField(0) * pixels / i
	}
}