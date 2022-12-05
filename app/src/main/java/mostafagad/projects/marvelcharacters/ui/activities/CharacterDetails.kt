package mostafagad.projects.marvelcharacters.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mostafagad.projects.marvelcharacters.databinding.ActivityCharacterDetailsBinding
import mostafagad.projects.marvelcharacters.ui.adapter.MarvelCharacterComicsEventStoriesAdapter
import mostafagad.projects.marvelcharacters.ui.viewModels.CharacterDetailsVM
import mostafagad.projects.marvelcharacters.utils.Ext.hide
import mostafagad.projects.marvelcharacters.utils.Ext.show
import mostafagad.projects.marvelcharacters.utils.Ext.toast

@AndroidEntryPoint
class CharacterDetails : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding

    private val viewModel: CharacterDetailsVM by viewModels()

    private val characterId:Long by lazy {
        intent.getLongExtra("characterId" , 0)
    }

    private val marvelCharacterComicsList: ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> = ArrayList()
    private val marvelCharacterComicsAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterComicsList)
    }

    private val marvelCharacterEventsList:ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> = ArrayList()
    private val marvelCharacterEventsAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterEventsList)
    }

    private val marvelCharacterStoriesList:ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> = ArrayList()
    private val marvelCharacterStoriesAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterStoriesList)
    }

    private val marvelCharacterSeriesList:ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> = ArrayList()
    private val marvelCharacterSeriesAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterSeriesList )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareMarvelCharRVS()
        loadMarvelCharacterDetails()
        collectMarvelCharacterDetails()
    }

    private fun prepareMarvelCharRVS(){
        binding.marvelComicsRV.adapter = marvelCharacterComicsAdapter
        binding.marvelEventsRV.adapter = marvelCharacterEventsAdapter
        binding.marvelSeriesRV.adapter = marvelCharacterSeriesAdapter
        binding.marvelStoriesRV.adapter = marvelCharacterStoriesAdapter
    }


    private fun collectMarvelCharacterDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.marvelComicsValue.collect{
                when{
                    it.isLoading -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.show()
                        }
                    }
                    it.charactersList?.isNotEmpty() == true -> {
                        withContext(Dispatchers.Main){
                            binding.marvelEmptyComicsTxt.hide()
                            binding.detailsProgressBar.hide()
                            marvelCharacterComicsList.clear()
                            marvelCharacterComicsList.addAll(it.charactersList)
                            marvelCharacterComicsAdapter.notifyItemRangeInserted(
                                marvelCharacterComicsList.size.plus(1), it.charactersList.size)

                        }
                    }
                    it.charactersList.isNullOrEmpty() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            binding.marvelEmptyComicsTxt.show()
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            it.error.toast(ctx = this@CharacterDetails)

                        }
                    }
                }

            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.marvelEventsValue.collect{
                when{
                    it.isLoading -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.show()
                        }
                    }
                    it.charactersList?.isNotEmpty() == true -> {
                        withContext(Dispatchers.Main){
                            binding.marvelEmptyEventsTxt.hide()
                            binding.detailsProgressBar.hide()
                            marvelCharacterEventsList.clear()
                            marvelCharacterEventsList.addAll(it.charactersList)
                            marvelCharacterEventsAdapter.notifyItemRangeInserted(marvelCharacterEventsList.size.plus(1) , it.charactersList.size)

                        }
                    }
                    it.charactersList.isNullOrEmpty() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            binding.marvelEmptyEventsTxt.show()
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            it.error.toast(ctx = this@CharacterDetails)

                        }
                    }
                }

            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.marvelSeriesValue.collect{
                when{
                    it.isLoading -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.show()
                        }
                    }
                    it.charactersList?.isNotEmpty() == true -> {
                        withContext(Dispatchers.Main){
                            binding.marvelEmptySeriesTxt.hide()
                            binding.detailsProgressBar.hide()
                            marvelCharacterSeriesList.clear()
                            marvelCharacterSeriesList.addAll(it.charactersList)
                            marvelCharacterSeriesAdapter.notifyItemRangeInserted(marvelCharacterSeriesList.size.plus(1) , it.charactersList.size)

                        }
                    }
                    it.charactersList.isNullOrEmpty() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            binding.marvelEmptySeriesTxt.show()
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            it.error.toast(ctx = this@CharacterDetails)

                        }
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.marvelStoriesValue.collect{
                when{
                    it.isLoading -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.show()
                        }
                    }
                    it.charactersList?.isNotEmpty() == true -> {
                        withContext(Dispatchers.Main){
                            binding.marvelEmptyStoriesTxt.hide()
                            binding.detailsProgressBar.hide()
                            marvelCharacterStoriesList.clear()
                            marvelCharacterStoriesList.addAll(it.charactersList)
                            marvelCharacterStoriesAdapter.notifyItemRangeInserted(marvelCharacterStoriesList.size.plus(1) , it.charactersList.size)

                        }
                    }
                    it.charactersList.isNullOrEmpty() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            binding.marvelEmptyStoriesTxt.show()
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            binding.detailsProgressBar.hide()
                            it.error.toast(ctx = this@CharacterDetails)

                        }
                    }
                }
            }
        }
    }

    private fun loadMarvelCharacterDetails(){
       CoroutineScope(Dispatchers.IO).launch {
           withContext(Dispatchers.Default) {
               viewModel.getCharacterComics(characterId = characterId)
           }
           withContext(Dispatchers.Default) {
               viewModel.getCharacterEvents(characterId = characterId)
           }
           withContext(Dispatchers.Default) {
               viewModel.getCharacterStories(characterId = characterId)
           }
           withContext(Dispatchers.Default) {
               viewModel.getCharacterSeries(characterId = characterId)
           }
       }
    }
}