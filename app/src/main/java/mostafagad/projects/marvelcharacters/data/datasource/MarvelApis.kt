package mostafagad.projects.marvelcharacters.data.datasource

import mostafagad.projects.marvelcharacters.data.datasource.model.CharactersDto
import mostafagad.projects.marvelcharacters.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApis {

    @GET("/v1/public/characters")
    suspend fun getAllMarvelCharacters(
        @Query("apikey") apiKey:String = Constants.API_KEY ,
        @Query("ts") timeStamp:String = Constants.timeStamp,
        @Query("hash") hash:String = Constants.hash() ,
        @Query("offset") offset:String
    ):CharactersDto

    @GET("/v1/public/characters/{id}/{type}")
    suspend fun getMarvelCharacterDetails(
        @Path("id") characterId:Long? = null ,
        @Path("type") type:String? = null ,
        @Query("apikey") apiKey:String = Constants.API_KEY ,
        @Query("ts") timeStamp:String = Constants.timeStamp,
        @Query("hash") hash:String = Constants.hash() ,
    ):CharactersDto

}