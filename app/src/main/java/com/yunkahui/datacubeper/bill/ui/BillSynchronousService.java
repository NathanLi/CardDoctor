package com.yunkahui.datacubeper.bill.ui;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.common.api.BaseUrl;
import com.yunkahui.datacubeper.common.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Arrays;

/**
 * 爬虫 socket 服务
 */
public class BillSynchronousService extends Service {

    public static final String RADIO_RECEIVE_MESSAGE = "RADIO_RECEIVE_MESSAGE";   //接收到数据
    public static final String RADIO_SEND_MESSAGE = "RADIO_SEND_MESSAGE";       //发送数据

    private LocalBroadcastManager mBroadcastManager;
    private final String mHost = "120.77.233.89";
    private final int mPort = 7002;

    private WeakReference<Socket> mSocketWeakReference;
    private InnerReaderThread mReaderThread;
    private InnerServiceReceiver mServiceReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("服务创建");
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
        mServiceReceiver = new InnerServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(RADIO_SEND_MESSAGE);
        mBroadcastManager.registerReceiver(mServiceReceiver, filter);

        initSocket();
    }

    //初始化socket
    public void initSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(mHost, mPort);
                    if (socket.isConnected()) {
                        mSocketWeakReference = new WeakReference<>(socket);
                        mReaderThread = new InnerReaderThread(socket);
                        mReaderThread.start();
                        LogUtils.e("socket连接成功");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("type", "analyzer_do_spider");
                            jsonObject.put("login_uid", "6225768607613864");
                            jsonObject.put("card_id", "6225768607613864");
                            jsonObject.put("uid", "10");
                            jsonObject.put("user_code", BaseUrl.getUSER_ID());
                            jsonObject.put("org_number", getResources().getString(R.string.org_number));
                            LogUtils.e("发送参数->" + jsonObject.toString());
                            Intent intent1 = new Intent(BillSynchronousService.RADIO_SEND_MESSAGE);
                            intent1.putExtra("message", jsonObject.toString());
                            mBroadcastManager.sendBroadcast(intent1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    LogUtils.e("initSocket: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //发送消息
    public void setMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = mSocketWeakReference.get();
                    if (socket.isConnected() && !socket.isClosed()) {
                        LogUtils.e("开始发送消息");
                        LogUtils.e("正在发送->"+message);
                        DataOutputStream outputStream = outputStream = new DataOutputStream(socket.getOutputStream());
                        outputStream.write(message.getBytes());
                        outputStream.flush();
                        LogUtils.e("发送消息完成");
                    } else {
                        LogUtils.e("发送消息异常-socket已关闭");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //接收数据线程
    private class InnerReaderThread extends Thread {

        private WeakReference<Socket> mWeakSocket;
        private boolean isRunning = true;

        public InnerReaderThread(Socket socket) {
            mWeakSocket = new WeakReference<>(socket);
            LogUtils.e("消息接收线程开启");
        }

        public void reset() {
            isRunning = false;
        }

        @Override
        public void run() {
            Socket socket = mWeakSocket.get();
            if (socket != null) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isRunning && ((length = is.read(buffer)) != -1)) {
                        LogUtils.e("线程接收到消息");
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            Intent intent = new Intent(RADIO_RECEIVE_MESSAGE);
                            intent.putExtra("message", message);
                            mBroadcastManager.sendBroadcast(intent);
                            LogUtils.e("线程接收到消息-已处理");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    //广播接收
    private class InnerServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (RADIO_SEND_MESSAGE.equals(intent.getAction())) {
                LogUtils.e("InnerServiceReceiver发送消息");
                String message = intent.getStringExtra("message");
                setMessage(message);

            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("服务关闭");
        mBroadcastManager.unregisterReceiver(mServiceReceiver);
        if (mReaderThread != null) {
            mReaderThread.reset();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
