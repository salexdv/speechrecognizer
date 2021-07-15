#include <wchar.h>
#include <thread>
#include "MainApp.h"
#include "ConversionWchar.h"

MainApp::MainApp() : cc(nullptr), obj(nullptr)
{
}

MainApp::~MainApp()
{
	if (obj)
	{
		stopSpeechRecognition(); // call to unregister broadcast receiver
		JNIEnv *env = getJniEnv();
		env->DeleteGlobalRef(obj);
		env->DeleteGlobalRef(cc);
	}
}

void MainApp::Initialize(IAddInDefBaseEx* cnn)
{
	if (!obj)
	{
		IAndroidComponentHelper* helper = (IAndroidComponentHelper*)cnn->GetInterface(eIAndroidComponentHelper);
		if (helper)
		{
			WCHAR_T* className = nullptr;
			convToShortWchar(&className, L"com.github.salexdv.speechrecognizer.addin.MainApp");
			jclass ccloc = helper->FindClass(className);
			delete[] className;
			className = nullptr;
			if (ccloc)
			{
				JNIEnv* env = getJniEnv();
				cc = static_cast<jclass>(env->NewGlobalRef(ccloc));
				env->DeleteLocalRef(ccloc);
				jobject activity = helper->GetActivity();
				// call of constructor for java class
				jmethodID methID = env->GetMethodID(cc, "<init>", "(Landroid/app/Activity;J)V");
				jobject objloc = env->NewObject(cc, methID, activity, (jlong)cnn);
				obj = static_cast<jobject>(env->NewGlobalRef(objloc));
				env->DeleteLocalRef(objloc);
				methID = env->GetMethodID(cc, "show", "()V");
				env->CallVoidMethod(obj, methID);
				env->DeleteLocalRef(activity);
			}
		}
	}
}

void MainApp::sleep(long delay) {
    std::this_thread::sleep_for(std::chrono::milliseconds(delay));
}

void MainApp::startSpeechRecognition() const
{
	if (obj)
	{		
		JNIEnv* env = getJniEnv();
		jmethodID methID = env->GetMethodID(cc, "startSpeechRecognition", "()V");
		env->CallVoidMethod(obj, methID);
	}
}

void MainApp::stopSpeechRecognition() const
{
	if (obj)
	{
		JNIEnv* env = getJniEnv();
		jmethodID methID = env->GetMethodID(cc, "stopSpeechRecognition", "()V");
		env->CallVoidMethod(obj, methID);
	}
}

void MainApp::playSoundAlert() const
{
	if (obj)
	{
		JNIEnv* env = getJniEnv();
		jmethodID methID = env->GetMethodID(cc, "playSoundAlert", "()V");
		env->CallVoidMethod(obj, methID);
	}
}


void MainApp::requestServiceStatus() const
{
	if (obj)
	{		
		JNIEnv* env = getJniEnv();
		jmethodID methID = env->GetMethodID(cc, "requestServiceStatus", "()V");
		env->CallVoidMethod(obj, methID);

	}
}


static const wchar_t g_EventSource[] = L"speechrecognizer";
static WcharWrapper s_EventSource(g_EventSource);


// name of function built according to Java native call
//
extern "C" JNIEXPORT void JNICALL Java_com_github_salexdv_speechrecognizer_addin_MainApp_OnRecognitionResult(JNIEnv* env, jclass jClass, jlong pObject, jstring data) {
	
	
	static const wchar_t g_EventName[] = L"RecognitionResult";	
	static WcharWrapper s_EventName(g_EventName);
	
	std::wstring result;

	if (data)
	{
		const jchar* pCh = env->GetStringChars(data, 0);
		jsize len = env->GetStringLength(data);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		env->ReleaseStringChars(data, pCh);
	}	

	IAddInDefBaseEx *pAddIn = (IAddInDefBaseEx *) pObject;
	
	if (pAddIn != nullptr) {
		pAddIn->ExternalEvent(s_EventSource, s_EventName, WcharWrapper(result.data()));
	}

}

extern "C" JNIEXPORT void JNICALL Java_com_github_salexdv_speechrecognizer_addin_MainApp_OnRecognitionPartialResult(JNIEnv * env, jclass jClass, jlong pObject, jstring data) {


	static const wchar_t g_EventName[] = L"RecognitionPartialResult";
	static WcharWrapper s_EventName(g_EventName);

	std::wstring result;

	if (data)
	{
		const jchar* pCh = env->GetStringChars(data, 0);
		jsize len = env->GetStringLength(data);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		env->ReleaseStringChars(data, pCh);
	}

	IAddInDefBaseEx* pAddIn = (IAddInDefBaseEx*)pObject;

	if (pAddIn != nullptr) {
		pAddIn->ExternalEvent(s_EventSource, s_EventName, WcharWrapper(result.data()));
	}

}

extern "C" JNIEXPORT void JNICALL Java_com_github_salexdv_speechrecognizer_addin_MainApp_OnRecognitionReady(JNIEnv * env, jclass jClass, jlong pObject, jstring data) {


	static const wchar_t g_EventName[] = L"RecognitionReady";
	static WcharWrapper s_EventName(g_EventName);
	
	std::wstring result;

	if (data)
	{
		const jchar* pCh = env->GetStringChars(data, 0);
		jsize len = env->GetStringLength(data);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		env->ReleaseStringChars(data, pCh);
	}

	IAddInDefBaseEx* pAddIn = (IAddInDefBaseEx*)pObject;

	if (pAddIn != nullptr) {
		pAddIn->ExternalEvent(s_EventSource, s_EventName, WcharWrapper(result.data()));
	}

}

extern "C" JNIEXPORT void JNICALL Java_com_github_salexdv_speechrecognizer_addin_MainApp_onBeginningOfSpeech(JNIEnv * env, jclass jClass, jlong pObject, jstring data) {


	static const wchar_t g_EventName[] = L"onBeginningOfSpeech";
	static WcharWrapper s_EventName(g_EventName);

	std::wstring result;

	if (data)
	{
		const jchar* pCh = env->GetStringChars(data, 0);
		jsize len = env->GetStringLength(data);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		env->ReleaseStringChars(data, pCh);
	}

	IAddInDefBaseEx* pAddIn = (IAddInDefBaseEx*)pObject;

	if (pAddIn != nullptr) {
		pAddIn->ExternalEvent(s_EventSource, s_EventName, WcharWrapper(result.data()));
	}

}

extern "C" JNIEXPORT void JNICALL Java_com_github_salexdv_speechrecognizer_addin_MainApp_RecServiceStatus(JNIEnv * env, jclass jClass, jlong pObject, jstring data) {


	static const wchar_t g_EventName[] = L"ServiceStatus";
	static WcharWrapper s_EventName(g_EventName);
	
	std::wstring result;

	if (data)
	{
		const jchar* pCh = env->GetStringChars(data, 0);
		jsize len = env->GetStringLength(data);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		env->ReleaseStringChars(data, pCh);
	}
	
	IAddInDefBaseEx* pAddIn = (IAddInDefBaseEx*)pObject;

	if (pAddIn != nullptr) {
		pAddIn->ExternalEvent(s_EventSource, s_EventName, WcharWrapper(result.data()));
	}

}

std::wstring MainApp::jstring2wstring(JNIEnv* jenv, jstring aStr)
{
	std::wstring result;

	if (aStr)
	{
		const jchar* pCh = jenv->GetStringChars(aStr, 0);
		jsize len = jenv->GetStringLength(aStr);
		const jchar* temp = pCh;
		while (len > 0)
		{
			result += *(temp++);
			--len;
		}
		jenv->ReleaseStringChars(aStr, pCh);
	}
	return result;
}

void MainApp::setCC(jclass _cc) {
    cc = _cc;
}

void MainApp::setOBJ(jobject _obj) {
    obj= _obj;
}
