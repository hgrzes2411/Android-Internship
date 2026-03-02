package com.example.androidintern.datastore.preferences

import androidx.datastore.core.Serializer
import com.example.androidintern.datastore.model.User
import com.google.gson.Gson
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<User?> {
    override val defaultValue: User? = null
    private val gson = Gson()

    override suspend fun readFrom(input: InputStream): User? {
        return try {
            gson.fromJson(input.readBytes().decodeToString(), User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun writeTo(t: User?, output: OutputStream) {
        output.write(gson.toJson(t).encodeToByteArray())
    }
}
