package com.yunkahui.datacubeper.share.logic;

import android.content.Context;

import com.hellokiki.rrorequest.HttpManager;
import com.hellokiki.rrorequest.SimpleCallBack;
import com.yunkahui.datacubeper.common.api.ApiService;
import com.yunkahui.datacubeper.common.bean.BaseBean;
import com.yunkahui.datacubeper.common.bean.Member;
import com.yunkahui.datacubeper.common.utils.RequestUtils;

import java.util.List;
import java.util.Map;

public class MemberLogic {

    //******** 获取普通/VIP会员列表 ********
    public void getMemberList(Context context, String memberType, int pageSize, int pageNum, SimpleCallBack<BaseBean<List<Member>>> callBack) {
        Map<String, String> params = RequestUtils.newParams(context)
                .addParams("member_type", memberType)
                .addParams("pageSize", pageSize)
                .addParams("pageNum", pageNum)
                .create();
        HttpManager.getInstance().create(ApiService.class).requestMemberList(params)
                .compose(HttpManager.<BaseBean<List<Member>>>applySchedulers()).subscribe(callBack);
    }
}
