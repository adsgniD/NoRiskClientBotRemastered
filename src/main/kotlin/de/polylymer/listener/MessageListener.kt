package de.polylymer.listener

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.Manager
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.reply
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.flow.collect
import java.util.*

object MessageListener {

    init {
        Manager.client.on<MessageCreateEvent> {
            val msg = this.message.content
            if (this.member != null) {
                //cape of the day support for submitted cape-channel
                if (this.member!!.isBot) {
                    if (this.message.channelId.asString == "790946998962487316") {
                        if (this.message.embeds.isNotEmpty()) {
                            //dont ask, mies traurig
                            Thread.sleep(2000)
                            this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                        }
                    }
                } else {
                    if (this.message.channelId.asString == "774596130541142037" && this.message.content.isNotEmpty()) {
                        val umfragenChannel =
                            this.getGuild()!!.getChannel(Snowflake("821017903381741609")) as MessageChannelBehavior
                        val invalidIdeasChannel =
                            this.getGuild()!!.getChannel(Snowflake("821023603369836546")) as MessageChannelBehavior
                        var found = false
                        umfragenChannel.messages.collect {
                            if (!found) {
                                if (it.asMessage().content.toLowerCase().contains(this.message.content.toLowerCase())) {
                                    this.message.channel.createMessage("Diese Idee ist bereits in ${umfragenChannel.mention}!")
                                    invalidIdeasChannel.createMessage("\"${this.message.content}\" by ${this.message.author!!.mention}")
                                    this.message.delete()
                                    found = true
                                }
                            }
                        }
                    }

                    //invite detection
                    if (this.message.content.toLowerCase().contains("discord.gg")) {
                        this.message.delete()
                        this.member!!.kick("Posting invites")
                    } else if (this.message.channelId.asString == "774982518133751858") {
                        //cape of the day support for gallery channel
                        if (this.message.attachments.isNotEmpty()) {
                            if (this.message.attachments.toList()[0].isImage) {
                                if (this.message.attachments.toList()[0].height == 256 && this.message.attachments.toList()[0].width == 512) {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.star.unicode))
                                }
                            }
                        }
                    }
                    //'lies die pins' feature
                    if (this.message.content.toLowerCase().contains("lies") && this.message.content.toLowerCase().contains("pins")) {
                        this.message.channel.pinnedMessages.collect {
                            this.message.channel.createMessage(it.content)
                        }
                    }

                    if (msg.contains("xD")) {
                        if(this.message.author!!.id.asString != "743435051512889434" /*Indikativ*/) {
                            if(!msg.contains("hast du eig ein bisschen obsi")) {
                                this.message.addReaction(ReactionEmoji.Unicode(Emojis.one.unicode))
                                this.message.addReaction(ReactionEmoji.Unicode(Emojis.two.unicode))
                            } else {
                                if(Random().nextInt(5) == 2) {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorO.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorB.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorS.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorI.unicode))
                                } else {
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorN.unicode))
                                    this.message.addReaction(ReactionEmoji.Unicode(Emojis.regionalIndicatorE.unicode))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
