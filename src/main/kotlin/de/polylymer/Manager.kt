package de.polylymer

import com.gitlab.kordlib.kordx.emoji.Emojis
import de.polylymer.commands.CommandManager
import de.polylymer.config.ConfigManager
import de.polylymer.listener.MessageListener
import de.polylymer.listener.ReactionListener
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.*
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.interaction.Interaction
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder
import kotlinx.coroutines.flow.collect

/*
* Using hardcoded channelIDs because its not a public bot.
*/


@KordPreview
suspend fun main() {
    Manager.start()
}

object Manager {

    lateinit var client: Kord; private set

    @KordPreview
    suspend fun start() {
        println("Starting...")
        client = Kord(
            ConfigManager.discordApplication.token
            ?: error("Configure the application before running it"))
        CommandManager.init()
        //REGISTER LISTENER
        MessageListener
        ReactionListener
        client.login()
    }
}

object KordEXT {

    //some weird bug appeared, this was a notlösung

    @KordPreview
    suspend fun Interaction.guild(): Guild {
        return kord.getGuild(data.guildId.value!!)!!
    }

    @KordPreview
    suspend fun Interaction.member(): Member {
        return guild().getMember(data.member.value!!.userId)
    }

}