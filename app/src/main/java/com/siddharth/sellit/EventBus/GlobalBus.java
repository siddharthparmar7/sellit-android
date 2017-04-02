package com.siddharth.sellit.EventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Sid on 2017-04-01.
 */

public class GlobalBus {
  private static EventBus sBus;
  public static EventBus getBus() {
    if (sBus == null)
      sBus = EventBus.getDefault();
    return sBus;
  }
}