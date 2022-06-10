package com.amosh.feature.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.amosh.base.BaseViewModel
import com.amosh.common.Constants
import com.amosh.common.Mapper
import com.amosh.common.Resource
import com.amosh.common.addIfNotExist
import com.amosh.domain.entity.ComicsEntity
import com.amosh.domain.useCases.GetComicsListUseCase
import com.amosh.feature.model.ComicsUiModel
import com.amosh.feature.ui.contract.MainContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getComicsListUseCase: GetComicsListUseCase,
    private val comicsMapper: Mapper<ComicsEntity, ComicsUiModel>,
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    private val listOfComics: MutableList<ComicsUiModel> = mutableListOf()

    override fun createInitialState(): MainContract.State {
        return MainContract.State(
            viewState = MainContract.ComicsState.Idle,
            selectedComics = null
        )
    }

    init {
        fetchComicsList()
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnFetchComicDetails -> {
                val item = event.id
                setSelectedComics(comics = listOfComics.find { comics -> comics.id == item })
            }
            is MainContract.Event.OnFetchComicsList -> fetchComicsList()
        }
    }

    /**
     * Fetch comics
     */
    private fun fetchComicsList() =
        viewModelScope.launch {
            getComicsListUseCase.execute(
                mapOf(
                    Constants.LIMIT to Constants.PAGE_SIZE,
                    Constants.OFFSET to listOfComics.size
                )
            )
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            setState { copy(viewState = MainContract.ComicsState.Loading) }
                        }
                        is Resource.Empty -> {
                            setState { copy(viewState = MainContract.ComicsState.Idle) }
                        }
                        is Resource.Error -> {
                            setEffect { MainContract.Effect.ShowError(message = it.exception.message) }
                        }
                        is Resource.Success -> {
                            val comicsList = comicsMapper.fromList(it.data)
                            comicsList.forEach { comics ->
                                listOfComics.addIfNotExist(comics)
                            }

                            setState {
                                copy(
                                    viewState = MainContract.ComicsState.Success(
                                        comicsList = listOfComics.toList()
                                    )
                                )
                            }
                        }
                    }
                }
        }


    /**
     * Set selected comics item
     */
    private fun setSelectedComics(comics: ComicsUiModel?) {
        // Set State
        setState { copy(selectedComics = comics) }
    }
}