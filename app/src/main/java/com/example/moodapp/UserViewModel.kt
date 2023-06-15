package com.example.moodapp
import androidx.lifecycle.ViewModel
import com.example.moodapp.database.User
import com.example.moodapp.database.UserDao
import kotlinx.coroutines.flow.MutableStateFlow

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    val isLoggedIn = MutableStateFlow(false)

    suspend fun login(username: String, password: String): Boolean {
        val user = userDao.getUser(username, password)
        if (user != null) {
            isLoggedIn.value = true
            return true
        } else {
            return false
        }
    }

    suspend fun register(username: String, password: String) {
        // You may want to add some form of input validation here
        val newUser = User(username = username, password = password)
        userDao.insertUser(newUser)
    }
}


