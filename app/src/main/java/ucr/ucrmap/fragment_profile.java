package ucr.ucrmap;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class fragment_profile extends Fragment {

    private ImageButton myClass;
    private ImageButton myFriends;
    private ImageButton myFavoriteLocations;
    private ImageButton mySavedEvents;

    SendCategory sendCategory;
    private Button logout;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    SendCategory sendData;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

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
        logout = (Button) v.findViewById(R.id.Logout);

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                if(user1 == null)
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(),Login_Firebase.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK); // RESET LIFE CYCLE
                    // PRESSING BACK BUTTON WILL DO NOTHING
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });


        return v;
    }

}
