package ucr.ucrmap;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.github.clans.fab.FloatingActionMenu;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;
import com.mapbox.services.api.directions.v5.DirectionsCriteria;

//import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
//import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
//import com.mapbox.directions.v5.models.DirectionsResponse;
//import com.mapbox.directions.v5.models.DirectionsRoute;


import com.mapbox.services.api.utils.turf.TurfHelpers;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import android.location.Location;
import com.mapbox.mapboxsdk.geometry.LatLng;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.services.android.location.LostLocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;

import com.mapbox.mapboxsdk.style.functions.Function;
import com.mapbox.mapboxsdk.style.functions.stops.Stop;
import com.mapbox.mapboxsdk.style.functions.stops.Stops;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.api.utils.turf.TurfException;
import com.mapbox.services.api.utils.turf.TurfJoins;
import com.mapbox.services.commons.models.Position;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.view.animation.AlphaAnimation;


import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationUnitType.TYPE_METRIC;


public class NewMapFragment extends Fragment implements OnMapReadyCallback, LocationEngineListener, PermissionsListener, LocationRecyclerViewAdapter.ClickListener {

    NavigationFragment.SendNavigation sendData;

    private MapboxMap mapboxMap;
    private MapView mapView;
    private StyleCycle styleCycle = new StyleCycle();

    //Origin + Destination stuff
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;

    private Location originLocation;
    private Location lastLocation;
    private Location ucrLocation;

    private Point ucrPoint;
    private Point currentPoint;
    private Point destinationPoint;
    //////////////////////////////////

    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;

    // variables for calculating and drawing a route

    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    ReceiveData receiveData;

    //POI
    private int chosenTheme;

    private CustomThemeManager customThemeManager;

    private FeatureCollection foodCollection;
    private FeatureCollection coffeeCollection;
    private FeatureCollection marketCollection;
    private FeatureCollection busCollection;
    private FeatureCollection libraryCollection;
    private FeatureCollection bikeCollection;
    private FeatureCollection buildingCollection;


    private ArrayList<IndividualLocation> listofFoodLocations;
    private ArrayList<IndividualLocation> listOfCoffeeLocations;
    private ArrayList<IndividualLocation> listOfMarketLocations;
    private ArrayList<IndividualLocation> listOfBusLocations;
    private ArrayList<IndividualLocation> listOfLibraryLocations;
    private ArrayList<IndividualLocation> listOfBikeLocations;
    private ArrayList<IndividualLocation> listOfBuildingLocations;


    private RecyclerView locationsRecyclerView;
    private LocationRecyclerViewAdapter styleRvAdapter;
    private Point destinationMarker2;


    private static final int CAMERA_MOVEMENT_SPEED_IN_MILSECS = 1200;

    //Indoor
    private GeoJsonSource indoorBuildingSource;
    private List<Position> boundingBox;
    private View levelButtons;

    //FAB BUTTON MENU
    private FloatingActionMenu mapMenu;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    private com.github.clans.fab.FloatingActionButton fab4;
    private com.github.clans.fab.FloatingActionButton fab5;

    SlidingUpPanelLayout mLayout;

    Point origintest;
    Point destinationtest;


    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();


    public NewMapFragment() {
        // Required empty public constructor
    }

    public static NewMapFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        NewMapFragment newmapFragment = new NewMapFragment();
        newmapFragment.setArguments(args);
        return newmapFragment;
    }

    public interface ReceiveData {
        double getLatitude();
        double getLongitude();
        String getPOI();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
            sendData = (NavigationFragment.SendNavigation) context;
            receiveData = (ReceiveData) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement ReceiveData");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Mapbox.getInstance(getActivity(), "pk.eyJ1IjoiamVmZmJvaXJkIiwiYSI6ImNqOW02YnQwdzRpN3gzM3BhcTMzMW9oOW0ifQ.JLcXB6TpQt3I4DDbA726Ig");
        View v = inflater.inflate(R.layout.fragment_newmap, container, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.setStyleUrl(styleCycle.getStyle());
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        locationsRecyclerView = v.findViewById(R.id.map_layout_rv); //map_layout_rv = recyclerview on activity_main
        levelButtons = v.findViewById(R.id.floor_level_buttons);




        //Floating menu
        mapMenu = (FloatingActionMenu) v.findViewById(R.id.map_menu);
        fab1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.fabStyles);
        fab2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.ucrLocation);
        fab3 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.currentLocation);
        fab4 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.classNav);
        fab5 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.mapDrawer);



        //BottomSheetLayout bottomSheet = (BottomSheetLayout) v.findViewById(R.id.bottomsheet);
        //bottomSheet.showWithSheetView(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friends, bottomSheet, false));


        /*
        mLayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });*/





        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapboxMap != null)
                {
                    mapboxMap.setStyleUrl(styleCycle.getNextStyle()); //Change Style
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapboxMap != null)
                {
                    LatLng latLng = new LatLng(33.974942, -117.327270);
                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                }
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapboxMap != null)
                {
                    setCameraPosition(originLocation); //Go to user location
                }
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String awsPoolId = null;

                if (currentRoute != null) {

                    NavigationViewOptions options = NavigationViewOptions.builder()
                            .directionsRoute(currentRoute)
                            //.origin(currentPoint)
                            //.destination(destinationMarker2)
                            .directionsProfile(DirectionsCriteria.PROFILE_WALKING)
                            .shouldSimulateRoute(false)
                            .awsPoolId(awsPoolId)
                            .build();


                    // Pass in your Amazon Polly pool id for speech synthesis using Amazon Polly
                    // Set to null to use the default Android speech synthesizer


                    // Call this method with Context from within an Activity
                    //NavigationLauncher.startNavigation(getActivity(), currentRoute, awsPoolId, true);
                    NavigationLauncher.startNavigation(getActivity(), options);
                }
                else
                {
                    Toast.makeText(getActivity(),"Error getting route", Toast.LENGTH_LONG).show();

                }


            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendData.openDrawer();
            }
        });





        //Set up Custom Theme
        //customThemeManager = new CustomThemeManager(getContext());
        //customThemeManager = new CustomThemeManager(chosenTheme, getActivity(), mapView, mapboxMap);
        //customThemeManager.initializeTheme();

        chosenTheme = R.style.AppTheme_Neutral;


        //POI
        // Create a GeoJSON feature collection from the GeoJSON file in the assets folder.
        try {
            getFeatureCollectionFromJson();
        } catch (Exception exception) {
            Log.e("NewMapFragment", "onCreate: " + exception);
            Toast.makeText(getActivity(), R.string.failure_to_load_file, Toast.LENGTH_LONG).show();
        }

        // Initialize a list of IndividualLocation objects for future use with recyclerview
        listofFoodLocations = new ArrayList<>();
        listOfCoffeeLocations = new ArrayList<>();
        listOfMarketLocations = new ArrayList<>();
        listOfBusLocations = new ArrayList<>();
        listOfLibraryLocations = new ArrayList<>();
        listOfBikeLocations = new ArrayList<>();
        listOfBuildingLocations = new ArrayList<>();





        Button buttonSecondLevel = (Button) v.findViewById(R.id.second_level_button);
        buttonSecondLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indoorBuildingSource.setGeoJson(loadGeoJsonFromAsset("test1.geojson"));
            }
        });

        Button buttonGroundLevel = (Button) v.findViewById(R.id.ground_level_button);
        buttonGroundLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indoorBuildingSource.setGeoJson(loadGeoJsonFromAsset("test1.geojson"));
            }
        });

        return v;

    }


    //Load the map
    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        enableLocationPlugin(); //Enable user location finding


        originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());

        ucrPoint = Point.fromLngLat(-117.327270,33.974942);
        //ucrLocation = Location.fromLngLat(ucrPoint);

        currentPoint = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());

        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(receiveData.getLatitude(), receiveData.getLongitude()))
                .title("BRNHL A125")
                .snippet("Classroom"));


        Icon food = IconFactory.getInstance(getActivity()).fromResource(R.drawable.food);
        Icon coffee = IconFactory.getInstance(getActivity()).fromResource(R.drawable.coffee);
        Icon market = IconFactory.getInstance(getActivity()).fromResource(R.drawable.market);
        Icon bus = IconFactory.getInstance(getActivity()).fromResource(R.drawable.bus);
        Icon library = IconFactory.getInstance(getActivity()).fromResource(R.drawable.library);
        Icon bike = IconFactory.getInstance(getActivity()).fromResource(R.drawable.bike);
        Icon building = IconFactory.getInstance(getActivity()).fromResource(R.drawable.white_unselected_house);

        Icon panda = IconFactory.getInstance(getActivity()).fromResource(R.mipmap.ic_panda);





        //POI SENT

        //FOOD
        if (receiveData.getPOI() == "Food") {

            //This removes all previous markers
            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            // Create a list of features from the feature collection
            List<Feature> featureList = foodCollection.getFeatures();

            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList.size(); x++) {

                Feature singleLocation = featureList.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName = singleLocation.getStringProperty("name");
                String singleLocationHours = singleLocation.getStringProperty("hours");
                String singleLocationDescription = singleLocation.getStringProperty("description");
                String singleLocationPhoneNum = singleLocation.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition = (Position) singleLocation.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng = new LatLng(singleLocationPosition.getLatitude(),
                        singleLocationPosition.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listofFoodLocations.add(new IndividualLocation(
                        singleLocationName,
                        singleLocationDescription,
                        singleLocationHours,
                        singleLocationPhoneNum,
                        singleLocationLatLng
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP

                MarkerOptions options = new MarkerOptions();
                options.position(singleLocationLatLng);
                options.title(singleLocationName);
                if (singleLocationName.toString().equals("Panda Express"))
                {
                    options.icon(panda);
                }
                else
                {
                    options.icon(food);
                }
                mapboxMap.addMarker(options);

                getInformationFromDirectionsApi(singleLocationLatLng.getLatitude(),
                        singleLocationLatLng.getLongitude(), false, x);

            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 1);


        }
        //COFFEE
        else if (receiveData.getPOI() == "Coffee Shops") {
            //This removes all previous markers
            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            // Create a list of features from the feature collection
            List<Feature> featureList2 = coffeeCollection.getFeatures();

            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList2.size(); x++) {

                Feature singleLocation2 = featureList2.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName2 = singleLocation2.getStringProperty("name");
                String singleLocationHours2 = singleLocation2.getStringProperty("hours");
                String singleLocationDescription2 = singleLocation2.getStringProperty("description");
                String singleLocationPhoneNum2 = singleLocation2.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition2 = (Position) singleLocation2.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng2 = new LatLng(singleLocationPosition2.getLatitude(),
                        singleLocationPosition2.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfCoffeeLocations.add(new IndividualLocation(
                        singleLocationName2,
                        singleLocationDescription2,
                        singleLocationHours2,
                        singleLocationPhoneNum2,
                        singleLocationLatLng2
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP


                mapboxMap.addMarker(new MarkerOptions()
                        .position(singleLocationLatLng2)
                        .title(singleLocationName2)
                        .icon(coffee));

                getInformationFromDirectionsApi(singleLocationLatLng2.getLatitude(),
                        singleLocationLatLng2.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 2);

        }
        //MARKETS
        else if (receiveData.getPOI() == "Markets") {

            //This removes all previous markers
            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            // Create a list of features from the feature collection
            List<Feature> featureList3 = marketCollection.getFeatures();

            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList3.size(); x++) {

                Feature singleLocation3 = featureList3.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName3 = singleLocation3.getStringProperty("name");
                String singleLocationHours3 = singleLocation3.getStringProperty("hours");
                String singleLocationDescription3 = singleLocation3.getStringProperty("description");
                String singleLocationPhoneNum3 = singleLocation3.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition3 = (Position) singleLocation3.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng3 = new LatLng(singleLocationPosition3.getLatitude(),
                        singleLocationPosition3.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfMarketLocations.add(new IndividualLocation(
                        singleLocationName3,
                        singleLocationDescription3,
                        singleLocationHours3,
                        singleLocationPhoneNum3,
                        singleLocationLatLng3
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP


                mapboxMap.addMarker(new MarkerOptions()
                        .position(singleLocationLatLng3)
                        .title(singleLocationName3)
                        .icon(market));
                getInformationFromDirectionsApi(singleLocationLatLng3.getLatitude(),
                        singleLocationLatLng3.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 3);
        }
        //BUS STOPS
        else if (receiveData.getPOI() == "Bus Stops") {

            //This removes all previous markers
            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            // Create a list of features from the feature collection
            List<Feature> featureList4 = busCollection.getFeatures();

            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList4.size(); x++) {

                Feature singleLocation4 = featureList4.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName4 = singleLocation4.getStringProperty("name");
                String singleLocationHours4 = singleLocation4.getStringProperty("hours");
                String singleLocationDescription4 = singleLocation4.getStringProperty("description");
                String singleLocationPhoneNum4 = singleLocation4.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition4 = (Position) singleLocation4.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng4 = new LatLng(singleLocationPosition4.getLatitude(),
                        singleLocationPosition4.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfBusLocations.add(new IndividualLocation(
                        singleLocationName4,
                        singleLocationDescription4,
                        singleLocationHours4,
                        singleLocationPhoneNum4,
                        singleLocationLatLng4
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP


                mapboxMap.addMarker(new MarkerOptions()
                        .position(singleLocationLatLng4)
                        .title(singleLocationName4)
                        .icon(bus));

                getInformationFromDirectionsApi(singleLocationLatLng4.getLatitude(),
                        singleLocationLatLng4.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 4);

        }
        else if (receiveData.getPOI() == "Libraries") {

            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            List<Feature> featureList5 = libraryCollection.getFeatures();

            //LIBRARIES
            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList5.size(); x++) {

                Feature singleLocation5 = featureList5.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName5 = singleLocation5.getStringProperty("name");
                String singleLocationHours5 = singleLocation5.getStringProperty("hours");
                String singleLocationDescription5 = singleLocation5.getStringProperty("description");
                String singleLocationPhoneNum5 = singleLocation5.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition5 = (Position) singleLocation5.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng5 = new LatLng(singleLocationPosition5.getLatitude(),
                        singleLocationPosition5.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfLibraryLocations.add(new IndividualLocation(
                        singleLocationName5,
                        singleLocationDescription5,
                        singleLocationHours5,
                        singleLocationPhoneNum5,
                        singleLocationLatLng5
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP


                mapboxMap.addMarker(new MarkerOptions()
                        .position(singleLocationLatLng5)
                        .title(singleLocationName5)
                        .icon(library));

                getInformationFromDirectionsApi(singleLocationLatLng5.getLatitude(),
                        singleLocationLatLng5.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 5);
        }
        //BIKE RACKS
        else if (receiveData.getPOI() == "Bike Racks") {

            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            List<Feature> featureList6 = bikeCollection.getFeatures();

            //LIBRARIES
            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList6.size(); x++) {

                Feature singleLocation6 = featureList6.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName6 = singleLocation6.getStringProperty("name");
                String singleLocationHours6 = singleLocation6.getStringProperty("hours");
                String singleLocationDescription6 = singleLocation6.getStringProperty("description");
                String singleLocationPhoneNum6 = singleLocation6.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition6 = (Position) singleLocation6.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng6 = new LatLng(singleLocationPosition6.getLatitude(),
                        singleLocationPosition6.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfBikeLocations.add(new IndividualLocation(
                        singleLocationName6,
                        singleLocationDescription6,
                        singleLocationHours6,
                        singleLocationPhoneNum6,
                        singleLocationLatLng6
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP


                mapboxMap.addMarker(new MarkerOptions()
                        .position(singleLocationLatLng6)
                        .title(singleLocationName6)
                        .icon(bike));

                getInformationFromDirectionsApi(singleLocationLatLng6.getLatitude(),
                        singleLocationLatLng6.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 6);

        }

        //BUILDINGS (works to this point)
        else if (receiveData.getPOI() == "Buildings") {

            for (Marker singleMarker : mapboxMap.getMarkers()) {
                singleMarker.remove();
            }

            List<Feature> featureList7 = buildingCollection.getFeatures(); //ERROR HERE


            // Loop through the locations to add markers to the map
            for (int x = 0; x < featureList7.size(); x++) {

                Feature singleLocation7 = featureList7.get(x);

                // Get the single location's String properties to place in its map marker
                String singleLocationName7= singleLocation7.getStringProperty("name");
                String singleLocationHours7 = singleLocation7.getStringProperty("hours");
                String singleLocationDescription7 = singleLocation7.getStringProperty("description");
                String singleLocationPhoneNum7 = singleLocation7.getStringProperty("phone");

                // Get the single location's LatLng coordinates
                Position singleLocationPosition7 = (Position) singleLocation7.getGeometry().getCoordinates();

                // Create a new LatLng object with the Position object created above
                LatLng singleLocationLatLng7 = new LatLng(singleLocationPosition7.getLatitude(),
                        singleLocationPosition7.getLongitude());

                // Add the location to the Arraylist of locations for later use in the recyclerview
                listOfBuildingLocations.add(new IndividualLocation( //AND HERE
                        singleLocationName7,
                        singleLocationDescription7,
                        singleLocationHours7,
                        singleLocationPhoneNum7,
                        singleLocationLatLng7
                ));

                // Add the location's marker to the map (RESPONSIBLE FOR ADDING MARKER TO MAP

                //OLD WAY TO add marker
                //mapboxMap.addMarker(new MarkerOptions()
                        //.position(singleLocationLatLng7)
                        //.title(singleLocationName7));

                //new way for more customization
                MarkerOptions options = new MarkerOptions();
                options.position(singleLocationLatLng7);
                options.title(singleLocationName7);
                if (singleLocationName7.toString().equals("UCR Bookstore"))
                {
                    options.icon(building);
                }
                else
                {
                    options.icon(building);
                }
                mapboxMap.addMarker(options);



                getInformationFromDirectionsApi(singleLocationLatLng7.getLatitude(),
                        singleLocationLatLng7.getLongitude(), false, x);
            }
            setUpRecyclerViewOfLocationCards(chosenTheme, 7); //MAYBE HERE

        }

        //Set destination from navigation menu input
        destinationPoint = Point.fromLngLat(receiveData.getLongitude(), receiveData.getLatitude());
        destinationCoord = new LatLng(receiveData.getLatitude(), receiveData.getLongitude());
        getRoute(currentPoint, destinationPoint);


        //Click on Recyclerview
        setUpMarkerClickListener();

        //INDOOR FLOOR PLAN

        boundingBox = new ArrayList<>();
        boundingBox.add(Position.fromCoordinates(-77.03791, 38.89715));
        boundingBox.add(Position.fromCoordinates(-77.03791, 38.89811));
        boundingBox.add(Position.fromCoordinates(-77.03532, 38.89811));
        boundingBox.add(Position.fromCoordinates(-77.03532, 38.89708));

        mapboxMap.setOnCameraChangeListener(new MapboxMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {

                if (position.zoom > 16) {
                    try {
                        if (TurfJoins.inside(Position.fromCoordinates(
                                position.target.getLongitude(),
                                position.target.getLatitude()),
                                boundingBox)) {
                            if (levelButtons.getVisibility() != View.VISIBLE) {
                                showLevelButton();
                            }
                        } else {
                            if (levelButtons.getVisibility() == View.VISIBLE) {
                                hideLevelButton();
                            }
                        }
                    } catch (TurfException turfException) {
                        turfException.printStackTrace();
                    }
                } else if (levelButtons.getVisibility() == View.VISIBLE) {
                    hideLevelButton();
                }
            }
        });

        indoorBuildingSource = new GeoJsonSource("indoor-building", loadGeoJsonFromAsset("test1.geojson"));
        mapboxMap.addSource(indoorBuildingSource);

        // Add the building layers since we know zoom levels in range
        loadBuildingLayer();

    }

    private void setUpMarkerClickListener() {
        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                // Get the position of the selected marker
                LatLng positionOfSelectedMarker = marker.getPosition();



                for (int x = 0; x < mapboxMap.getMarkers().size(); x++) {
                    if (mapboxMap.getMarkers().get(x).getPosition() == positionOfSelectedMarker) {
                        // Scroll the recyclerview to the selected marker's card. It's "x-1" below because
                        // the current device location marker is part of the marker list but doesn't have its own card
                        // in the actual recyclerview.
                        locationsRecyclerView.smoothScrollToPosition(x); //RIGHT HERE

                    }
                }
                adjustMarkerSelectStateIcons(marker); //may cay cause if click onmap marker + location marker

                // Return true so that the selected marker's info window doesn't pop up
                return true;
            }
        });
    }

    private void adjustMarkerSelectStateIcons(Marker marker) {

        // Get the directionsApiClient route to the selected marker except if the mock device location marker is selected
        // Check for an internet connection before making the call to Mapbox Directions API
        if (deviceHasInternetConnection()) {
            // Start the call to the Mapbox Directions API
            getInformationFromDirectionsApi(marker.getPosition().getLatitude(),
                    marker.getPosition().getLongitude(), true, null);
        } else {
            Toast.makeText(getActivity(), R.string.no_internet_message, Toast.LENGTH_LONG).show();
        }

    }

    private boolean deviceHasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void getInformationFromDirectionsApi(double destinationLatCoordinate, double destinationLongCoordinate,
                                                 final boolean fromMarkerClick, @Nullable final Integer listIndex) {

        // Set up origin and destination coordinates for the call to the Mapbox Directions API
        currentPoint = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        destinationMarker2 = Point.fromLngLat(destinationLongCoordinate, destinationLatCoordinate); //POI Destinations




        // Initialize the directionsApiClient object for eventually drawing a navigation route on the map
        NavigationRoute.builder()
                .origin(currentPoint)
                .destination(destinationMarker2)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.access_token))
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // Check that the response isn't null and that the response has a route

                        if (fromMarkerClick) {
                            // Retrieve and draw the navigation route on the map
                            currentRoute = response.body().routes().get(0);

                            if (navigationMapRoute != null) {
                                navigationMapRoute.removeRoute();
                            } else {
                                navigationMapRoute = new NavigationMapRoute(mapView, mapboxMap); //HERE
                            }
                            navigationMapRoute.addRoute(currentRoute);


                            if (destinationMarker != null) {
                                mapboxMap.removeMarker(destinationMarker);
                            }


                            //Flag = 2;

                        } else {
                            // Use Mapbox Turf helper method to convert meters to miles and then format the mileage number
                            DecimalFormat df = new DecimalFormat("#.#");
                            String finalConvertedFormattedDistance = String.valueOf(df.format(TurfHelpers.convertDistance(
                                    response.body().routes().get(0).distance(), "meters", "miles")));

                            // Set the distance for each location object in the list of locations
                            if (listIndex != null) {

                                if (receiveData.getPOI() == "Food") {
                                    listofFoodLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    // Refresh the displayed recyclerview when the location's distance is set
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Coffee Shops")
                                {
                                    listOfCoffeeLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Markets")
                                {
                                    listOfMarketLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Bus Stops")
                                {
                                    listOfBusLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Libraries")
                                {
                                    listOfLibraryLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Bike Racks")
                                {
                                    listOfBikeLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                                if (receiveData.getPOI() == "Buildings")
                                {
                                    listOfBuildingLocations.get(listIndex).setDistance(finalConvertedFormattedDistance);
                                    styleRvAdapter.notifyDataSetChanged(); //UNCOMMENT
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });

    }

    private void repositionMapCamera(LatLng newTarget) {
        CameraPosition newCameraPosition = new CameraPosition.Builder()
                .target(newTarget)
                .bearing(0)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition), CAMERA_MOVEMENT_SPEED_IN_MILSECS);
    }

    @Override
    public void onItemClick(int position) {

        // Get the selected individual location via its card's position in the recyclerview of cards
        if (receiveData.getPOI() == "Food") {
            IndividualLocation selectedLocation = listofFoodLocations.get(position);

            // Retrieve and change the selected card's marker to the selected marker icon
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);

            // Reposition the map camera target to the selected marker
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);

            // Start call to the Mapbox Directions API
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Coffee Shops")
        {
            IndividualLocation selectedLocation = listOfCoffeeLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Markets")
        {
            IndividualLocation selectedLocation = listOfMarketLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Bus Stops")
        {
            IndividualLocation selectedLocation = listOfBusLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Libraries")
        {
            IndividualLocation selectedLocation = listOfLibraryLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Bike Racks")
        {
            IndividualLocation selectedLocation = listOfBikeLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }
        if (receiveData.getPOI() == "Buildings")
        {
            IndividualLocation selectedLocation = listOfBuildingLocations.get(position);
            Marker markerTiedToSelectedCard = mapboxMap.getMarkers().get(position);
            adjustMarkerSelectStateIcons(markerTiedToSelectedCard);
            LatLng selectedLocationLatLng = selectedLocation.getLocation();
            repositionMapCamera(selectedLocationLatLng);
            getInformationFromDirectionsApi(selectedLocationLatLng.getLatitude(), selectedLocationLatLng.getLongitude(), true, null);
        }

    }

    //Attach Gson to recyclerview adapter
    private void setUpRecyclerViewOfLocationCards(int chosenTheme, int flag) {

        SnapHelper snapHelper = new LinearSnapHelper();

        if (flag == 1)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listofFoodLocations, getContext(), this, chosenTheme, 1);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 2)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfCoffeeLocations, getContext(), this, chosenTheme, 2);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 3)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfMarketLocations, getContext(), this, chosenTheme, 3);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 4)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfBusLocations, getContext(), this, chosenTheme, 4);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 5)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfLibraryLocations, getContext(), this, chosenTheme, 5);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 6)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfBikeLocations, getContext(), this, chosenTheme, 6);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }
        if (flag == 7)
        {
            locationsRecyclerView.setHasFixedSize(true);
            locationsRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity())); //Need this to display recyclerview
            styleRvAdapter = new LocationRecyclerViewAdapter(listOfBuildingLocations, getContext(), this, chosenTheme, 7);
            locationsRecyclerView.setOnFlingListener(null);
            locationsRecyclerView.setAdapter(styleRvAdapter);
            snapHelper.attachToRecyclerView(locationsRecyclerView);
        }


    }


    /////////Regular DRAW ROUTE ON MAP//////////////////////
    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .profile("walking")
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        /*
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        }*/

                        navigationMapRoute = new NavigationMapRoute(mapView, mapboxMap); //HERE

                        navigationMapRoute.addRoute(currentRoute);

                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        repositionMapCamera(destinationCoord);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    private void getFeatureCollectionFromJson() throws IOException {
        try {
            // Use fromJson() method to convert the GeoJSON file into a usable FeatureCollection object
            foodCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_food.geojson"));
            coffeeCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_coffee.geojson"));
            marketCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_markets.geojson"));
            busCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_bus.geojson"));
            libraryCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_libraries.geojson"));
            bikeCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_bike.geojson"));
            buildingCollection = FeatureCollection.fromJson(loadGeoJsonFromAsset("list_of_building.geojson"));
        } catch (Exception exception) {
            Log.e("MainActivity", "getFeatureCollectionFromJson: " + exception);
        }
    }

    private String loadGeoJsonFromAsset(String filename) {
        try {
            // Load the GeoJSON file from the local asset folder
            InputStream is = getActivity().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (Exception exception) {
            Log.e("MapActivity", "Exception Loading GeoJSON: " + exception.toString());
            exception.printStackTrace();
            return null;
        }
    }



    //////////ENABLE CURRENT LOCATION TRACK/////////////////////////////////
    ///Enable Location layer plugin
    //Initalize location engine
    //add user location to map as location layer

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Create an instance of LOST location engine
            initializeLocationEngine();

            locationPlugin = new LocationLayerPlugin(mapView, mapboxMap, locationEngine);
            locationPlugin.setLocationLayerEnabled(LocationLayerMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    //Initialize Location Engine
    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {
        locationEngine = new LostLocationEngine(getActivity());
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.setInterval(0);
        locationEngine.setFastestInterval(500);
        locationEngine.addLocationEngineListener(this);

        locationEngine.activate();

        lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            onLocationChanged(lastLocation);
            originLocation = lastLocation;
        }

    }

    //Move camera to this position and zoom
    private void setCameraPosition(Location location) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }

    //Request permission from user to get their location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    //If user says yes to allow permission, enable user location tracking, else exit
    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationPlugin();
        } else {
            Toast.makeText(getActivity(), "Please allow location permission.",
                    Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }


    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    //Update user location when they move around
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            //setCameraPosition(location); //follow user with camera as location changes
            locationEngine.removeLocationEngineListener(this);
        }
        if (locationPlugin != null) {
            Log.d(TAG, "forceLocationUpdate");
            locationPlugin.forceLocationUpdate(location);
        }
    }
    //////////END CURRENT LOCATION TRACK/////////////////////////////////


    /////////////////////Add the mapView's own lifecycle methods to the activity's lifecycle methods////////

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }


        /*
        if (locationPlugin != null) {
            locationPlugin.onStart();
        }*/
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }

        mapView.onStop();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mapView.endNavigation();
        mapView.onDestroy();

        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
            locationEngine.deactivate();
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (locationEngine != null) {
            locationEngine.addLocationEngineListener(this);
            if (!locationEngine.isConnected()) {
                locationEngine.activate();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (locationEngine != null) {
            locationEngine.removeLocationEngineListener(this);
        }


    }
    /////////////////////ENB OF the mapView's own lifecycle methods to the activity's lifecycle methods


    /////////////MAP STYLES////////////////////////
    private static class StyleCycle {
        private static final String[] STYLES = new String[] {
                Style.MAPBOX_STREETS,
                Style.OUTDOORS,
                Style.LIGHT,
                Style.DARK,
                Style.SATELLITE_STREETS
        };

        private int index;

        private String getNextStyle() {
            index++;
            if (index == STYLES.length) {
                index = 0;
            }
            return getStyle();
        }

        private String getStyle() {
            return STYLES[index];
        }
    }
    //////////////END MAP STYLES////////////

    //INDOOR MAP FUNCTIONS

    private void hideLevelButton() {
        // When the user moves away from our bounding box region or zooms out far enough the floor level
        // buttons are faded out and hidden.
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(500);
        levelButtons.startAnimation(animation);
        levelButtons.setVisibility(View.GONE);
    }

    private void showLevelButton() {
        // When the user moves inside our bounding box region or zooms in to a high enough zoom level,
        // the floor level buttons are faded out and hidden.
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        levelButtons.startAnimation(animation);
        levelButtons.setVisibility(View.VISIBLE);
    }

    private void loadBuildingLayer() {
        // Method used to load the indoor layer on the map. First the fill layer is drawn and then the
        // line layer is added.

        FillLayer indoorBuildingLayer = new FillLayer("indoor-building-fill", "indoor-building").withProperties(
                fillColor(Color.parseColor("#eeeeee")),
                // Function.zoom is used here to fade out the indoor layer if zoom level is beyond 16. Only
                // necessary to show the indoor map at high zoom levels.
                fillOpacity(Function.zoom(Stops.exponential(
                        Stop.stop(17f, fillOpacity(1f)),
                        Stop.stop(16.5f, fillOpacity(0.5f)),
                        Stop.stop(16f, fillOpacity(0f))
                )))

        );

        mapboxMap.addLayer(indoorBuildingLayer);

        LineLayer indoorBuildingLineLayer = new LineLayer("indoor-building-line", "indoor-building").withProperties(
                lineColor(Color.parseColor("#50667f")),
                lineWidth(0.5f),
                lineOpacity(Function.zoom(Stops.exponential(
                        Stop.stop(17f, lineOpacity(1f)),
                        Stop.stop(16.5f, lineOpacity(0.5f)),
                        Stop.stop(16f, lineOpacity(0f))
                )))

        );
        mapboxMap.addLayer(indoorBuildingLineLayer);
    }



}