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

package com.tmtron.greenannotations;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.androidannotations.annotations.EService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@EService
class SomeEService extends Service {

    // used for testing only
    @SuppressWarnings("unused")
    @EventBusGreenRobot
    public EventBus eventBus;

    @SuppressWarnings("EmptyMethod")
    @Subscribe
    public void handleEvent(final SomeEvent event) { }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
