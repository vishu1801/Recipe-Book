package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

public class SearchByIngredientsFragment extends Fragment {
    SearchView searchView;
    ListView listView;
    ArrayList<String> mylist;
    ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.fragment_search,container,false);
        View root=inflater.inflate(R.layout.fragment_search,null);
        searchView = (SearchView) root.findViewById(R.id.search_view);
        listView = root.findViewById(R.id.list_item);
        mylist=new ArrayList<String>();
        mylist.add("onion");
        mylist.add("chocolate");
        mylist.add("tomatoes");

        adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return root;
    }
}
