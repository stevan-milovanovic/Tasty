package com.example.tasty.di

import com.example.tasty.BuildConfig
import com.example.tasty.BuildConfig.API_HOST_HEADER
import com.example.tasty.BuildConfig.API_HOST_HEADER_VALUE
import com.example.tasty.BuildConfig.API_KEY_HEADER
import com.example.tasty.BuildConfig.API_KEY_HEADER_VALUE
import com.example.tasty.data.network.TastyNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideTastyNetworkApi(builder: Retrofit.Builder): TastyNetworkApi = builder
        .build()
        .create(TastyNetworkApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(client: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
        )
        .build()

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header(API_KEY_HEADER, API_KEY_HEADER_VALUE)
        requestBuilder.header(API_HOST_HEADER, API_HOST_HEADER_VALUE)
        return@Interceptor chain.proceed(requestBuilder.build())
    }
}
