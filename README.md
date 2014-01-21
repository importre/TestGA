Google Analytics Sample App
===========================



요구사항
--------

- Android SDK
- GA SDK for Android v3 (beta)
    - `libGoogleAnalyticsServices.jar`
- GA를 적용할 앱
- GA property 생성 [\<1\>][1]



0. 적용 과정
------------

1. `AndroidManifest.xml` 수정
2. `analytics.xml` 추가
3. `EasyTracker` 메소드 적용


### 적용 후 확인 할 수 있는 사항

- 활성 사용자
- 사용 중인 화면
- 사용자 정의 이벤트
- 충돌, 예외 등



1. AndroidManifest.xml
----------------------

Google Analytics 사용시 필요한 권한 추가

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```



2. analytics.xml
----------------

`res/values/`에 추가 [\<2\>][2]

```
<?xml version="1.0" encoding="utf-8" ?>

<resources>
    <!--Replace placeholder ID with your tracking ID-->
    <string name="ga_trackingId">UA-XXXX-Y</string>

    <!--Enable automatic activity tracking-->
    <bool name="ga_autoActivityTracking">true</bool>

    <!--Enable automatic exception tracking-->
    <bool name="ga_reportUncaughtExceptions">true</bool>

    <!-- The screen names that will appear in reports -->
    <string name="io.github.importre.android.testga.MainActivity">Home</string>
</resources>
```



3. EasyTracker 메소드 적용
-----------------------

- Screen
    - `EasyTracker.activityStart()`
    - `EasyTracker.activityStop()`

- Send
    - Event
        - `MapBuilder.createEvent()`
    - Exception
        - `MapBuilder.createException()`










[1]: https://support.google.com/analytics/answer/2614741?hl=ko "GA 속성 설정 도움말"
[2]: https://developers.google.com/analytics/devguides/collection/android/parameters#parameters "EasyTracker 속성"
