package com.example.androidintern.viewmodels

import app.cash.turbine.test
import com.example.androidintern.datastore.UserRepository
import com.example.androidintern.datastore.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProfileViewModel
    
    // --- Mocked Approach ---
    private val mockedUserRepository = mockk<UserRepository>(relaxed = true)
    
    // --- Fake Approach ---
    private class FakeUserRepository : UserRepository {
        private val _userId = MutableStateFlow<Int?>(null)
        override val userId: Flow<Int?> = _userId

        var userResult: Result<User> = Result.failure(Exception("Not initialized"))
        var loginResult: Result<Unit> = Result.success(Unit)
        var logoutCalled = false

        fun emitUserId(id: Int?) {
            _userId.value = id
        }

        override suspend fun login(username: String, password: String): Result<Unit> = loginResult

        override suspend fun getUser(id: Int): Result<User> = userResult

        override suspend fun logout() {
            logoutCalled = true
        }
    }
    
    private val fakeUserRepository = FakeUserRepository()

    private val testUser = User(
        id = 1,
        username = "testuser",
        email = "test@example.com",
        firstName = "Test",
        lastName = "User",
        image = ""
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- Tests using Mocked Repository ---

    @Test
    fun `when userId is emitted, fetchUser is called and uiState is updated (Mocked)`() = runTest {
        val userIdFlow = MutableStateFlow<Int?>(null)
        every { mockedUserRepository.userId } returns userIdFlow
        coEvery { mockedUserRepository.getUser(1) } returns Result.success(testUser)

        viewModel = ProfileViewModel(mockedUserRepository)
        
        viewModel.uiState.test {
            assertEquals(ProfileUiState(), awaitItem())

            userIdFlow.value = 1
            
            // Wait for coroutines to complete
            advanceUntilIdle()
            
            val state = expectMostRecentItem()
            assertEquals(testUser, state.user)
            assertEquals(false, state.isLoading)
        }
        
        coVerify { mockedUserRepository.getUser(1) }
    }

    @Test
    fun `login success updates uiState and calls onSuccess (Mocked)`() = runTest {
        every { mockedUserRepository.userId } returns MutableStateFlow(null)
        coEvery { mockedUserRepository.login(any(), any()) } returns Result.success(Unit)
        
        viewModel = ProfileViewModel(mockedUserRepository)
        var successCalled = false

        viewModel.login("user", "pass") { successCalled = true }
        
        advanceUntilIdle()
        
        viewModel.uiState.test {
            val state = expectMostRecentItem()
            assertEquals(false, state.isLoginLoading)
        }
        
        assertEquals(true, successCalled)
    }

    // --- Tests using Fake Repository ---

    @Test
    fun `when userId is null, user in uiState is null (Fake)`() = runTest {
        viewModel = ProfileViewModel(fakeUserRepository)
        
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertNull(initialState.user)
            
            fakeUserRepository.emitUserId(1)
            fakeUserRepository.userResult = Result.success(testUser)
            
            advanceUntilIdle()
            
            val stateWithUser = expectMostRecentItem()
            assertEquals(testUser, stateWithUser.user)

            fakeUserRepository.emitUserId(null)
            advanceUntilIdle()
            
            val stateNullUser = expectMostRecentItem()
            assertNull(stateNullUser.user)
        }
    }

    @Test
    fun `logout calls repository logout (Fake)`() = runTest {
        viewModel = ProfileViewModel(fakeUserRepository)
        
        viewModel.logout()
        advanceUntilIdle()
        
        assertEquals(true, fakeUserRepository.logoutCalled)
    }
    
    @Test
    fun `login failure updates loginError (Fake)`() = runTest {
        viewModel = ProfileViewModel(fakeUserRepository)
        val errorMessage = "Invalid credentials"
        fakeUserRepository.loginResult = Result.failure(Exception(errorMessage))
        
        viewModel.login("wrong", "wrong") {}
        
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = expectMostRecentItem()
            assertEquals(errorMessage, state.loginError)
        }
    }
}
