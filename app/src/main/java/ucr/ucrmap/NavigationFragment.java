package ucr.ucrmap;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


        StartNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerBuilding.getText().toString().equals("BRNHL") && spinnerRoom.getText().toString().equals("A125")){

                    sendData.setNavigation(33.975625, -117.327152);

                }
                else if (spinnerBuilding.getText().toString().equals("BRNHL") && spinnerRoom.getText().toString().equals("B118")){

                }
                else if (spinnerBuilding.getText().toString().equals("BOYHL") && spinnerRoom.getText().toString().equals("1471")){
                    sendData.setNavigation(33.885570, -118.186480); //Home

                }
                else if (spinnerBuilding.getText().toString().equals("INTN") && spinnerRoom.getText().toString().equals("1002")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTN") && spinnerRoom.getText().toString().equals("1006")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTN") && spinnerRoom.getText().toString().equals("1020")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("1121")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("1125")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("1130")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("1132")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("1134")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("2130")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("2132")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("2134")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("2136")){

                }
                else if (spinnerBuilding.getText().toString().equals("INTS") && spinnerRoom.getText().toString().equals("2138")){

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG") && spinnerRoom.getText().toString().equals("138")){

                    sendData.setNavigation(33.97498, -117.325464);

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG") && spinnerRoom.getText().toString().equals("139")){

                    sendData.setNavigation(33.974905, -117.325897);

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG") && spinnerRoom.getText().toString().equals("141")){

                    sendData.setNavigation(33.974895, -117.325886);

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG") && spinnerRoom.getText().toString().equals("142")){

                    sendData.setNavigation(33.974942, -117.326065);

                }
                else if (spinnerBuilding.getText().toString().equals("CHUNG") && spinnerRoom.getText().toString().equals("143")){

                    sendData.setNavigation(33.974889, -117.326181);

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