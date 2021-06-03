package de.polylymer.commands.implementation
import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import java.nio.file.Path

@KordPreview
object CreateCapeCommand : SlashCommand(
    name = "createcape",
    description = "creates a cape\n-by adsgniD"
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            val process = ProcessBuilder("python3", "../CreateCape/CapeManager.py", ).start()
            val filePath = Path.of("../CreateCape/out.png")
            addFile(path = filePath)
        }
    }
}
