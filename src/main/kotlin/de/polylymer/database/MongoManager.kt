package de.polylymer.database

import de.polylymer.config.Config
import de.polylymer.database.data.Alias
import net.axay.blueutils.database.mongodb.MongoDB

object MongoManager {

    val mongoDB = MongoDB(Config.databaseInfo)

    val aliases = mongoDB.getCollectionOrCreate<Alias>("NRC_ALIASES")

}