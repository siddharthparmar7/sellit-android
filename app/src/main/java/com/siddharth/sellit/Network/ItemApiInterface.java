package com.siddharth.sellit.Network;


import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Model.User;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

  @POST("api/sign-in")
  Call<HashMap<String, String>> signIn(@Body HashMap<String, HashMap<String, String>> params);

  @DELETE("api/sign-out")
  Call<Void> signOut();

  @POST("api/items/{itemId}")
  Call<HashMap<String, String>> updateItem(@Body HashMap<String, String> params, @Path(value = "itemId") int itemId);

  @POST("api/items/create")
  Call<HashMap<String, String>> createItem(@Body HashMap<String, String> params);

  @GET("items/{itemId}.json")
  Call<Item> getItem(@Path(value = "itemId") int itemId);

  @DELETE("api/items/{itemId}")
  Call<Void> deleteItem(@Path(value = "itemId") int itemId);
//
//  @Multipart
  //@POST("myupload/articles/create.json")
//  @POST("myupload/articles.json")
//  Call<Article> createArticle(@Part MultipartBody.Part file,
//                              @Part("article[title]") RequestBody title,
//                              @Part("article[content]") RequestBody content);
}
