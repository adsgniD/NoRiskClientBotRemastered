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
object RolesCommand : SlashCommand(
    name = "roles",
    description = "Explains the roles"
){
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            embed{
                title = "Rollen"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.guild().getIconUrl(Image.Format.GIF)!!
                color = Color(0, 251, 255)
                thumbnail = thumb
                val foot = EmbedBuilder.Footer()
                foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                foot.text = interaction.guild().name
                footer = foot

                field{
                    name = "Admin"
                    value = "Die Admins sind die Administratoren des Clients. Sie sind für die Leitung und Verwaltung des Clients verantwortlich. Bitte beachte, dass sie nicht gepingt werden wollen!"
                }
                field{
                    name = "Coder"
                    value = "Die Coder sind die Entwickler vom Client"
                }
                field{
                    name = "Helper"
                    value = "Die Helper helfen dir mit Fragen über den Client. Manche Helper und Supporter haben einen blauen Namen, sie erstellen zusätzlich noch Cosmetics für den Client. \n" +
                            "<Pausiert>"
                }
                field{
                    name = "Supporter"
                    value = "Die Supporter sind eigentlich Moderatoren des HGLabor.de Servers. Bei Fragen solltest du dich lieber an die Helper wenden."
                }
                field{
                    name = "Contributor"
                    value = "Die Contributoren sind Leute die sich aktiv für den Erhalt des NoRiskClient Bots einsetzen. Sie bringen weitere Ideen ein und coden teilweise selbst am Bot.\n" +
                            "Wenn du dich auch am Bot beteiligen willst, kannst du mal auf GitHub vorbeischauen, alles weitere ist dort erklärt:\n" +
                            "https://github.com/PolylymerDE/NoRiskClientBotRemastered"
                }
                field{
                    name = "Community"
                    value = "Alte Member, die früher Capes für die Community erstellt haben.\n" +
                            "<Pausiert>"
                }
            }
        }
    }
}