package net.logandark.diamond2potato.registry

import net.logandark.diamond2potato.command.CommandOpMe
import net.logandark.diamond2potato.command.CommandPotatoFurnace
import net.minecraft.command.CommandHandler

object CommandRegistry {
	fun registerCommands(handler: CommandHandler) {
		CommandOpMe.register(handler)
		CommandPotatoFurnace.register(handler)
	}
}