package com.app.myfitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myfitnessapp.FinalCaloriesActivity;
import com.app.myfitnessapp.LoginActivity;
import com.app.myfitnessapp.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import io.ghyeok.stickyswitch.widget.StickySwitch;

public class CaloriesActivity extends AppCompatActivity {



    int tdee = 15;
    int bmr = 0;
    double weightz;
    ToggleSwitch act, act2;
    BubbleSeekBar seek;
    AHBottomNavigation bottomNavigation;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);




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
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setForceTint(true);

        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.teal_200));
        bottomNavigation.setNotificationBackgroundColorResource(R.color.teal_200);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setOnTabSelectedListener(ob);













        act = findViewById(R.id.activity_switch);
        act2 = findViewById(R.id.activity_switch2);
        seek = findViewById(R.id.seekDays);

        final TextView ageT = findViewById(R.id.edit_age);
        final TextView heightT = findViewById(R.id.edit_height);
        final TextView weightT = findViewById(R.id.edit_weight);
        final Button calc = findViewById(R.id.calculate);
        final Button reset = findViewById(R.id.reset);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CaloriesActivity.this);

                final AlertDialog dialog = mBuilder.create();
                if (ageT.getText().toString().trim().length() == 0 || ageT.getText().toString().equals("0") ||
                        weightT.getText().toString().trim().length() == 0 || weightT.getText().toString().equals("0") ||
                        heightT.getText().toString().trim().length() == 0 || heightT.getText().toString().equals("0") || (weightT.getText().toString().length() > 0 && weightT.getText().toString().substring(0, 1).equals(".")))
                    Toast.makeText(CaloriesActivity.this, R.string.toast_make_sure, Toast.LENGTH_SHORT).show();
                else {
                    dialog.show();

                        bmr = BMRcalcKG(Integer.parseInt(ageT.getText().toString()), Integer.parseInt(heightT.getText().toString()), Double.parseDouble(weightT.getText().toString()), false);
                        weightz = Double.parseDouble(weightT.getText().toString());




                    int activ = act.getCheckedTogglePosition();
                    int intense = act2.getCheckedTogglePosition();
                    int seeki = seek.getProgress();
                    tdee = tdeeTest(bmr, activ, intense, seeki);
                    dialog.dismiss();

                    Bundle bundle = new Bundle();
                    bundle.putDouble("WEIGHT", weightz);
                    bundle.putInt("TDEE", tdee);

                    simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime());

                    DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    aa.child("calories").setValue(tdee);
                    aa.child("dailycalories").setValue(tdee);

                    aa.child("date").setValue(simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime()));

                    aa.child("weight").setValue(weightz).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(getApplicationContext(), FinalCaloriesActivity.class);
                            intent.putExtra("TDEE",tdee);
                            intent.putExtra("WEIGHT",weightz);

                            startActivity(intent);
                        }
                    });



                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ageT.setText("");
                heightT.setText("");
                weightT.setText("");

                act.setCheckedTogglePosition(0);
                act2.setCheckedTogglePosition(0);
                seek.setProgress(0);
            }
        });
    }
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(intent);
            finish();

        }
    }

    private int tdeeTest(int bmr, int activ, int intense, int seeki) {
        double active2, intense2, seeki2;
        switch (activ) {
            case 0:
                active2 = 1.2;
                break;
            case 1:
                active2 = 1.3;
                break;
            default:
                active2 = 1.75;
                break;
        }
        switch (intense) {
            case 0:
                intense2 = active2 + 0.05;
                break;
            case 1:
                intense2 = active2 + 0.1;
                break;
            default:
                intense2 = active2 + 0.15;
                break;
        }
        seeki2 = seeki * 0.01 + intense2;

        return ((int) (bmr * seeki2));
    }

    public int BMRcalcKG(int age, int height, double weight, boolean gender) {
        if (gender) {
            return (int) ((10 * weight) + (6.25 * height) - (5 * age) + 5);
        } else {
            return (int) ((10 * weight) + (6.25 * height) - (5 * age) - 161);
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