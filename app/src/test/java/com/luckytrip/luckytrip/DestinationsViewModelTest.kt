package com.luckytrip.luckytrip

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luckytrip.luckytrip.ui.main.DestinationsViewModel
import com.luckytrip.luckytrip.ui.main.DestinationsViewState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class DestinationsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()


    private val receivedUiStates = mutableListOf<DestinationsViewState>()

    @ExperimentalCoroutinesApi
    @Test
    fun `should return Success when network request is successful`() =
        mainCoroutineScopeRule.runBlockingTest {
            val mainRepository = MainRepositoryMock()
            val viewModel =
                DestinationsViewModel(mainRepository, null)
            observeViewModel(viewModel)

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.tryAgainDestinations()

            advanceUntilIdle()

            assertEquals(
                listOf(DestinationsViewState.DestinationsResponseData(listOf())),
                receivedUiStates
            )
        }

    private fun observeViewModel(viewModel: DestinationsViewModel) {
        viewModel.destinationsViewState.observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}