package com.siddharth.sellit.Network;


import com.siddharth.sellit.Model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by Sid on 2017-03-25.
 */

public interface ItemApiInterface
{
  @GET("/items.json")
  Call<List<Item>> getAllItems();

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
