package de.polylymer.commands

import de.polylymer.Manager
import de.polylymer.commands.implementation.CapeCommand
import de.polylymer.commands.implementation.DownloadCommand
import de.polylymer.commands.implementation.RandomCommand
import de.polylymer.commands.implementation.TemplateCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.createApplicationCommand
import dev.kord.core.entity.Guild
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.flow.collect

@KordPreview
object CommandManager {

    private val commands = HashMap<String, SlashCommand>()

    fun register(command: SlashCommand) {
        commands[command.name] = command
    }

    suspend fun init() {

        DownloadCommand
        CapeCommand
        RandomCommand
        TemplateCommand

        cleanupGuilds()
        registerOnGuilds()
        Manager.client.on<GuildCreateEvent> {
            this.guild.cleanupCommands()
            this.guild.registerCommands()
        }
        Manager.client.on<InteractionCreateEvent> {
            if(this.interaction.channel.id.asString == "774273609467691018") {
                commands[interaction.command.name]?.handleCommand(interaction)
            } else {
                this.interaction.channel.createMessage("Bitte führe Bot-Commands in " + this.interaction.guild.getChannel(Snowflake("774273609467691018")
                ).mention + " aus " + this.interaction.member.mention)
            }
        }
        Manager.client.on<MessageCreateEvent> {
            if(!this.member!!.isBot) {
                if(this.message.content.contains("discord.gg")) {
                    this.message.delete()
                    this.member!!.kick("Posting invites")
                }
            }
        }
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