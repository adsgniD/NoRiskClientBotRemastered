package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import de.polylymer.database.MongoManager
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.core.entity.interaction.string
import org.litote.kmongo.findOne

@KordPreview
object TagCommand : SlashCommand(
    name = "tag",
    description = "Show users who can't download an execute a file how to do something.",
    {
        subCommand("post", "Show users who can't download an execute a file how to do something.") {
            string("entry", "Select an valid alias-tag.") {
                required = true
                for (it in MongoManager.aliases.find())
                    choice(it.key, it.key)
            }
        }
        subCommand("list", "Show users who can't download an execute a file how to do something.")
    }
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            val entry = interaction.command.options["entry"]?.string()
            if (entry != null) {
                val alias = MongoManager.aliases.findOne("{\"key\":\"${entry}\"}")
                content = alias?.value ?: "This alias could not been found. Try `/tag list`"
            } else {
                var listCompound = ""
                for (alias in MongoManager.aliases.find()) {
                    listCompound+=alias.key + ","
                }
                content = listCompound
            }
        }

    }
}