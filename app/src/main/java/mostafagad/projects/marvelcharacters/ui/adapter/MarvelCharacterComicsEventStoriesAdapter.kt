package mostafagad.projects.marvelcharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import mostafagad.projects.marvelcharacters.R
import mostafagad.projects.marvelcharacters.data.datasource.model.Result
import mostafagad.projects.marvelcharacters.databinding.MarvelDetailsItemBinding

class MarvelCharacterComicsEventStoriesAdapter(private val marveLCharactersDetailsList: ArrayList<Result?>?) :
    RecyclerView.Adapter<MarvelCharacterComicsEventStoriesAdapter.CharacterViewHolder>() {

    private lateinit var scaleAnim: Animation
    private var mLastPosition: Int = -1
    private lateinit var recyclerView: RecyclerView


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class CharacterViewHolder(private val marvelDetailsItemBinding: MarvelDetailsItemBinding) : ViewHolder(marvelDetailsItemBinding.root) {
        fun bind(item: Result?){
            marvelDetailsItemBinding.detailsItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val marvelCharacterBinding = MarvelDetailsItemBinding.inflate(inflater , parent , false)
        return CharacterViewHolder(marvelCharacterBinding)

    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        startAnimation(vew = holder.itemView, position = position)
        val itemWidth: Int = (recyclerView.layoutManager?.width!! - 62) / 2
        holder.itemView.layoutParams.width = itemWidth - 10

        holder.bind(item = marveLCharactersDetailsList?.get(position))
    }

    override fun getItemCount(): Int = marveLCharactersDetailsList?.size!!

    private fun startAnimation(vew:View, position:Int){
        if (position > mLastPosition) {
            scaleAnim = AnimationUtils.loadAnimation(vew.context , R.anim.fall_down)
            vew.startAnimation(scaleAnim)
            mLastPosition = position
        }
    }

}