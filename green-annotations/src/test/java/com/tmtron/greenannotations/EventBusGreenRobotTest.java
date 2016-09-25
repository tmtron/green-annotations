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

import org.androidannotations.internal.AndroidAnnotationProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class EventBusGreenRobotTest extends ProcessorTestHelper {

    @Before
    public void setUp() {
        // register our AndroidManifest.xml
        addManifestProcessorParameter(EventBusGreenRobotTest.class);
        addProcessor(AndroidAnnotationProcessor.class);
        // explicitly set the encoding
        // some test-files include special characters:
        // e.g. the copyright symbol
        addCompilerOptions("-encoding", "UTF-8");
    }

    @Test
    public void activityHasBusInitialisation() {
        CompileResult compileResult = compileFiles(
                SomeActivity.class,
                SomeEbean.class
        );
        assertCompilationSuccessful(compileResult);
        assertGeneratedClassContainsBusInitialisation(SomeActivity.class);
        assertGeneratedClassDoesNotContainBusRegistration(SomeActivity.class);

        assertGeneratedClassContainsBusInitialisation(SomeEbean.class);
        assertGeneratedClassDoesNotContainBusRegistration(SomeEbean.class);
    }

    @Test
    public void activityHasBusRegistration() {
        CompileResult compileResult = compileFiles(
                SomeActivityWithSubscriber.class,
                SomeEbean.class
        );
        assertCompilationSuccessful(compileResult);
        assertGeneratedClassContainsBusInitialisation(SomeActivityWithSubscriber.class);
        assertGeneratedClassContainsBusRegistration(SomeActivityWithSubscriber.class);

        assertGeneratedClassContainsBusInitialisation(SomeEbean.class);
        assertGeneratedClassDoesNotContainBusRegistration(SomeEbean.class);
    }

    @Test
    public void eBeanCompiles() {
        CompileResult compileResult = compileFiles(
                SomeEbean.class
        );
        assertCompilationSuccessful(compileResult);
        assertGeneratedClassContainsBusInitialisation(SomeEbean.class);
        assertGeneratedClassDoesNotContainBusRegistration(SomeEbean.class);
    }

    /**
     * the compilation of {@link SomeEbeanError} is expected to fail,
     * because this {@link EventBusGreenRobot} annotation is used
     * on a variable of type string (instead of {@link org.greenrobot.eventbus.EventBus})
     *
     * @throws IOException when the java file for the test is not accessible
     */
    @Test
    public void eBeanCompilationFails() throws IOException {
        CompileResult compileResult = compileFiles(SomeEbeanError.class);
        assertCompilationErrorOn(SomeEbeanError.class, "@EventBusGreenRobot", compileResult);
    }

}
