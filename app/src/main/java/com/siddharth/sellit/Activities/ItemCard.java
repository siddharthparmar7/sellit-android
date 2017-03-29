package com.siddharth.sellit.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.siddharth.sellit.R;

public class ItemCard extends AppCompatActivity
{

  public static final String ID = "ID";
  public static final String POSITION = "POSITION";
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_card);
  }
}
