package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction


@KordPreview
object AdminCommand : SlashCommand(
    name = "admin",
    description = "Explains the role Admin"
){
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            content = "Die Admins sind die Administratoren des Clients. Sie sind für die Leitung und Verwaltung des Clients verantwortlich. Bitte beachte, dass sie nicht gepingt werden wollen!"
        }
    }
}