package com.siddharth.sellit.Model;

import android.media.Image;

/**
 * Created by Sid on 2017-03-25.
 */

public class Item
{
  private Integer id;
  private String title;
  private Double price;
  private String category;
  private String description;
  private Boolean status;
  private MyImage image;
  private String email;
  private String phone_number;
  private String location;
  private Integer userId;
  private String createdAt;
  private String updatedAt;
  private String url;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Double getPrice()
  {
    return price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public String getCategory()
  {
    return category;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Boolean getStatus()
  {
    return status;
  }

  public void setStatus(Boolean status)
  {
    this.status = status;
  }

  public MyImage getImage()
  {
    return image;
  }

  public void setImage(MyImage image)
  {
    this.image = image;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone_number()
  {
    return phone_number;
  }

  public void setPhone_number(String phoneNumber)
  {
    this.phone_number = phoneNumber;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public Integer getUserId()
  {
    return userId;
  }

  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }

  public String getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(String createdAt)
  {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt()
  {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt)
  {
    this.updatedAt = updatedAt;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }


  public class MyImage{
    private String imgURL;
      public MyImage(String imgURL){
        this.imgURL = imgURL;
      }
  }
}

