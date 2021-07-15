#include <jni.h>
#include <string>
#include "../../../../../Inf18/MainApp.h"


extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_salexdv_speechrecognizer_addin_MainActivity_hello(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from speechrecognizer++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_com_github_salexdv_speechrecognizer_addin_MainApp_testSpeechRecognition(JNIEnv *env, jobject objloc) {
    jclass ccLoc = env ->GetObjectClass(objloc);
    jclass cc = static_cast<jclass>(env->NewGlobalRef(ccLoc));
    jobject obj = static_cast<jobject>(env->NewGlobalRef(objloc));

    MainApp mainApp{};
    mainApp.setCC(cc);
    mainApp.setOBJ(obj);
    mainApp.startSpeechRecognition();
}


extern "C"
JNIEXPORT void JNICALL
Java_com_github_salexdv_speechrecognizer_addin_MainApp_testSleep(JNIEnv *env, jobject objloc) {
    jclass ccLoc = env ->GetObjectClass(objloc);
    jclass cc = static_cast<jclass>(env->NewGlobalRef(ccLoc));
    jobject obj = static_cast<jobject>(env->NewGlobalRef(objloc));

    MainApp mainApp{};
    mainApp.setCC(cc);
    mainApp.setOBJ(obj);
    mainApp.sleep(2000);
}