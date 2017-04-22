package com.siddharth.sellit.Model;

/**
 * Created by Sid on 2017-04-22.
 */

public class User
{
  private String user_email;
  private int user_id;
  private String auth_token;
  private boolean user_logged_in = false;

  public String getUser_email()
  {
    return user_email;
  }

  public void setUser_email(String user_email)
  {
    this.user_email = user_email;
  }

  public int getUser_id()
  {
    return user_id;
  }

  public void setUser_id(int user_id)
  {
    this.user_id = user_id;
  }

  public String getAuth_token()
  {
    return auth_token;
  }

  public void setAuth_token(String auth_token)
  {
    this.auth_token = auth_token;
  }

  public boolean isUser_logged_in()
  {
    return user_logged_in;
  }

  public void setUser_logged_in(boolean user_logged_in)
  {
    this.user_logged_in = user_logged_in;
  }
}
