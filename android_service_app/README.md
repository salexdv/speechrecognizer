# Android-приложение "Сервис распознавания речи для мобильной 1С"

# Описание
Приложение (сервис/служба), реализующее взаимодействие с объектом [SpeechRecognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer), и умеющее принимать и отдавать определенные [Intents](https://developer.android.com/guide/components/intents-filters).
В основе приложения лежат два важных объекта:
1. [Service](https://developer.android.com/reference/android/app/Service]), работающий в фоне
2. [BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver), который отвечает за взаимодействие с другими приложениями

## Intents

### Входящие
| Intent                                              | Описание                                    |
| --------------------------------------------------- | ------------------------------------------- |
| `com.github.salexdv.speechrecognizer.START`         | Запуск распознавания                        |
| `com.github.salexdv.speechrecognizer.STOP`          | Остановка распознавания                     |
| `com.github.salexdv.speechrecognizer.STATUS`        | Запрос статуса сервиса                      |

### Исходящие
| Intent                                              | Описание                                    |
| --------------------------------------------------- | ------------------------------------------- |
| `com.github.salexdv.speechrecognizer.ON_READY`      | При готовности к распознаванию              |
| `com.github.salexdv.speechrecognizer.ON_BEGININIG`  | При начале распознавания речи               |
| `com.github.salexdv.speechrecognizer.ON_END`        | При завршении распознавания                 |
| `com.github.salexdv.speechrecognizer.RESULT`        | Результат распознавания                     |
| `com.github.salexdv.speechrecognizer.PARTIAL_RESULT`| Промежуточные результаты распознавания      |
| `com.github.salexdv.speechrecognizer.ERROR`         | При возникновении ошибки                    |
| `com.github.salexdv.speechrecognizer.NOT_AVAILABLE` | При невозможности работы службы             |
| `com.github.salexdv.speechrecognizer.SERVICE_STATUS`| Возврат статуса работы                      |

### Параметры
Все параметры для Intents передаются через дополнительную ключ-пару `message`

## Требования
- Android API не ниже 28 версии

## Разворачивание окружения разработки на Windows 10
1. Установить Android Studio
2. В студии перейти в  Tools -> SDK Manager -> SDK Platforms и выбрать ANDROID API 28
3. В студии перейти в  Tools -> SDK Manager -> SDK Tools и выбрать следующее: 
* Android SDK build Tools
	* Android SDK Platform tools
	* Android SDK tools
	* Intel x86 Emulator Accelerator (для возможности запуска эмулятора из студии)

## Сборка приложения
1. Открыть проект в Android Studio и скомпилировать apk (Build -> Build bundle / APK -> APK). 
2. Полученный файл `apk` установить на устройство.