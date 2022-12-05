package mostafagad.projects.marvelcharacters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mostafagad.projects.marvelcharacters.domain.repository.MarvelRepo
import mostafagad.projects.marvelcharacters.utils.MarvelDetailsListState
import mostafagad.projects.marvelcharacters.utils.Response
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsVM @Inject constructor(private val marvelRepo: MarvelRepo) : ViewModel() {

    private val marvelComicsState = MutableStateFlow(MarvelDetailsListState())
    var marvelComicsValue: StateFlow<MarvelDetailsListState> = marvelComicsState

    private val marvelEventsState = MutableStateFlow(MarvelDetailsListState())
    var marvelEventsValue: StateFlow<MarvelDetailsListState> = marvelEventsState

    private val marvelSeriesState = MutableStateFlow(MarvelDetailsListState())
    var marvelSeriesValue: StateFlow<MarvelDetailsListState> = marvelSeriesState

    private val marvelStoriesState = MutableStateFlow(MarvelDetailsListState())
    var marvelStoriesValue: StateFlow<MarvelDetailsListState> = marvelStoriesState


    fun getCharacterComics(characterId:Long) = viewModelScope.launch(Dispatchers.IO) {
        invoke(characterId =  characterId , type = "comics").collect{
            when(it){
                is Response.Success -> {
                    marvelComicsState.value = MarvelDetailsListState(charactersList = it.data)
                }
                is Response.Loading -> {
                    marvelComicsState.value = MarvelDetailsListState(isLoading = true)
                }
                is Response.Error -> {
                    marvelComicsState.value = MarvelDetailsListState(error = it.message?:"An Unexpected Error")
                }
            }
        }

    }
    fun getCharacterEvents(characterId:Long) = viewModelScope.launch(Dispatchers.IO) {
        invoke(characterId =  characterId , type = "events").collect{
            when(it){
                is Response.Success -> {
                    marvelEventsState.value = MarvelDetailsListState(charactersList = it.data)
                }
                is Response.Loading -> {
                    marvelEventsState.value = MarvelDetailsListState(isLoading = true)
                }
                is Response.Error -> {
                    marvelEventsState.value = MarvelDetailsListState(error = it.message?:"An Unexpected Error")
                }
            }
        }

    }
    fun getCharacterSeries(characterId:Long) = viewModelScope.launch(Dispatchers.IO) {
        invoke(characterId =  characterId , type = "series").collect{
            when(it){
                is Response.Success -> {
                    marvelSeriesState.value = MarvelDetailsListState(charactersList = it.data)
                }
                is Response.Loading -> {
                    marvelSeriesState.value = MarvelDetailsListState(isLoading = true)
                }
                is Response.Error -> {
                    marvelSeriesState.value = MarvelDetailsListState(error = it.message?:"An Unexpected Error")
                }
            }
        }

    }
    fun getCharacterStories(characterId:Long) = viewModelScope.launch(Dispatchers.IO) {
        invoke(characterId =  characterId , type = "stories").collect{
            when(it){
                is Response.Success -> {
                    marvelStoriesState.value = MarvelDetailsListState(charactersList = it.data)
                }
                is Response.Loading -> {
                    marvelStoriesState.value = MarvelDetailsListState(isLoading = true)
                }
                is Response.Error -> {
                    marvelStoriesState.value = MarvelDetailsListState(error = it.message?:"An Unexpected Error")
                }
            }
        }

    }

    fun invoke(characterId:Long , type:String): Flow<Response<List<mostafagad.projects.marvelcharacters.data.datasource.model.Result>>> =
        flow {
            try {
                emit(Response.Loading())
                val list = marvelRepo.getCharacterDetails(characterId = characterId , type = type).data.results
                emit(Response.Success(list))
            }catch (e:Exception){
                emit(Response.Error(e.printStackTrace().toString()))

            }
        }
}