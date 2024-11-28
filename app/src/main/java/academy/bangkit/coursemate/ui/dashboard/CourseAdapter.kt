package academy.bangkit.coursemate.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import academy.bangkit.coursemate.R
import academy.bangkit.coursemate.model.Course

class CourseAdapter(private val courses: List<Course>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.title.text = course.title
        holder.shortIntro.text = course.short_intro
        holder.enrollButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(course.url)
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = courses.size

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_course_title)
        val shortIntro: TextView = itemView.findViewById(R.id.text_course_intro)
        val enrollButton: Button = itemView.findViewById(R.id.button_enroll)
    }
}
