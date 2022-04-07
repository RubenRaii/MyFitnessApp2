package com.app.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class DietActivity extends AppCompatActivity {


    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bnve);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Home", R.drawable.ic_baseline_home_24, R.color.teal_200);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Profile", R.drawable.ic_baseline_perm_identity_24, R.color.teal_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Workout", R.drawable.ic_baseline_fastfood_24, R.color.teal_200);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Diet", R.drawable.ic_baseline_calendar_today_24, R.color.teal_200);

        int qq = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._9sdp);
        int qqa = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp);

        bottomNavigation.setTitleTextSize(qqa,qq);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item1);


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


//                    Intent intent = new Intent(getApplicationContext(),FilterActivity.class);
//
//
//                    startActivity(intent);
//                    overridePendingTransition(0, 0);



                    break;

                case 1:


                    break;
                case 2:
//                    Intent intent2 = new Intent(getApplicationContext(),AdminActivity.class);
//                    startActivity(intent2);
//                    overridePendingTransition(0, 0);
//                    bottomNavigation.enableItemAtPosition(1);
//                    bottomNavigation.setCurrentItem(1);


                    break;
            }

            return true;
        }
    };
}