package com.siddharth.sellit.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siddharth.sellit.Activities.MainActivity;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sid on 2017-03-25.
 */

public class ItemRestService
{
  //  public static final String BASE_URL = "https://sell-it-siddharthparmar7.c9users.io/";
//    public static final String BASE_URL = "http://auth-custom-devise-siddharthparmar7.c9users.io/";
  public static final String BASE_URL = "http://10.0.2.2:3000/";
  private static ItemApiInterface apiService = null;

  private ItemRestService() {};

  public static ItemApiInterface getItemRestService()
  {
    if (apiService != null)
    {
      return apiService;
    }


    // Define the interceptor, add authentication headers
//    final String credential = Credentials.basic("a@a.com", "hi");
    Interceptor interceptor = new Interceptor()
    {
      @Override
      public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException
      {
        return chain.proceed(chain.request().newBuilder().build());
      }
    };

    // Add the interceptor to OkHttpClient
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.interceptors().add(interceptor);
    OkHttpClient client = builder.build();



    // Create Retrofit connection
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ItemApiInterface apiService =
        retrofit.create(ItemApiInterface.class);

    return apiService;
  }
}
