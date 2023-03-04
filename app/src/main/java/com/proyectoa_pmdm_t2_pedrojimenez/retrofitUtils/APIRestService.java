package com.proyectoa_pmdm_t2_pedrojimenez.retrofitUtils;

import com.proyectoa_pmdm_t2_pedrojimenez.retrofitData.Centro;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRestService {
    public static final String BASE_URL = "https://datos.madrid.es/egob/catalogo/";

    @GET("203166-0-universidades-educacion.json")
    Call<Centro> getDataFilter(@Query("latitud") double lat,
                               @Query("longitud") double lon,
                               @Query("distancia") int dist);

    @GET("203166-0-universidades-educacion.json")
    Call<Centro> getData();

    @GET("tipo/entidadesyorganismos/{id_url}")
    Call<Centro> getData(@Path("id_url") String url);
}
