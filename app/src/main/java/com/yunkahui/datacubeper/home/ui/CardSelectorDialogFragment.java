package com.yunkahui.datacubeper.home.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.CardSelectorBean;
import com.yunkahui.datacubeper.common.view.CardSelector;
import com.yunkahui.datacubeper.home.adapter.RechargeAdapter;

import java.util.ArrayList;

/**
 * @author WYF on 2018/4/19/019.
 */
public class CardSelectorDialogFragment extends DialogFragment {

    private OnCheckedChangeListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_selector, container);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final ArrayList<CardSelectorBean> list = getArguments().getParcelableArrayList("list");
        RechargeAdapter adapter = new RechargeAdapter(getActivity(), R.layout.layout_list_item_card_select, list);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (CardSelectorBean c : list) {
                    c.setChecked(false);
                }
                list.get(position).setChecked(true);
                adapter.notifyDataSetChanged();
                dismiss();
                listener.onCheckedChange(list.get(position).getBankCardName(), list.get(position).getBankCardNum());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        listener = l;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(String bankName, String num);
    }
}
