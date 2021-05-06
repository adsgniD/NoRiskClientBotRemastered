package de.polylymer.commands.implementation

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.KordEXT.guild
import de.polylymer.Manager
import de.polylymer.commands.SlashCommand
import de.polylymer.database.MongoManager
import de.polylymer.database.data.Report
import de.polylymer.database.data.ban
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.interaction.Interaction
import dev.kord.core.entity.interaction.int
import dev.kord.core.entity.interaction.string
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.bson
import org.litote.kmongo.json
import java.util.*

@KordPreview
object ReportCommand : SlashCommand(
        name = "report",
        description = "Reporte einen Nutzer für DM-Werbung oder sonstiges",
        {
            string("userid", "Die Discord-ID vom Regelbrecher") {
                required = true
            }
            string("proof", "Beweis zum Regelbruch (z.B. Link zu einem Screenshot)") {
                required = true
            }
            string("other", "Sonstige Anmerkungen") {
                required = false
            }
        }
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            val name = interaction.command.options["userid"]?.string()
            val proof = interaction.command.options["proof"]?.string()
            val other = interaction.command.options["other"]?.string()
            if (name != null && proof != null) {
                if (proof.contains("https://")) {
                    val reportChannel = interaction.guild().getChannel(Snowflake("839880949072134234")) as MessageChannelBehavior
                    val id = Random().nextInt(10000000)
                    MongoManager.reportData.insertOne(Report(id, name))
                    reportChannel.createEmbed {
                        title = "New Report"
                        val thumb = EmbedBuilder.Thumbnail()
                        thumb.url = interaction.guild().getIconUrl(Image.Format.GIF)!!
                        thumbnail = thumb
                        val foot = EmbedBuilder.Footer()
                        foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                        foot.text = "Report-ID: $id"
                        footer = foot
                        image = proof
                        description = "${interaction.guild().getMember(Snowflake(name)).mention} got reported. ${if (other != null) "$other" else ""}\nReact to ban / ignore the person"
                        color = Color(0, 251, 255)
                    }
                }
            }
        }
    }

    init {
        Manager.client.on<MessageCreateEvent> {
            if (this.message.channelId.asString == "839880949072134234") {
                if (this.member!!.isBot) {
                    if (this.message.embeds.isNotEmpty()) {
                        this.message.addReaction(ReactionEmoji.Unicode(Emojis.whiteCheckMark.unicode))
                        this.message.addReaction(ReactionEmoji.Unicode(Emojis.wastebasket.unicode))
                    }
                }
            }
        }
        Manager.client.on<ReactionAddEvent> {
            if (this.message.channelId.asString == "839880949072134234") {
                if (this.message.asMessage().embeds.isNotEmpty()) {
                    if (this.getUserAsMember() != null) {
                        if (!this.getUserAsMember()!!.isBot) {
                            if (this.getUserAsMember()!!.getPermissions().contains(Permission.BanMembers)) {
                                val reportID = this.message.asMessage().embeds[0].footer!!.text.replace("Report-ID: ", "").toInt()
                                if (this.emoji == ReactionEmoji.Unicode(Emojis.wastebasket.unicode)) {
                                    this.message.delete()
                                } else if (this.emoji == ReactionEmoji.Unicode(Emojis.whiteCheckMark.unicode)) {
                                    MongoManager.getReport(reportID)?.ban()
                                    MongoManager.reportData.deleteOne(MongoManager.getReport(reportID)?.json?.bson!!)
                                    this.message.delete()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}