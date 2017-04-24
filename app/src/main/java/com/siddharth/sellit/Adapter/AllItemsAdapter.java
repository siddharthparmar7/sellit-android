package com.siddharth.sellit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.siddharth.sellit.Activities.ItemCard;
import com.siddharth.sellit.Activities.MainActivity;
import com.siddharth.sellit.EventBus.Events;
import com.siddharth.sellit.EventBus.GlobalBus;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.Network.ItemRestService;
import com.siddharth.sellit.Network.MyPicaso;
import com.siddharth.sellit.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sid on 2017-03-29.
 */

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ItemHolder>
{
  private LayoutInflater inflater;
  private Context context;
  public List<Item> itemList;
  private String TAG = "MY_DEBUG";

  public AllItemsAdapter(Context context)
  {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.itemList = MainActivity.itemList;
  }

  @Override
  public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View view = inflater.inflate(R.layout.single_item, parent, false);
    return new ItemHolder(view);
  }

  @Override
  public void onBindViewHolder(ItemHolder holder, int position)
  {
    Item item = itemList.get(position);
    holder.item_title.setText(item.getTitle());
    holder.item_description.setText("Description: " + item.getDescription());
    holder.item_price.setText("CAD " + item.getPrice());
    holder.downloadAndShowPicture(position, holder.item_image);
  }

  @Override
  public int getItemCount()
  {
    return itemList.size();
  }

  public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
  {
    private TextView item_title;
    private TextView item_price;
    private TextView item_description;
    private ImageView item_image;
    private View item;
    private CardView cardView;
    public ItemHolder(View itemView)
    {
      super(itemView);

      cardView = (CardView) itemView.findViewById(R.id.cardView);
      item_title = (TextView) itemView.findViewById(R.id.item_title_textView);
      item_description = (TextView) itemView.findViewById(R.id.item_description_textView);
      item_price = (TextView) itemView.findViewById(R.id.item_price_textView);
      item_image = (ImageView) itemView.findViewById(R.id.item_image);
      item = itemView.findViewById(R.id.container);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
      int pos = getAdapterPosition();
      if(view.getId() == item.getId())
      {
        Events.FragmentToActivityMessage fragmentToActivityMessage = new Events.FragmentToActivityMessage(pos);
//        fragmentToActivityMessage
        EventBus.getDefault().postSticky(fragmentToActivityMessage);
        Intent intent = new Intent(context, ItemCard.class);
        ((Activity)context).startActivity(intent);
      }
    }



    /////////////////////  START - Download Picture /////////////////////////////////

  public void downloadAndShowPicture(int pos, ImageView imageView)
  {
    String imageUri = ItemRestService.BASE_URL + itemList.get(pos).getImage();

    MyPicaso.getImageLoader(getApplicationContext()).load(imageUri).resize(200, 200).
        centerCrop().error(R.drawable.ic_menu_camera).into(imageView);

//    Log.e("MY_DEBUG", "Image URL  = " + imageUri);
  }
    /////////////////////  END - Download Picture /////////////////////////////////
  }



}
