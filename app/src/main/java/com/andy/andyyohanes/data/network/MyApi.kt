package com.andy.andyyohanes.data.network

import com.andy.andyyohanes.data.network.response.UserResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") keyword: String?,
        @Query("page") page: Int?
    ) : Response<UserResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}