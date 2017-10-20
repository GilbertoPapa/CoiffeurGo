package com.coiffeurgo.streamcode.projectsc01.controllers;

import android.util.Log;

import com.coiffeurgo.streamcode.projectsc01.API.CompaniesAPI;
import com.coiffeurgo.streamcode.projectsc01.models.API.Answer;
import com.coiffeurgo.streamcode.projectsc01.utils.Configuration;
import com.coiffeurgo.streamcode.projectsc01.utils.DeserializersAnswer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create gilbertopapa on 03/08/2017.
 */

public class CompaniesController {
    private Gson gson = new GsonBuilder().registerTypeAdapter(Answer.class, new DeserializersAnswer()).create();
    private Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl(new Configuration().getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private CompaniesAPI _toApi = retrofit.create(CompaniesAPI.class);

    private Answer _answer = null;
    public void SearchWithNameCompanies(String name){
        Call<Answer> call = _toApi.getSearchWithNameCompanies(name);
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                _answer = response.body();
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {

            }
        });
    }

    public void SearchWithLocationBarberShops(String latitude,String longitude,String distance){
        Call<Answer> call = _toApi.getSearchWithLocationCompanies(latitude,longitude,distance);
        call.enqueue(new Callback<Answer>() {
            @Override
            public void onResponse(Call<Answer> call, Response<Answer> response) {
                _answer = response.body();
            }

            @Override
            public void onFailure(Call<Answer> call, Throwable t) {

            }
        });
    }

    public Answer getAnswer(){
        return this._answer;
    }
}
