package academy.bangkit.coursemate.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    init {
        // Simulasikan data pengguna, ini bisa diganti dengan data asli
        _username.value = "John Doe"  // Gantilah dengan data asli, misalnya dari SharedPreferences
    }
}
