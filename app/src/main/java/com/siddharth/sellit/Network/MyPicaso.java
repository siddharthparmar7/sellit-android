package com.siddharth.sellit.Network;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Sid on 2017-03-29.
 */

public class MyPicaso
{
  private static Picasso sPicasso;

  private MyPicaso()
  {
  }

  public static Picasso getImageLoader(final Context context)
  {
    if (sPicasso == null)
    {

      Interceptor interceptor = new Interceptor()
      {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException
        {
          Request newRequest = chain.request().newBuilder()
              .header("Accept", "application/json")
              .header("Accept", "image/*")
              .addHeader("User-Agent", "Picasso-2-FN")
              .build();
          return chain.proceed(newRequest);
        }
      };

      // Add the interceptor to OkHttpClient
      OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
      okHttpClientBuilder.interceptors().add(interceptor);
      OkHttpClient okHttpClient = okHttpClientBuilder.build();


      Picasso.Builder picassoBuilder = new Picasso.Builder(context);
      picassoBuilder.downloader(new OkHttp3Downloader(okHttpClient));
      sPicasso = picassoBuilder.build();
    }
    return sPicasso;
  }
}
