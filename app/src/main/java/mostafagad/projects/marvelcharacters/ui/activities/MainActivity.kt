package mostafagad.projects.marvelcharacters.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import mostafagad.projects.marvelcharacters.R
import mostafagad.projects.marvelcharacters.databinding.ActivityMainBinding
import mostafagad.projects.marvelcharacters.ui.viewModels.CharactersVM
import mostafagad.projects.marvelcharacters.ui.adapter.MarvelCharactersAdapter
import mostafagad.projects.marvelcharacters.ui.interfaces.MovieController
import mostafagad.projects.marvelcharacters.utils.Ext.hide
import mostafagad.projects.marvelcharacters.utils.Ext.show
import mostafagad.projects.marvelcharacters.utils.Ext.toast

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MovieController{

    private val viewModel: CharactersVM by viewModels()

    private val mainActivityDataBinding:ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this , R.layout.activity_main)
    }

    private val marvelCharactersList:ArrayList<mostafagad.projects.marvelcharacters.domain.model.Character> = ArrayList()
    private val marvelCharactersAdapter: MarvelCharactersAdapter by lazy {
        MarvelCharactersAdapter(charactersList = marvelCharactersList , movieController = this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        prepareMarvelCharsRV()
        collectMarvelCharacters()
        collectMarvelCharacters()
    }

    private fun prepareMarvelCharsRV(){
        val lytManager = GridLayoutManager(
            this, 2,
            GridLayoutManager.VERTICAL, false
        )
        mainActivityDataBinding.marvelCharactersRV.layoutManager = lytManager
        mainActivityDataBinding.marvelCharactersRV.adapter = marvelCharactersAdapter

    }
    private fun collectMarvelCharacters(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.marveValue.collect{
                when{
                    it.isLoading -> {
                       withContext(Dispatchers.Main){
                           mainActivityDataBinding.progressBar.show()
                       }
                    }
                    it.charactersList.isNotEmpty() -> {
                        withContext(Dispatchers.Main){
                            mainActivityDataBinding.progressBar.hide()
                            marvelCharactersList.clear()
                            marvelCharactersList.addAll(it.charactersList)
                            marvelCharactersAdapter.notifyItemRangeInserted(marvelCharactersList.size.plus(1) , it.charactersList.size)
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            mainActivityDataBinding.progressBar.hide()
                            it.error.toast(ctx = this@MainActivity)
                        }
                    }
                }
            }
        }

    }

    override fun getDetails(id: Long) {
        val detailsIntent = Intent(this , CharacterDetails::class.java)
        detailsIntent.putExtra("characterId" , id)
        startActivity(detailsIntent)
    }
}