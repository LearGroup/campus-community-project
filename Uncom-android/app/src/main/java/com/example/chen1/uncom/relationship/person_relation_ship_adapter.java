package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.utils.LoadImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/6/21.
 */

public class person_relation_ship_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<RelationShipLevelBean> data=null;
    public person_relation_ship_adapter(Context context ,ArrayList<RelationShipLevelBean> dataList){
        this.data=dataList;
        this.context=context;
    }

    private TextView username;
    private CircleImageView headImage;


    public ArrayList<RelationShipLevelBean> getData() {
        return data;
    }

    public void setData(ArrayList<RelationShipLevelBean> data) {
        this.data = data;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }
        return data.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        LinearLayout linearLayout=(LinearLayout)layoutInflater.inflate(R.layout.person_relation_ship_item_layout,null);
        headImage=(CircleImageView) linearLayout.findViewById(R.id.appCompatImageView3);
        username=(TextView) linearLayout.findViewById(R.id.person_username);
        username.setText(data.get(position).getUsername());
        LoadImageUtils.getFirendHeaderImage(data.get(position).getHeader_pic(),context,headImage);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getFragmentManager();
                person_detailed_information_fragment fragment = person_detailed_information_fragment.getInstance();
                Bundle bundle=new Bundle();
                bundle.putParcelable("frendData",data.get(position));
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,fragment).commit();

            }
        });

        return linearLayout;
    }
}
