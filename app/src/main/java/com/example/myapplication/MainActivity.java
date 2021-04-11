package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Fragment.DecideFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE =2811 ;
    List<AuthUI.IdpConfig> providers;
    DatabaseReference admin= FirebaseDatabase.getInstance().getReference().child("admin");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providers = Arrays.asList(
                //google builder,
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()

        );
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            showSignInOptions();
        }

        else {
            admin.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        Intent intent=new Intent(getApplicationContext(), DecideFragment.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent homeIntent = new Intent(getApplicationContext(), DecideFragment.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    private void showSignInOptions() {
        //noinspection deprecation
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.SignIn)
//                        .setLogo(R.drawable.logo)
                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            final IdpResponse response=IdpResponse.fromResultIntent(data);
            if(resultCode!=RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),""+response.getError().getMessage(),Toast.LENGTH_SHORT).show();
            }
            else
            {
                admin.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (snapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                            if(response.isNewUser()) {
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users");
                                user.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("email")
                                        .setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            }

                            Intent intent = new Intent(getApplicationContext(), DecideFragment.class);
                            startActivity(intent);
                            finish();


                        }
                        else {
                            if(response.isNewUser()) {
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users");
                                user.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("email")
                                        .setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            }


                            Intent homeIntent = new Intent(getApplicationContext(), DecideFragment.class);
                            startActivity(homeIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        }

    }


}