package ucr.ucrmap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_comments extends Fragment {

    ListView commentsListView;
    CommentsAdapter adapter;
    List<Comment> commentList;
    //static View rootView;

    public fragment_comments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comments, container, false);

        /*
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        }*/

        commentsListView = (ListView) v.findViewById(R.id.listview_comments);
        commentList = new ArrayList<>();

        //Comment(String commentAuthorData, String commentDateData, String commentBodyData) {

        commentList.add(new Comment("Richard", "October 7, 2017 10:51 PM", "Hey this is a test"));
        commentList.add(new Comment("Edward", "October 7, 2017 10:53 PM", "I can see your comment!"));
        commentList.add(new Comment("Richard", "October 7, 2017 10:55 PM", "cool!"));



        adapter = new CommentsAdapter(getContext(), commentList);
        commentsListView.setAdapter(adapter);


        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragment_comments f = (fragment_comments) getFragmentManager()
                .findFragmentById(R.id.comments_fragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

}
