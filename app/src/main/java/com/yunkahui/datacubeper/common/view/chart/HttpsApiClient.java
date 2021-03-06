//
//  Created by  fred on 2017/1/12.
//  Copyright © 2016年 Alibaba. All rights reserved.
//

package com.yunkahui.datacubeper.common.view.chart;

import com.alibaba.cloudapi.sdk.client.HttpApiClient;
import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.model.ApiCallback;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.model.HttpClientBuilderParams;


public class HttpsApiClient extends HttpApiClient {
    public final static String HOST = "dm-51.data.aliyun.com";
    static HttpsApiClient instance = new HttpsApiClient();
    public static HttpsApiClient getInstance(){return instance;}

    public void init(HttpClientBuilderParams httpClientBuilderParams){
        httpClientBuilderParams.setScheme(Scheme.HTTPS);
        httpClientBuilderParams.setHost(HOST);
        super.init(httpClientBuilderParams);
    }



    public void identification(byte[] body , ApiCallback callback) {
        String path = "/rest/160601/ocr/ocr_idcard.json";
        ApiRequest request = new ApiRequest(HttpMethod.POST_BODY , path, body);
        

        sendAsyncRequest(request , callback);
    }
}