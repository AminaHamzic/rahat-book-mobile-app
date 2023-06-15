/*import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.moodapp.AppNavGraph
import com.example.moodapp.MainScreen
import com.example.moodapp.ui.theme.MoodAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Box(modifier = Modifier.fillMaxSize()) {
                        AppNavGraph(navController)
                        MainScreen()

                    }
            }
        }
    }
}}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoodAppTheme {
        MainScreen()
    }}
*/
package com.example.moodapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.moodapp.AppNavGraph
import com.example.moodapp.MainScreen
import com.example.moodapp.UserViewModel
import com.example.moodapp.UserViewModelFactory
import com.example.moodapp.database.UserDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userDao = UserDatabase.getDatabase(this).userDao()
                    val viewModelFactory = UserViewModelFactory(userDao)
                    val viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
                    AppNavGraph(navController, viewModel)
                   }
            }
        }
    }
}


