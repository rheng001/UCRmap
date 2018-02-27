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




public class fragment_monday extends Fragment{

    private VivzAdapter adapter;
    List<recycler_information> classData;
    DatabaseHelper mDatabasehelper;
    RecyclerView rv;

    public fragment_monday() {
        // Required empty public constructor
    }

    ReceiveMonClass receivemonClass;


    public interface ReceiveMonClass {
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
            receivemonClass = (ReceiveMonClass) context;

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
        View v = inflater.inflate(R.layout.fragment_monday, container, false);
        classData = new ArrayList<>();
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand


        rv = (RecyclerView) v.findViewById(R.id.rv_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));



        adapter = new VivzAdapter(getActivity(), classData);

        rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        rv.setAdapter(adapter);
        retreive();
       // classData.add(new recycler_information("CS 190", "UV-theatre","101", "5:00PM", "6:00PM", 1));

        if (receivemonClass.getDay() == null)
        {
            //classData.add(new recycler_information("", "", "", "", 0));
        }
        else if (receivemonClass.getDay().toString() == "mon")
        {

            add();


        }




        return v;
    }
    public void add()
    {
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        // classData.add(new recycler_information(receivemonClass.getClassName(), receivemonClass.getBuildingName(), receivemonClass.getRoomName(), receivemonClass.getStartTime(), receivemonClass.getEndTime(), receivemonClass.getIntLayout()));
        String class_name = receivemonClass.getClassName();
        String building_name = receivemonClass.getBuildingName();
        String room_name = receivemonClass.getRoomName();
        String start_time = receivemonClass.getStartTime();
        String end_time = receivemonClass.getEndTime();
        System.out.println("in here");
        //sleep()
        long result = mDatabasehelper.addData_Class(receivemonClass.getClassName(), receivemonClass.getBuildingName(), receivemonClass.getRoomName(), receivemonClass.getStartTime(), receivemonClass.getEndTime());
        //long result = mDatabasehelper.addData_Friend("ggg",3);
        //long result = mDatabasehelper.addData_Class(class_name,building_name,room_name,start_time,end_time);
        // close database
        System.out.println("worked");
        retreive();
        adapter.notifyDataSetChanged();

    }
    public void retreive()
    {
        classData.clear();
        System.out.println("in here222222");

        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        Cursor c = mDatabasehelper.getAllClasses();
        while(c.moveToNext())
        {
            System.out.println("in here466");

            String class_name = c.getString(1);
            String building_name = c.getString(2);
            String room_name = c.getString(3);
            String start_time = c.getString(4);
            String end_time = c.getString(5);
            System.out.println(class_name);

            recycler_information r = new recycler_information(class_name,building_name,room_name,start_time,end_time, 1); // added the 1 to make it retreive the classes when you open the page
            classData.add(r);
        }
        if(!(classData.size()<1))
        {
            //System.out.println("in here4444");
           // System.out.println(classData);
           // adapter = new VivzAdapter(getActivity(), classData);
            rv.setAdapter(adapter);
        }
       // System.out.println(classData);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
