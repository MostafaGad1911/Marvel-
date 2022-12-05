package mostafagad.projects.marvelcharacters.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mostafagad.projects.marvelcharacters.BuildConfig
import mostafagad.projects.marvelcharacters.MarvelApplication
import mostafagad.projects.marvelcharacters.data.datasource.MarvelApis
import mostafagad.projects.marvelcharacters.data.repository.MarvelRepositoryImp
import mostafagad.projects.marvelcharacters.domain.repository.MarvelRepo
import mostafagad.projects.marvelcharacters.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext():Context = MarvelApplication.context()

    @Provides
    fun provideChucker(context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()
    }
    @Provides
    fun provideLogger(): Interceptor = if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY) else HttpLoggingInterceptor()

    @Provides
    fun provideHttpClient(chuckerInterceptor: ChuckerInterceptor ,logging: Interceptor): OkHttpClient =  OkHttpClient.Builder()
        .addInterceptor(logging)
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(false)
        .addInterceptor(chuckerInterceptor)
        .followSslRedirects(false)
        .writeTimeout(50, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideMarvelApis(okHttpClient: OkHttpClient):MarvelApis = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MarvelApis::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepo(apis: MarvelApis):MarvelRepo = MarvelRepositoryImp(api = apis)


}