@echo off

echo Debug Hash key (base 64)
keytool -exportcert -alias androiddebugkey -keystore C:\Users\exiang\.android\debug.keystore  -storepass android -keypass android | openssl sha1 -binary | openssl base64

echo.
echo Debug MD5
keytool -list -alias androiddebugkey -keystore C:\Users\exiang\.android\debug.keystore -storepass android -keypass android

echo.
echo Release Hash key (base 64)
keytool -exportcert -alias alias -keystore keystore | openssl sha1 -binary | openssl base64

echo.
echo Release MD5
keytool -list -alias alias -keystore keystore

pause
