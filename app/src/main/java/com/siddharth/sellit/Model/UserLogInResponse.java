package com.siddharth.sellit.Model;

/**
 * Created by Sid on 2017-04-13.
 */

public class UserLogInResponse
{
  private String email;
  private String token;

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getToken()
  {
    return token;
  }

  public void setToken(String token)
  {
    this.token = token;
  }

}
