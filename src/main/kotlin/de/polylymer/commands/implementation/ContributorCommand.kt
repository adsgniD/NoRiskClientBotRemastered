package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction


@KordPreview
object ContributorCommand : SlashCommand(
    name = "contributor",
    description = "Explains the role Contributor"
){
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            content = "Die Contributoren sind Leute die sich aktiv für den Erhalt des NoRiskClient Bots einsetzen. Sie bringen weitere Ideen ein und coden teilweise selbst am Bot.\n" +
                      "Wenn du dich auch am Bot beteiligen willst, kannst du mal auf GitHub vorbeischauen, alles weitere ist dort erklärt:\n" +
                      "https://github.com/PolylymerDE/NoRiskClientBotRemastered"
        }
    }
}