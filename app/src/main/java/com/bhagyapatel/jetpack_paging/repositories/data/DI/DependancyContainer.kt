package com.bhagyapatel.jetpack_paging.repositories.data.DI

import com.bhagyapatel.jetpack_paging.repositories.data.Remote.RepositoriesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependancyContainer {
    val repositoriesRetrofitClient: RepositoriesApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.github.com/search/")
        .build().create(RepositoriesApiService::class.java)
}