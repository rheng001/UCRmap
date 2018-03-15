package ucr.ucrmap;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaychang.srv.OnLoadMoreListener;
import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleRecyclerView;
import com.jaychang.srv.decoration.SectionHeaderProvider;
import com.jaychang.srv.decoration.SimpleSectionHeaderProvider;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;


public class fragment_ucrevent extends Fragment {

    SimpleRecyclerView simpleRecyclerView;
    ReceiveData receiveData;
    NavigationFragment.SendNavigation sendData;
    ShimmerLayout shimmerText;
    View mView;


    public fragment_ucrevent() {
        // Required empty public constructor
    }

    public static fragment_ucrevent newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_ucrevent ucreventFragment = new fragment_ucrevent();
        ucreventFragment.setArguments(args);
        return ucreventFragment;
    }

    public interface ReceiveData {
        ArrayList<Pair<String, String>> getDays();

        ArrayList<Pair<String, String>> getEventTitle();

        ArrayList<Pair<String, String>> getBuilding();

        ArrayList<Pair<String, String>> getTime();

        ArrayList<Pair<String, String>> getLink();

        ArrayList<Pair<String, String>> getImage();


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

        View v = inflater.inflate(R.layout.fragment_ucrevent, container, false);

        simpleRecyclerView = v.findViewById(R.id.recyclerView);
        //shimmerText = (ShimmerLayout) v.findViewById(R.id.shimmer_text);
        //shimmerText.startShimmerAnimation();


        //bindData();
        addRecyclerHeaders();
        bindData();

        return v;
    }

    private void setViewLayout(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(mView);
    }

    /*
    - Create RecyclerViewHeaders
     */
    private void addRecyclerHeaders() {
        SectionHeaderProvider<Galaxy> sh = new SimpleSectionHeaderProvider<Galaxy>() {
            @NonNull
            @Override
            public View getSectionHeaderView(@NonNull Galaxy Galaxy, int i) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.header, null, false);
                TextView textView = view.findViewById(R.id.headerTxt);
                textView.setText(Galaxy.getCategoryName());
                return view;
            }

            @Override
            public boolean isSameSection(@NonNull Galaxy Galaxy, @NonNull Galaxy nextGalaxy) {
                return Galaxy.getCategoryId() == nextGalaxy.getCategoryId();
            }

            // Optional, whether the header is sticky, default false
            @Override
            public boolean isSticky() {
                return false;
            }
        };
        simpleRecyclerView.setSectionHeader(sh);
    }

    /*
    - Bind data to our RecyclerView
     */
    private void bindData() {
        List<Galaxy> Galaxys = getData();
        //CUSTOM SORT ACCORDING TO CATEGORIES
        Collections.sort(Galaxys, new Comparator<Galaxy>() {
            public int compare(Galaxy Galaxy, Galaxy nextGalaxy) {
                return Galaxy.getCategoryId() - nextGalaxy.getCategoryId();
            }
        });

        List<GalaxyCell> cells = new ArrayList<>();

        //LOOP THROUGH GALAXIES INSTANTIATING THEIR CELLS AND ADDING TO CELLS COLLECTION
        for (Galaxy galaxy : Galaxys) {
            GalaxyCell cell = new GalaxyCell(galaxy, Galaxys );
            // There are two default cell listeners: OnCellClickListener<CELL, VH, T> and OnCellLongClickListener<CELL, VH, T>
            cell.setOnCellClickListener(new SimpleCell.OnCellClickListener<Galaxy>() {
                @Override
                public void onCellClicked(Galaxy item) {

                    //Toast.makeText(getActivity(), item.getDescription().toString(), Toast.LENGTH_SHORT).show(); //Description = buildng name
                    //Toast.makeText(getActivity(), item.getTime().toString(), Toast.LENGTH_SHORT).show(); //Time = room number

                    if (item.getDescription().toString().equals("Bell Tower")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Navigate to Event");
                        builder.setMessage("Navigate to " + item.getDescription().toString() + "?");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                sendData.setNavigation(33.973413, -117.328156);
                            }
                        });
                        builder.create();
                        builder.show();

                    }


                }
            });
            cell.setOnCellLongClickListener(new SimpleCell.OnCellLongClickListener<Galaxy>() {
                @Override
                public void onCellLongClicked(Galaxy item) {
                    setViewLayout(R.layout.model_empty);
                    //shimmerText.stopShimmerAnimation();

                }
            });


            cells.add(cell);
        }
        simpleRecyclerView.addCells(cells);
    }

    /*
    - Data Source
    - Returns an arraylist of galaxies.
     */
    private ArrayList<Galaxy> getData() {
        ArrayList<Galaxy> galaxies = new ArrayList<>();

       /* for (int i = 0; i < receiveData.getEventTitle().size(); i++)
        {
            //Category cat = new Category(i, days.get(i));
           // Toast.makeText(getActivity(), receiveData.getEventTitle().toString(),Toast.LENGTH_SHORT).show();
        }*/



        int counter = 0;
        for (int i = 0; i <= receiveData.getDays().size() - 1; i++) {


            Category cat = new Category(i, receiveData.getDays().get(i).first);
            Log.i("Testing1", receiveData.getDays().get(i).first);
            //Toast.makeText(getActivity(), receiveData.getEventTitle().get(counter).second.toString(), Toast.LENGTH_SHORT).show();
            Log.i("Testing2", receiveData.getDays().get(i).second);
            Log.i("Testing3", receiveData.getEventTitle().get(counter).second);



            try {
            while (receiveData.getEventTitle().get(counter).second.equals(receiveData.getDays().get(i).second))
            {
                Log.i("Testing2", receiveData.getDays().get(i).second);
                Log.i("Testing3", receiveData.getEventTitle().get(counter).second);
                Galaxy g1 = new Galaxy(receiveData.getEventTitle().get(counter).first.toString(), receiveData.getBuilding().get(counter).first.toString(),
                        receiveData.getTime().get(counter).first.toString(),cat, receiveData.getImage().get(counter).first.toString());

                galaxies.add(g1);
                counter = counter + 1;
            }
            }catch(IndexOutOfBoundsException ex)
            {
                ex.printStackTrace();
            }
        }
            return galaxies;
        }

}
