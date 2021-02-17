package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.followUp
import dev.kord.core.entity.Embed
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder

@KordPreview
object DownloadCommand : SlashCommand(
    "download",
    "Get every link for the installation"
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.acknowledge(true).followUp {
            embed {
                title = "Download"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.getGuild().getIconUrl(Image.Format.GIF)!!
                color = Color(0, 251, 255)
                thumbnail = thumb
                field {
                    name = "Tutorial-Video"
                    value = "https://www.youtube.com/watch?v=4dcUdz2Efdc"
                }
                field {
                    name = "Download-Link"
                    value = "http://noriskclient.de/downloads/client/latest.jar"
                }
                field {
                    name = "Download-Link (1.16.5, No Support)"
                    value = "https://noriskclient.de/downloads/1.16.5noriskclientv2.zip"
                }
                field {
                    name = "Installer"
                    value = "https://noriskclient.de/downloads/installer.jar"
                }
            }
        }
    }
}