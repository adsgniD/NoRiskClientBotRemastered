package de.polylymer.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import kotlin.reflect.full.isSubclassOf

enum class RequestMethod {
    GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH
}

val jsonDeserializer = Json {
    ignoreUnknownKeys = true

    isLenient = true
    allowSpecialFloatingPointValues = true
}

fun httpString(
    url: String,
    method: RequestMethod = RequestMethod.GET,
    charset: Charset = Charsets.UTF_8
) = httpRequest(url, method) { readText(charset) }

inline fun <reified T> httpJson(
    url: String,
    method: RequestMethod = RequestMethod.GET,
    charset: Charset = Charsets.UTF_8
): T = httpRequest(url, method) {
    if (T::class.isSubclassOf(JsonElement::class)) {
        val element = jsonDeserializer.parseToJsonElement(readText(charset))
        when {
            T::class.isSubclassOf(JsonPrimitive::class) -> element.jsonPrimitive
            T::class == JsonArray::class -> element.jsonArray
            T::class == JsonObject::class -> element.jsonObject
            else -> element
        } as T
    } else {
        jsonDeserializer.decodeFromString(readText())
    }
}

inline fun <T> httpRequest(
    url: String,
    method: RequestMethod = RequestMethod.GET,
    handler: InputStream.() -> T
): T = httpRequest(url, method).use(handler)

fun httpRequest(
    url: String,
    method: RequestMethod = RequestMethod.GET,
): InputStream {
    val con = URL(url).openConnection() as HttpURLConnection
    con.requestMethod = method.name
    con.connectTimeout = 10000
    con.readTimeout = 10000
    con.setRequestProperty("User-Agent", "BlauBot")
    con.connect()
    if (con.responseCode in 200..299) {
        return con.inputStream
    } else {
        throw IOException("Request to $url failed with status ${con.responseCode} ${con.responseMessage}")
    }
}

fun InputStream.readText(charset: Charset = Charsets.UTF_8) =
    readBytes().toString(charset)