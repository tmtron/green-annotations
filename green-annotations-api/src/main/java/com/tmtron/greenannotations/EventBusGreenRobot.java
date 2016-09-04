package com.tmtron.greenannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Use it on fields of any Android-Annotations enhanced class to inject the Greenrobot default Eventbus.
 * </p>
 * <p>
 * The annotation will also automatically add code to onStart/onStop to register/unregister the event bus,
 * if you use a org.greenrobot.eventbus.Subscribe annotation on any method of the class.
 * </p>
 * <blockquote>
 * <p>
 * Example :
 * <p>
 * <pre>
 * import com.tmtron.greenannotations.EventBusGreenRobot;
 * import org.greenrobot.eventbus.EventBus;
 *
 * &#064;EActivity
 * public class MyActivity extends Activity {
 *
 * 	// inject Greenrobot Eventbus
 * 	&#064;EventBusGreenRobot
 * 	EventBus eventBus;
 *
 * }
 * </pre>
 * </blockquote>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface EventBusGreenRobot {

}
