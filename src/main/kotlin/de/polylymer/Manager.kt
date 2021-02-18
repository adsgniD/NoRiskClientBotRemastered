package de.polylymer

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.commands.CommandManager
import de.polylymer.config.ConfigManager
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.*
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.getChannelOf
import dev.kord.core.entity.GuildEmoji
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder

@KordPreview

suspend fun main() = Manager.start()

object Manager {

    lateinit var client: Kord; private set

    @KordPreview
    suspend fun start() {
        println("Starting...")
        client = Kord(
            ConfigManager.discordApplication.token
            ?: error("Configure the application before running it"))
        CommandManager.init()
        client.on<MessageCreateEvent> {
            if (this.member != null) {
                if(this.member!!.isBot) {
                    if(this.message.channelId.asString == "790946998962487316") {
                        if(this.message.embeds.isNotEmpty()) {
                            this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                        }
                    }
                } else {
                    if(this.message.content.toLowerCase().contains("discord.gg")) {
                        this.message.delete()
                        this.member!!.kick("Posting invites")
                    } else if(this.message.channelId.asString == "774982518133751858") {
                        if(this.message.attachments.isNotEmpty()) {
                            this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                        }
                    }
                    if(this.message.content.toLowerCase().contains("cape") && this.message.content.toLowerCase().contains("nicht")) {
                        this.message.channel.createMessage("https://media.discordapp.net/attachments/774274615408328724/809743719057326122/ouahhhh_MeIn_CaPe_GeHt_NiChT.gif")
                    }
                }
            }
        }
        client.on<ReactionAddEvent> {
            if(this.getUserAsMember() != null) {
                if(!this.getUserAsMember()!!.isBot) {
                    if(this.channelId.asString == "790946998962487316") {
                        if(this.emoji == ReactionEmoji.Unicode(Emojis.star.unicode)) {
                            if(this.message.asMessage().embeds.isNotEmpty()) {
                                val channel = this.guild!!.getChannel(Snowflake("811526154469113886")) as MessageChannelBehavior
                                channel.createEmbed {
                                    title = "Cape of the Day"
                                    description = "Das Cape des heutigen Tages ist..."
                                    val thumb = EmbedBuilder.Thumbnail()
                                    val reactionAddEvent = this@on
                                    thumb.url = reactionAddEvent.guild!!.asGuild().getIconUrl(Image.Format.GIF)!!
                                    color = Color(0, 251, 255)
                                    thumbnail = thumb
                                    image = reactionAddEvent.message.asMessage().embeds[0].image!!.url!!
                                    val foot = EmbedBuilder.Footer()
                                    foot.icon = reactionAddEvent.getGuild()!!.getIconUrl(Image.Format.GIF)!!
                                    foot.text = reactionAddEvent.getGuild()!!.name
                                    footer = foot
                                }
                            }
                        }
                    } else if(this.channelId.asString == "774982518133751858") {
                        if(this.emoji == ReactionEmoji.Unicode(Emojis.star.unicode)) {
                            if(this.getUserAsMember()!!.getPermissions().contains(Permission.ManageMessages)) {
                                if(this.message.asMessage().attachments.isNotEmpty()) {
                                    val channel = this.guild!!.getChannel(Snowflake("811526154469113886")) as MessageChannelBehavior
                                    channel.createEmbed {
                                        title = "Cape of the Day"
                                        description = "Das Cape des heutigen Tages ist..."
                                        val thumb = EmbedBuilder.Thumbnail()
                                        val reactionAddEvent = this@on
                                        thumb.url = reactionAddEvent.guild!!.asGuild().getIconUrl(Image.Format.GIF)!!
                                        color = Color(0, 251, 255)
                                        thumbnail = thumb
                                        image = reactionAddEvent.message.asMessage().attachments.toList()[0].url
                                        val foot = EmbedBuilder.Footer()
                                        foot.icon = reactionAddEvent.getGuild()!!.getIconUrl(Image.Format.GIF)!!
                                        foot.text = reactionAddEvent.getGuild()!!.name
                                        footer = foot
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        client.login()
        println("hawhha lustig")

        println("xd")
    }
}