package com.tmtron.greenannotations.test;

import android.app.Activity;
import com.tmtron.greenannotations.EventBusGreenRobot;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;

@EActivity
public class GreenEventBusActivity extends Activity {

    @EventBusGreenRobot
    EventBus bus;

}
