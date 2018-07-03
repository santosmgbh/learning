set alias=lddigital
set keystorepath=C:\Gabriel\Projetos\git\ld-digital\mobile\mobile\googleplay-keystore.jks
set sdkpath=C:\Users\Boleu\AppData\Local\Android\android-sdk\build-tools\26.0.2\

set mypath=%cd%
set jdkpath=%JAVA_HOME%



cd %jdkpath%\bin
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore %keystorepath% %mypath%\app-release-unsigned.apk %alias%
cd %sdkpath%
zipalign -v 4 %mypath%\app-release-unsigned.apk %mypath%\app-release-signed.apk
apksigner verify %mypath%\app-release-signed.apk