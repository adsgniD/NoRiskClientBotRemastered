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
                /*
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
                 */
                description = "**CLIENT ENDE?!**\n" +
                        "\n" +
                        "Wie bestimmt einige schon wissen ist NoRisk jetzt bald Badlion Partner und damit sieht es für den NoRiskClient eher schlecht aus. Außerdem sind die Cape, Cosmetic und Download-Server aus und werden wahrscheinlich nicht wieder hochgefahren. Für alle die noch nicht bescheid wussten, jetzt wisst ihr bescheid!\n" +
                        "\n" +
                        "> **Was bedeutet das für den Client?**\n" +
                        "\n" +
                        "Der Client wird nicht mehr weiter entwickelt und Funktionen wie Capes werden wahrscheinlich wegfallen und zum download wird es ihn scheinbar auch nicht mehr geben\n" +
                        "\n" +
                        "> **Mehr Infos?!**\n" +
                        "\n" +
                        "Ich hab selbst nicht mehr Informationen weswegen ihr nur NoRisk selbst fragen könnt. Ich denke aber es gibt nichts zum fragen da es __**wahrscheinlich**__ so sein wird wie oben beschrieben. Ansonsten fragt ihn im Stream (bitte nicht 10 mal das selbe Fragen und auch nicht spammen wenn er eure Frage nicht das erste mal beantwortet hat) \n" +
                        "https://www.twitch.tv/norisk"
            }
        }
    }
}