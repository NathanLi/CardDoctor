//
// Created by Administrator on 2018/5/21.
//

#ifndef CARDDOCTOR_COMPRESS_H
#define CARDDOCTOR_COMPRESS_H

#include <jni.h>

#define true 1
#define false 0

#ifdef __cplusplus
extern "C" {
#include <../include/jpeglib.h>
#endif

JNIEXPORT jint JNICALL
Java_com_yunkahui_datacubeper_common_utils_ImageCompress_compress
        (JNIEnv * env, jclass clazz, jobject bitmap, jint quality, jstring dstFile,jboolean optimize);

#ifdef __cplusplus
}
#endif
#endif //CARDDOCTOR_COMPRESS_H
