package academy.bangkit.coursemate.model

data class RecommendationResponse(
    val predicted_category: String,
    val recommended_courses: List<Course>
)
