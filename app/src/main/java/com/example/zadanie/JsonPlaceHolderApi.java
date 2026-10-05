package com.example.zadanie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("wiadomosci.json")
    Call<List<Wiadomosc>> getWiadomosci();
}