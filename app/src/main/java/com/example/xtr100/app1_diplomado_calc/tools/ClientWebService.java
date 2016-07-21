package com.example.xtr100.app1_diplomado_calc.tools;

import android.graphics.Path;

import com.example.xtr100.app1_diplomado_calc.entities.Operation;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by XTR100 on 20/07/2016.
 */
public interface ClientWebService {

    @POST("sum")
    Call<Operation> getOperation(@Body Operation operation);
}
