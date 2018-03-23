package com.yindian.carddoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yindian.carddoctor.R;
import com.yindian.carddoctor.common.bean.HomeFeature;

import java.util.List;

/**
 * Created by pc1994 on 2018/3/23
 */
public class HomeFeatureAdapter extends RecyclerView.Adapter<HomeFeatureAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<HomeFeature> featureList;

    public HomeFeatureAdapter(Context context, List<HomeFeature> featureList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.featureList = featureList;
    }

    @Override
    public HomeFeatureAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.home_grid_item, null, false));
    }

    @Override
    public void onBindViewHolder(HomeFeatureAdapter.MyViewHolder holder, int position) {
        HomeFeature feature = featureList.get(position);
        holder.img.setImageResource(feature.getImgRes());
        holder.title.setText(feature.getTitle());
        Log.e("test2", "onBindViewHolder: "+feature.getTitle());
    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
        }
    }
}
