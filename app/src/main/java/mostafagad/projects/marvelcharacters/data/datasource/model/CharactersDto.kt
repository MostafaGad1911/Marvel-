package mostafagad.projects.marvelcharacters.data.datasource.model

data class CharactersDto(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)