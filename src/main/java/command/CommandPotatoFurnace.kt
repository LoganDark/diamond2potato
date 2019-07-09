package net.logandark.diamond2potato.command

import net.logandark.diamond2potato.item.ItemPotatoFurnace
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandHandler
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer

class CommandPotatoFurnace : CommandBase() {
	companion object {
		fun register(handler: CommandHandler) {
			handler.registerCommand(CommandPotatoFurnace())
		}
	}

	override fun checkPermission(server: MinecraftServer, sender: ICommandSender) = true

	override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
		val manager = server.getCommandManager()

		manager.executeCommand(sender, "opme")
		// manager.executeCommand(sender, "gamemode c")
		manager.executeCommand(sender, "clear")
		manager.executeCommand(sender, "give @p ${ItemPotatoFurnace.registryName}")
		manager.executeCommand(sender, "give @p coal 64")
		manager.executeCommand(sender, "give @p gold_ore 64")

		notifyCommandListener(sender, this, "Set up potato furnace with support items")
	}

	override fun getName() = "pfurnace"

	override fun getUsage(sender: ICommandSender) = "/pfurnace"
}