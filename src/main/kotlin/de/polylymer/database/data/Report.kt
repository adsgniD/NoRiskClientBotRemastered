package de.polylymer.database.data

import de.polylymer.Manager
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.ban

data class Report(val id: Int, val userID: String)

suspend fun Report.ban() {
    Manager.client.getGuild(Snowflake("774271756549619722"))!!.asGuild().getMember(Snowflake(id.toString())).ban {
        deleteMessagesDays = 1
        reason = "Banned through report"
    }
}
