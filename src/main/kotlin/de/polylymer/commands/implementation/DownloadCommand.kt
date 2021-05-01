package de.polylymer.commands.implementation

import de.polylymer.KordEXT.guild
import de.polylymer.commands.SlashCommand
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.Image
import dev.kord.rest.builder.message.EmbedBuilder

@KordPreview
object DownloadCommand : SlashCommand(
    "download",
    "Get every link for the installation"
) {

    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            embed {
                title = "Download"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.guild().getIconUrl(Image.Format.GIF)!!
                color = Color(0, 251, 255)
                thumbnail = thumb
                val foot = EmbedBuilder.Footer()
                foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                foot.text = interaction.guild().name
                footer = foot
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
                    name = "2.0-DevBuild (1.16.5)"
                    value = "Klicke [hier](https://workupload.com/file/RzYjDg6v4UK) um dir Devbuilds herunterzuladen.\nMehr Informationen gibt es unter `/tag post devbuilds`"
                }
                field {
                    name = "Installer"
                    value = "https://noriskclient.de/downloads/installer.jar"
                }
            }
        }
    }
}