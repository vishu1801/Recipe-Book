package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.API.ApiClient;
import com.example.myapplication.Custom_Adapter.Ingredient_lis_for_pantry_adapter;
import com.example.myapplication.Custom_Adapter.Ingredient_list_for_search_adapter;
import com.example.myapplication.R;
import com.example.myapplication.UserResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.API.Recipes_url.apiKey;
import static com.example.myapplication.R.layout;

public class SearchByIngredientsFragment extends Fragment {
    SearchView searchView;
    ArrayList<UserResponse> mylist_for_search;
    Ingredient_list_for_search_adapter adapter_for_search;
    ListView listView_for_search,listView_for_pantry;
    Button view_recipe;
    TextView your_ingredient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root_view = inflater.inflate(layout.fragment_search,container,false);
        View root=inflater.inflate(R.layout.fragment_search,null);

        searchView = (SearchView) root.findViewById(R.id.search_view);
        view_recipe = (Button) root.findViewById(R.id.view_recipe);
        view_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_related_recipe();
            }
        });
        for_pantry(root);
        for_search(root);
        your_ingredient = root.findViewById(R.id.your_ingredient_text);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0){
                    for_search(root);
                    get_Ingredients(newText);

                    listView_for_pantry.setVisibility(View.GONE);
                    listView_for_search.setVisibility(View.VISIBLE);
                    view_recipe.setVisibility(View.GONE);
                    your_ingredient.setVisibility(View.GONE);

                }
                else {
                    listView_for_pantry.setVisibility(View.VISIBLE);
                    listView_for_search.setVisibility(View.GONE);
                    view_recipe.setVisibility(View.VISIBLE);
                    your_ingredient.setVisibility(View.VISIBLE);
                }
//                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return root;
    }

    private Void for_pantry(View view){

        ArrayList<UserResponse> mylist_for_pantry = new ArrayList<UserResponse>();
        listView_for_pantry=(ListView)view.findViewById(R.id.list_item_for_pantry);
        Ingredient_lis_for_pantry_adapter adapter_for_pantry = new Ingredient_lis_for_pantry_adapter(getActivity(), layout.ingredient_item_list_for_pantry,mylist_for_pantry);
        listView_for_pantry.setAdapter(adapter_for_pantry);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name = snapshot.child("name").getValue().toString();
                String image = snapshot.child("image").getValue().toString();

                UserResponse userResponse = new UserResponse();
                userResponse.setImage(image);
                userResponse.setName(name);

                mylist_for_pantry.add(userResponse);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                mylist_for_pantry.indexOf(snapshot.getKey());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter_for_pantry.notifyDataSetChanged();
        return null;
    }

    private Void for_search(View view){

        mylist_for_search=new ArrayList<UserResponse>();
        listView_for_search = view.findViewById(R.id.list_item_for_search);
        adapter_for_search=new Ingredient_list_for_search_adapter(getActivity(), R.layout.ingredient_list_item,mylist_for_search);
        listView_for_search.setAdapter(adapter_for_search);
        return null;
    }

    private void  view_related_recipe(){

        Fragment fragment = new SearchByRecipesFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_nav, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        Intent intent = new Intent(getActivity(),DecideFragment.class);
//        intent.putExtra("view_recipe","true");
//        startActivity(intent);
//        SearchByRecipesFragment ldf = new SearchByRecipesFragment();
//        getFragmentManager().beginTransaction().add(R.id.container, ldf).commit();

    }

    public void get_Ingredients(String incomplete_ingredient){
        Call<List<UserResponse>> userResponseCall = ApiClient.getUserService().get_ingredients(apiKey,incomplete_ingredient,10);
        userResponseCall.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {

                if(response.isSuccessful()) {
                    mylist_for_search.clear();
                    mylist_for_search.addAll(response.body());
//                    if(mylist_for_search.isEmpty()){
//                        view_recipe.setVisibility(VISIBLE);
//                        your_ingredient.setVisibility(VISIBLE);
//                        listView_for_pantry.setVisibility(VISIBLE);
//                    }else{
//                        view_recipe.setVisibility(INVISIBLE);
//                        your_ingredient.setVisibility(INVISIBLE);
//                        listView_for_pantry.setVisibility(INVISIBLE);
//                    }
                    adapter_for_search.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Check Your Internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
