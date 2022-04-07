package com.app.myfitnessapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myfitnessapp.EditProfileActivity;
import com.app.myfitnessapp.FinalCaloriesActivity;
import com.app.myfitnessapp.LoginActivity;
import com.app.myfitnessapp.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference database;
    TextView tvName,tvBio,tvAge,tvWandH;

    EditText tvNameEdit,tvBioEdit;
    RoundedImageView imageEdit,editBtn;
    LinearLayout original,edit;
    CardView save,logout;
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();

        tvWandH = findViewById(R.id.tvWandH);

        tvAge = findViewById(R.id.tvAge);

        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        logout = findViewById(R.id.logout);

        imageEdit = findViewById(R.id.image);
        tvBioEdit = findViewById(R.id.tvBioEdit);
        tvNameEdit = findViewById(R.id.tvNameEdit);

        original = findViewById(R.id.original);
        edit = findViewById(R.id.edit);

        save = findViewById(R.id.save);

        editBtn = findViewById(R.id.editBtn);



        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bnve);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_baseline_home_24, R.color.teal_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Calender", R.drawable.ic_baseline_calendar_today_24, R.color.teal_200);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Diet", R.drawable.ic_baseline_fastfood_24, R.color.teal_200);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Recommendation", R.drawable.ic_baseline_amp_stories_24, R.color.teal_200);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile", R.drawable.ic_baseline_perm_identity_24, R.color.teal_200);

        int qq = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._9sdp);
        int qqa = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);

        bottomNavigation.setTitleTextSize(qqa,qq);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);


        bottomNavigation.setVisibility(View.VISIBLE);


        bottomNavigation.setAccentColor(Color.parseColor("#38A1F3"));
        bottomNavigation.setInactiveColor(Color.parseColor("#5F7586"));
        //bottomNavigation.enableItemAtPosition(1);
        bottomNavigation.setCurrentItem(4);
        bottomNavigation.setForceTint(true);

        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.teal_200));
        bottomNavigation.setNotificationBackgroundColorResource(R.color.teal_200);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setOnTabSelectedListener(ob);




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });




        database  = FirebaseDatabase.getInstance().getReference().child("users");







        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(auth.getCurrentUser()!=null){

                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                    startActivity(intent);

                }else{

                    Toast.makeText(ProfileActivity.this,"Login to Edit Profile",Toast.LENGTH_SHORT).show();

                }


            }
        });

        if(auth.getCurrentUser()!=null){

            logout.setVisibility(View.VISIBLE);


            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.child(auth.getCurrentUser().getUid()).child("name").exists()){

                        String pic = snapshot.child(auth.getCurrentUser().getUid()).child("image").getValue().toString();
                        Picasso.with(ProfileActivity.this).load(pic).placeholder(R.drawable.prfile_pic).error(R.drawable.prfile_pic).into(imageEdit);



                        if(snapshot.child(auth.getCurrentUser().getUid()).hasChild("name")){

                            tvName.setText(snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString());

                        }


                        if(snapshot.child(auth.getCurrentUser().getUid()).hasChild("bio")){

                            tvBio.setText(snapshot.child(auth.getCurrentUser().getUid()).child("bio").getValue().toString());

                        }



                        if(snapshot.child(auth.getCurrentUser().getUid()).hasChild("age")){

                            tvAge.setText(snapshot.child(auth.getCurrentUser().getUid()).child("age").getValue().toString());

                        }

                        if(snapshot.child(auth.getCurrentUser().getUid()).hasChild("weight") && snapshot.child(auth.getCurrentUser().getUid()).hasChild("height")){

                            tvWandH.setText(snapshot.child(auth.getCurrentUser().getUid()).child("weight").getValue().toString()+"\n"+snapshot.child(auth.getCurrentUser().getUid()).child("height").getValue().toString());

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }else {
            logout.setVisibility(View.GONE);
        }





    }

    AHBottomNavigation.OnTabSelectedListener ob = new AHBottomNavigation.OnTabSelectedListener() {
        @Override
        public boolean onTabSelected(int position, boolean wasSelected) {
            switch(position){

                case 0:


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent);
                    overridePendingTransition(0, 0);



                    break;

                case 1:

                    Intent intent1 = new Intent(getApplicationContext(), NoteActivity.class);

                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    break;
                case 2:
                    Intent intent2 = new Intent(getApplicationContext(), FinalCaloriesActivity.class);

                    startActivity(intent2);
                    overridePendingTransition(0, 0);

                    break;
                case 3:
                    Intent intent3 = new Intent(getApplicationContext(),RecommendationActivity.class);

                    startActivity(intent3);
                    overridePendingTransition(0, 0);

                    break;
                case 4:
                    Intent intent4 = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent4);
                    overridePendingTransition(0, 0);


                    break;

            }

            return true;
        }
    };

}