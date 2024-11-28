package academy.bangkit.coursemate.network

import academy.bangkit.coursemate.model.RecommendationRequest
import academy.bangkit.coursemate.model.RecommendationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendationApiService {
    @POST("api/recommend")
    fun getRecommendations(@Body request: RecommendationRequest): Call<RecommendationResponse>
}
