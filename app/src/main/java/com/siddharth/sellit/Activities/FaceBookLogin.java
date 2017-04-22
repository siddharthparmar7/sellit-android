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

public class FaceBookLogin extends AppCompatActivity
{
  private TextView textView;
  private LoginButton mFBloginButton;
  private CallbackManager callbackManager;
  public String email;
  public String name;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
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
        final String str = "User ID: "
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
                  name = object.getString("name");

                  email = object.getString("email");
                  Log.v("Email = ", " " + email);
                  Log.d("Token", str);
                  textView.setText(str);
                  Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_LONG).show();

//                  Intent intent = new Intent(getApplicationContext(), AddItem.class);
//                  startActivity(intent);

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
        textView.setText("FaceBookLogin attempt canceled.");
      }

      @Override
      public void onError(FacebookException error)
      {
        textView.setText("FaceBookLogin attempt failed.");
      }
    });
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }
}
