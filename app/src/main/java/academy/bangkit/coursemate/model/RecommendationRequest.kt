package academy.bangkit.coursemate.model

data class RecommendationRequest(
    val interest: String,
    val course_type: String,
    val duration: String
)
