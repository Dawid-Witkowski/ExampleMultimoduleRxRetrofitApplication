package winged.example.presentation.doggoFragment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import winged.example.domain.model.DoggoApiResponseModel
import winged.example.domain.repository.DoggoRepository
import javax.inject.Inject

@HiltViewModel
class DoggoFragmentViewModel @Inject constructor(private val repository: DoggoRepository) : ViewModel() {
    fun makeRequest(): Observable<DoggoApiResponseModel> {
        return repository.getRandomDoggo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
    }
}
