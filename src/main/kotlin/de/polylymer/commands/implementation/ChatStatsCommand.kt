package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.channel.TextChannelBehavior
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.entity.interaction.Interaction
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList

@KordPreview
object ChatStatsCommand : SlashCommand(
    name = "chatstats",
    description = "Shows the Users with the most Chat-Messages"
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.acknowledge(true)
        var msg = ArrayList<Message>()
        interaction.guild.channels.collect {
            if(it is TextChannelBehavior) {
                val channel = it as TextChannelBehavior
                msg.addAll(channel.messages.toList())
            }
        }
        interaction.channel.createMessage("**${msg.size}** messages")
    }
}