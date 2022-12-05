package mostafagad.projects.marvelcharacters.data.datasource.model

import mostafagad.projects.marvelcharacters.domain.model.Character

data class Result(
    val comics: Comics,
    val description: String,
    val title: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
){
    fun toCharacter():mostafagad.projects.marvelcharacters.domain.model.Character{
        return Character(id = id , name = name , description = description , thumbnail = thumbnail?.path.toString(), thumbnailExt = thumbnail?.extension.toString(), comics = comics.items.map { it.name })
    }
}