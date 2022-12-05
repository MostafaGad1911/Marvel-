package mostafagad.projects.marvelcharacters.data.datasource.model

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)