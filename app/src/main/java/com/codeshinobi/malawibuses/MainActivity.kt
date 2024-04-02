package com.codeshinobi.malawibuses

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.codeshinobi.malawibuses.ui.theme.MalawiBusesTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        setContent {
            MalawiBusesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
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
@kotlinx.serialization.Serializable
data class Bus(
    val id:Int = 0,
    val created_at:String = "",
    val company_name:String = "",
    val company_id:String = ""
)
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