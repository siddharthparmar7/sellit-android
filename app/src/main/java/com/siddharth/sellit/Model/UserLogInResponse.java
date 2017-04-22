package com.siddharth.sellit.Model;

/**
 * Created by Sid on 2017-04-13.
 */

public class UserLogInResponse
{
//  private String email;
//  private String token;
//
//  public String getEmail()
//  {
//    return email;
//  }
//
//  public void setEmail(String email)
//  {
//    this.email = email;
//  }
//
//  public String getToken()
//  {
//    return token;
//  }
//
//  public void setToken(String token)
//  {
//    this.token = token;
//  }

  private UserLogin user_login;

  public UserLogin getUser_login()
  {
    return user_login;
  }

  public void setUser_login(UserLogin user_login)
  {
    this.user_login = user_login;
  }

}

class User_login{
  private String email;
  private String password;

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

}
