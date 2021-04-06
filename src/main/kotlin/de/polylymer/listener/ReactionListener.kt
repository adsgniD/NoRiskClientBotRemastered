package de.polylymer.listener

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.Manager
import dev.kord.common.Color
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder

object ReactionListener {

    init {
        Manager.client.on<ReactionAddEvent> {
            if(this.getUserAsMember() != null) {
                //prevent users from posting bad emojis
                /*
                if(this.emoji == ReactionEmoji.Unicode(Emojis.loveYouGesture.unicode) || this.emoji == ReactionEmoji.Unicode(
                        Emojis.metal.unicode) || this.emoji == ReactionEmoji.Unicode(Emojis.v.unicode) || this.emoji == ReactionEmoji.Unicode(
                        Emojis.callMe.unicode)) {
                    this.message.deleteReaction(this.emoji)
                }
                 */

                //CAPE OF THE DAY FUNCTIONALITY
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
                                    this.message.deleteAllReactions()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}