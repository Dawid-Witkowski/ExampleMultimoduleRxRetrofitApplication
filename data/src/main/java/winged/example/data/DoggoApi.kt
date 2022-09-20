package winged.example.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import winged.example.domain.model.DoggoApiResponseModel

interface DoggoApi {
    // returns only one value and always one value
    // per call, so we don't need a flowable or maybe
    @GET("/api/breeds/image/random")
    fun getRandomDoggo(): Single<DoggoApiResponseModel>
}
