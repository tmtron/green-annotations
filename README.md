# androidannotations-greenrobot

this is a testproject to learn how to develop a plugin for [Android Annotations](http://androidannotations.org/)

first test is to develop an annotation for the [Greenrobot Eventbus](http://greenrobot.org/eventbus/)

DO NOT USE FOR PRODUCTION!

## usage in your Android Studio project:

in the build.gradle file of the module project:

    dependencies {
        // Android Annotations for Greenrobot
        apt 'com.tmtron:green-annotations:1.0.0-SNAPSHOT'
        compile 'com.tmtron:green-annotations-api:1.0.0-SNAPSHOT'
    }

