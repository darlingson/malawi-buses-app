package com.codeshinobi.malawibuses

import BusViewModel
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codeshinobi.malawibuses.ui.theme.MalawiBusesTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val busViewModel by viewModels<BusViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        setContent {
            MalawiBusesTheme {
                val owner = LocalViewModelStoreOwner.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    owner?.let {
                        val viewModel:BusViewModel = viewModel(
                            it,
                            "BusViewModel",
                            BusViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )
                        Scaffold {
//                            Greeting("Android")
                            MainScreen(Modifier.padding(it), viewModel)
                        }
                    }
                }
            }
        }
    }
@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: BusViewModel) {
    val vm: BusViewModel = viewModel
}
    private fun getData(){
        lifecycleScope.launch {
            val client = getClient()
            val supabaseResponse = client.postgrest["buses"].select()
            val data = supabaseResponse.decodeList<Bus>()
            Log.e("DATA:", data.toString())
        }
    }
    private fun getClient():SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = SensitiveData.DATABASE_URL,
            supabaseKey = SensitiveData.API_KEY
        ){
            install(Postgrest)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MalawiBusesTheme {
        Greeting("Android")
    }
}


class BusViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BusViewModel(application) as T
    }
}