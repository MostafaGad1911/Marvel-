package mostafagad.projects.marvelcharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import mostafagad.projects.marvelcharacters.R
import mostafagad.projects.marvelcharacters.databinding.MarvelItemBinding
import mostafagad.projects.marvelcharacters.ui.interfaces.MovieController

class MarvelCharactersAdapter(private val charactersList: ArrayList<mostafagad.projects.marvelcharacters.domain.model.Character> , val movieController: MovieController? = null) :
    RecyclerView.Adapter<MarvelCharactersAdapter.CharacterViewHolder>() {

    private lateinit var scaleAnim: Animation
    private var mLastPosition: Int = -1
    private lateinit var recyclerView: RecyclerView

    private val _movieController:MovieController? = movieController

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    inner class CharacterViewHolder(private val marvelItemBinding: MarvelItemBinding) : ViewHolder(marvelItemBinding.root) {
        fun bind(item:mostafagad.projects.marvelcharacters.domain.model.Character){
            marvelItemBinding.marvelItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val marvelCharacterBinding = MarvelItemBinding.inflate(inflater , parent , false)
        return CharacterViewHolder(marvelCharacterBinding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        startAnimation(vew = holder.itemView, position = position)
        val itemWidth: Int = (recyclerView.layoutManager?.width!! - 62) / 2
        holder.itemView.layoutParams.width = itemWidth - 10

        holder.bind(item = charactersList[position])
        holder.itemView.setOnClickListener{
            _movieController?.getDetails(id = charactersList[position].id.toLong())
        }
    }

    override fun getItemCount(): Int = charactersList.size

    private fun startAnimation(vew:View, position:Int){
        if (position > mLastPosition) {
            scaleAnim = AnimationUtils.loadAnimation(vew.context , R.anim.fall_down)
            vew.startAnimation(scaleAnim)
            mLastPosition = position
        }
    }

}