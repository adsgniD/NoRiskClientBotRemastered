package de.polylymer.database.data

import kotlinx.serialization.Serializable

@Serializable
data class UserCapeData(val snowflake: String, var capesOfTheDay: Int)
