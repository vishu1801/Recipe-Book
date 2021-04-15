package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.UserResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchByRecipesFragment extends Fragment {
    ListView listView_for_ingredient;
    String ingredients="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent_view=inflater.inflate(R.layout.recipes,container,false);
        View root_view= inflater.inflate(R.layout.recipes,null);

        get_ingredients();
        init(root_view);

        return root_view;
    }

    private void init(View v){

    }
    private void get_ingredients(){
        ArrayList<UserResponse> mylist_for_pantry = new ArrayList<UserResponse>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Ingredients").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ingredients=ingredients+dataSnapshot.child("name").getValue().toString()+",";
                }
                

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        if(mylist_for_pantry.isEmpty()){
//            Toast.makeText(getActivity(), "Yes empty", Toast.LENGTH_SHORT).show();
//        }
//        for (UserResponse userResponse:mylist_for_pantry){
//            ingredients=ingredients + userResponse.getName().toString() + ",";
//        }
    }
}
