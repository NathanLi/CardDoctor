package com.yunkahui.datacubeper.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.HomeItem;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<HomeItem> mFeatureList;

    public HomeItemAdapter(Context context, List<HomeItem> featureList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mFeatureList = featureList;
    }

    @Override
    public HomeItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.layout_list_item_home_feature, null, false));
    }

    @Override
    public void onBindViewHolder(HomeItemAdapter.MyViewHolder holder, int position) {
        HomeItem feature = mFeatureList.get(position);
        holder.img.setImageResource(feature.getImgRes());
        holder.title.setText(feature.getTitle());
        holder.featureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeatureList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout featureLayout;
        private ImageView img;
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            featureLayout = itemView.findViewById(R.id.ll_feature);
            img = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
