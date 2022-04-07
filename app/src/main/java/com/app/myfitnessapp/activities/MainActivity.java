package com.app.myfitnessapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.myfitnessapp.FinalCaloriesActivity;
import com.app.myfitnessapp.LoginActivity;
import com.app.myfitnessapp.R;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class MainActivity extends AppCompatActivity {


    AHBottomNavigation bottomNavigation;
    int tdee = 0;
    int cals = 0;
    double wt = 0;
    TextView content,titles;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        content =  findViewById(R.id.content);
        titles =  findViewById(R.id.titles);

        //==========Bottom Navigation=============

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
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setForceTint(true);

        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.teal_200));
        bottomNavigation.setNotificationBackgroundColorResource(R.color.teal_200);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setOnTabSelectedListener(ob);



        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            aa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("calories").exists() && snapshot.child("weight").exists() && snapshot.child("date").exists()   ){


                        if(snapshot.child("date").getValue().toString().equals(simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime()))){

                            String aa = snapshot.child("dailycalories").getValue().toString();
                            String ww = snapshot.child("weight").getValue().toString();

                            tdee = Integer.valueOf(aa);
                            wt = Double.parseDouble(ww);
                            TextView td = findViewById(R.id.tdee2);
                            final TextView tdd = findViewById(R.id.tdee);
                            final TextView p = findViewById(R.id.cal_protein);
                            final TextView c = findViewById(R.id.cal_carb);
                            final TextView f = findViewById(R.id.cal_fat);

                            final PieChart pie = findViewById(R.id.chart);

                            cals = tdee;


                            List<PieEntry> entries = new ArrayList<>();
                            entries.add(new PieEntry((int) (wt * 1.8), "Protein"));
                            entries.add(new PieEntry((int) (cals - cals * 0.3 - wt * 1.8 * 4) / 4, "Carb"));
                            entries.add(new PieEntry((int) (cals * 0.3 / 9), "Fat"));
                            PieDataSet set = new PieDataSet(entries, null);
                            set.setColors(getResources().getColor(R.color.bluez), getResources().getColor(R.color.greenz), getResources().getColor(R.color.red));
                            set.setSliceSpace(3f);
                            set.setSelectionShift(9f);
                            set.setValueFormatter(new PercentFormatter());
                            PieData data = new PieData(set);
                            pie.getDescription().setEnabled(false);
                            pie.getLegend().setEnabled(false);
                            pie.setUsePercentValues(true);
                            pie.setHoleColor(getResources().getColor(R.color.colorGrey));
                            pie.setHoleRadius(35f);
                            pie.setData(data);
                            pie.spin(500, 0, -360f, Easing.EasingOption.EaseInOutQuad);
                            p.setText(String.valueOf(((int) (wt * 1.8 * 4))));
                            f.setText(String.valueOf(((int) (cals * 0.3))));
                            c.setText(String.valueOf(cals - ((int) (wt * 1.8 * 4)) - ((int) (cals * 0.3))));

                            update(tdee, wt, tdee);
                        }




                    }else
                    if(snapshot.child("calories").exists() && snapshot.child("weight").exists() ){

                        String aa = snapshot.child("calories").getValue().toString();
                        String ww = snapshot.child("weight").getValue().toString();

                        tdee = Integer.valueOf(aa);
                        wt = Double.parseDouble(ww);
                        TextView td = findViewById(R.id.tdee2);
                        final TextView tdd = findViewById(R.id.tdee);
                        final TextView p = findViewById(R.id.cal_protein);
                        final TextView c = findViewById(R.id.cal_carb);
                        final TextView f = findViewById(R.id.cal_fat);

                        final PieChart pie = findViewById(R.id.chart);

                        cals = tdee;


                        List<PieEntry> entries = new ArrayList<>();
                        entries.add(new PieEntry((int) (wt * 1.8), "Protein"));
                        entries.add(new PieEntry((int) (cals - cals * 0.3 - wt * 1.8 * 4) / 4, "Carb"));
                        entries.add(new PieEntry((int) (cals * 0.3 / 9), "Fat"));
                        PieDataSet set = new PieDataSet(entries, null);
                        set.setColors(getResources().getColor(R.color.bluez), getResources().getColor(R.color.greenz), getResources().getColor(R.color.red));
                        set.setSliceSpace(3f);
                        set.setSelectionShift(9f);
                        set.setValueFormatter(new PercentFormatter());
                        PieData data = new PieData(set);
                        pie.getDescription().setEnabled(false);
                        pie.getLegend().setEnabled(false);
                        pie.setUsePercentValues(true);
                        pie.setHoleColor(getResources().getColor(R.color.colorGrey));
                        pie.setHoleRadius(35f);
                        pie.setData(data);
                        pie.spin(500, 0, -360f, Easing.EasingOption.EaseInOutQuad);
                        p.setText(String.valueOf(((int) (wt * 1.8 * 4))));
                        f.setText(String.valueOf(((int) (cals * 0.3))));
                        c.setText(String.valueOf(cals - ((int) (wt * 1.8 * 4)) - ((int) (cals * 0.3))));

                        update(tdee, wt, tdee);

                    }else{

                        Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), CaloriesActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//                        finish();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime());


        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            Query query = FirebaseFirestore.getInstance().collection("notes").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("myNotes").whereEqualTo("date",simpleDateFormat.format(Calendar.getInstance(Locale.ENGLISH).getTime())).limit(1);
            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if(queryDocumentSnapshots.getDocuments().size()>0){
                        DocumentSnapshot aa = queryDocumentSnapshots.getDocuments().get(0);
                        String a = aa.get("title").toString();
                        String  p  = aa.get("content").toString();
                        titles.setText(a);
                        content.setText(p);

                    }


                }
            });
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(intent);
            finish();

        }
    }
    private void update(int tdee, double wt, int goal) {
        SharedPreferences sharedPreferences = getSharedPreferences("Tdee", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putInt("TDEE", tdee);
        mEditor.putString("WEIGHT", String.valueOf(wt));
        mEditor.putInt("GOAL", goal);
        mEditor.apply();
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