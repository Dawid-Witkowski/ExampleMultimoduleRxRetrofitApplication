package winged.example.domain.repository

import io.reactivex.rxjava3.core.Single
import winged.example.domain.model.DoggoApiResponseModel

interface DoggoRepository {
    fun getRandomDoggo(): Single<DoggoApiResponseModel>
}
