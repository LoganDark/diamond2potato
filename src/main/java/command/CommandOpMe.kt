package net.logandark.diamond2potato.command

import com.mojang.authlib.GameProfile
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.CommandHandler
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer

class CommandOpMe : CommandBase() {
	companion object {
		fun register(handler: CommandHandler) {
			handler.registerCommand(CommandOpMe())
		}
	}

	override fun checkPermission(server: MinecraftServer, sender: ICommandSender) = true

	override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
		val senderEntity = sender.commandSenderEntity

		if (senderEntity is EntityPlayer) {
			val senderPlayer: EntityPlayer = senderEntity
			val gameProfile = server.playerProfileCache.getProfileByUUID(senderPlayer.uniqueID)

			if (gameProfile is GameProfile) {
				server.playerList.addOp(gameProfile)
				notifyCommandListener(sender, this, "Opped self")
			} else {
				throw CommandException("Not a player")
			}
		}
	}

	override fun getName() = "opme"

	override fun getUsage(sender: ICommandSender) = "/opme"
}