package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AllResponse.Temp;
import com.example.myapplication.Custom_Adapter.Fav_Adapter;
import com.example.myapplication.R;
import com.example.myapplication.Recipe_Details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements Fav_Adapter.Onfav_Listener{

    RecyclerView recyclerView;
    ArrayList<Temp> fav_recipes;
    Fav_Adapter fav_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view = inflater.inflate(R.layout.fragment_fav,container,false);
        View root_view = inflater.inflate(R.layout.fragment_fav,null);

        init(root_view);
        get_recipes();

        return root_view;
    }

    private void init(View root){
        fav_recipes=new ArrayList<>();
        recyclerView=root.findViewById(R.id.fav_recycler);
        fav_adapter=new Fav_Adapter(getContext(),getActivity(),fav_recipes,FavoriteFragment.this::Onfavclick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fav_adapter);
    }

    private void get_recipes(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Favourite").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    fav_recipes.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        Temp temp = new Temp();
                        temp.setId(Integer.parseInt(snapshot1.child("id").getValue().toString()));
                        temp.setImage(snapshot1.child("image").getValue().toString());
                        temp.setTitle(snapshot1.child("title").getValue().toString());

                        fav_recipes.add(temp);
                    }
                    fav_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void Onfavclick(int position) {
        Intent intent = new Intent(getContext(), Recipe_Details.class);
        intent.putExtra("id",fav_recipes.get(position).getId());
        startActivity(intent);
    }
}
