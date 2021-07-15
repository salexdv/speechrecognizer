# Распознавание речи в мобильной платформе 1С

# Описание
Для распознавания речи используется связка из [приложения для Android (службы)](/android_service_app) и [внешней компоненты](/addin_recognizer) для мобильной платформы 1С. Взаимодействие между службой и компонентой реализовано через [Intents](https://developer.android.com/reference/android/content/Intent). Распознавание речи служба выполняет через [SpeechRecognizer](https://developer.android.com/reference/android/speech/SpeechRecognizer)

## Схема работы
![](https://github.com/salexdv/git_images/blob/master/speechrecognizer_schema.png?raw=true)

## Требования
- Android API не ниже 28 версии
- Платформа 1С не ниже 8.3.10

## Благодарности
В разработке использованы материалы, которые на конференции INFOSTART 2018 EDUCATION представил Игорь Кисиль, в частности статья на Инфостарт [Внешние компоненты мобильной платформы 1С для ОС Андроид](https://infostart.ru/1c/articles/987286/), за что ему большое спасибо! Также очень помогла вот эта [публикация](https://infostart.ru/public/1141918/) и [репозиторий](https://github.com/ripreal/androidUtils1cExt)