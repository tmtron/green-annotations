package com.tmtron.greenannotations.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class GreenEventBusActivityTest {

    @Test
    public void testEventBusAssigned() {
        GreenEventBusActivity activity = Robolectric.setupActivity(GreenEventBusActivity_.class);

        assertThat(activity.bus).isNotNull();
    }

}
