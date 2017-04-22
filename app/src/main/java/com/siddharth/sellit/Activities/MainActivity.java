package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.siddharth.sellit.Fragments.AllItemsFragment;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Model.User;
import com.siddharth.sellit.Network.ItemApiInterface;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener
{

  private TextView textView;
  private LoginButton mFBloginButton;
  private CallbackManager callbackManager;
  private User activeUser = UserLogin.activeUser;
  public static List<Item> itemList = new ArrayList<Item>();

  public static final String TAG = "REST APPLICATION";
  public static final String FRAGMENT_TAG = "FRAGMENT TAG";

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

//    FACEBOOK -> Activate the FB API
    FacebookSdk.sdkInitialize(getApplicationContext());

//    initialize the call back manager
    callbackManager = CallbackManager.Factory.create();



    setContentView(R.layout.activity_main);


    textView = (TextView) findViewById(R.id.textView);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View view)
      {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();

        Intent intent = new Intent(getApplicationContext(), FaceBookLogin.class);
        startActivity(intent);

      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    // download all items from the server

    if(activeUser.isUser_logged_in())
    {
//      start the fragment and show the items
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.fragment, new AllItemsFragment(), FRAGMENT_TAG);
      ft.commit();
      getAllItems();
    }
    else{
      //    Start Login Activity
      Intent intent = new Intent(this, UserLogin.class);
      startActivity(intent);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    callbackManager.onActivityResult(requestCode, resultCode, data);
  }

  ///////////////////// START - GET ALL Items /////////////////////////////////////////////

  public void getAllItems()
  {
    ItemApiInterface apiService = ItemRestService.getItemRestService();
    final Call<List<Item>> items = apiService.getAllItems();
    items.enqueue(new Callback<List<Item>>()
    {

      @Override
      public void onResponse(Call<List<Item>> call, Response<List<Item>> response)
      {
        int statusCode = response.code();
        itemList = response.body();
        Log.d("MYDEBUG", "" + statusCode);

        if (response.isSuccessful())
        {
          Log.e(TAG, "SUCCESS !!! ");
//          call the all items fragment to show all the items
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.fragment, new AllItemsFragment(), FRAGMENT_TAG);
          ft.commit();
        }
      }

      @Override
      public void onFailure(Call<List<Item>> call, Throwable t)
      {
        // Log error here since request failed
        Log.e(TAG, "FAIL  = " + call.toString());
        t.printStackTrace();
        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
      }
    });
  }
  /////////////////////   END - GET ALL ARTICLES /////////////////////////////////////////////

  @Override
  public void onBackPressed()
  {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START))
    {
      drawer.closeDrawer(GravityCompat.START);
    } else
    {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings)
    {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item)
  {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera)
    {
      // Handle the camera action
    } else if (id == R.id.nav_gallery)
    {

    } else if (id == R.id.nav_slideshow)
    {

    } else if (id == R.id.nav_manage)
    {

    } else if (id == R.id.nav_share)
    {

    } else if (id == R.id.nav_send)
    {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
