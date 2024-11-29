package academy.bangkit.coursemate.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import academy.bangkit.coursemate.model.RecommendationRequest
import academy.bangkit.coursemate.model.RecommendationResponse
import academy.bangkit.coursemate.model.Course
import academy.bangkit.coursemate.repository.RecommendationRepository

class DashboardViewModel : ViewModel() {
    private val repository = RecommendationRepository()

    private val _savedCourses = MutableLiveData<List<Course>>()
    val savedCourses: LiveData<List<Course>> get() = _savedCourses

    private val _predictedCategory = MutableLiveData<String>()
    val predictedCategory: LiveData<String> get() = _predictedCategory

    fun getRecommendations(request: RecommendationRequest): LiveData<Result<RecommendationResponse>> {
        return repository.getRecommendations(request)
    }

    fun setSavedCourses(courses: List<Course>) {
        _savedCourses.value = courses
    }

    fun setPredictedCategory(category: String) {
        _predictedCategory.value = category
    }
}

