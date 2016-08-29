package com.tmtron.eventbus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Use it on fields of any enhanced class to injects a Greenrobot Eventbus.
 * </p>
 * <blockquote>
 * <p>
 * Example :
 * <p>
 * <pre>
 * &#064;EActivity
 * public class MyActivity extends Activity {
 *
 * 	// inject Greenrobot Eventbus
 * 	&#064;EventBus
 * 	EventBus eventBus;
 *
 * }
 * </pre>
 * </blockquote>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface EventBus {

}
