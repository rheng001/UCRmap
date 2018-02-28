package ucr.ucrmap;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;




public class fragment_tuesday extends Fragment{

    private VivzAdapter adapter;
    List<recycler_information> classData;
    RecyclerView rv;

    public fragment_tuesday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tuesday, container, false);
        classData = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rv_recycler_view2);
        adapter = new VivzAdapter(getActivity(), classData);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        retreive();

        return v;
    }


    public void retreive()
    {
        classData.clear();
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        Cursor c = mDatabasehelper.getAllClasses("tues");


            while (c.moveToNext()) {

                String class_name = c.getString(1);
                String building_name = c.getString(2);
                String room_name = c.getString(3);
                String start_time = c.getString(4);
                String end_time = c.getString(5);
                recycler_information r = new recycler_information(class_name, building_name, room_name, start_time, end_time, 1); // added the 1 to make it retreive the classes when you open the page
                classData.add(r);
            }
            if (!(classData.size() < 1)) {
                rv.setAdapter(adapter);
            }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
