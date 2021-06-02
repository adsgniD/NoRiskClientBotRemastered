package de.polylymer.commands.implementation

import de.polylymer.KordEXT.member
import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction


@KordPreview
object SurvivalCommand : SlashCommand(
    name = "survival",
    description = "Random survival quotes"
){
    //var list = listOf("Test", "Test2", "Test3")

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            //content = RandomCommand.list.random().replace("{ping}", interaction.member().asMember().mention)
        }
    }
}