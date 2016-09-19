# Green Annotations

An [Android Annotations](http://androidannotations.org/) plugin to support [Green Robot](http://greenrobot.org/).

This is a test-project to learn how to develop a plugin for [Android Annotations](http://androidannotations.org/).  
The first test is to develop an annotation for the [Greenrobot Eventbus](http://greenrobot.org/eventbus/)

NOT READY FOR PRODUCTION!

<!--
 ![] is the markdown syntax to add an image and this is surounded by a link to the travis-ci builds page
-->
[![Build Status](https://travis-ci.org/tmtron/green-annotations.svg)](https://travis-ci.org/tmtron/green-annotations/builds)

## Usage in your Android Studio project:

In the `build.gradle` file of the module project:

    dependencies {
        // Android Annotations for Greenrobot
        apt 'com.tmtron:green-annotations:1.0.0-SNAPSHOT'
        compile 'com.tmtron:green-annotations-api:1.0.0-SNAPSHOT'
    }

## License
This plugin is under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). Copyright 2016, Martin
