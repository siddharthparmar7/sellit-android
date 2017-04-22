package com.siddharth.sellit.Network;


import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Model.UserLogInResponse;
import com.siddharth.sellit.Model.UserLogin;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by Sid on 2017-03-25.
 */

public interface ItemApiInterface
{
  @Headers({
      "Content-Type: application/json",
      "Accept: application/json",
      "User-Agent: Sell-it-Android",
      "Cache-Control: no-cache"
  })

//  API calls

  @GET("items.json")
  Call<List<Item>> getAllItems();

  @POST("api/sign_in")
  Call<HashMap<String, String>> signIn(@Body HashMap<String, HashMap<String, String>> params);

  @POST("/")
  Call<List<Item>> deleteItem();
//
//  @Multipart
  //@POST("myupload/articles/create.json")
//  @POST("myupload/articles.json")
//  Call<Article> createArticle(@Part MultipartBody.Part file,
//                              @Part("article[title]") RequestBody title,
//                              @Part("article[content]") RequestBody content);
}
