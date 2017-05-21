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
import android.widget.ImageView;

public class floor_plan extends AppCompatActivity {
public  ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan);
        overridePendingTransition(0, 0);



//        ImageView img = (ImageView) findViewById(R.id.floor_plan_img);
//        img.setImageResource(R.drawable.wch139);

        img = (ImageView) findViewById(R.id.floor_plan_img);
        img.setImageResource(getIntent().getIntExtra("myImageResource",R.drawable.chung138));








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
                        Intent navMenu = new Intent(floor_plan.this, NavActivity.class);
                        startActivity(navMenu);
                        break;
                    case R.id.action_map:
                        Intent navMap = new Intent(floor_plan.this, MainActivity.class);
                        startActivity(navMap);
                        break;
                    case R.id.action_places:
                        Intent navPlaces = new Intent(floor_plan.this, PlacesActivity.class);
                        startActivity(navPlaces);
                        break;
                    case R.id.action_about:
                        Intent navAbout = new Intent(floor_plan.this, AboutActivity.class);
                        startActivity(navAbout);
                        break;
                }
                return true;
            }
        });
    }
}
