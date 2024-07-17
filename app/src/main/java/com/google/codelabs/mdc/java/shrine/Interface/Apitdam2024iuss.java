package com.google.codelabs.mdc.java.shrine.Interface;

import com.google.codelabs.mdc.java.shrine.Model.ReqAuth;
import com.google.codelabs.mdc.java.shrine.Model.ReqRegistro;
import com.google.codelabs.mdc.java.shrine.Model.RptaAuth;
import com.google.codelabs.mdc.java.shrine.Model.RptaLeerProductos;
import com.google.codelabs.mdc.java.shrine.Model.RptaRegistro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Apitdam2024iuss {

    @POST("auth")
    Call<RptaAuth> auth(@Body ReqAuth reqAuth);

    @POST("registro")
    Call<RptaRegistro> registro(@Body ReqRegistro reqRegister);

    @GET("api_leerproductos")
    Call<RptaLeerProductos> leerProductos(@Header("Authorization") String authorization);
}
