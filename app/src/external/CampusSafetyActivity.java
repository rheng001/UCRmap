package theunderground.com.ucrmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Larry Parsons on 4/14/2016.
 */
public class CampusSafetyActivity extends Activity{

    private ImageButton mBackButton = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campus_safety_activity_layout);
        mBackButton = (ImageButton)findViewById(R.id.backButton);
        Button mCSES = (Button) findViewById(R.id.callCSES);
        Button mUCPD = (Button) findViewById(R.id.callUCPD);
        Button m911 = (Button) findViewById(R.id.call911);

        mCSES.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9518273772"));
                startActivity(i);
            }
        });

        mUCPD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9518275222"));
                startActivity(i);
            }
        });

        m911.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:911"));
                startActivity(i);
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void back(){
        Intent intent = new Intent(CampusSafetyActivity.this, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
