package mostafagad.projects.marvelcharacters.data.datasource.model

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)