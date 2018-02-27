package ucr.ucrmap;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;
import com.ncapdevi.fragnav.FragNavController;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.roughike.bottombar.TabSelectionInterceptor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewMapFragment.ReceiveData, fragment_ucrevent.ReceiveData, fragment_monday.ReceiveMonClass, fragment_tuesday.ReceiveTuesClass, fragment_wednesday.ReceiveWedClass, fragment_thursday.ReceiveThurClass, fragment_friday.ReceiveFriClass,
        fragment_profile.SendCategory, NavigationFragment.SendNavigation, fragment_schedule.SendClass, VivzAdapter.OnRecyclerItemClickListener {

    double set_longitude;
    double set_latitude;
    String set_poi;

    TextView set_start;
    TextView set_end;

    String className;
    String buildingName;
    String roomName;
    String startTime;
    String endTime;
    int classLayout;
    String classDay;

    ArrayList<String> Dates;
    ArrayList<Pair<String,String>> Title;
    ArrayList<Pair<String,String>> Building;
    ArrayList<Pair<String,String>> Room;


    DatabaseHelper mDataBaseHelper;

    private BottomBar bottomBar;
    private FragNavController mNavController;

    private final int INDEX_NAVIGATION = FragNavController.TAB1;
    private final int INDEX_NEWMAP = FragNavController.TAB2;
    private final int INDEX_PLACES = FragNavController.TAB3;
    private final int INDEX_ABOUT = FragNavController.TAB4;
    private final int INDEX_PROFILE = FragNavController.TAB5;
    private final int INDEX_SCHEDULE = FragNavController.TAB6;
    private final int INDEX_FRIENDS = FragNavController.TAB7;
    private final int INDEX_EVENTS = FragNavController.TAB8;
    private final int INDEX_SETTINGS = FragNavController.TAB9;
    private final int INDEX_UCREVENT = FragNavController.TAB10;


    private AccountHeader headerResult = null;
    Drawer result = null;
    private static final int PROFILE_SETTING = 100000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dates = new ArrayList<>();
        Title = new ArrayList<Pair<String, String>>();
        Building = new ArrayList<Pair<String, String>>();
        Room = new ArrayList<Pair<String, String>>();

        mDataBaseHelper = new DatabaseHelper(this);
        //web_Crawl_Events crawl= new web_Crawl_Events();
       // new doit().execute(); uncomment for web crawler


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        LocalBroadcastManager.getInstance(this).registerReceiver(classMessageReceiver, new IntentFilter("class-message"));

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar); //tell android we're using our own actionbar now
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> test = new ArrayList<>();


        Intent i = getIntent();
        //test = (ArrayList<String>) getIntent().getSerializableExtra("list");
        test = i.getStringArrayListExtra("my_array");

        /*
        for (int k = 0; k< 4; k++)
        {
            Toast.makeText(MainActivity.this, test.get(k).toString(), Toast.LENGTH_SHORT).show();

        }*/





        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        //Fragment Index
        List<Fragment> fragments = new ArrayList<>(10);
        fragments.add(NavigationFragment.newInstance(0));
        fragments.add(NewMapFragment.newInstance(0));
        fragments.add(PlacesFragment.newInstance(0));
        fragments.add(AboutFragment.newInstance(0));
        fragments.add(fragment_profile.newInstance(0));
        fragments.add(fragment_schedule.newInstance(0));
        fragments.add(fragment_friends.newInstance(0));
        fragments.add(fragment_events.newInstance(0));
        fragments.add(fragment_settings.newInstance(0));
        fragments.add(fragment_ucrevent.newInstance(0));


        //Link fragment to controller
        //mNavController = new FragNavController(getSupportFragmentManager(),R.id.container,fragments);

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .rootFragments(fragments)
                .build();


        //Start bottom bar

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        //bottomBar.selectTabAtPosition(INDEX_PLACES); on start, select this tab first
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                //Intent intent = null;
                if (tabId == R.id.action_nav) {
                    mNavController.switchTab(INDEX_NAVIGATION);
                    //set_poi = "cancel"; //Need to reset POI selection when reloading map
                } else if (tabId == R.id.action_map) {
                    mNavController.switchTab(INDEX_NEWMAP);

                } else if (tabId == R.id.action_places) {
                    mNavController.switchTab(INDEX_PLACES);
                    set_poi = ""; //Need to reset POI selection when reloading map
                } else if (tabId == R.id.action_profile) {
                    mNavController.switchTab(INDEX_PROFILE);
                    /*
                    for (String s : Dates)
                    {
                        Toast.makeText(MainActivity.this, s.toString(), Toast.LENGTH_SHORT).show();

                    }*/

                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {//Want Refresh on reselect
            @Override
            public void onTabReSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.action_nav:
                        mNavController.switchTab(INDEX_NAVIGATION);
                        break;

                    case R.id.action_map:
                        mNavController.switchTab(INDEX_NEWMAP);
                        break;

                    case R.id.action_places:
                        mNavController.switchTab(INDEX_PLACES);
                        break;

                    case R.id.action_profile:
                        mNavController.switchTab(INDEX_PROFILE);
                        break;

                }
            }
        });
        //end bottom bar

        //Account
        final IProfile profile = new ProfileDrawerItem().withName("Edward Hernandez").withEmail("ehern044@ucr.edu").withIcon(R.drawable.ic_avatar).withIdentifier(100);


        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header_blue_background)
                .addProfiles(
                        profile
                )

                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true) //navigation drawer status bar open
                .addDrawerItems(
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings).withIdentifier(1),
                        new DividerDrawerItem(),

                        new PrimaryDrawerItem().withName("Visit our website").withIcon(R.drawable.ic_link_variant_black_24dp).withIdentifier(2),
                        new PrimaryDrawerItem().withName("Rate This App").withIcon(R.drawable.ic_stars_black_24dp).withIdentifier(3),
                        new PrimaryDrawerItem().withName("Contact Us").withIcon(R.drawable.ic_email_black_24dp).withIdentifier(4),
                        new PrimaryDrawerItem().withName("Advertise with us").withIcon(R.drawable.ic_domain_black_24dp).withIdentifier(5),
                        new PrimaryDrawerItem().withName("FAQ").withIcon(R.drawable.ic_help_circle_black_24dp).withIdentifier(6),

                        new SectionDrawerItem().withName("Privacy & Legal"),
                        new PrimaryDrawerItem().withName("Version 1.0 Beta")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {

                            if (drawerItem.getIdentifier() == 1) {
                                mNavController.switchTab(INDEX_SETTINGS);
                            } else if (drawerItem.getIdentifier() == 2) {
                                Uri uri = Uri.parse("https://edward8e.github.io/UCRMaps/index.html");
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

                                startActivity(browserIntent);

                                //mNavController.switchTab(INDEX_SCHEDULE);
                            } else if (drawerItem.getIdentifier() == 3) {
                                //mNavController.switchTab(INDEX_FRIENDS);
                            } else if (drawerItem.getIdentifier() == 4) {

                                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, "rheng009@gmail.com");
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacting UCR");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi....");

                                emailIntent.setType("text/plain"); // <-- HERE
                                startActivity(emailIntent); // <-- AND HERE

                                //mNavController.switchTab(INDEX_EVENTS);
                            } else if (drawerItem.getIdentifier() == 5) {
                                //mNavController.switchTab(INDEX_UCREVENT);

                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(false); //Disable to get actiobar gone
        //result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true); //Disable to get actiobar gone


        //Permissions section
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //The user have conceded permission
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //close the app or do whatever you want
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
        //end Permission

    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        outState = result.saveInstanceState(outState);
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
        //bottomBar.onSaveInstanceState();
    }

    public void setTime(String name, int num) //Get Fragment B(addClass)
    {
        //set time here instead of addClass fragment
        if (num == 0) //start time button
        {
            set_start = (TextView) this.findViewById(R.id.startTextTime);
            //set_start.setText(name);
        } else if (num == 1) //end time button
        {
            set_end = (TextView) this.findViewById(R.id.endTextTime);
            set_end.setText(name);
        }

    }


    public void setNavigation(Double latitude, Double longitude) {
        set_latitude = latitude;
        set_longitude = longitude;

        //Toast.makeText(getApplicationContext(), String.valueOf(set_latitude), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), String.valueOf(set_longitude), Toast.LENGTH_LONG).show();

        //getLatitude(set_latitude);
        //getLongitude(set_longitude);

        bottomBar.selectTabAtPosition(INDEX_NEWMAP);
        mNavController.switchTab(INDEX_NEWMAP);


    }

    @Override
    public void openDrawer() {
        result.openDrawer();
    }

    //Receive POI Selected from VivzAdapter
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int poi = intent.getIntExtra("POI", 0);
            String classTest = intent.getStringExtra("CLASS");

            if (poi == 0)
                set_poi = "Food";
            else if (poi == 1)
                set_poi = "Coffee Shops";
            else if (poi == 2)
                set_poi = "Markets";
            else if (poi == 3)
                set_poi = "Bus Stops";
            else if (poi == 4)
                set_poi = "Libraries";
            else if (poi == 5)
                set_poi = "Bike Racks";
            else if (poi == 6)
                set_poi = "Buildings";


            bottomBar.selectTabAtPosition(INDEX_NEWMAP);
            mNavController.switchTab(INDEX_NEWMAP);

        }
    };

    //Receive POI Selected from VivzAdapter
    public BroadcastReceiver classMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String classTest = intent.getStringExtra("CLASS");


            if (classTest == "CHUNG")
            {
                bottomBar.selectTabAtPosition(INDEX_NEWMAP);
                mNavController.switchTab(INDEX_NEWMAP);
            }

        }
    };



    public void onRecyclerItemClick(String data) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("You tapped...");
        //alertDialogBuilder.setMessage(data);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    //NEWMAP fragment gets latitude from main activity
    @Override
    public double getLatitude() {
        return set_latitude;
    }

    //NEWMAP fragment gets longitude from main activity
    @Override
    public double getLongitude() {
        return set_longitude;
    }

    //NEWMAP fragment gets POI from main activity
    @Override
    public String getPOI() {
        return set_poi;
    }


    @Override
    public void setCategory(String category) {
        if (category == "Class") {
            mNavController.switchTab(INDEX_SCHEDULE);

        }
        if (category == "Friend") {
            mNavController.switchTab(INDEX_FRIENDS);

        }
        if (category == "Favorite") {
            //mNavController.switchTab(INDEX_SCHEDULE);

        }
        if (category == "Saved") {
            mNavController.switchTab(INDEX_UCREVENT);

        }
    }

    @Override
    public void setClass(String classNameData, String buildingDescriptionData,String roomDescriptionData, String startTimeData, String endTimeData, int layoutType, String classDayData) {

        className = classNameData;
        buildingName = buildingDescriptionData;
        roomName = roomDescriptionData;
        startTime = startTimeData;
        endTime = endTimeData;
        classLayout = layoutType;
        classDay = classDayData;

    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getBuildingName() {
        return buildingName;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public int getIntLayout() {
        return classLayout;
    }

    @Override
    public String getDay() {
        return classDay;
    }

    @Override
    public ArrayList<String> getDays() {

        return Dates;
    }

    @Override
    public ArrayList<Pair<String,String>> getEventTitle() {

        return Title;
    }

    @Override
    public ArrayList<Pair<String,String>> getBuilding() {

        return Building;
    }

    @Override
    public ArrayList<Pair<String,String>> getRoom() {

        return Room;
    }

    public class doit extends AsyncTask<Void,ArrayList<String>,ArrayList<String>> { //Returning string

        String title, date;
        Document document,document1;
        int count;
        ArrayList<String> Date = new ArrayList<String>();
        //ArrayList<String> Title = new ArrayList<String>();
        //ArrayList<String> Building = new ArrayList<String>();
        //ArrayList<String> Room = new ArrayList<String>();
        Elements dates_content, eventlist,testlinks,testloc1;
        Element testtable,testloc;
        int counter= 0;
        int counter_row= 0;
        String link1,new_loc,event_info,room;

        @Override
        protected ArrayList<String> doInBackground(Void... arg0) { //Want string returned

            try {
                document = Jsoup.connect("https://events.ucr.edu/calendar/week").get();
                title = document.title();
                dates_content = document.getElementsByClass("box_title_small date_divider");
                eventlist = document.getElementsByClass("item event_item vevent");


                for(Element link: dates_content.select("h2")) // gets the date from <h2>
                {
                    // get dates
                    Log.i("date:",link.ownText());
                    String day_number = link.ownText();
                    day_number = day_number.substring(day_number.length()-2);
                    Date.add(link.ownText());
                    System.out.println(link.ownText());
                   // Log.i("day:",day_number);

                    eventlist = document.getElementsByClass("item event_item vevent");
                    for(Element link2:eventlist)
                    {
                        String match_date = link2.select("abbr").toString(); // get the date for the event
                        int cut = match_date.indexOf('T');
                        match_date = match_date.substring(13,cut); // start of date from <abbr text >
                        match_date = match_date.substring(match_date.length()-2);
                       // Log.i("mmmm",match_date);
                       // Log.i("test2",link2.id());



                        if(day_number.equals(match_date))
                        {
                            Element link4 = document.getElementById(link2.id().toString());
                            Log.i("test5",link4.select("div.heading").text());
                            System.out.println("Title: " +link4.select("div.heading").text()); // title
                            System.out.println("Location: "+link4.select("div.location").text()); // location
                            System.out.println("Time: "+link4.select("abbr").text()); // time and day
                            System.out.println("Link: "+link4.select("a").first().attr("abs:href").toString()); // link to event
                        }
                    }


                }

                // get the description of the events
                for(Element link1:eventlist.select("h4.description"))
                {
                   // Log.i("test",link1.ownText());
                }
               // testtable = document.getElementById("event_instance_3296816");
               // String  x = testtable.select("h4.description").text();

               // Log.i("yes",x);


                for(Element link2:eventlist.select("div.heading"))
                {
                    //Log.i("test2",link2.ownText());
                }
             /*   eventlist = document.getElementsByClass("item event_item vevent");
                for(Element link2:eventlist)
                {
                    String match_date = link2.select("abbr").toString(); // get the date for the event
                    int cut = match_date.indexOf('T');
                    match_date = match_date.substring(13,cut); // start of date from <abbr text >
                    match_date = match_date.substring(match_date.length()-2);
                    Log.i("mmmm",match_date);
                    Log.i("test2",link2.id());
                }*/

            } catch (IOException e) {
                Log.i("NOOO: ","gg");
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(ArrayList<String> str) {
            super.onPostExecute(str);
            for (int i = 0; i < Room.size(); i++) {
                System.out.println(Building.get(i));
                System.out.println(Room.get(i));
            }

        }

    }

}

