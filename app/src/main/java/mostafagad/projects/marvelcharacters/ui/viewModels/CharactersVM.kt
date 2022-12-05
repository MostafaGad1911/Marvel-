package mostafagad.projects.marvelcharacters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mostafagad.projects.marvelcharacters.domain.repository.MarvelRepo
import mostafagad.projects.marvelcharacters.utils.MarvelListState
import mostafagad.projects.marvelcharacters.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharactersVM @Inject constructor(private val marvelRepo: MarvelRepo) : ViewModel() {

    private val marvelState = MutableStateFlow(MarvelListState())
    var marveValue: StateFlow<MarvelListState> = marvelState

    init {
        getAllCharacters(offset = 20)
    }
    private fun getAllCharacters(offset: Int) = viewModelScope.launch(Dispatchers.IO) {
        invoke(offset = offset).collect{
            when(it){
                is Response.Success -> {
                    marvelState.value = MarvelListState(charactersList = it.data?: emptyList())
                }
                is Response.Loading -> {
                    marvelState.value = MarvelListState(isLoading = true)
                }
                is Response.Error -> {
                    marvelState.value = MarvelListState(error = it.message?:"An Unexpected Error")
                }
            }
        }

    }

    fun invoke(offset: Int): Flow<Response<List<mostafagad.projects.marvelcharacters.domain.model.Character>>> =
        flow {
            try {
                emit(Response.Loading())
                val list = marvelRepo.getAllCharacters(offset = offset).data.results.map { it.toCharacter() }
                emit(Response.Success(list))
            }catch (e:Exception){
                emit(Response.Error(e.printStackTrace().toString()))

            }
        }
}