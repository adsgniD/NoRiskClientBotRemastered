package de.polylymer.listener

import de.polylymer.Manager
import de.polylymer.database.MongoManager
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on

object ReadyListener {

    init {
        Manager.client.on<ReadyEvent> {
            MongoManager.reloadBlacklist()
        }
    }

}