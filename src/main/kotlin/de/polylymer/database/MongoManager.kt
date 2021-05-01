package de.polylymer.database

import de.polylymer.config.Config
import de.polylymer.database.data.Alias
import de.polylymer.database.data.Cape
import de.polylymer.database.data.UserCapeData
import net.axay.blueutils.database.mongodb.MongoDB
import org.litote.kmongo.bson
import org.litote.kmongo.findOne
import org.litote.kmongo.json

object MongoManager {

    val mongoDB = MongoDB(Config.databaseInfo)

    val aliases = mongoDB.getCollectionOrCreate<Alias>("NRC_ALIASES")

    val capesOfTheYear = mongoDB.getCollectionOrCreate<Cape>("NRC_CAPES_OF_THE_YEAR")

    val userCapeData = mongoDB.getCollectionOrCreate<UserCapeData>("NRC_USER_CAPE_DATA")

    fun increaseCapesOfTheDay(userID: String) {
        val userData = userCapeData.findOne("{\"snowflake\":\"${userID}\"}")
        if(userData == null) {
            userCapeData.insertOne(UserCapeData(userID, 1))
        } else {
            var capesOfTheDay = userData.capesOfTheDay
            capesOfTheDay++
            userCapeData.deleteOne(userData.json.bson)
            userCapeData.insertOne(UserCapeData(userID,capesOfTheDay))
        }
    }

    fun getUserData(userID: String): UserCapeData {
        return userCapeData.findOne("{\"snowflake\":\"${userID}\"}") ?: UserCapeData(userID, 0)
    }

}