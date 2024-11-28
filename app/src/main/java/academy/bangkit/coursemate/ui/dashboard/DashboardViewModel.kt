package academy.bangkit.coursemate.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import academy.bangkit.coursemate.model.RecommendationRequest
import academy.bangkit.coursemate.model.RecommendationResponse
import academy.bangkit.coursemate.repository.RecommendationRepository

class DashboardViewModel : ViewModel() {
    private val repository = RecommendationRepository()

    fun getRecommendations(request: RecommendationRequest): LiveData<Result<RecommendationResponse>> {
        return repository.getRecommendations(request)
    }
}
