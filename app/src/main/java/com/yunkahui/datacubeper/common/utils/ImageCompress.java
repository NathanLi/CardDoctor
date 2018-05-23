package com.yunkahui.datacubeper.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.yunkahui.datacubeper.R;

import java.io.File;

/**
 * 图片压缩工具类
 */
public class ImageCompress {

    static {
        System.loadLibrary("compress");
    }

    public static final int quality = 10;

    public static final String DIR = "/compressImage/";   //暂存压缩后图片的文件夹

    public static native int compress(Bitmap bitmap, int quality, String dstFile_, boolean optMinSize);

    //哈夫曼压缩
    public static void compress(final String filePath, final onCompressListener listener) {
        File saveFile = new File(Environment.getExternalStorageDirectory(), DIR);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        final String savePath = saveFile.getAbsolutePath() + File.separator + new File(filePath).getName();

        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                int result = compress(bitmap, quality, savePath, true);
                if (listener != null) {
                    listener.onFinish(result != -1 ? savePath : "");
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        });
    }

    /**
     * 删除所有缓存的压缩图片
     */
    public static void deleteAllCompress() {

        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory() + DIR;
                try {
                    File file = new File(path);
                    if (!file.exists()) {
                        return;
                    }
                    File[] compress = file.listFiles();
                    for (int i = 0; i < compress.length; i++) {
                        File file1 = compress[i];
                        if (file1.exists()) {
                            file1.delete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface onCompressListener {
        void onFinish(String path);
    }


}
