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




public class fragment_wednesday extends Fragment{

    private VivzAdapter adapter;
    List<recycler_information> classData;
    RecyclerView rv;

    public fragment_wednesday() {
        // Required empty public constructor
    }

    ReceiveWedClass receivewedClass;


    public interface ReceiveWedClass {
        String getClassName();
        String getBuildingName();
        String getRoomName();
        String getStartTime();
        String getEndTime();
        int getIntLayout();
        String getDay();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
            receivewedClass = (ReceiveWedClass) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement ReceiveData");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wednesday, container, false);
        classData = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rv_recycler_view3);
        adapter = new VivzAdapter(getActivity(), classData);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        retreive();


        if (receivewedClass.getDay() == null)
        {
            //classData.add(new recycler_information("", "", "", "", 0));
        }
        else if (receivewedClass.getDay().toString() == "wed")
        {

            add();
            //classData.add(new recycler_information(receivetuesClass.getClassName(), receivetuesClass.getBuildingName(), receivetuesClass.getStartTime(), receivetuesClass.getEndTime(), receivetuesClass.getIntLayout()));
            //adapter.notifyDataSetChanged();


        }



        return v;
    }

    public void add()
    {
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        String class_name = receivewedClass.getClassName();
        String building_name = receivewedClass.getBuildingName();
        String room_name = receivewedClass.getRoomName();
        String start_time = receivewedClass.getStartTime();
        String end_time = receivewedClass.getEndTime();
        long result = mDatabasehelper.addData_Class(class_name,building_name,room_name,start_time,end_time,receivewedClass.getDay());
        // close database
        retreive();
        adapter.notifyDataSetChanged();
    }
    public void retreive()
    {
        classData.clear();
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        Cursor c = mDatabasehelper.getAllClasses("wed");
        if (c == null)
        {
            return;
        }
        else {
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
