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

import com.tmtron.greenannotations.GreenAnnotationsPlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ServiceController;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class GreenEventBusActivityTest {

    private static final int START_ID = 17;

    @Test
    public void testEventBusAssigned() {
        GreenEventBusActivity activity = Robolectric.setupActivity(GreenEventBusActivity_.class);

        /* Note: this test is just used to get rid of a wrong IDEA Code Analysis warning
         * in the green-annotations-test:
         *  Module 'green-annotations-test' sources do not depend on module 'green-annotations' sources
         *
         * This call will insert an explicit dependency on the green-annotations project
         * and the warning is gone
         *
         * see also: http://stackoverflow.com/questions/39589379/idea-how-to-handle-module-a-sources-do-not-depend-on-module-b-sources-war
         */
        assertThat(GreenAnnotationsPlugin.class.getName()).isNotEmpty();

        assertThat(activity.bus).isNotNull();
        assertThat(activity.eventIdentifier).isNullOrEmpty();
    }

    @Test
    public void testEventFired() {
        GreenEventBusActivity activity = Robolectric.setupActivity(GreenEventBusActivity_.class);

        assertThat(activity.bean4EventTest).isNotNull();
        activity.bean4EventTest.fireEvent();
        assertThat(activity.eventIdentifier).isEqualTo(Bean4EventTest.EVENT_IDENTIFIER);
    }

    @Test
    public void testServiceEventFired() {
        ServiceController<Service4EventTest_> serviceController = Robolectric.buildService(Service4EventTest_.class).attach().create();
        Service4EventTest service = serviceController.get();

        assertThat(service.eventBus).isNotNull();

        assertThat(service.startId).isEqualTo(0);
        serviceController.startCommand(0, START_ID);
        assertThat(service.startId).isEqualTo(START_ID);

        assertThat(service.eventIdentifier).isNullOrEmpty();
        service.fireEvent();
        assertThat(service.eventIdentifier).isEqualTo(Service4EventTest.EVENT_IDENTIFIER);

        serviceController.destroy();
    }


}
