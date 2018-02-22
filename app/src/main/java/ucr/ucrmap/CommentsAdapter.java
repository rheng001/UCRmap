package ucr.ucrmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 10/7/2017.
 */

public class CommentsAdapter extends BaseAdapter{

    Context mcContext;
    List<Comment> commentList;
    LayoutInflater mInflater;

    public CommentsAdapter(Context mcContext, List<Comment> commentList) {
        this.mcContext = mcContext;
        this.commentList = commentList;
        mInflater = (LayoutInflater) mcContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.custom_comments_row, viewGroup , false);
        //View v = View.inflate(mcContext, R.layout.custom_comments_row, null);
        TextView commentAuthor = (TextView) v.findViewById(R.id.commentAuthor);
        TextView commentDate = (TextView) v.findViewById(R.id.commentDate);
        TextView commentBody = (TextView) v.findViewById(R.id.commentBody);


        //Comment comment = (Comment) getItem(position);

        commentAuthor.setText(commentList.get(position).getCommentAuthorData());
        commentDate.setText(commentList.get(position).getCommentDateData());
        commentBody.setText(commentList.get(position).getCommentBodyData());

        //v.setTag(commentList.get(position).getId);

        return v;
    }
}
