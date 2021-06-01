package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction


@KordPreview
object SupporterCommand : SlashCommand(
    name = "supporter",
    description = "Explains the role Supporter"
){
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            content = "Die Supporter sind eigentlich Moderatoren des HGLabor.de Servers. Bei Fragen solltest du dich lieber an die Helper wenden."
        }
    }
}