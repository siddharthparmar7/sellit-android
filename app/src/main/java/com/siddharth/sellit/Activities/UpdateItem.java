package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.siddharth.sellit.EventBus.Events;
import com.siddharth.sellit.Fragments.AllItemsFragment;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Network.ItemApiInterface;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.channels.ClosedByInterruptException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateItem extends AppCompatActivity implements View.OnClickListener
{
  private String TAG = "UPDATE ACTIVITY";

  private EditText title;
  private EditText description;
  private EditText price;
  private EditText category;
  private EditText email;
  private EditText phone;
  private CheckBox status;
  private EditText location;

  private Item item;

  private Button update_item_button;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_item);

    title = (EditText) findViewById(R.id.edit_title);
    description = (EditText) findViewById(R.id.edit_description);
    price = (EditText) findViewById(R.id.edit_price);
    category = (EditText) findViewById(R.id.edit_category);
    email = (EditText) findViewById(R.id.edit_email);
    phone = (EditText) findViewById(R.id.edit_phone_number);
    status = (CheckBox) findViewById(R.id.edit_status);
    location = (EditText) findViewById(R.id.edit_location);

    update_item_button = (Button) findViewById(R.id.update_item);
    update_item_button.setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    if(v.getId() == update_item_button.getId())
    {
      if(!(title.getText().toString().isEmpty() || price.getText().toString().isEmpty() ||
          category.getText().toString().isEmpty() || location.getText().toString().isEmpty() ||
          description.getText().toString().isEmpty()))
      {
        update_item();
      }
    }
  }

  private void update_item(){
    ItemApiInterface apiService = ItemRestService.getItemRestService();
    HashMap<String, String> params = new HashMap<>();
    params.put("title", title.getText().toString());
    params.put("price", price.getText().toString());
    params.put("category", category.getText().toString());
    params.put("description", description.getText().toString());
    params.put("status", "" + status.isChecked());
    params.put("email", email.getText().toString());
    params.put("phone_number", phone.getText().toString());
    params.put("location", location.getText().toString());

    Call<HashMap<String, String>> call = apiService.updateItem(params, item.getId());
    call.enqueue(new Callback<HashMap<String, String>>()
    {

      @Override
      public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response)
      {
        int statusCode = response.code();
//        Log.d(TAG, "" + statusCode);
        HashMap<String, String> body = response.body();
        if(response.isSuccessful())
        {

          Intent intent = new Intent(getApplicationContext(), ItemCard.class);
          getApplicationContext().startActivity(intent);
        }
      }

      @Override
      public void onFailure(Call<HashMap<String, String>> call, Throwable t)
      {
        Log.d(TAG, t.getLocalizedMessage());
      }
    });
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void fill_details(Events.ActivityToActivity activityToActivity) {
    item = activityToActivity.getItem();
    title.setText(item.getTitle());
    description.setText(item.getDescription());
    category.setText(item.getCategory());
    email.setText(item.getEmail());
    phone.setText(item.getPhone_number());
    price.setText("" + item.getPrice());
    status.setChecked(item.getStatus());
    location.setText(item.getLocation());
  }

  @Override
  public void onResume()
  {
    super.onResume();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onPause()
  {
    super.onPause();
    EventBus.getDefault().unregister(this);
  }
}
