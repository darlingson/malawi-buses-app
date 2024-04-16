import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeshinobi.malawibuses.Bus
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BusViewModel(application: Application) : ViewModel() {
    var client: SupabaseClient
    init {
         client = createSupabaseClient(
            supabaseUrl = SensitiveData.DATABASE_URL,
            supabaseKey = SensitiveData.API_KEY
        ){
            install(Postgrest)
        }
    }
    suspend fun fetchBuses(): StateFlow<List<Bus>> {
        val supabaseResponse = client.postgrest["buses"].select()
        val data = supabaseResponse.decodeList<Bus>()
        return MutableStateFlow(data)
    }
    val buses: StateFlow<List<Bus>> = fetchBuses()
}
