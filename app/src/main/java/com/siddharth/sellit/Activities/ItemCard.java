package com.siddharth.sellit.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.siddharth.sellit.Adapter.AllItemsAdapter;
import com.siddharth.sellit.EventBus.Events;
import com.siddharth.sellit.EventBus.GlobalBus;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Network.MyPicaso;
import com.siddharth.sellit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ItemCard extends AppCompatActivity
{

  public static final String ID = "ID";
  public static final String POSITION = "POSITION";
  public static final int SHOW_ITEM = 1000;
  public static final String TAG = "MY_DEBUG";

  List<Item> itemList = MainActivity.itemList;

  private ImageView image;
  private TextView title;
  private TextView description;
  private TextView price;
  private TextView category;
  private TextView email;
  private TextView phone;
  private TextView location;
  private int adapterPosition;

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
  }

  @Subscribe(sticky = true)
  public void getPositionNumber(Events.FragmentToActivityMessage fragmentToActivityMessage)
  {
    adapterPosition = fragmentToActivityMessage.getPosition();
    putDetailsOnScreen(adapterPosition);
  }

  public void putDetailsOnScreen(int pos){
    Item item = itemList.get(pos);
    title.setText(item.getTitle());
    description.setText(item.getDescription());
    price.setText("" + item.getPrice());
    category.setText(item.getCategory());
    email.setText(item.getEmail());
    phone.setText(item.getPhone_number());
    location.setText(item.getLocation());

    String imageUri = MainActivity.BASE_URL + itemList.get(pos).getImage();

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
}
