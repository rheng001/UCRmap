package ucr.ucrmap;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.materialdrawer.Drawer;
import com.ncapdevi.fragnav.FragNavController;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    SendNavigation sendData;
    DatabaseHelper mDatabasehelper;
    String lat_coord;
    String long_coord;


    public NavigationFragment()
    {
        //Required empty public constructor
    }


    public static NavigationFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        NavigationFragment navFragment = new NavigationFragment();
        navFragment.setArguments(args);
        return navFragment;
    }

    public interface SendNavigation {
        void setNavigation(Double latitude, Double longitude);
        void openDrawer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
            sendData = (SendNavigation) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement SendNavigation");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v =  inflater.inflate(R.layout.fragment_navigation, container, false);
        mDatabasehelper = new DatabaseHelper(getActivity());

        //Title
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false); //disables default actionbar title UCRmap
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
         mTitle.setText("UCR Navigation");


        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData.openDrawer();
            }
        });

        final MaterialSpinner spinnerBuilding = (MaterialSpinner) v.findViewById(R.id.spinner);
        spinnerBuilding.setItems(getResources().getStringArray(R.array.building_array));


        final MaterialSpinner spinnerRoom = (MaterialSpinner) v.findViewById(R.id.room);
        spinnerRoom.setItems(getResources().getStringArray(R.array.CHUNG_array));


        final Button StartNavButton = (Button)v.findViewById(R.id.start_nav_button);

        //Broadcast
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(classMessageReceiver, new IntentFilter("class-message"));




        //Need more than 1 in array list to show up
        spinnerBuilding.setOnItemSelectedListener((new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                if (item.equals("BRNHL")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.BRNHL_array));

                }

                else if (item.equals("BOYHL")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.BOYHL_array));

                }
                else if (item.equals("INTN")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.INTN_array));

                }
                else if (item.equals("INTS")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.INTS_array));

                }

                else if (item.equals("CHUNG")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.CHUNG_array));

                }
                else if (item.equals("HMNSS")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.HMNSS_array));

                }
                else if (item.equals("LFSC")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.LFSC_array));

                }

                else if (item.equals("MSE")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.MSE_array));

                }
                else if (item.equals("OLMH")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.OLMH_array));

                }
                else if (item.equals("PHY")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.PHY_array));

                }
                else if (item.equals("PRCE")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.PRCE_array));

                }
                else if (item.equals("SPR")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.SPR_array));

                }
                else if (item.equals("SURGE")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.SURGE_array));

                }
                else if (item.equals("SPTH")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.SPTH_array));

                }
                else if (item.equals("UV_THEATRE")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.UV_THEATRE_array));

                }
                else if (item.equals("UNLH")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.UNLH_array));

                }

                else if (item.equals("WAT")) {
                    spinnerRoom.setEnabled(true);
                    StartNavButton.setEnabled(true);

                    spinnerRoom.setItems(getResources().getStringArray(R.array.WAT_array));

                }

                else {
                    spinnerRoom.setEnabled(false);
                    StartNavButton.setEnabled(false);
                }
            }
        }));

        //Miscoordinate, UV Theatre, Physics 2000

        //ONLY BUILDINGS FOR NOW
        StartNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*BACKUP
                if (spinnerBuilding.getText().toString().equals("BRNHL") && spinnerRoom.getText().toString().equals("A125")){
                    Cursor c = mDatabasehelper.getLatLong("BRNHL");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }*/

                if (spinnerBuilding.getText().toString().equals("BRNHL")){
                    Cursor c = mDatabasehelper.getLatLong("BRNHL");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("BOYHL")){
                    Cursor c = mDatabasehelper.getLatLong("BOYHL");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("INTN")){
                    Cursor c = mDatabasehelper.getLatLong("INTN");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("INTS")){
                    Cursor c = mDatabasehelper.getLatLong("INTS");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG")){
                    Cursor c = mDatabasehelper.getLatLong("CHUNG");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("HMNSS")){
                    Cursor c = mDatabasehelper.getLatLong("HMNSS");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("LFSC")){
                    Cursor c = mDatabasehelper.getLatLong("LFSC");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("MSE")){
                    Cursor c = mDatabasehelper.getLatLong("MSE");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("OLMH")){
                    Cursor c = mDatabasehelper.getLatLong("OLMH");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("PHY")){
                    Cursor c = mDatabasehelper.getLatLong("PHY");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("PRCE")){
                    Cursor c = mDatabasehelper.getLatLong("PRCE");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("SPR")){
                    Cursor c = mDatabasehelper.getLatLong("SPR");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("SURGE")){
                    Cursor c = mDatabasehelper.getLatLong("SURGE");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("SPTH")){
                    Cursor c = mDatabasehelper.getLatLong("SPTH");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("UV_THEATRE")){
                    Cursor c = mDatabasehelper.getLatLong("UV_THEATRE");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("UNLH")){
                    Cursor c = mDatabasehelper.getLatLong("UNLH");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }
                else if (spinnerBuilding.getText().toString().equals("WAT")){
                    Cursor c = mDatabasehelper.getLatLong("WAT");

                    while(c.moveToNext())
                    {
                        long_coord = c.getString(0);
                        lat_coord = c.getString(1);
                    }
                    sendData.setNavigation(Double.parseDouble(long_coord), Double.parseDouble(lat_coord));

                }


            }
        });


        return v;
    }

    public BroadcastReceiver classMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String classTest = intent.getStringExtra("CLASS");

            if (classTest == "CHUNG")
            {
                sendData.setNavigation(33.97498, -117.325464);

            }



        }
    };


}
