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
    private lateinit var predictedCategoryText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val interestSpinner: Spinner = root.findViewById(R.id.spinner_interest)
        val courseTypeSpinner: Spinner = root.findViewById(R.id.spinner_course_type)
        val durationSpinner: Spinner = root.findViewById(R.id.spinner_duration)
        val submitButton: Button = root.findViewById(R.id.button_submit)
        courseList = root.findViewById(R.id.recycler_courses)
        progressBar = root.findViewById(R.id.progress_bar)
        errorText = root.findViewById(R.id.text_error)
        predictedCategoryText = root.findViewById(R.id.text_predicted_category)

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        courseList.layoutManager = LinearLayoutManager(requireContext())

        // Setup adapters for Spinners
        setupSpinner(interestSpinner, R.array.interest_options)
        setupSpinner(courseTypeSpinner, R.array.course_type_options)
        setupSpinner(durationSpinner, R.array.duration_options)

        submitButton.setOnClickListener {
            val interest = interestSpinner.selectedItem.toString()
            val courseType = courseTypeSpinner.selectedItem.toString()
            val duration = durationSpinner.selectedItem.toString()

            // Validate that the user has selected a valid option, not the default
            if (interest == "Select Your Interest" || courseType == "Select Course Type" || duration == "Select Max Duration (In Weeks)") {
                Toast.makeText(requireContext(), "Please select all options correctly.", Toast.LENGTH_SHORT).show()
            } else {
                fetchRecommendations(RecommendationRequest(interest, courseType, duration))
            }
        }

        return root
    }

    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(
            requireContext(),
            arrayResId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun fetchRecommendations(request: RecommendationRequest) {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        predictedCategoryText.visibility = View.GONE

        viewModel.getRecommendations(request).observe(viewLifecycleOwner) { result ->
            progressBar.visibility = View.GONE
            if (result.isSuccess) {
                val response = result.getOrNull()
                if (response != null) {
                    predictedCategoryText.text = "Predicted Category: ${response.predicted_category}"
                    predictedCategoryText.visibility = View.VISIBLE
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
