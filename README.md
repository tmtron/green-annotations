# Green Annotations

An [Android Annotations](http://androidannotations.org/) plugin to support the [Greenrobot Eventbus](http://greenrobot.org/eventbus/).  
It can inject the [default EventBus](http://greenrobot.org/files/eventbus/javadoc/3.0/org/greenrobot/eventbus/EventBus.html#getDefault--) and automatically handle the registration/unregistration.

<!--
 ![] is the markdown syntax to add an image and this is surounded by a link to the travis-ci builds page
-->
[![Build Status](https://travis-ci.org/tmtron/green-annotations.svg?label=travis)](https://travis-ci.org/tmtron/green-annotations/builds) [![Maven Central](https://img.shields.io/maven-central/v/com.tmtron/green-annotations.svg?maxAge=2592000)](https://maven-badges.herokuapp.com/maven-central/com.tmtron/green-annotations) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-green--annotations-green.svg?style=true)](https://android-arsenal.com/details/1/4405) [![Javadoc](https://javadoc-emblem.rhcloud.com/doc/com.tmtron/green-annotations/badge.svg)](http://www.javadoc.io/doc/com.tmtron/green-annotations/) [![license](https://img.shields.io/github/license/tmtron/green-annotations.svg?maxAge=2592000)](https://raw.githubusercontent.com/tmtron/green-annotations/develop/LICENSE) [![Join the chat at https://gitter.im/green-annotations/Lobby](https://badges.gitter.im/green-annotations/Lobby.svg)](https://gitter.im/green-annotations/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) <a href="http://www.methodscount.com/?lib=com.tmtron%3Agreen-annotations-api%3A1.0.1"><img src="https://img.shields.io/badge/Methods and size-0 | 4 KB-e91e63.svg"/></a>

## Usage in your Android Studio project:

In the `build.gradle` file of the module project:

```gradle
    dependencies {
        // Android Annotations for Greenrobot
        apt 'com.tmtron:green-annotations:1.0.1'
        compile 'com.tmtron:green-annotations-api:1.0.1'
    }
```

## Details

### Before
Shows the Activity without using AndroidAnnotations or GreenAnnotations: 

```java
    public class ActivityBefore extends Activity {
    
        EventBus eventBus;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            eventBus = EventBus.getDefault();
        }
    
        @Override
        public void onStart() {
            super.onStart();
            eventBus.register(this);
        }
    
        @Override
        public void onStop() {
            eventBus.unregister(this);
            super.onStop();
        }
    
        public void fireEvent(String message) {
            eventBus.post(new MessageEvent(message));
        }
    
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(MessageEvent event) {
            Toast.makeText(getApplicationContext(), event.message, Toast.LENGTH_SHORT).show();
        }
    
    }
```

### After
Shows the same Activity with AndroidAnnotations and GreenAnnotations:

```java
    @EActivity
    public class ActivityAfter extends Activity {
    
        @EventBusGreenRobot
        EventBus eventBus;
    
        public void fireEvent(String message) {
            eventBus.post(new MessageEvent(message));
        }
    
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(MessageEvent event) {
            Toast.makeText(getApplicationContext(), event.message, Toast.LENGTH_SHORT).show();
        }
    
    }
```


## License
This plugin is under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). Copyright 2016, Martin Trummer
