package com.yunkahui.datacubeper.test.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.bean.CardTestItem;
import com.yunkahui.datacubeper.common.utils.SimpleDateFormatUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 历史测评列表adapter
 */

public class TestHistoryListAdapter extends BaseQuickAdapter<CardTestItem,BaseViewHolder> {

    public TestHistoryListAdapter(int layoutResId, @Nullable List<CardTestItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardTestItem item) {
        helper.setText(R.id.text_view_title,item.getApr_id()+"");
        helper.setText(R.id.text_view_time, SimpleDateFormatUtils.formatYMD(item.getCreate_time()));
        if(TextUtils.isEmpty(item.getApr_return_datas())){
            helper.setText(R.id.text_view_test_result,"测评失败");
            helper.setTextColor(R.id.text_view_test_result, Color.parseColor("#FF0000"));
        }else{
            helper.setText(R.id.text_view_test_result,"测评成功");
            helper.setTextColor(R.id.text_view_test_result, Color.parseColor("#949494"));
        }

    }
}
