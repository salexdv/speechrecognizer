#include "../include/AddInDefBase.h"
#include "../include/IAndroidComponentHelper.h"
#include "../jni/jnienv.h"
#include "../include/IMemoryManager.h"
#include <string>

/* Wrapper calling class from java build com.github.salexdv.speechrecognizer */

class MainApp
{
private:

	jclass cc;
	jobject obj;	

public:

	MainApp();
	~MainApp();

	void setCC(jclass _cc);
	void setOBJ(jobject _obj);

	void Initialize(IAddInDefBaseEx*);
	
	void startSpeechRecognition() const;
	void stopSpeechRecognition() const;
	void playSoundAlert() const;
	void requestServiceStatus() const;
	void sleep(long delay);	

	static std::wstring jstring2wstring(JNIEnv* jenv, jstring aStr);
};