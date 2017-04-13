package com.siddharth.sellit.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sid on 2017-04-13.
 */

public class UserLogin
{
//  private Map<String, String> = new HashMap<>

  private String authenticity_token;
  private String email;
  private String password;

  public String getAuthenticity_token()
  {
    return authenticity_token;
  }

  public void setAuthenticity_token(String authenticity_token)
  {
    this.authenticity_token = authenticity_token;
  }
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
