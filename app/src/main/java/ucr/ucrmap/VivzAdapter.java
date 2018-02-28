package ucr.ucrmap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;


import com.mikepenz.materialize.color.Material;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

/**
 * Created by Richard on 9/3/2017.
 */

public class VivzAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static final String TAG = "VivzAdapter";
    private static final int TYPE_CLASS = 1;
    private static final int TYPE_FRIEND = 2;
    private static final int TYPE_EVENT = 3;
    private static final int TYPE_PLACES = 4;

    private LayoutInflater inflater;
    private final Context mcontext;
    private OnRecyclerItemClickListener recycleMe;

    List<recycler_information> recycleData = Collections.emptyList();


    public interface OnRecyclerItemClickListener {

        void onRecyclerItemClick(String data);
    }


    ////////////done initalizing

    public VivzAdapter(Context context, List<recycler_information> data) {
        DatabaseHelper mDatabasehelper = new DatabaseHelper(context); // this part understand
        inflater = LayoutInflater.from(context);
        this.recycleData = data;
        mcontext = context;
    }

    @Override //
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        if (viewType == TYPE_CLASS) //1
        {
            view = inflater.inflate(R.layout.custom_class_row, parent, false);
            holder = new MyClassViewHolder(view);
        }
        else if (viewType == TYPE_FRIEND) //2
        {
            view = inflater.inflate(R.layout.custom_friend_row, parent, false);
            holder = new MyFriendViewHolder(view);
        }
        else if (viewType == TYPE_EVENT) //3
        {
            view = inflater.inflate(R.layout.custom_event_row, parent, false);
            holder = new MyEventViewHolder(view);
        }
        else if (viewType == TYPE_PLACES) //4
        {
            view = inflater.inflate(R.layout.custom_places_row, parent, false);
            holder = new MyPlaceViewHolder(view);
        }
        return holder;
    }

    @Override //
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyClassViewHolder)
        {
            recycler_information current = recycleData.get(position);
            ((MyClassViewHolder) holder).className.setText(current.classNameData);
            ((MyClassViewHolder) holder).buildingDescription.setText(current.buildingDescriptionData);
            ((MyClassViewHolder) holder).roomDescription.setText(current.roomDescriptionData);
            ((MyClassViewHolder) holder).startClassTime.setText(current.startTimeData);
            ((MyClassViewHolder) holder).endClassTime.setText(current.endTimeData);
        }
        else if (holder instanceof MyFriendViewHolder)
        {
            recycler_information current = recycleData.get(position);
            ((MyFriendViewHolder) holder).friendName.setText(current.friendNameData);
            ((MyFriendViewHolder) holder).friendID.setText(String.valueOf(current.friendIDData)); // this part was confusing with integer textview
            ((MyFriendViewHolder) holder).friendIcon.setImageResource(current.friendIconId);
        }

        else if (holder instanceof MyEventViewHolder)
        {
            recycler_information current = recycleData.get(position);
            ((MyEventViewHolder) holder).eventTitle.setText(current.eventTitleData);
            ((MyEventViewHolder) holder).eventDate.setText(current.eventDateData);
            ((MyEventViewHolder) holder).eventTime.setText(current.eventTimeData);
        }
        else if (holder instanceof MyPlaceViewHolder)
        {
            recycler_information current = recycleData.get(position);
            if (position == 0)
            {
                ((MyPlaceViewHolder) holder).view.setBackgroundColor((Color.parseColor("#F7CD66")));
            }
            ((MyPlaceViewHolder) holder).placeName.setText(current.placeNameData);
            ((MyPlaceViewHolder) holder).placeIcon.setImageResource(current.placeIconId);
        }
    }

    @Override
    public int getItemViewType(int position) {


        recycler_information item1 = recycleData.get(position);

        if (item1.layoutType == 1)
        {
            return TYPE_CLASS;
        }

        else if (item1.layoutType == 2)
        {
            return TYPE_FRIEND;
        }
        else if (item1.layoutType == 3)
        {
            return TYPE_EVENT;
        }
        else if (item1.layoutType == 4)
        {
            return TYPE_PLACES;
        }
        return TYPE_FRIEND;

    }

    @Override
    public int getItemCount() {
        //return friendData.size(); //turn on for friend // off for class
        //return classData.size(); //turn on for class // off for friend

        return recycleData.size();

    }


    public void delete(int position) //use this for delete button to delete a class
    {
        recycleData.remove(position);
        notifyItemRemoved(position);
        //call like this delete(getPosition()) on CLICK/on delete button change;
        // in the button command, choose which class to delete
        //need to save adapter listview to save those changes sqllitedatabase, can customize animation of delete
    }

    /*  http://www.devexchanges.info/2015/09/android-material-design-features.html
    private void setDataToView(TextView name, ImageView genderIcon, int position)
    {
        name.setText(recycleData.get(position).friendNameData);
        genderIcon.setImageResource(R.drawable.ic_bear2);
    }*/

    //4 Class instances
    class MyClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView className;
        TextView buildingDescription;
        TextView roomDescription;
        TextView startClassTime;
        TextView endClassTime;

        public MyClassViewHolder(View itemView)
        {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.classNameView);
            buildingDescription = (TextView) itemView.findViewById(R.id.buildingDescriptionView);
            roomDescription = (TextView) itemView.findViewById(R.id.roomDescriptionView);
            startClassTime = (TextView) itemView.findViewById(R.id.startTimeView);
            endClassTime = (TextView) itemView.findViewById((R.id.endTimeView));
            itemView.setOnClickListener(this); //select whole element
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(final View v)
        {
            int position = getLayoutPosition();
            //recycleMe.onRecyclerItemClick("what"); //Prototype come to this later, passing alertdialog to main activitiy

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Navigate to Class");
            builder.setMessage("Navigate to " + className.getText().toString() + "?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            if (buildingDescription.getText().toString().equals("CHUNG") && roomDescription.getText().toString().equals("138"))
                            {
                                toastMessage("Selected Chung 138");

                            }
                            if (buildingDescription.getText() != "CHUNG" && roomDescription.getText() != "138")
                            {
                                toastMessage("Will be available once database is fully populated");

                            }

                            //Based on User class and room choosen, search database and send the coordinates to map
                            Intent intent = new Intent("class-message");
                            intent.putExtra("CLASS",buildingDescription.getText());
                            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            //setDataToView(friendName, friendIcon, position); want to show friend icon + name idividually on click
            builder.show();
            //Toast.makeText(v.getContext(), "Friend clicked at " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) { //Delete class
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete class");
            builder.setMessage("Are you sure you want to delete " + className.getText().toString() + " from your class schedule?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            toastMessage("Deleted : " + className.getText().toString());
                            DatabaseHelper mDatabasehelper = new DatabaseHelper(mcontext); // this part understand
                            mDatabasehelper.DeleteData_Class(className.getText().toString());
                            recycleData.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),recycleData.size());

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            builder.show();
            return true;
        }



    }
    //end class

    class MyFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView friendName;
        TextView friendID;
        ImageView friendIcon;
        CardView friendView;


        public MyFriendViewHolder(View itemView)
        {
            super(itemView);
            friendIcon = (ImageView) itemView.findViewById(R.id.friendIcon);
            friendName = (TextView) itemView.findViewById(R.id.friendName);
            friendID = (TextView) itemView.findViewById(R.id.friendID);
            friendView = (CardView) itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this); //need to include this to longclick

        }

        @Override
        public void onClick(View v)
        {
            int position = getLayoutPosition();
            //recycleMe.onRecyclerItemClick("what"); //Prototype come to this later, passing alertdialog to main activitiy

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Locate friend");
            builder.setMessage("Do you want to locate " + friendName.getText() + " on the map?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            //setDataToView(friendName, friendIcon, position); want to show friend icon + name idividually on click
            builder.show();
            //Toast.makeText(v.getContext(), "Friend clicked at " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {//Delete friend long click
            final DatabaseHelper db = new DatabaseHelper(v.getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete friend");
            builder.setMessage("Are you sure you want to delete " + friendName.getText().toString() + " from your friend's list?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            toastMessage("Deleted : " + friendID.getText().toString());
                            db.DeleteData_Friends(Integer.parseInt(friendID.getText().toString()));

                            recycleData.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),recycleData.size());

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            builder.show();
            return true;
        }
    }
    //end friend

    class MyEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView eventTitle;
        TextView eventDate;
        TextView eventTime;


        public MyEventViewHolder(View itemView)
        {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.getEventTitle);
            eventDate = (TextView) itemView.findViewById(R.id.getEventDate);
            eventTime = (TextView) itemView.findViewById(R.id.getEventTime);
            itemView.setOnClickListener(this); //select whole element
            itemView.setOnLongClickListener(this); //need to include this to longclick

        }

        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(),R.style.MyDialogTheme);
            builder.setView(R.layout.fragment_add_event_details); // shouldn't be .

            //final AlertDialog show = builder.show();


            builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.dismiss();


                }
            });

            //final AlertDialog show = builder.show();

            /*
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                         @Override
                                         public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                             if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                                             {
                                                 dialog.dismiss();
                                                 Log.i("ONBACKPRESSED", "SUCCESSFULLY BACKPRESSED");
                                                 //show.dismiss();
                                                 return true;
                                             }
                                             return false;
                                         }
                                     });*/
            builder.create();
            builder.show(); //crashing when back pressed and going back


        }


        @Override
        public boolean onLongClick(View v) {
            final DatabaseHelper db = new DatabaseHelper(v.getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete event");
            builder.setMessage("Are you sure you want to delete " + eventTitle.getText().toString() + " event?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            db.DeleteData_Events(eventTitle.getText().toString());
                            recycleData.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),recycleData.size());

                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            builder.show();
            return true;
        }

    }
    //end event

    class MyPlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView placeName;
        ImageView placeIcon;
        CardView placeView;
        View view;



        public MyPlaceViewHolder(View itemView)
        {
            super(itemView);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeIcon = (ImageView) itemView.findViewById(R.id.placesIcon);
            placeView = (CardView) itemView.findViewById(R.id.card_view2);
            view = itemView;

            itemView.setOnClickListener(this); //select whole element
        }

        @Override
        public void onClick(final View v)
        {
            int position = getLayoutPosition();
            //recycleMe.onRecyclerItemClick("what"); //Prototype come to this later, passing alertdialog to main activitiy
            final int setPlaceName = getAdapterPosition();
            String placeName = "";

            if (setPlaceName == 0)
            {
                placeName = "Food Places";
            }
            else if (setPlaceName == 1)
            {
                placeName = "Coffee Shops";
            }
            else if (setPlaceName == 2)
            {
                placeName = "Markets";
            }
            else if (setPlaceName == 3)
            {
                placeName = "Bus Stops";
            }
            else if (setPlaceName == 4)
            {
                placeName = "Libraries";
            }
            else if (setPlaceName == 5)
            {
                placeName = "Bike Racks";
            }
            else if (setPlaceName == 6)
            {
                placeName = "Buildings";
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Place Navigation");
            builder.setMessage("Show all " + placeName + " on the map?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            Intent intent = new Intent("custom-message");
                            intent.putExtra("POI",setPlaceName);
                            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);



                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create();
            //setDataToView(friendName, friendIcon, position); want to show friend icon + name idividually on click
            builder.show();
            //Toast.makeText(v.getContext(), "Friend clicked at " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }
    //end places
    private void toastMessage(String message){
        Toast.makeText(mcontext,message,Toast.LENGTH_SHORT).show();
   }
}
