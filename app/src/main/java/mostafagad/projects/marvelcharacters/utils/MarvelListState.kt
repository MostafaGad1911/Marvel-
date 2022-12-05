package mostafagad.projects.marvelcharacters.utils

data class MarvelListState(
    val isLoading:Boolean = false ,
    val  charactersList:List<mostafagad.projects.marvelcharacters.domain.model.Character> = emptyList() ,
    val error:String = ""
)