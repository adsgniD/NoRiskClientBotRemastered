package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.followUp
import dev.kord.core.entity.interaction.Interaction

@KordPreview
object TemplateCommand : SlashCommand(
    name = "template",
    description = "Get the template to design your own cape"
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.acknowledge().followUp {
            content = "https://cdn.discordapp.com/attachments/774273183804948500/811656301616169030/template.png"
        }
    }

}