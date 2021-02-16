package de.polylymer

import de.polylymer.commands.CommandManager
import de.polylymer.config.ConfigManager
import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

@KordPreview

suspend fun main() = Manager.start()

object Manager {

    lateinit var client: Kord; private set

    @KordPreview
    suspend fun start() {
        println("hallo")
        client = Kord(
            ConfigManager.discordApplication.token
            ?: error("Configure the application before running it"))
        println("..?")
        CommandManager.init()
        println("kek")
        client.on<MessageCreateEvent> {
            if (this.member != null) {
                if(this.member!!.isBot) return@on
                if(this.message.content.contains("discord.gg")) {
                    this.message.delete()
                    this.member!!.kick("Posting invites")
                }
            }
        }
        client.login()
        println("hawhha lustig")

        println("xd")
    }
}