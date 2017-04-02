package com.siddharth.sellit.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddharth.sellit.Adapter.AllItemsAdapter;
import com.siddharth.sellit.Activities.MainActivity;
import com.siddharth.sellit.EventBus.GlobalBus;
import com.siddharth.sellit.Model.Item;
import com.siddharth.sellit.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllItemsFragment extends Fragment
{

  public static List<Item> itemList = MainActivity.itemList;
  private RecyclerView mAllItemsRecyclerView;
  private AllItemsAdapter itemsAdapter;

  public AllItemsFragment()
  {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_all_items, container, false);

    mAllItemsRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_items_list);
    mAllItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    itemsAdapter = new AllItemsAdapter(getActivity());
    mAllItemsRecyclerView.setAdapter(itemsAdapter);

    return rootView;
  }

}
