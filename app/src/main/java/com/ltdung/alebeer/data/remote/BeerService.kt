package com.ltdung.alebeer.data.remote

import com.ltdung.alebeer.data.remote.model.BeerRemoteModel
import com.ltdung.alebeer.data.remote.model.RemoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerService {
    @GET("/api/api-testing/sample-data")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): RemoteModel<List<BeerRemoteModel>>
}
