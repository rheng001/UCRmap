package ucr.ucrmap;


import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class fragment_schedule extends AppCompatDialogFragment implements View.OnClickListener {


    public static fragment_schedule newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_schedule scheduleFragment = new fragment_schedule();
        scheduleFragment.setArguments(args);
        return scheduleFragment;
    }

    List<recycler_information> myClass; //Initalize list

    private ViewPager mViewPager;
    private TabLayout tabLayout;


    DatabaseHelper mDatabasehelper;

    //Initializing toggle button holders to false
    String monResult = "";
    String tuesResult  = "";
    String wedResult  = "";
    String thurResult  = "";
    String friResult  = "";

    private int mSelectedItem;


    MultiSelectToggleGroup multi;

    TextView set_start;
    TextView set_end;
    TextView set_building;
    TextView set_room;
    EditText set_class;


    public fragment_schedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.container2); //list

        //Using SectionsPageAdapter to bind the TabLayout (tabs with different titles) and ViewPager(different pages of fragment) together
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);



        return v;
    }

    //////////////////////HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
    private void setupViewPager(ViewPager viewPager)
    {
        //create a fragment list in order for tablayout
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getFragmentManager()); //FragmentPagerAdapter
        System.out.println(adapter.toString());
        adapter.addFragment(new fragment_monday(), "MON");
        adapter.addFragment(new fragment_tuesday(), "TUES");
        adapter.addFragment(new fragment_wednesday(), "WED");
        adapter.addFragment(new fragment_thursday(), "THURS");
        adapter.addFragment(new fragment_friday(), "FRI");
        viewPager.setAdapter(adapter);
    }



    @Override
    public void onClick(View view) //Big mommma jamma onclick button
    {
        switch (view.getId())
        {
            case R.id.addButton: //want fragment to popout as a alertdialog //Need to reimplement all fragment_addClass code into this case then
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.fragment_add_class, null);
                builder.setView(mView); //Set view of alert dialog

                builder.setTitle("Add a class..."); //Add a header style

                View chooseBuilding = (Button) mView.findViewById(R.id.buildingButton);
                View chooseRoom = (Button) mView.findViewById(R.id.roomButton);
                View chooseStart = (Button) mView.findViewById(R.id.timeButton);
                View chooseEnd = (Button) mView.findViewById(R.id.timeButton2);
                View saveButton = (Button) mView.findViewById(R.id.saveButton); //SAVEEEEEEEEEEEEE


                final AlertDialog show = builder.show();

                saveButton.setOnClickListener(onConfirmListener(show)); //Confirm save classes



                set_start = (TextView) mView.findViewById(R.id.startTextTime);
                set_end = (TextView) mView.findViewById(R.id.endTextTime);
                set_building = (TextView) mView.findViewById(R.id.buildingText);
                set_room = (TextView) mView.findViewById(R.id.roomText);
                set_class = (EditText) mView.findViewById(R.id.classText);

                multi = (MultiSelectToggleGroup) mView.findViewById(R.id.group_weekdays);


                //CHOOSE BUILDING
                chooseBuilding.setOnClickListener(new View.OnClickListener() ////////ADD TIME BUTTON
                {

                    @Override
                    public void onClick(View view)
                    {
                        //View buildingView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_class,null);

                        AlertDialog.Builder builder4 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                        builder4.create();
                        builder4.setTitle("Select a building");

                        builder4.setSingleChoiceItems(R.array.building_array, 0,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mSelectedItem = which;
                                    }
                                })
                                // Set the action buttons
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Toast.makeText(getActivity(), "You selected !! \n " + getResources().getStringArray(R.array.classrooms)[mSelectedItem], Toast.LENGTH_SHORT).show();
                                        set_building.setText(getResources().getStringArray(R.array.building_array)[mSelectedItem]);


                                    }
                                });

                        builder4.show();
                    }
                });
                //CHOOSE ROOM
                chooseRoom.setOnClickListener(new View.OnClickListener() ////////ADD TIME BUTTON
                {

                    @Override
                    public void onClick(View view)
                    {
                        AlertDialog.Builder builder5 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                        builder5.setTitle("Select a room");
                        builder5.create();


                        if (set_building.getText().toString().equals("BRNHL")) {
                            builder5.setSingleChoiceItems(R.array.BRNHL_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.BRNHL_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("BOYHL")) {
                            builder5.setSingleChoiceItems(R.array.BOYHL_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.BOYHL_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("INTN")) {
                            builder5.setSingleChoiceItems(R.array.INTN_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.INTN_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("INTS")) {
                            builder5.setSingleChoiceItems(R.array.INTS_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.INTS_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("CHUNG")) {
                            builder5.setSingleChoiceItems(R.array.CHUNG_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.CHUNG_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("HMNSS")) {
                            builder5.setSingleChoiceItems(R.array.HMNSS_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.HMNSS_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("LFSC")) {
                            builder5.setSingleChoiceItems(R.array.LFSC_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.LFSC_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("MSE")) {
                            builder5.setSingleChoiceItems(R.array.MSE_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.MSE_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("OLMH")) {
                            builder5.setSingleChoiceItems(R.array.OLMH_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.OLMH_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("PHY")) {
                            builder5.setSingleChoiceItems(R.array.PHY_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.PHY_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("PRCE")) {
                            builder5.setSingleChoiceItems(R.array.PRCE_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.PRCE_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("SPR")) {
                            builder5.setSingleChoiceItems(R.array.SPR_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.SPR_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("SURGE")) {
                            builder5.setSingleChoiceItems(R.array.SURGE_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.SURGE_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("SPTH")) {
                            builder5.setSingleChoiceItems(R.array.SPTH_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.SPTH_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("UV_THEATRE")) {
                            builder5.setSingleChoiceItems(R.array.UV_THEATRE_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.UV_THEATRE_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("UNLH")) {
                            builder5.setSingleChoiceItems(R.array.UNLH_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.UNLH_array)[mSelectedItem]); }}); }

                        if (set_building.getText().toString().equals("WAT")) {
                            builder5.setSingleChoiceItems(R.array.WAT_array, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSelectedItem = which; }});

                            builder5.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    set_room.setText(getResources().getStringArray(R.array.WAT_array)[mSelectedItem]); }}); }



                        builder5.show();
                    }
                });
                //end choose building

                //Choose start time
                chooseStart.setOnClickListener(new View.OnClickListener() ////////ADD TIME BUTTON
                {
                    TimePicker timePicker;

                    @Override
                    public void onClick(View view)
                    {
                        View timeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
                        timePicker = (TimePicker) timeView.findViewById(R.id.dialog_time_picker);


                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                        builder2.create();
                        builder2.setView(timeView);
                        builder2.setTitle(R.string.time_picker_title);
                        builder2.setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                int hour = 0;
                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                    hour = timePicker.getHour();
                                                }
                                                else
                                                {
                                                    hour = timePicker.getCurrentHour();
                                                }

                                                int minute = 0;
                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                    minute = timePicker.getMinute();
                                                }
                                                else
                                                {
                                                    minute = timePicker.getCurrentMinute();
                                                }
                                                ////////////////////////////

                                                String name1 = updateTime(hour, minute); //gets the result of what we clicked
                                                set_start.setText(name1);
                                            }
                                        });
                        builder2.show();

                    }
                });
                //end choose start time

                //Choose end time
                chooseEnd.setOnClickListener(new View.OnClickListener() ////////ADD TIME BUTTON
                {
                    TimePicker timePicker;

                    @Override
                    public void onClick(View view)
                    {
                        View timeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
                        timePicker = (TimePicker) timeView.findViewById(R.id.dialog_time_picker);


                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                        builder2.create();
                        builder2.setView(timeView);
                        builder2.setTitle(R.string.time_picker_title);
                        builder2.setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int hour = 0;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            hour = timePicker.getHour();
                                        }
                                        else
                                        {
                                            hour = timePicker.getCurrentHour();
                                        }

                                        int minute = 0;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            minute = timePicker.getMinute();
                                        }
                                        else
                                        {
                                            minute = timePicker.getCurrentMinute();
                                        }
                                        ////////////////////////////

                                        String name1 = updateTime(hour, minute); //gets the result of what we clicked
                                        set_end.setText(name1);
                                    }
                                });
                        builder2.show();
                    }
                });
                //end choose end time

                ///save button
                myClass = new ArrayList<>();



                //TOGGLE BUTTONS
                multi.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
                    @Override
                    public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked)
                    {
                        Set<Integer> checkedIds = multi.getCheckedIds();
                        Set<Integer> positions = new HashSet<>(); // Holder for all checked positions
                        for (int id : checkedIds) {
                            View bview = multi.findViewById(id);
                            int position = multi.indexOfChild(bview);
                            positions.add(position);
                        }
                        if (isChecked)
                        {
                            if(positions.contains(0))
                                monResult = "mon";
                            if(positions.contains(1))
                                tuesResult = "tues";
                            if(positions.contains(2))
                                wedResult = "wed";
                            if(positions.contains(3))
                                thurResult = "thur";
                            if(positions.contains(4))
                                friResult = "fri";
                        }
                        //Toast.makeText(getActivity(), positions.toString(), Toast.LENGTH_SHORT).show(); //Need to change this to class databasse -this is filler
                    }
                });

                builder.create();
                break;

            case R.id.deleteButton:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setTitle("Delete a class")
                        .setSingleChoiceItems(R.array.classrooms, 0,
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mSelectedItem = which;
                                        //System.out.println(which);
                                        //mDatabasehelper.DeleteData_Class(mSelectedItem);

                                    }
                                })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "You deleted  !! \n ", Toast.LENGTH_SHORT).show(); //Need to change this to class databasse -this is filler

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(getActivity(), "You clicked Cancel \n No Item was selected !!", Toast.LENGTH_SHORT).show();

                            }
                        });
                builder2.create();
                builder2.show();
                break;
        }

    }
    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String myTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return myTime;
    }

    private View.OnClickListener onConfirmListener(final AlertDialog show) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), set_class.getText().toString() + " was successfully added to your schedule.", Toast.LENGTH_LONG).show();

                if (monResult == "mon")
                {
                    add(set_class.getText().toString(), set_building.getText().toString(), set_room.getText().toString(), set_start.getText().toString(), set_end.getText().toString(), monResult);
                    monResult = "";
                }
                if (tuesResult == "tues")
                {
                    add(set_class.getText().toString(), set_building.getText().toString(), set_room.getText().toString(), set_start.getText().toString(), set_end.getText().toString(), tuesResult);
                    tuesResult = "";
                }

                if (wedResult == "wed")
                {
                    add(set_class.getText().toString(), set_building.getText().toString(), set_room.getText().toString(), set_start.getText().toString(), set_end.getText().toString(), wedResult);
                    wedResult = "";
                }
                if (thurResult == "thur")
                {
                    add(set_class.getText().toString(), set_building.getText().toString(), set_room.getText().toString(), set_start.getText().toString(), set_end.getText().toString(), thurResult);
                    thurResult = "";
                }
                if (friResult == "fri")
                {
                    add(set_class.getText().toString(), set_building.getText().toString(), set_room.getText().toString(), set_start.getText().toString(), set_end.getText().toString(), friResult);
                    friResult = "";
                }
                mViewPager.getAdapter().notifyDataSetChanged(); //CODE THAT UPDATES/REFRESHES WOOO
                show.dismiss();

            }
        };
    }
    public void add(String class_name, String building_name, String room_name, String start,String end,String day)
    {
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        long result = mDatabasehelper.addData_Class(class_name,building_name,room_name,start,end,day);

    }


}
