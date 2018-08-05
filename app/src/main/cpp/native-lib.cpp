#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_cwgreene_testopencv_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++! bob";
    return env->NewStringUTF(hello.c_str());
}
