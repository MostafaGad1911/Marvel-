package mostafagad.projects.marvelcharacters.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import mostafagad.projects.marvelcharacters.R
import mostafagad.projects.marvelcharacters.ui.interfaces.MovieController
import mostafagad.projects.marvelcharacters.utils.Ext.loadImg
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

    class CharacterViewHolder(itemView: View) : ViewHolder(itemView) {
        val marvelCharImg = itemView.findViewById<ImageView>(R.id.marvelCharImg)!!
        val movieName = itemView.findViewById<TextView>(R.id.movieCharTxt)!!

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.marvel_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        startAnimation(vew = holder.itemView, position = position)
        val itemWidth: Int = (recyclerView.layoutManager?.width!! - 62) / 2
        holder.itemView.layoutParams.width = itemWidth - 10

        val character = charactersList[position]
        holder.movieName.text = character.name
        "${character.thumbnail}/portrait_xlarge.${character.thumbnailExt}".loadImg(img = holder.marvelCharImg,
            ctx = holder.itemView.context)

        holder.itemView.setOnClickListener{
            _movieController?.getDetails(id = character.id.toLong())
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