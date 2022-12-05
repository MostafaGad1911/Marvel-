package mostafagad.projects.marvelcharacters.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mostafagad.projects.marvelcharacters.R
import mostafagad.projects.marvelcharacters.databinding.ActivityCharacterDetailsBinding
import mostafagad.projects.marvelcharacters.ui.adapter.MarvelCharacterComicsEventStoriesAdapter
import mostafagad.projects.marvelcharacters.ui.viewModels.CharacterDetailsVM
import mostafagad.projects.marvelcharacters.utils.Ext.hide
import mostafagad.projects.marvelcharacters.utils.Ext.show
import mostafagad.projects.marvelcharacters.utils.Ext.toast

@AndroidEntryPoint
class CharacterDetails : AppCompatActivity() {

    private val binding: ActivityCharacterDetailsBinding by lazy {
        DataBindingUtil.setContentView(this , R.layout.activity_character_details)
    }

    private val viewModel: CharacterDetailsVM by viewModels()

    private val characterId: Long by lazy {
        intent.getLongExtra("characterId", 0)
    }

    private val marvelCharacterComicsList: ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> =
        ArrayList()
    private val marvelCharacterComicsAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterComicsList)
    }

    private val marvelCharacterEventsList: ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> =
        ArrayList()
    private val marvelCharacterEventsAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterEventsList)
    }

    private val marvelCharacterStoriesList: ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> =
        ArrayList()
    private val marvelCharacterStoriesAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterStoriesList)
    }

    private val marvelCharacterSeriesList: ArrayList<mostafagad.projects.marvelcharacters.data.datasource.model.Result?> =
        ArrayList()
    private val marvelCharacterSeriesAdapter: MarvelCharacterComicsEventStoriesAdapter by lazy {
        MarvelCharacterComicsEventStoriesAdapter(marveLCharactersDetailsList = marvelCharacterSeriesList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        prepareMarvelCharRVS()
        loadMarvelCharacterDetails()
    }

    private fun prepareMarvelCharRVS() {
        binding.marvelComicsRV.adapter = marvelCharacterComicsAdapter
        binding.marvelEventsRV.adapter = marvelCharacterEventsAdapter
        binding.marvelSeriesRV.adapter = marvelCharacterSeriesAdapter
        binding.marvelStoriesRV.adapter = marvelCharacterStoriesAdapter
    }


    private suspend fun collectMarvelCharacterComics() {
        viewModel.marvelComicsValue.collect {
            when {
                it.isLoading -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.show()
                    }
                }

                it.charactersList.isNullOrEmpty() -> {
                    withContext(Dispatchers.Main) {
                        Log.i("COMICS_EMPTY", "test")
                        binding.detailsProgressBar.hide()
                        if (marvelCharacterComicsList.isEmpty()) binding.marvelEmptyComicsTxt.show() else binding.marvelEmptyComicsTxt.hide()
                    }
                }
                it.charactersList.isNotEmpty() -> {
                    withContext(Dispatchers.Main) {
                        Log.i("COMICS_NOT_EMPTY", "test")
                        binding.detailsProgressBar.hide()
                        marvelCharacterComicsList.clear()
                        marvelCharacterComicsList.addAll(it.charactersList)
                        if (marvelCharacterComicsList.isEmpty()) binding.marvelEmptyComicsTxt.show() else binding.marvelEmptyComicsTxt.hide()
                        marvelCharacterComicsAdapter.notifyItemRangeInserted(
                            marvelCharacterComicsList.size.plus(1), it.charactersList.size
                        )

                    }
                }
                it.error.isNotBlank() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        it.error.toast(ctx = this@CharacterDetails)

                    }
                }
            }

        }
    }

    private suspend fun collectMarvelCharacterEvents() {
        viewModel.marvelEventsValue.collect {
            when {
                it.isLoading -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.show()
                    }
                }
                it.charactersList?.isNotEmpty() == true -> {
                    withContext(Dispatchers.Main) {
                        binding.marvelEmptyEventsTxt.hide()
                        binding.detailsProgressBar.hide()
                        marvelCharacterEventsList.clear()
                        marvelCharacterEventsList.addAll(it.charactersList)
                        marvelCharacterEventsAdapter.notifyItemRangeInserted(
                            marvelCharacterEventsList.size.plus(1),
                            it.charactersList.size
                        )

                    }
                }
                it.charactersList.isNullOrEmpty() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        binding.marvelEmptyEventsTxt.show()
                    }
                }
                it.error.isNotBlank() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        it.error.toast(ctx = this@CharacterDetails)

                    }
                }
            }

        }
    }

    private suspend fun collectMarvelCharacterSeries() {
        viewModel.marvelSeriesValue.collect {
            when {
                it.isLoading -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.show()
                    }
                }
                it.charactersList?.isNotEmpty() == true -> {
                    withContext(Dispatchers.Main) {
                        binding.marvelEmptySeriesTxt.hide()
                        binding.detailsProgressBar.hide()
                        marvelCharacterSeriesList.clear()
                        marvelCharacterSeriesList.addAll(it.charactersList)
                        marvelCharacterSeriesAdapter.notifyItemRangeInserted(
                            marvelCharacterSeriesList.size.plus(1),
                            it.charactersList.size
                        )

                    }
                }
                it.charactersList.isNullOrEmpty() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        binding.marvelEmptySeriesTxt.show()
                    }
                }
                it.error.isNotBlank() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        it.error.toast(ctx = this@CharacterDetails)

                    }
                }
            }
        }
    }

    private suspend fun collectMarvelCharacterStories() {
        viewModel.marvelStoriesValue.collect {
            when {
                it.isLoading -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.show()
                    }
                }
                it.charactersList?.isNotEmpty() == true -> {
                    withContext(Dispatchers.Main) {
                        binding.marvelEmptyStoriesTxt.hide()
                        binding.detailsProgressBar.hide()
                        marvelCharacterStoriesList.clear()
                        marvelCharacterStoriesList.addAll(it.charactersList)
                        marvelCharacterStoriesAdapter.notifyItemRangeInserted(
                            marvelCharacterStoriesList.size.plus(1),
                            it.charactersList.size
                        )

                    }
                }
                it.charactersList.isNullOrEmpty() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        binding.marvelEmptyStoriesTxt.show()
                    }
                }
                it.error.isNotBlank() -> {
                    withContext(Dispatchers.Main) {
                        binding.detailsProgressBar.hide()
                        it.error.toast(ctx = this@CharacterDetails)

                    }
                }
            }
        }
    }


    private fun loadMarvelCharacterDetails() {
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
            withContext(Dispatchers.Main) {
                collectMarvelCharacterComics()
            }
            withContext(Dispatchers.Main) {
                collectMarvelCharacterEvents()
            }
            withContext(Dispatchers.Main) {
                collectMarvelCharacterSeries()
            }
            withContext(Dispatchers.Main) {
                collectMarvelCharacterStories()
            }
        }
    }
}