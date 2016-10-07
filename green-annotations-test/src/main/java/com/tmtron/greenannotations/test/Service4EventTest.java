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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.tmtron.greenannotations.EventBusGreenRobot;
import org.androidannotations.annotations.EService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@SuppressWarnings({"WeakerAccess"})
@EService
public class Service4EventTest extends Service {
    static final String EVENT_IDENTIFIER = "service4EventTest-id";

    @SuppressWarnings("all")
    @EventBusGreenRobot
    EventBus eventBus;

    void fireEvent() {
        eventBus.post(new Event4Tests(EVENT_IDENTIFIER));
    }

    int startId = 0;
    String eventIdentifier;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event4Tests event4Tests) {
        eventIdentifier = event4Tests.identifier;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
