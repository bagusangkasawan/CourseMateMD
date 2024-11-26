package academy.bangkit.coursemate.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import academy.bangkit.coursemate.R
import academy.bangkit.coursemate.model.RecommendationRequest

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var courseList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val interestInput: EditText = root.findViewById(R.id.input_interest)
        val courseTypeInput: EditText = root.findViewById(R.id.input_course_type)
        val durationInput: EditText = root.findViewById(R.id.input_duration)
        val submitButton: Button = root.findViewById(R.id.button_submit)
        courseList = root.findViewById(R.id.recycler_courses)
        progressBar = root.findViewById(R.id.progress_bar)
        errorText = root.findViewById(R.id.text_error)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        courseList.layoutManager = LinearLayoutManager(requireContext())

        submitButton.setOnClickListener {
            val interest = interestInput.text.toString()
            val courseType = courseTypeInput.text.toString()
            val duration = durationInput.text.toString()

            if (interest.isNotBlank() && courseType.isNotBlank() && duration.isNotBlank()) {
                fetchRecommendations(RecommendationRequest(interest, courseType, duration))
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun fetchRecommendations(request: RecommendationRequest) {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE

        viewModel.getRecommendations(request).observe(viewLifecycleOwner) { result ->
            progressBar.visibility = View.GONE
            if (result.isSuccess) {
                val response = result.getOrNull()
                if (response != null) {
                    courseList.adapter = CourseAdapter(response.recommended_courses)
                } else {
                    errorText.text = "No courses found."
                    errorText.visibility = View.VISIBLE
                }
            } else {
                errorText.text = "Failed to load recommendations."
                errorText.visibility = View.VISIBLE
            }
        }
    }
}
