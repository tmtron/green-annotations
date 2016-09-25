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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation on fields of any Android-Annotations enhanced class to inject the Greenrobot default Eventbus.
 *
 * <ul>
 * <li>the event-bus field (annotated with this annotation) will be initialize
 * in the {@code _init} method with the default event bus: {@code EventBus.getDefault()}</li>
 * <li>in addition, when the parent class has
 * <ul>
 *   <li>life-cycle methods (e.g. {@code org.androidannotations.annotations.EActivity},
 *   {@code org.androidannotations.annotations.EService}, etc.)</li>
 *    <li>AND at least one {@code Subscribe} annotation</li>
 * </ul>
 * we will also add {@code EventBus.register}/{@code EventBus.unregister} calls in the
 * {@code onStart}/{@code onStop} methods
 * </li>
 * </ul>
 * <p>
 * Example :
 * </p>
 * <pre><code>
 * import com.tmtron.greenannotations.EventBusGreenRobot;
 * import org.greenrobot.eventbus.EventBus;
 *
 * &#064;EActivity
 * public class MyActivity extends Activity {
 *
 *   // inject Greenrobot Eventbus
 *   &#064;EventBusGreenRobot
 *   EventBus eventBus;
 *
 * }
 * </code></pre>
 *
 * @see <a href="https://github.com/tmtron/green-annotations">green-annotations project on GitHub</a>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface EventBusGreenRobot {

}
