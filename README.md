# IFSC Code Validator

This application was created in Java. This application is used to verify the IFSC code and obtain bank information from the IFSC code. This application validates ifsc codes using the Razorpay API, retrofit for GET responses, and gsonfactory for parsing JSON.
<br/><br/>
<img src="./test.gif" alt="test_image" width="300">

## Razorpay IFSC Toolkit API

API server that serves Razorpay's IFSC API.<br/>
For more information please see [Razorpay IFSC API](https://ifsc.razorpay.com/)

# Libraries are used :

## Retrofit

Retrofit is a very popular HTTP client library for Android. Using Retrofit makes it easy to parse API response and use it in your application. It has built-in GSON converter that can automatically parse HTTP response into an Object or any other types in Java that can be used in your code.<br/>
To add Retrofit to your project, add the following dependency to your project `build.gradle` file :

```
dependencies {
  implementation 'com.squareup.retrofit2:retrofit:2.7.2'
  implementation 'com.squareup.retrofit2:converter-gson:2.7.2'
}
```

For more information please see [this website](https://square.github.io/retrofit/)

### License

```
Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Lottie for Android

Lottie is a mobile library for Android and iOS that parses [Adobe After Effects](https://www.adobe.com/products/aftereffects.html) animations exported as json with [Bodymovin](https://github.com/airbnb/lottie-web) and renders them natively on mobile!<br/>
To add Lottie for Android to your project, add the following dependency to your project `build.gradle` file :

```
dependencies {
  implementation 'com.airbnb.android:lottie:5.2.0'
}
```

#### learn more at https://airbnb.io/lottie/
