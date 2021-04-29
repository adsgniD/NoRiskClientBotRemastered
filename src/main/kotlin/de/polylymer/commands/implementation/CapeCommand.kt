package de.polylymer.commands.implementation

import de.polylymer.KordEXT.guild
import de.polylymer.commands.SlashCommand
import dev.kord.common.Color
import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.interaction.followUp
import dev.kord.core.entity.interaction.Interaction
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.Image

@KordPreview
object CapeCommand : SlashCommand(
    name = "cape",
    description = "Get a description how to upload your custom cape"
) {
    override suspend fun handleCommand(interaction: Interaction) {
        interaction.ackowledgePublic().followUp {
            embed {
                title = "Custom Cape"
                val thumb = EmbedBuilder.Thumbnail()
                thumb.url = interaction.guild().getIconUrl(Image.Format.GIF)!!
                thumbnail = thumb
                val foot = EmbedBuilder.Footer()
                foot.icon = interaction.guild().getIconUrl(Image.Format.GIF)!!
                foot.text = interaction.guild().name
                footer = foot
                color = Color(0, 251, 255)
                field {
                    name = "Folgende Schritte erklären dir wie du korrekt ein Cape einreichst!"
                    value = "1. Lade dir zuerst das Template aus " + interaction.guild().getChannel(
                        Snowflake("774292827524956181")).mention + " oder mit /template in " + interaction.guild().getChannel(
                        Snowflake("774273609467691018")).mention + " herunter.\n" +
                            "2. Bearbeite das Cape mit Programmen wie z.B paint.net\n" +
                            "3. Gehe auf den Minecraft Server kitpvp.de und gebe /cape ein\n" +
                            "4. Klicke auf den Link im Chat und lade dort dein bearbeitetes Template hoch\n" +
                            "5. Fertig! Habe Geduld und warte bis dein Cape geprüft wurde. In " + interaction.guild().getChannel(
                        Snowflake("774295768181899325")).mention + " kannst du überprüfen, ob dein Cape bereits überprüft wurde"
                }
            }
        }
    }
}