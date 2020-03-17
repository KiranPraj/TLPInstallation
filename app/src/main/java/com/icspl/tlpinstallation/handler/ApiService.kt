package com.icspl.tlpinstallation.handler

import com.icspl.tlpinstallation.Model.CLientModel
import com.icspl.tlpinstallation.Model.ContractorModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService
{
    @GET("GetClient")
    fun getClient(): Call<CLientModel>

    @GET("GetContractor")
    fun getContractor(): Call<ContractorModel>
}