package mostafagad.projects.marvelcharacters.domain.repository

import mostafagad.projects.marvelcharacters.data.datasource.model.CharactersDto

interface MarvelRepo {
    suspend fun getAllCharacters(offset:Int):CharactersDto
    suspend fun getCharacterDetails(characterId:Long , type:String):CharactersDto
}