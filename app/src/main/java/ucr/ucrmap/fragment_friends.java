package ucr.ucrmap;

import android.util.Log;
import android.database.Cursor;
import android.content.Context;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_friends extends Fragment {

    private String TAG = "fragment_friends";
    DatabaseHelper mDatabasehelper;
    private FloatingActionButton fab;
    private String gender;
    List<recycler_information> friendData;
    RecyclerView rv;
    private Context mcontext;


    public static fragment_friends newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_friends friendsFragment = new fragment_friends();
        friendsFragment.setArguments(args);
        return friendsFragment;
    }

    private VivzAdapter adapter;

    public fragment_friends() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "here");
        mDatabasehelper = new DatabaseHelper(getActivity());


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.addFriendFab);
        fab.setOnClickListener(onAddingListener());

        friendData = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.friend_recycler);
        adapter = new VivzAdapter(getActivity(),friendData); /////here
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        retreive();
        return v;
    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_friend, null);

                builder.setView(mView); //Set view of alert dialog

                TextView tv = (TextView) mView.findViewById(R.id.addTitle);
                tv.setText("Add a friend");


                // set the custom dialog components - texts and image
                final EditText name = (EditText) mView.findViewById(R.id.name);
                EditText ID = (EditText) mView.findViewById(R.id.ID);
                Spinner spnGender = (Spinner) mView.findViewById(R.id.gender);
                View btnAdd = mView.findViewById(R.id.btn_ok);
                View btnCancel = mView.findViewById(R.id.btn_cancel);



                final AlertDialog show = builder.show();

                //set spinner adapter
                ArrayList<String> gendersList = new ArrayList<>();
                gendersList.add("Male");
                gendersList.add("Female");
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, gendersList);
                spnGender.setAdapter(spnAdapter);

                //set handling event for 2 buttons and spinner
                spnGender.setOnItemSelectedListener(onItemSelectedListener());
                btnAdd.setOnClickListener(onConfirmListener(name, ID, show)); //Confirm add friend
                btnCancel.setOnClickListener(onCancelListener(show));

                builder.create();

            }
        };
    }
    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                if (position == 0) {
                    gender = "male";
                } else {
                    gender = "female";
                }
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        };
    }

    ////OKAY BUTTON
    private View.OnClickListener onConfirmListener(final EditText name, final EditText ID, final AlertDialog show) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gender == "male")
                {
                    save(name,ID);
                }
                else if (gender == "female")
                {
                    save(name,ID);
                }
                Toast.makeText(getActivity(), "Added friend", Toast.LENGTH_LONG).show();

                //notify data set changed in RecyclerView  adapter
                adapter.notifyDataSetChanged();
                show.dismiss();

            }
        };
    }
    //////CANCEL BUTTON
    private View.OnClickListener onCancelListener(final AlertDialog show) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();

            }
        };
    }
    private void save(final EditText name, final EditText ID)
    {
        //https://stackoverflow.com/questions/20464273/get-the-application-context-in-fragment-in-android
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        String N = name.getText().toString();
        int x = Integer.parseInt(ID.getText().toString());
        long result = mDatabasehelper.addData_Friend(N,x);
        if(result > 0)
        {
            name.setText("");
            ID.setText("");
        }
        // CLOSE DB to not leave it open
        retreive();

    }
    private void retreive()
    {
        friendData.clear();
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        Cursor c = mDatabasehelper.getAllFriends();
        while(c.moveToNext())
        {
            String name_2 = c.getString(1);
            int ID_num = Integer.parseInt(c.getString(2));
            recycler_information r = new recycler_information(name_2,ID_num,"male",R.drawable.ic_bear2, 2);
            friendData.add(r); //Adding into database
        }
        if(!(friendData.size()<1))
        {

            rv.setAdapter(adapter);
        }
    }

}
