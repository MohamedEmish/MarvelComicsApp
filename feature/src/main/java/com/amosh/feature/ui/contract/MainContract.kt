package com.amosh.feature.ui.contract

import com.amosh.base.UiEffect
import com.amosh.base.UiEvent
import com.amosh.base.UiState
import com.amosh.feature.model.ComicsUiModel

/**
 * Contract of Main Screen
 */
class MainContract {

    sealed class Event : UiEvent {
        object OnFetchComicsList : Event()
        data class OnFetchComicDetails(val id: String) : Event()
    }

    data class State(
        val viewState: ComicsState,
        val selectedComics: ComicsUiModel? = null
    ) : UiState

    sealed class ComicsState {
        object Idle : ComicsState()
        object Loading : ComicsState()
        data class Success(val comicsList: List<ComicsUiModel>) : ComicsState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }
}