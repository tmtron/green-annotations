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

import org.androidannotations.annotations.EBean;


/**
 * the compilation of {@link SomeEbeanError} is expected to fail,
 * because the {@link EventBusGreenRobot} annotation is used
 * on a variable of type string (instead of {@link org.greenrobot.eventbus.EventBus})
 */
@EBean
class SomeEbeanError {

    @EventBusGreenRobot
    // used for testing only and should fail anyway
    @SuppressWarnings("unused")
    public String eventBus;

}
