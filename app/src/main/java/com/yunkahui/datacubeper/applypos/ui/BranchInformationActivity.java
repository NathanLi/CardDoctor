package com.yunkahui.datacubeper.applypos.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonObject;
import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.Branch;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.RequestUtils;
import com.yunkahui.datacubeper.common.view.LoadingViewDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 支行信息列表
 */
public class BranchInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private EditText mEditTextSearch;

    private String mCardNum;
    private String mBankName;
    private String mDepositProvince;
    private String mDepositCity;

    private List<Branch> mBranches;
    private List<Branch> mAllBranches;
    private InnerRecyclerViewAdapter mViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_information);

        mCardNum =getIntent().getStringExtra("card_number");
        mBankName=getIntent().getStringExtra("bank_name");
        mDepositProvince=getIntent().getStringExtra("deposit_province");
        mDepositCity=getIntent().getStringExtra("deposit_city");

        mRecyclerView=findViewById(R.id.recycler_view);
        mEditTextSearch=findViewById(R.id.edit_text_search);

        findViewById(R.id.button_search).setOnClickListener(this);
        mBranches=new ArrayList<>();
        mAllBranches=new ArrayList<>();
        initActionBar();
        initRecyclerView();
        loadData("");
    }

    private void initActionBar(){
        this.setTitle("支行信息");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initRecyclerView(){
        mViewAdapter=new InnerRecyclerViewAdapter(R.layout.layout_branch_list_item,mBranches);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mViewAdapter);
        mViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("bank_name",mBranches.get(position).getBank_name());
                intent.putExtra("bank_cnaps",mBranches.get(position).getBank_cnaps());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    /**
     * 筛选符合的数据
     * @param text
     */
    private void screening(String text){
        if(TextUtils.isEmpty(text)){
            mBranches.clear();
            mBranches.addAll(mAllBranches);
        }else{
            List<Branch> branches=new ArrayList<>();
            for (int i=0;i<mAllBranches.size();i++){
                if(mAllBranches.get(i).getBank_name().indexOf(text)!=-1){
                    branches.add(mAllBranches.get(i));
                }
            }
            mBranches.clear();
            mBranches.addAll(branches);
        }
        mViewAdapter.notifyDataSetChanged();
    }


    private void loadData(String key){

        LoadingViewDialog.getInstance().show(this);
        RequestUtils.InnerParam innerParam=RequestUtils.newParams(this)
                .addParams("bank_card_number", mCardNum)
                .addParams("bank_name",mBankName)
                .addParams("deposit_province",mDepositProvince)
                .addParams("deposit_city",mDepositCity);
        if(!TextUtils.isEmpty(key)){
            innerParam.addParams("keyword",key);
        }
        Map<String,String> params=innerParam.create();
        HttpManager.getInstance().create(ApiService.class).checkBranchBank(params)
                .compose(HttpManager.<JsonObject>applySchedulers()).subscribe(new SimpleCallBack<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                LoadingViewDialog.getInstance().dismiss();
                LogUtils.e("支行信息->"+jsonObject.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadingViewDialog.getInstance().dismiss();
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search:
                loadData(mEditTextSearch.getText().toString());
                break;
        }
    }


    private class InnerRecyclerViewAdapter extends BaseQuickAdapter<Branch,BaseViewHolder> {

        public InnerRecyclerViewAdapter(int layoutResId, @Nullable List<Branch> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Branch item) {
            helper.setText(R.id.text_view_title,item.getBank_name());
            if(item.isCheck()){
                helper.setTextColor(R.id.text_view_title, Color.parseColor("#007aff"));
            }else{
                helper.setTextColor(R.id.text_view_title, Color.parseColor("#666666"));
            }
        }
    }



}