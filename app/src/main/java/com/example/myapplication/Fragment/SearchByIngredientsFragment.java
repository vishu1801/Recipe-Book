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

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.R;
import com.example.myapplication.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;

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


        adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//
//                if(newText==""){
//                    searchView.setQueryHint("Search By Ingredients");
//                }
//                else {
//                    get_Ingredients(newText);
//                }
                get_Ingredients(newText);
//                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }
    public void get_Ingredients(String incomplete_ingredient){
        Call<List<UserResponse>> userResponseCall = ApiClient.getUserService().get_ingredients(apiKey,incomplete_ingredient,11);
        userResponseCall.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {

                if(response.isSuccessful()) {
                    mylist.clear();
                    for(int i=0;i<response.body().size();i++){
                        mylist.add(response.body().get(i).getName().toString());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
            }
        });
    }
}
