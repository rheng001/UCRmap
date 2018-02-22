package ucr.ucrmap;


import android.content.Context;
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




public class fragment_thursday extends Fragment{

    private VivzAdapter adapter;
    List<recycler_information> classData;

    public fragment_thursday() {
        // Required empty public constructor
    }

    ReceiveThurClass receivethurClass;


    public interface ReceiveThurClass {
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
            receivethurClass = (ReceiveThurClass) context;
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
        View v = inflater.inflate(R.layout.fragment_thursday, container, false);
        classData = new ArrayList<>();

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.rv_recycler_view4);
        adapter = new VivzAdapter(getActivity(), classData);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));



        classData.add(new recycler_information("CS 161", "CHUNG","138", "5:00PM", "6:00PM", 1));
        classData.add(new recycler_information("CS 101", "WAT", "1000","5:00PM", "6:00PM", 1));



        if (receivethurClass.getDay() == null)
        {
            //classData.add(new recycler_information("", "", "", "", 0));
        }
        else if (receivethurClass.getDay().toString() == "thur")
        {

            add();
            //classData.add(new recycler_information(receivetuesClass.getClassName(), receivetuesClass.getBuildingName(), receivetuesClass.getStartTime(), receivetuesClass.getEndTime(), receivetuesClass.getIntLayout()));
            //adapter.notifyDataSetChanged();


        }



        return v;
    }

    public void add()
    {
        classData.add(new recycler_information(receivethurClass.getClassName(), receivethurClass.getBuildingName(), receivethurClass.getRoomName(), receivethurClass.getStartTime(), receivethurClass.getEndTime(), receivethurClass.getIntLayout()));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
