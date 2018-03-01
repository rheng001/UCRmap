package ucr.ucrmap;

/**
 * Created by Oclemy on 2017 for ProgrammingWizards TV Channel and http://www.camposha.info.
 - Our galaxycell class
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaychang.srv.SimpleCell;
import com.jaychang.srv.SimpleViewHolder;
/*
- Our galaxy cell object.
- Derives from SimpleCell
- Represents a single viewitem that will get inflated from our model layout.
 */
public class GalaxyCell extends SimpleCell<Galaxy,GalaxyCell.ViewHolder>  {


    public GalaxyCell(@NonNull Galaxy item) {
        super(item);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.model;
    }

    /*
    - Return a ViewHolder instance
     */
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, View cellView) {
        return new ViewHolder(cellView);
    }

    /*
    - Bind data to widgets in our viewholder.
     */
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Context context, Object o) {
        viewHolder.titleTxt.setText(getItem().getName());
        viewHolder.descTxt.setText(getItem().getDescription());
        viewHolder.timeTxt.setText(getItem().getTime());
        viewHolder.linkTxt.setText(getItem().getLink());
    }
    /**
     - Our ViewHolder class.
     - Inner static class.
     * Define your view holder, which must extend SimpleViewHolder.
     * */
    static class ViewHolder extends SimpleViewHolder {
        TextView titleTxt,descTxt, timeTxt, linkTxt;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.nameTxt);
            descTxt=itemView.findViewById(R.id.descTxt);
            timeTxt=itemView.findViewById(R.id.timeTxt);
            linkTxt=itemView.findViewById(R.id.linkTxt);


        }
    }
}

