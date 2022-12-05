package mostafagad.projects.marvelcharacters.utils

import mostafagad.projects.marvelcharacters.data.datasource.model.Result

data class MarvelDetailsListState(
    val isLoading: Boolean = false,
    val charactersList: List<Result?>? = null,
    val error: String = "",
)