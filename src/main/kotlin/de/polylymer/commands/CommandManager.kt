package de.polylymer.commands

import de.polylymer.Manager
import de.polylymer.commands.implementation.DownloadCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.createApplicationCommand
import dev.kord.core.entity.Guild
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import kotlin.collections.HashMap
import dev.kord.core.on
import kotlinx.coroutines.flow.collect

@KordPreview
object CommandManager {

    private val commands = HashMap<String, SlashCommand>()

    fun register(command: SlashCommand) {
        commands[command.name] = command
    }

    suspend fun init() {
        println("1")
        DownloadCommand
        println("2")
        cleanupGuilds()
        println("3")
        registerOnGuilds()
        println("4")
        Manager.client.on<GuildCreateEvent> {
            this.guild.cleanupCommands()
            this.guild.registerCommands()
        }
        println("5")
        Manager.client.on<InteractionCreateEvent> {
            commands[interaction.command.name]?.handleCommand(interaction)
        }
        println("6")
    }

    private suspend fun registerOnGuilds() = Manager.client.guilds.collect { it.registerCommands() }

    private suspend fun cleanupGuilds() = Manager.client.guilds.collect { it.cleanupCommands() }

    private suspend fun Guild.registerCommands() {
        CommandManager.commands.forEach { commandEntry ->
            val command = commandEntry.value
            createApplicationCommand(command.name, command.description) { command.builder.invoke(this) }
        }
    }

    private suspend fun Guild.cleanupCommands() {
        commands.collect { command ->
            if (!CommandManager.commands.containsKey(command.name))
                command.delete()
        }
    }

}