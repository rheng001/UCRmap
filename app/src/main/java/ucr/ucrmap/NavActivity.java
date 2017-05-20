package ucr.ucrmap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        overridePendingTransition(0, 0);






            final Spinner spinnerBuilding = (Spinner) findViewById(R.id.building);
            final Spinner spinnerRoom = (Spinner) findViewById(R.id.room);
            final Button StartNavButton = (Button)findViewById(R.id.start_nav_button);

//            spinnerRoom.setEnabled(false);

            ArrayAdapter<CharSequence> buildingAdapter = ArrayAdapter.createFromResource(this, R.array.building_array, android.R.layout.simple_spinner_item);
            buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter<CharSequence> buildingCodesAdapter = ArrayAdapter.createFromResource(this, R.array.CHUNG_array, android.R.layout.simple_spinner_item);
            buildingCodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerBuilding.setAdapter(buildingAdapter);

            spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spinnerBuilding.getSelectedItem().equals("CHUNG")) {
                        spinnerRoom.setEnabled(true);
                        StartNavButton.setEnabled(true);

                        ArrayAdapter buildingCodesAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                                R.array.CHUNG_array, android.R.layout.simple_spinner_item);
                        spinnerRoom.setAdapter(buildingCodesAdapter);
                    }

                    else if (spinnerBuilding.getSelectedItem().equals("BRNHL")) {
                        spinnerRoom.setEnabled(true);
                        StartNavButton.setEnabled(true);

                        ArrayAdapter buildingCodesAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                                R.array.BRNHL_array, android.R.layout.simple_spinner_item);
                        spinnerRoom.setAdapter(buildingCodesAdapter);
                    }

                     else if (spinnerBuilding.getSelectedItem().equals("MSE")) {
                        spinnerRoom.setEnabled(true);
                        StartNavButton.setEnabled(true);

                        ArrayAdapter buildingCodesAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                                R.array.MSE_array, android.R.layout.simple_spinner_item);
                        spinnerRoom.setAdapter(buildingCodesAdapter);
                    }

                     else if (spinnerBuilding.getSelectedItem().equals("WAT")) {
                        spinnerRoom.setEnabled(true);
                        StartNavButton.setEnabled(true);

                        ArrayAdapter buildingCodesAdapter = ArrayAdapter.createFromResource(getBaseContext(),
                                R.array.WAT_array, android.R.layout.simple_spinner_item);
                        spinnerRoom.setAdapter(buildingCodesAdapter);
                    }

                    else {
                        spinnerRoom.setEnabled(false);
                        StartNavButton.setEnabled(false);
                    }
                }



                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            spinnerRoom.setAdapter(buildingCodesAdapter);

        StartNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Intent FloorPlan = new Intent(NavActivity.this, floor_plan.class);
               startActivity(FloorPlan);
            }
        });





        //BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_nav:
                        break;
                    case R.id.action_map:
                        Intent navMap = new Intent(NavActivity.this, MainActivity.class);
                        startActivity(navMap);
                        break;
                    case R.id.action_places:
                        Intent navPlaces = new Intent(NavActivity.this, PlacesActivity.class);
                        startActivity(navPlaces);
                        break;
                    case R.id.action_about:
                        Intent navAbout = new Intent(NavActivity.this, AboutActivity.class);
                        startActivity(navAbout);
                        break;
                }
                return true;
            }
        });
    }


}
