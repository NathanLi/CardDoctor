package com.yunkahui.datacubeper.bill.ui;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.yunkahui.datacubeper.common.utils.LogUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private final String mHost = "192.168.1.167";
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

        Intent intent1 = new Intent(RADIO_RECEIVE_MESSAGE);
        intent1.putExtra("message", "测试广播");
        mBroadcastManager.sendBroadcast(intent1);
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
                        LogUtils.e("socket连接成功");
                        mSocketWeakReference = new WeakReference<Socket>(socket);
                        mReaderThread = new InnerReaderThread(socket);
                        mReaderThread.start();
                    }
                } catch (IOException e) {
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
                        LogUtils.e("发送->"+message);
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
            mWeakSocket = new WeakReference<Socket>(socket);
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
