package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.siddharth.sellit.EventBus.Events;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Model.User;
import com.siddharth.sellit.Network.ItemApiInterface;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.R;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateItem extends AppCompatActivity implements View.OnClickListener
{

  private User activeUser = UserLogin.activeUser;
  private List<Item> itemList = MainActivity.itemList;
  private Item newItem = new Item();

  private String TAG = "CREATE ITEM";

  private EditText title;
  private EditText price;
  private EditText description;
  private EditText location;
  private EditText category;
  private EditText email;
  private EditText phone;
  private CheckBox status;
  private ImageView image;

  private Button createButton;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_item);

    title = (EditText) findViewById(R.id.edit_title);
    price = (EditText) findViewById(R.id.edit_price);
    description = (EditText) findViewById(R.id.edit_description);
    location = (EditText) findViewById(R.id.edit_location);
    category = (EditText) findViewById(R.id.edit_category);
    email = (EditText) findViewById(R.id.edit_email);
    phone = (EditText) findViewById(R.id.edit_phone_number);
    status = (CheckBox)  findViewById(R.id.edit_status);
    image = (ImageView) findViewById(R.id.imageView_item);

    createButton = (Button) findViewById(R.id.create_item_button);
    createButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    if(v.getId() == createButton.getId())
    {
      if(!(title.getText().toString().isEmpty() || price.getText().toString().isEmpty() ||
          category.getText().toString().isEmpty() || location.getText().toString().isEmpty() ||
          description.getText().toString().isEmpty()))
      {
//        append the new item to the item list
        newItem.setTitle(title.getText().toString());
        newItem.setPhone_number(phone.getText().toString());
        newItem.setPrice(Double.parseDouble(price.getText().toString()));
        newItem.setCategory(category.getText().toString());
        newItem.setEmail(email.getText().toString());
        newItem.setLocation(location.getText().toString());
        newItem.setDescription(description.getText().toString());
        newItem.setStatus(status.isChecked());
//        itemList.add(newItem);
        createItem();
      }
      else{
        Toast.makeText(getApplicationContext(),"Did you fill in all the fields?", Toast.LENGTH_LONG).show();
      }
    }
  }

  private void createItem()
  {
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
    params.put("user_id", "" + activeUser.getUser_id());

    Call<HashMap<String, String>> call = apiService.createItem(params);
    call.enqueue(new Callback<HashMap<String, String>>()
    {

      @Override
      public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response)
      {
        int statusCode = response.code();
        Log.d(TAG, "" + statusCode);
        HashMap<String, String> body = response.body();
        if(response.isSuccessful())
        {
//          EventBus.getDefault().postSticky(new Events.ActivityToActivity(newItem));
          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          getApplicationContext().startActivity(intent);
        }
        else{
          Toast.makeText(getApplicationContext(), "opps! looks like you are not getting correct response", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<HashMap<String, String>> call, Throwable t)
      {
        Log.d(TAG, t.getLocalizedMessage());
        Toast.makeText(getApplicationContext(), "opps! something went wrong " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
      }
    });
  }
}
