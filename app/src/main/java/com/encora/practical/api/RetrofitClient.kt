
import com.encora.practical.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitClient {

   private const val BASE_URL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    val itunesService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
