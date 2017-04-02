package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.siddharth.sellit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class AddItem extends AppCompatActivity
{
  private TextView textView;
  private LoginButton mFBloginButton;
  private CallbackManager callbackManager;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    textView = (TextView) findViewById(R.id.textView);


    //    FACEBOOK -> Activate the FB API
    FacebookSdk.sdkInitialize(getApplicationContext());

//    initialize the call back manager
    callbackManager = CallbackManager.Factory.create();

    mFBloginButton = (LoginButton) findViewById(R.id.facebook_login_button);

    mFBloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
    {
      @Override
      public void onSuccess(LoginResult loginResult)
      {
        String str = "User ID: "
            + loginResult.getAccessToken().getUserId()
            + "\n" +
            "Auth Token: "
            + loginResult.getAccessToken().getToken();

        // Facebook Email address
        GraphRequest request = GraphRequest.newMeRequest(
            loginResult.getAccessToken(),
            new GraphRequest.GraphJSONObjectCallback() {
              @Override
              public void onCompleted(
                  JSONObject object,
                  GraphResponse response) {
                Log.v("LoginActivity Response ", response.toString());

                try {
                  String Name = object.getString("name");

                  String FEmail = object.getString("email");
                  Log.v("Email = ", " " + FEmail);
                  Toast.makeText(getApplicationContext(), "Name ", Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
            });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();

      }

      @Override
      public void onCancel()
      {
        textView.setText("Login attempt canceled.");
      }

      @Override
      public void onError(FacebookException error)
      {
        textView.setText("Login attempt failed.");
      }
    });
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }
}
