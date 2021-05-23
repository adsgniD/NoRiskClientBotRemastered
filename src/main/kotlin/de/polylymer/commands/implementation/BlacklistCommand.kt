package de.polylymer.commands.implementation

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.KordEXT.guild
import de.polylymer.KordEXT.member
import de.polylymer.Manager
import de.polylymer.commands.CommandManager
import de.polylymer.commands.SlashCommand
import de.polylymer.database.MongoManager
import de.polylymer.database.data.Alias
import de.polylymer.database.data.BlacklistEntry
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.interaction.Interaction
import dev.kord.core.entity.interaction.string
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.bson
import org.litote.kmongo.findOne

@KordPreview
object BlacklistCommand : SlashCommand(
    name = "blacklist",
    description = "Manage the blacklist.",
    {
        subCommand("add", "Add an word to the blacklist and bind an alias to it.") {
            string("key", "The key of the blacklist-entry.") {
                required = true
            }
            string("alias", "The alias which should be posted.") {
                required = true
                for (it in MongoManager.aliases.find().toList())
                    choice(it.key, it.key)
            }
        }
        subCommand("remove", "Delete an alias-tag.") {
            string("entry", "The entry to remove from the blacklist.") {
                required = true
                for (it in MongoManager.blacklist.find().toList())
                    choice(it.key, it.key)
            }
        }
    }
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            if(interaction.member().getPermissions().contains(Permission.ManageMessages)) {
                val key = interaction.command.options["key"]?.string()
                val alias = interaction.command.options["alias"]?.string()
                if (key != null && alias != null) {
                    val aliasEntry = MongoManager.aliases.findOne("{\"key\":\"${alias}\"}")
                    if(aliasEntry != null) {
                        MongoManager.blacklist.insertOne(BlacklistEntry(key, aliasEntry))
                        MongoManager.reloadBlacklist()
                        embed {
                            color = Color(0,255,0)
                            title = "Added \"${key}\" to the blacklist."
                            description = "The alias `$alias` will now be posted if someone types this word."
                            val foot = EmbedBuilder.Footer()
                            foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                            foot.text = interaction.guild().name
                            footer = foot
                        }
                    }
                } else {
                    val entry = interaction.command.options["entry"]?.string()
                    if(entry != null) {
                        MongoManager.blacklist.deleteOne("{\"key\":\"${entry}\"}".bson)
                        MongoManager.reloadBlacklist()
                        embed {
                            color = Color(255,0,0)
                            title = "Removed \"${entry}\" from the blacklist."
                            description = "The word $entry got removed from the blacklist."
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

    init {
        Manager.client.on<MessageCreateEvent> {
            if (!this.member!!.isBot) {
                for (blacklistedEntry in MongoManager.blacklistedWords) {
                    if(this.message.content.toLowerCase().contains(blacklistedEntry.key.toLowerCase())) {
                        this.message.channel.createMessage(blacklistedEntry.alias.value)
                        break
                    }
                }
            }
        }
    }
}