package de.polylymer.commands.implementation

import de.polylymer.commands.SlashCommand
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.core.behavior.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.Image

@KordPreview
object CapeCommand : SlashCommand(
    "cape",
    "get a description how to upload your custom cape"
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.acknowledge(true).followUp {
            embed {
                title = "Custom Cape"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.getGuild().getIconUrl(Image.Format.GIF)!!
                thumbnail = thumb
                color = Color(0, 251, 255)
                field {
                    name = "Folgende Schritte erklären dir wie du korrekt ein Cape einreichst!"
                    value = "Lade dir zuerst das Template aus #faq oder mit /template in #botcommands herunter.\n" +
                            "Bearbeite das Cape mit Programmen wie z.B paint.net\n" +
                            "Gehe auf den Minecraft Server kitpvp.de und gebe /cape ein\n" +
                            "Klicke auf den Link im Chat und lade dort dein bearbeitetes Template hoch\n" +
                            "Fertig! Habe Geduld und warte bis dein Cape geprüft wurde. In #capelog kannst du überprüfen, ob dein Cape bereits überprüft wurde"
                }
            }
        }
    }
}