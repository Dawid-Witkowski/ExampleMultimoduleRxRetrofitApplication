package winged.example.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import winged.example.data.DoggoApi
import winged.example.data.R
import winged.example.data.remote.repository.DoggoRepositoryImpl
import winged.example.domain.interceptor.RequestInterceptor
import winged.example.domain.repository.DoggoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    // to be honest interceptors might be one of the most fun ways to prevent crashing AND supply
    // error messages to your activities/fragments, If you are reading this and do not know much
    // about them, I highly encourage you to learn how to use them
    @Provides
    @Singleton
    fun provideOurInterceptor(@ApplicationContext appContext: Context) = RequestInterceptor(appContext)

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideDoggoApi(@ApplicationContext appContext: Context, client: OkHttpClient): DoggoApi {
        return Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.baseUrl))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(DoggoApi::class.java)
    }

    // will be singleton anyway (because of our api), so doesn't need to be declared
    @Provides
    fun provideRepository(api: DoggoApi): DoggoRepository {
        return DoggoRepositoryImpl(api)
    }
}
