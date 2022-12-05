package mostafagad.projects.marvelcharacters

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        ctx = this
    }

    companion object{
        private lateinit var ctx: Context
        fun context(): Context {
            return ctx
        }
    }

}