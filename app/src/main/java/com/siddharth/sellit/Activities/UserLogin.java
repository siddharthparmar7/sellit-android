package com.siddharth.sellit.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.siddharth.sellit.Fragments.AllItemsFragment;
import com.siddharth.sellit.Model.User;
import com.siddharth.sellit.Network.ItemApiInterface;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserLogin extends AppCompatActivity implements View.OnClickListener
{
  private TextView email;
  private TextView password;
  private Button sign_in;
  private String TAG = "SIGN_IN/SIGN_OUT";
  private String FRAGMENT_TAG = "FRAGMENT_TAG";
  ItemApiInterface apiService = null;
  public static User activeUser = new User();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_login);
    email = (TextView) findViewById(R.id.editText_email);
    password = (TextView) findViewById(R.id.editText_password);
    email.requestFocus();
    sign_in = (Button) findViewById(R.id.button_sign_in);
    sign_in.setOnClickListener(this);
  }

  @Override
  public void onClick(View view)
  {
    String userEmail;
    String userPassword;
    if(view.getId() == sign_in.getId())
    {
      Log.d(TAG,"signing in");
      if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
      {
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();
        sign_in(userEmail, userPassword);
      }
      else{
        Toast.makeText(getApplicationContext(), "User email or password is not correct", Toast.LENGTH_LONG).show();
      }
    }
  }

  @Override
  public void onBackPressed()
  {
    // super.onBackPressed(); // Comment this super call to avoid calling finish()
  }


  private void sign_in(String email, String password){

    apiService = ItemRestService.getItemRestService();
    HashMap<String,HashMap<String,String>> params = new HashMap<>();
    HashMap<String,String> credentials = new HashMap<>();
    credentials.put("email", email);
    credentials.put("password", password);
    params.put("user_login", credentials);

    Call<HashMap<String, String>> call = apiService.signIn(params);
    call.enqueue(new Callback<HashMap<String, String>>()
    {
      @Override
      public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response)
      {

        int statusCode = response.code();
        HashMap<String, String> body = response.body();
        Log.d(TAG,"" + response.code());
        if (response.isSuccessful())
        {
          Log.d(TAG, "code = " + Integer.toString(statusCode));
          activeUser.setAuth_token(body.get("auth_token"));
          activeUser.setUser_email(body.get("user_email"));
          activeUser.setUser_id(Integer.parseInt(body.get("user_id")));
          activeUser.setUser_logged_in(true);
          Log.d(TAG, "auth_token = " + activeUser.getAuth_token());
          Log.d(TAG, "user_id = " + activeUser.getUser_id());
          Log.d(TAG, "user_id = " + activeUser.getUser_email());
          Toast.makeText(getApplicationContext(), "Login successful!",Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          startActivity(intent);
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Login failed: " + Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<String, String>> call, Throwable t)
      {
        Log.d(TAG, t.getLocalizedMessage());
      }

    });
  }

  public void sign_out(final Context context){
    apiService = ItemRestService.getItemRestService();
    Call<Void> call = apiService.signOut();
    call.enqueue(new Callback<Void>()
    {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response)
      {

        int statusCode = response.code();
        Log.d(TAG," signing out " + response.code());
        if(statusCode == 200)
        {
          activeUser = null;
          Intent intent = new Intent(context, UserLogin.class);
          context.startActivity(intent);
        }
        else
        {
          Toast.makeText(context, "Opps, something went wrong status: " + statusCode, Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t)
      {
        Log.d(TAG, t.getLocalizedMessage());
        Toast.makeText(context, "Opps, something went wrong", Toast.LENGTH_LONG).show();
      }

    });

  }
}
