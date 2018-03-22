package org.alphasupercoolwolfsquad

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PetServiceAPI {

    @Headers(
            "Accept:application/json",
            "Content-Type:multipart/form-data"
    )
    @Multipart
    @POST("pet")
    fun postRequest(@Part("name") name: String,
                    @Part("filename") filename: String,
                    @Part("type") type: String,
                    @Part("data") data: ByteArray): Observable<PetServiceResponse>

    @Headers(
            "Accept:application/json",
            "Content-Type:application/json"
    )
    @POST("petbug")
    fun postBugRequest(@Body request: PetServiceBugRequest): Observable<PetServiceBugResponse>

    @Headers(
            "Accept:application/json",
            "Content-Type:multipart/form-data"
    )
    @Multipart
    @POST("pet")
    fun postRequestTwo(@PartMap partMap: HashMap<String, RequestBody>,
                       @Part file: MultipartBody.Part): Observable<PetServiceResponse>

    companion object {
        fun create(): PetServiceAPI {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://petservices.cloud.heliusit.net/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(PetServiceAPI::class.java)
        }
    }

}