package ucr.ucrmap;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

    private VivzAdapter adapter;
    NavigationFragment.SendNavigation sendData;



    public static PlacesFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        PlacesFragment placesFragment = new PlacesFragment();
        placesFragment.setArguments(args);
        return placesFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
            sendData = (NavigationFragment.SendNavigation) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement SendNavigation");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment_places, container, false);


        //Title
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.include9);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false); //disables default actionbar title UCRmap
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("UCR Places");

        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData.openDrawer();
            }
        });

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.rv_place_recycler);
        adapter = new VivzAdapter(getActivity(),getData());
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        rv.addItemDecoration(itemDecorator);

        //rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.HORIZONTAL));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    public static List<recycler_information> getData()
    {
        List<recycler_information> placeData = new ArrayList<>();
        String[] placeName = {"Food", "Coffee Shops", "Markets", "Bus Stops", "Libraries", "Bike Racks"};
        int[] placeIcon = {R.drawable.food, R.drawable.coffee, R.drawable.market, R.drawable.bus,
                R.drawable.library,  R.drawable.bike};
        for (int i = 0; i < placeName.length; i++) //add icon as well
        {
            recycler_information current = new recycler_information();
            current.placeNameData=placeName[i];
            current.placeIconId=placeIcon[i];
            current.layoutType = 4;
            placeData.add(current);
        }
        return placeData;
    }

}
