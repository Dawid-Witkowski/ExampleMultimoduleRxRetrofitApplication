package winged.example.data.remote.repository

import io.reactivex.rxjava3.core.Single
import winged.example.data.DoggoApi
import winged.example.domain.model.DoggoApiResponseModel
import winged.example.domain.repository.DoggoRepository
import javax.inject.Inject

class DoggoRepositoryImpl @Inject constructor(private val api: DoggoApi) : DoggoRepository {
    override fun getRandomDoggo(): Single<DoggoApiResponseModel> {
        return api.getRandomDoggo()
    }
}
