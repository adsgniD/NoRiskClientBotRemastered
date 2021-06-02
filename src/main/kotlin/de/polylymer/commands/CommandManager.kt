package de.polylymer.commands

import de.polylymer.KordEXT.guild
import de.polylymer.KordEXT.member
import de.polylymer.Manager
import de.polylymer.commands.implementation.*
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.createApplicationCommand
import dev.kord.core.entity.Guild
import dev.kord.core.event.guild.GuildCreateEvent
import dev.kord.core.event.interaction.InteractionCreateEvent
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
        StatsCommand
        CocoCommand
        AliasCommand
        TagCommand
        RandomCapeCommand
        ReportCommand
        BlacklistCommand
        RolesCommand
        cleanupGuilds()
        Manager.client.guilds.collect {
            println("Registering commands for ${it.name}")
        }
        registerOnGuilds()
        Manager.client.on<GuildCreateEvent> {
            this.guild.cleanupCommands()
            this.guild.registerCommands()
        }
        Manager.client.on<InteractionCreateEvent> {
            if(this.interaction.channel.id.asString == "774273609467691018" || this.interaction.member().getPermissions().contains(Permission.ManageMessages)) {
                commands[interaction.command.rootName]?.handleCommand(interaction)
            } else {
                this.interaction.channel.createMessage("Bitte führe Bot-Commands in " + this.interaction.guild().getChannel(Snowflake("774273609467691018")
                ).mention + " aus " + this.interaction.member().mention)
            }
        }
    }

    suspend fun reloadCommands() {
        cleanupGuilds()
        registerOnGuilds()
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