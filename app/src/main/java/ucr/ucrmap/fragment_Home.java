package ucr.ucrmap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_Home extends Fragment {


    public static fragment_Home newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        fragment_Home homeFragment = new fragment_Home();
        homeFragment.setArguments(args);
        return homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homescreen, container, false);
    }

}
