package com.example.androidintern.datastore

import com.example.androidintern.datastore.model.LoginResponse
import com.example.androidintern.datastore.model.User
import com.example.androidintern.datastore.preferences.UserSessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    val userId: Flow<Int?>
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun getUser(id: Int): Result<User>
    suspend fun logout()
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userSessionManager: UserSessionManager
) : UserRepository {
    override val userId: Flow<Int?> = userSessionManager.userId

    override suspend fun login(username: String, password: String): Result<Unit> {
        return try {
            // 1. Login request
            val loginResponse = apiService.login(mapOf("username" to username, "password" to password))
            
            // 2. Get All users request to find matching user (simulating API limitation as requested)
            val usersResponse = apiService.getUsers()
            val matchedUser = usersResponse.users.find { it.username == username }
            
            if (matchedUser != null) {
                // 3. Save User id in Data Store
                userSessionManager.saveUserId(matchedUser.id)
                Result.success(Unit)
            } else {
                Result.failure(Exception("User not found in list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUser(id: Int): Result<User> {
        return try {
            val user = apiService.getUser(id)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        userSessionManager.clearSession()
    }
}
