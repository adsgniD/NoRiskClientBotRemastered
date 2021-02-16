package de.polylymer

import de.polylymer.commands.CommandManager
import de.polylymer.config.ConfigManager
import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord

@KordPreview

suspend fun main() = Manager.start()

object Manager {

    lateinit var client: Kord; private set

    @KordPreview
    suspend fun start() {
        client = Kord(
            ConfigManager.discordApplication.token
            ?: error("Configure the application before running it"))
        CommandManager.init()
        client.login()
    }
}