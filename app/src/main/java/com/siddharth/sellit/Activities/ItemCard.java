package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siddharth.sellit.Adapter.AllItemsAdapter;
import com.siddharth.sellit.EventBus.Events;
import com.siddharth.sellit.EventBus.GlobalBus;
import com.siddharth.sellit.Fragments.AllItemsFragment;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Model.User;
import com.siddharth.sellit.Network.ItemApiInterface;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.Network.MyPicaso;
import com.siddharth.sellit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ItemCard extends AppCompatActivity implements View.OnClickListener
{

  public static final String ID = "ID";
  public static final String POSITION = "POSITION";
  public static final int SHOW_ITEM = 1000;
  public static final String TAG = "ITEM CARD";
  private User activeUser = UserLogin.activeUser;
  private Item item = null;

  List<Item> itemList = MainActivity.itemList;

  private int itemId;
  private int itemPos;
  private ImageView image;
  private TextView title;
  private TextView description;
  private TextView price;
  private TextView category;
  private TextView email;
  private TextView phone;
  private TextView location;
  private int adapterPosition;
  private Button delete;
  private Button update;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_card);

//    find the elements
    image = (ImageView) findViewById(R.id.card_imageView);
    title = (TextView) findViewById(R.id.card_title_textView);
    description = (TextView) findViewById(R.id.card_description_textView);
    price = (TextView) findViewById(R.id.card_price_textView);
    category = (TextView) findViewById(R.id.card_category_textView);
    email = (TextView) findViewById(R.id.card_email_textView);
    phone = (TextView) findViewById(R.id.card_phone_textView);
    location = (TextView) findViewById(R.id.card_location_textView);
    delete = (Button) findViewById(R.id.delete_item);
    update = (Button) findViewById(R.id.update_item);

    delete.setOnClickListener(this);
    update.setOnClickListener(this);
  }

  @Subscribe(sticky = true)
  public void getPositionNumber(Events.FragmentToActivityMessage fragmentToActivityMessage)
  {
    adapterPosition = fragmentToActivityMessage.getPosition();
    putDetailsOnScreen(adapterPosition, item);
  }

  public void putDetailsOnScreen(int pos, Item item){
    item = itemList.get(pos);
    itemPos = pos;
    itemId = item.getId();
    title.setText(item.getTitle());
    description.setText(item.getDescription());
    price.setText("" + item.getPrice());
    category.setText(item.getCategory());
    email.setText(item.getEmail());
    phone.setText(item.getPhone_number());
    location.setText(item.getLocation());

//    disable the buttons if user is not the author of the item
    if(activeUser.getUser_id() != item.getUserId())
    {
//      disable it
      delete.setEnabled(false);
      update.setEnabled(false);
//      hide it
      delete.setVisibility(View.GONE);
      update.setVisibility(View.GONE);
    }

    String imageUri = ItemRestService.BASE_URL + itemList.get(pos).getImage();

    MyPicaso.getImageLoader(getApplicationContext()).load(imageUri).resize(250, 250).error(R.drawable.ic_menu_camera).into(image);
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

  @Override
  public void onClick(View view)
  {
    if(view.getId() == delete.getId())
    {
      deleteItem();
    }
    else if(view.getId() == update.getId())
    {
      updateItem();
    }
  }

  private void updateItem(){
    Log.d(TAG, "here " + item.getTitle());
    EventBus.getDefault().postSticky(new Events.ActivityToActivity(item));
    Intent intent = new Intent(this, UpdateItem.class);
    startActivity(intent);
  }

  private void deleteItem(){
    ItemApiInterface apiService = ItemRestService.getItemRestService();
    final Call<Void> items = apiService.deleteItem(itemId);
    items.enqueue(new Callback<Void>()
    {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response)
      {
        int statusCode = response.code();
        Log.d(TAG, "" + statusCode);
        if (response.isSuccessful())
        {
          Log.e(TAG, "SUCCESS !!! ");
          itemList.remove(itemPos);
          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
          getApplicationContext().startActivity(intent);
        }
      }
      @Override
      public void onFailure(Call<Void> call, Throwable t)
      {
        // Log error here since request failed
        Log.e(TAG, "FAIL  = " + call.toString());
        t.printStackTrace();
        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
      }
    });
  }
}
