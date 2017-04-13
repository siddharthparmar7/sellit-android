package com.siddharth.sellit.Network;

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
  //private static ArticleRestService restClient = null;
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
        Request newRequest = chain.request().newBuilder()
//            .header("Authorization", credential)
            .header("Accept", "application/json")
            .header("authenticity_token", "WvZqupaxCrhqouCSqVeUmnUe4DZl1zhxOeRyjhBWjmz4zJZtrPb/hbnNvQKVmXw6o/Nplva4aEGSzejqRnucCw==")
            .header("user[email]", "a@a.com")
            .header("user[password]", "hi")
            .addHeader("User-Agent", "Retrofit-2-PS")
            .build();
        return chain.proceed(newRequest);
      }
    };

    // Add the interceptor to OkHttpClient
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder.interceptors().add(interceptor);
    OkHttpClient client = builder.build();

    // Create Retrofit connection
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(MainActivity.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    ItemApiInterface apiService =
        retrofit.create(ItemApiInterface.class);

    return apiService;
  }
}
