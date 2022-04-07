package com.app.myfitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.app.myfitnessapp.FinalCaloriesActivity;
import com.app.myfitnessapp.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class RecommendationActivity extends AppCompatActivity {

    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

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
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setForceTint(true);

        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.teal_200));
        bottomNavigation.setNotificationBackgroundColorResource(R.color.teal_200);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setOnTabSelectedListener(ob);



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