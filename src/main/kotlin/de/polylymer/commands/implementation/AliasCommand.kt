package de.polylymer.commands.implementation

import de.polylymer.KordEXT.guild
import de.polylymer.KordEXT.member
import de.polylymer.commands.SlashCommand
import de.polylymer.database.MongoManager
import de.polylymer.database.data.Alias
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.core.entity.interaction.string
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.bson

@KordPreview
object AliasCommand : SlashCommand(
    name = "alias",
    description = "Create an alias-tag to read.",
    {
        subCommand("create", "Create an alias-tag.") {
            string("key", "The key of the alias-tag.") {
                required = true
            }
            string("value", "The value of the alias-tag.") {
                required = true
            }
        }
        subCommand("delete", "Delete an alias-tag.") {
            string("alias", "The alias-tag to delete.") {
                required = true
                for (it in MongoManager.aliases.find().toList())
                    choice(it.key, it.key)
            }
        }
    }
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            if(interaction.member().getPermissions().contains(Permission.ManageMessages)) {
                val key = interaction.command.options["key"]?.string()
                val value = interaction.command.options["value"]?.string()
                if (key != null && value != null) {
                    MongoManager.aliases.insertOne(Alias(key,value))
                    MongoManager.reconnect()
                    embed {
                        color = Color(0,255,0)
                        title = "Alias created."
                        description = "You can read the alias-tag with `/tag post $key`"
                        val foot = EmbedBuilder.Footer()
                        foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                        foot.text = interaction.guild().name
                        footer = foot
                    }
                } else {
                    val alias = interaction.command.options["alias"]?.string()
                    if(alias != null) {
                        MongoManager.aliases.deleteOne("{\"key\":\"${alias}\"}".bson)
                        MongoManager.reconnect()
                        embed {
                            color = Color(255,0,0)
                            title = "Alias deleted."
                            description = "The alias $alias got deleted."
                            val foot = EmbedBuilder.Footer()
                            foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                            foot.text = interaction.guild().name
                            footer = foot
                        }
                    }
                }
            }

        }
    }
}