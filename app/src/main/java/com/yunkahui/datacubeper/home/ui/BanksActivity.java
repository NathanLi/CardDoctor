package com.yunkahui.datacubeper.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.bean.HomeItem;
import com.yunkahui.datacubeper.home.adapter.HomeItemAdapter;
import com.yunkahui.datacubeper.share.ui.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class BanksActivity extends AppCompatActivity implements IActivityStatusBar {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_banks);
        super.onCreate(savedInstanceState);
        setTitle("银行咨询");
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        final String[] titles = new String[] {
                "中国工商银行", "中国农业银行", "中国银行", "中国建设银行", "招商银行", "广东发展银行", "中国交通银行", "中国光大银行",
                "中信银行", "兴业银行", "中国民生银行", "华夏银行", "平安银行", "浦发银行", "广州银行", "北京银行",
                "上海银行"
                //, "平安惠普", "长沙银行"
        };
        Integer[] pics = new Integer[] {
                R.mipmap.bank_zhongguogongshang, R.mipmap.bank_zhongguonongye, R.mipmap.bank_zhongguo, R.mipmap.bank_zhongguojianshe,
                R.mipmap.bank_zhaoshang, R.mipmap.bank_guangdongfazhan, R.mipmap.bank_zhongguojiaoton, R.mipmap.bank_zhongguoguangda,
                R.mipmap.bank_zhongxin, R.mipmap.bank_xingye, R.mipmap.bank_zhongguomingsheng, R.mipmap.bank_huaxia,
                R.mipmap.bank_pingan, R.mipmap.bank_shanghaipufa, R.mipmap.bank_guangzhou, R.mipmap.bank_beijing,
                R.mipmap.bank_shanghai
                //, R.mipmap.bank_pinganhuipu, R.mipmap.bank_changsha
        };
        List<HomeItem> list = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            list.add(new HomeItem(pics[i], titles[i]));
        }
        final String[] links = new String[] {
                "http://www.icbc.com.cn/ICBC/%e7%89%a1%e4%b8%b9%e5%8d%a1/%e7%bc%a4%e7%ba%b7%e6%b4%bb%e5%8a%a8/", "http://www.abchina.com/cn/PersonalServices/ABCPromotion/National/", "http://www.boc.cn/bcservice/bi3/",
                "http://creditcard.ccb.com/favorable/activity.html", "http://best.cmbchina.com/", "http://card.cgbchina.com.cn/Channel/11820301",
                "http://creditcard.bankcomm.com/content/pccc/dismerchant/merchant.html", "http://xyk.cebbank.com/home/activities/index.htm?catename=NC_ACTI", "http://cards.ecitic.com/youhui/index.shtml",
                "http://creditcard.cib.com.cn/index.html", "http://creditcard.cmbc.com.cn/promotioninfo/index.aspx", "http://www.hxb.com.cn/home/cn/clientServ/kuaixu/list.shtml",
                "http://creditcard.pingan.com/cms-tmplt/creditecard/searchPreferentialInformation.do", "http://www.spdbccc.com.cn/zh/youhui.html", "http://creditcard.gzcb.com.cn/gzcb_web/activityList.action",
                "http://www.bankofbeijing.com.cn/creditcard/pages/activity/index.html", "http://www.bankofshanghai.com/zh/xyk/xyk_ywdt/index.shtml"
                //, "http://runcredit.wqdian.cn/55ae8c4e1494436d9298f34f919e7f6b.html", "http://runcredit.wqdian.cn/b9f2f0cf6c6f4590a98be6e5ab99f3df.html"
        };
        HomeItemAdapter adapter = new HomeItemAdapter(R.layout.layout_list_item_home, list);
        adapter.bindToRecyclerView(mRecyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(BanksActivity.this, WebViewActivity.class)
                        .putExtra("title", titles[position])
                        .putExtra("url", links[position]));
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }
}
