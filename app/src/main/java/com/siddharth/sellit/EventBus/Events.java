package com.siddharth.sellit.EventBus;

import com.siddharth.sellit.Model.Item;

import java.util.List;

/**
 * Created by Sid on 2017-04-01.
 */

public class Events
{

  // Event used to send message from fragment to activity.
  public static class FragmentToActivityMessage
  {
    private int position;
    public int getPosition()
    {
      return position;
    }

    public void setPosition(int position)
    {
      this.position = position;
    }


    public FragmentToActivityMessage(int position)
    {
      this.position = position;
    }
  }

  // Event used to send message from activity to fragment.
  public static class ActivityToFragmentMessage
  {
    public String getMsg()
    {
      return msg;
    }

    public void setMsg(String msg)
    {
      this.msg = msg;
    }

    private String msg;

    public ActivityToFragmentMessage(String msg)
    {
      this.msg = msg;
    }
  }

  public static class ActivityToActivity
  {
    private Item item;
    public ActivityToActivity(Item item)
    {
      this.item = item;
    }

    public Item getItem()
    {
      return item;
    }

    public void setItem(Item item)
    {
      this.item = item;
    }
  }
}
