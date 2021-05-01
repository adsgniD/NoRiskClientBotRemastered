package de.polylymer.commands.implementation

import de.polylymer.KordEXT.guild
import de.polylymer.commands.SlashCommand
import de.polylymer.database.MongoManager
import de.polylymer.database.data.Cape
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder

@KordPreview
object RandomCapeCommand : SlashCommand(
    name = "randomcape",
    description = "Posts a random cape of the day."
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            val cape = MongoManager.capesOfTheYear.find().toList().random()
            embed {
                title = "Random Cape"
                description = "Der Bot hat sich für dieses Cape entschieden!\n \nCape von: ${cape.uploadedBy}"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.guild().getIconUrl(Image.Format.GIF)!!
                color = Color(0, 251, 255)
                thumbnail = thumb
                image = cape.url
                val foot = EmbedBuilder.Footer()
                foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                foot.text = interaction.guild().name
                footer = foot
            }
        }
    }
}