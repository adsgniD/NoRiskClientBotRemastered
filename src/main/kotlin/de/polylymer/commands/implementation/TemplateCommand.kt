package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction

@KordPreview
object TemplateCommand : SlashCommand(
    name = "template",
    description = "Get the template to design your own cape"
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            content = "Achtung: Der Capeserver ist im Moment nicht erreichbar!\nhttps://cdn.discordapp.com/attachments/788821736815853608/827167628745375744/template2.png"
        }
    }

}
