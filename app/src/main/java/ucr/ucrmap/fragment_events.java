package ucr.ucrmap;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import com.mapbox.mapboxsdk.style.layers.Property;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_events extends Fragment implements View.OnClickListener {

    RecyclerView rv;
    List<recycler_information> eventData;

    EditText
            eventTitle,
            eventLocation,
            eventDetails,
            eventtDurationHours,
            eventtDurationMinutes;
    TextView
            eventTime,
            eventDate;


    public static fragment_events newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_events eventsFragment = new fragment_events();
        eventsFragment.setArguments(args);
        return eventsFragment;
    }

    private VivzAdapter adapter;


    public fragment_events() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);


        ImageButton ibNewEvent = (ImageButton) v.findViewById(R.id.ibNewEvent);
        ibNewEvent.setOnClickListener(this);

        ImageButton ibCalender = (ImageButton) v.findViewById(R.id.imageButton2);
        ibCalender.setOnClickListener(this);

        eventData = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.rv_event_recycler);
        adapter = new VivzAdapter(getActivity(),eventData);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        setRecyclerViewData();
        retreive();

        return v;
    }

    private void setRecyclerViewData() //Initialzing friends on start
    {
        //String eventTitleData, String eventDateData, String eventTimeData, int layoutType
        eventData.add(new recycler_information("Beach Party", "September 30", "5:00PM-6:00PM", 3));
        eventData.add(new recycler_information("Study Session", "September 31", "7:00PM-8:00PM", 3));
        eventData.add(new recycler_information("Finals Day", "October 5", "12:00PM-1:00PM", 3));
        eventData.add(new recycler_information("Halloween", "October 31", "3:00PM-4:00PM", 3));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.ibNewEvent:
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                View mView = getLayoutInflater().inflate(R.layout.fragment_add_event, null);
                builder.setView(mView);



                View createButton = mView.findViewById(R.id.eventCreateButton);
                View cancelButton = mView.findViewById(R.id.eventCancelButton);

                View eventTimeButton = mView.findViewById(R.id.eventTimeButton);
                View eventDayButton = mView.findViewById(R.id.eventDayButton);


                eventTime = (TextView) mView.findViewById(R.id.eventTimeConfirm);
                eventDate = (TextView) mView.findViewById(R.id.eventDayConfirm);

                eventTitle = (EditText) mView.findViewById(R.id.eventTitle);
                eventLocation = (EditText) mView.findViewById(R.id.eventLocation);
                eventDetails = (EditText) mView.findViewById(R.id.eventDetails);
                eventtDurationHours = (EditText) mView.findViewById(R.id.eventDurationHours);
                eventtDurationMinutes = (EditText) mView.findViewById(R.id.eventDurationMinutes);

                final AlertDialog show = builder.show();


                createButton.setOnClickListener(onConfirmListener(show)); //Confirm add friend
                cancelButton.setOnClickListener(onCancelListener(show));
                eventTimeButton.setOnClickListener(onTimeListener());
                eventDayButton.setOnClickListener(onDayListener());


                builder.create();



            case R.id.imageButton2:
                Toast.makeText(getActivity(), "Clicked calender", Toast.LENGTH_LONG).show();
                break;
            //case R.id.btnCommentSend:


            default:
                break;

        }
    }
    private View.OnClickListener onTimeListener() { //Event time
        return new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                View timeViewPicker = LayoutInflater.from(getActivity()).inflate(R.layout.fragment__day_time_picker,null);

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                builder2.create();
                builder2.setView(timeViewPicker);
                builder2.setTitle(R.string.time_picker_title);

                final AlertDialog show = builder2.show();


                View fromButton = timeViewPicker.findViewById(R.id.eventFromTimeButton);
                View ToButton = timeViewPicker.findViewById(R.id.eventToTimeButton);
                View doneButton = timeViewPicker.findViewById(R.id.btnDone);

                fromButton.setOnClickListener(onFromToListener(1));
                ToButton.setOnClickListener(onFromToListener(2));
                doneButton.setOnClickListener(onCancelListener(show));

                //builder2.show();
            }
        };
    }

    private View.OnClickListener onFromToListener(final int flag) { //ON FROM TO
        return new View.OnClickListener() {
            TimePicker timePicker;

            @Override
            public void onClick(View v) {
                View timeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time,null);
                timePicker = (TimePicker) timeView.findViewById(R.id.dialog_time_picker);


                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                builder2.create();
                builder2.setView(timeView);
                builder2.setTitle(R.string.time_picker_title);
                builder2.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    hour = timePicker.getHour();
                                }
                                else
                                {
                                    hour = timePicker.getCurrentHour();
                                }

                                int minute = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    minute = timePicker.getMinute();
                                }
                                else
                                {
                                    minute = timePicker.getCurrentMinute();
                                }
                                ////////////////////////////


                                String name1 = updateTime(hour, minute); //gets the result of what we clicked
                                String combinedTime = new StringBuilder().append(name1).append("-").append(name1).toString(); //fix this later to add both times
                                eventTime.setText(combinedTime);


                            }
                        });
                builder2.show();
            }

        };
    }


    private View.OnClickListener onDayListener() {
        return new View.OnClickListener() {

            DatePicker datePicker;

            @Override
            public void onClick(View v)
            {

                View timeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_day,null);
                datePicker = (DatePicker) timeView.findViewById(R.id.dialog_day_picker);


                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());//Creates a new instance of timepickdia and return it
                builder2.create();
                builder2.setView(timeView);
                builder2.setTitle(R.string.day_picker_title);
                builder2.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    year = datePicker.getYear();
                                }
                                else
                                {
                                    year = datePicker.getYear();
                                }

                                int month = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    month = datePicker.getMonth();
                                }
                                else
                                {
                                    month = datePicker.getMonth();
                                }
                                int day = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                    day = datePicker.getDayOfMonth();
                                }
                                else
                                {
                                    day = datePicker.getDayOfMonth();
                                }
                                ////////////////////////////

                                String name2 = updateDate(month+1, day, year); //gets the result of what we clicked
                                eventDate.setText(name2);
                            }
                        });
                builder2.show();
            }
        };
    }



    private View.OnClickListener onConfirmListener(final AlertDialog show) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String eventTitleData, String eventDateData, String eventTimeData, int layoutType
                //eventData.add(new recycler_information(eventTitle.getText().toString(), eventDate.getText().toString(), eventTime.getText().toString(), 3));

                save(eventTitle, eventDate,eventTime);


                //***In future for different events, we must pass these values to constructor so each activity has their own
                Toast.makeText(getActivity(), "Event Location " + eventTitle.getText().toString() + "\nEvent Details "
                        + eventDetails.getText().toString()
                        + "\nEvent Duration: "
                        + eventtDurationHours.getText().toString() +" hour " + eventtDurationMinutes.getText().toString() + " minutes", Toast.LENGTH_LONG).show();

                //notify data set changed in RecyclerView adapter
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
    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        //myTime = " " - intitally empty 1st pass
        //myTime = "12:400 PM " on second pass


        String myTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        //combinedTIme = new StringBuilder().append(myTime).toString();

        return myTime; //myTime = "12:40 PM" on exit
                        //myTime = "12:40 PM - 1:00PM" on 2nd exit
    }
    private String updateDate(int month, int day, int year) {


        String myDate = new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year).toString();

        return myDate;
    }

    private void save(final EditText eventTitleData, final TextView eventDateData, final TextView eventTimeData)
    {
        /*
        //https://stackoverflow.com/questions/20464273/get-the-application-context-in-fragment-in-android
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        String event = eventTitleData.getText().toString();
        String event_date = eventDateData.getText().toString();
        String event_time = eventTimeData.getText().toString();
        long result = mDatabasehelper.addData_Event(event,event_date,event_time);
        if(result > 0)
        {
            eventTimeData.setText("");
            eventDateData.setText("");
            eventTitleData.setText("");
        }
        // CLOSE DB to not leave it open
        retreive();
        */
    }
    private void retreive()
    {
        /*
        eventData.clear();
        DatabaseHelper mDatabasehelper = new DatabaseHelper(getActivity().getApplicationContext()); // this part understand
        Cursor c = mDatabasehelper.getAllEvents();
        while(c.moveToNext())
        {
            String title = c.getString(0);
            String date = c.getString(1);
            String time = c.getString(2);

            recycler_information r = new recycler_information(title,date,time, 3);
            eventData.add(r);
        }
        if(!(eventData.size()<1))
        {

            rv.setAdapter(adapter);
        }
        */
    }
}
