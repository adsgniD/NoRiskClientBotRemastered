package de.polylymer.database.data

import de.polylymer.Manager
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.ban
import dev.kord.rest.builder.ban.BanCreateBuilder
import kotlinx.serialization.Serializable

@Serializable
data class Report(val id: Int, val userID: String)

suspend fun Report.ban() {
    println("Banned $id")
    Manager.client.getGuild(Snowflake("774271756549619722"))!!.asGuild().getMember(Snowflake(id.toString())).ban {
        reason = "Other / Automated by NoRiskClientBot"
    }
}
