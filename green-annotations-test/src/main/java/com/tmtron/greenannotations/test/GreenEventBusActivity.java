/*
 * Copyright © 2016 Martin Trummer (martin.trummer@tmtron.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tmtron.greenannotations.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.tmtron.greenannotations.EventBusGreenRobot;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/* this has no effect: see
 * http://stackoverflow.com/questions/39590817/androidannotations-how-to-handle-activity-is-not-registered-in-the-manifest
 */
@SuppressLint("Registered")
@SuppressWarnings("CanBeFinal")
@EActivity
public class GreenEventBusActivity extends Activity {

    @EventBusGreenRobot
    EventBus bus;

    @Bean
    Bean4EventTest bean4EventTest;

    String eventIdentifier;

    @Subscribe
    public void handleEvent(Event4Tests event4Tests) {
        eventIdentifier = event4Tests.identifier;
    }

}
