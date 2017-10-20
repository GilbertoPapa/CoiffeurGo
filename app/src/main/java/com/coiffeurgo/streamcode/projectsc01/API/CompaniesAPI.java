package com.coiffeurgo.streamcode.projectsc01.API;

import com.coiffeurgo.streamcode.projectsc01.models.API.Answer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gilbertopapa on 03/08/2017.
 */

public interface CompaniesAPI {
    @FormUrlEncoded
    @POST("api/v2/search-with-name-companies")
    Call<Answer> getSearchWithNameCompanies(@Field("name") String name);

    @FormUrlEncoded
    @POST("api/v2/search-with-location-companies")
    Call<Answer> getSearchWithLocationCompanies(@Field("latitude") String latitude, @Field("longitude") String longitude, @Field("distance") String distance);
}
