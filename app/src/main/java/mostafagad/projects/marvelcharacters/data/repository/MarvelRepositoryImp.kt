package mostafagad.projects.marvelcharacters.data.repository

import mostafagad.projects.marvelcharacters.data.datasource.MarvelApis
import mostafagad.projects.marvelcharacters.data.datasource.model.CharactersDto
import mostafagad.projects.marvelcharacters.domain.repository.MarvelRepo
import javax.inject.Inject

class MarvelRepositoryImp @Inject constructor(
    private val api:MarvelApis
):MarvelRepo{
    override suspend fun getAllCharacters(offset: Int): CharactersDto {
       return api.getAllMarvelCharacters(offset = offset.toString())
    }

    override suspend fun getCharacterDetails(characterId: Long , type:String): CharactersDto {
        return api.getMarvelCharacterDetails(characterId = characterId, type = type)

    }


}