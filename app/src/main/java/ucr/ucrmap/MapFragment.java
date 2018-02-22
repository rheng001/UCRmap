package ucr.ucrmap;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.GraphHopperRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment  {

    private TrackGPS gps;
    private TextToSpeech tts;

    double longitude;
    double latitude;

    ReceiveData receiveData;

    public static MapFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);
        return mapFragment;
    }

    public interface ReceiveData {
        double getLatitude();
        double getLongitude();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
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

        View v = inflater.inflate(R.layout.fragment_map, container, false);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        MapView map = (MapView) v.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setUseDataConnection(true);
        map.setMultiTouchControls(true);
        map.setMinZoomLevel(11);

        IMapController mapController = map.getController();
        mapController.setZoom(22);

        //Gets current location

        gps = new TrackGPS(getActivity());

        if(gps.canGetLocation()){

            longitude = gps.getLongitude();
            latitude = gps .getLatitude();
            //Toast.makeText(getActivity(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {

            gps.showSettingsAlert();
        }


        GeoPoint currentLocation = new GeoPoint(latitude, longitude); //Get current location
        //GeoPoint currentLocation = new GeoPoint(33.882455, -117.436041 ); //hardcoded my house


        double gotLatitude = receiveData.getLatitude();
        double gotLongitude = receiveData.getLongitude();

        //Toast.makeText(getActivity(), "Received Latitude " + String.valueOf(gotLatitude), Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "Received Longitude " + String.valueOf(gotLongitude), Toast.LENGTH_LONG).show();


        GeoPoint endPoint = new GeoPoint(gotLatitude, gotLongitude);
        GeoPoint startPoint = new GeoPoint(33.974942, -117.327270); //UCR MAP

        //Instantiate main map

        mapController.setCenter(startPoint);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(currentLocation);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_start_point)); //specify marker icon
        startMarker.setTitle("Start");
        map.invalidate();

        Marker endMarker = new Marker(map);
        endMarker.setPosition(endPoint);
        endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(endMarker);
        endMarker.setIcon(getResources().getDrawable(R.drawable.ic_end_point)); //specify marker icon
        endMarker.setTitle("End");
        map.invalidate();


        RoadManager roadManager = new GraphHopperRoadManager("4875b127-11b4-4669-9e66-730e41eb9856", false);
        roadManager.addRequestOption("vehicle=foot");
        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        waypoints.add(currentLocation);
        waypoints.add(endPoint);
        Road road = roadManager.getRoad(waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        roadOverlay.setColor(Color.GREEN);
        map.getOverlays().add(roadOverlay);
        map.invalidate();


        //This prints out the steps of directions. Need to add voice

        //mLength = distance
        //mDuration = time
        //mInstructions = text instruction


        //Drawable nodeIcon = (getResources().getDrawable(R.drawable.ic_about);
        for (int i=0; i<road.mNodes.size(); i++){
            RoadNode node = road.mNodes.get(i);
            Marker nodeMarker = new Marker(map);
            nodeMarker.setSnippet(node.mInstructions);
            nodeMarker.setSubDescription(Road.getLengthDurationText(getActivity(), node.mLength, node.mDuration));
            nodeMarker.setPosition(node.mLocation);
            nodeMarker.setIcon(getResources().getDrawable(R.drawable.ic_waypoint)); //Turn marker icon
            nodeMarker.setTitle("Step "+i);
            map.getOverlays().add(nodeMarker);


        }

        return v;
    }
}
