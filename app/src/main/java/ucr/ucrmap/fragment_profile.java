package ucr.ucrmap;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class fragment_profile extends Fragment {

    private ImageButton myClass;
    private ImageButton myFriends;
    private ImageButton myFavoriteLocations;
    private ImageButton mySavedEvents;

    SendCategory sendCategory;


    public interface SendCategory {
        void setCategory(String category);
        void openDrawer();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the TimeListener so we can send events to the host
            sendCategory = (fragment_profile.SendCategory) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement SendCategory");
        }
    }



    public static fragment_profile newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_profile profileFragment = new fragment_profile();
        profileFragment.setArguments(args);
        return profileFragment;
    }

    public fragment_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.include10);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false); //disables default actionbar title UCRmap
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCategory.openDrawer();
            }
        });

        myClass = (ImageButton) v.findViewById(R.id.mySchedule);
        myFriends = (ImageButton) v.findViewById(R.id.myFriends);
        myFavoriteLocations = (ImageButton) v.findViewById((R.id.myFavoriteLocations));
        mySavedEvents = (ImageButton) v.findViewById(R.id.mySavedEvents);

        myClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCategory.setCategory("Class");
            }
        });

        myFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCategory.setCategory("Friend");

            }
        });

        myFavoriteLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCategory.setCategory("Favorite");
            }
        });

        mySavedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCategory.setCategory("Saved");
            }
        });


        return v;
    }

}
