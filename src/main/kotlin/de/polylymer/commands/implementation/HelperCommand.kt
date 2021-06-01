package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction


@KordPreview
object HelperCommand : SlashCommand(
    name = "helper",
    description = "Explains the role Helper"
){
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            content = "Die Helper helfen dir mit Fragen über den Client. Im Moment werden keine weiteren Helper eingestellt."
        }
    }
}