package de.polylymer.database.data

import kotlinx.serialization.Serializable

@Serializable
data class BlacklistEntry(val key: String, val alias: Alias)
