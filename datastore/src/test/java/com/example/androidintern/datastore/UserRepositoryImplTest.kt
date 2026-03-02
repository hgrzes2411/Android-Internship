package com.example.androidintern.datastore

import com.example.androidintern.datastore.model.LoginResponse
import com.example.androidintern.datastore.model.User
import com.example.androidintern.datastore.model.UsersListResponse
import com.example.androidintern.datastore.preferences.UserSessionManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {

    private val apiService = mockk<ApiService>()
    private val userSessionManager = mockk<UserSessionManager>(relaxed = true)
    private lateinit var repository: UserRepositoryImpl

    private val testUser = User(
        id = 1,
        username = "testuser",
        email = "test@example.com",
        firstName = "Test",
        lastName = "User",
        image = "image.url"
    )

    @Before
    fun setup() {
        // Mock userId before repository creation because it's assigned in constructor
        every { userSessionManager.userId } returns flowOf(null)
        repository = UserRepositoryImpl(apiService, userSessionManager)
    }

    @Test
    fun `login success saves user id`() = runTest {
        val loginResponse = LoginResponse(1, "testuser", "e@e.com", "f", "l", "m", "i", "t")
        val usersListResponse = UsersListResponse(listOf(testUser), 1, 0, 1)

        coEvery { apiService.login(any()) } returns loginResponse
        coEvery { apiService.getUsers() } returns usersListResponse

        val result = repository.login("testuser", "password")

        assertTrue(result.isSuccess)
        coVerify { userSessionManager.saveUserId(1) }
    }

    @Test
    fun `login failure when user not found`() = runTest {
        val loginResponse = LoginResponse(1, "testuser", "e@e.com", "f", "l", "m", "i", "t")
        val usersListResponse = UsersListResponse(listOf(), 0, 0, 0)

        coEvery { apiService.login(any()) } returns loginResponse
        coEvery { apiService.getUsers() } returns usersListResponse

        val result = repository.login("testuser", "password")

        assertTrue(result.isFailure)
        assertEquals("User not found in list", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getUser success returns user`() = runTest {
        coEvery { apiService.getUser(1) } returns testUser

        val result = repository.getUser(1)

        assertTrue(result.isSuccess)
        assertEquals(testUser, result.getOrNull())
    }

    @Test
    fun `getUser failure`() = runTest {
        val exception = RuntimeException("API Error")
        coEvery { apiService.getUser(1) } throws exception

        val result = repository.getUser(1)

        assertTrue(result.isFailure)
        assertEquals("API Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `userId flow returns correct id`() = runTest {
        every { userSessionManager.userId } returns flowOf(123)
        // Need to recreate repository or mock the property if possible. 
        // Recreating is safer since it's assigned in init.
        val repo = UserRepositoryImpl(apiService, userSessionManager)

        val userId = repo.userId.first()

        assertEquals(123, userId)
    }

    @Test
    fun `logout clears session`() = runTest {
        repository.logout()

        coVerify { userSessionManager.clearSession() }
    }
}
