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

import org.androidannotations.testutils.AAProcessorTestHelper;

import java.io.File;

class ProcessorTestHelper extends AAProcessorTestHelper {

    private void assertGeneratedClassContains(Class sourceClass, String... expectedCodeLines) {
        File generatedFile = toGeneratedFile(sourceClass);
        assertGeneratedClassContains(generatedFile, expectedCodeLines);
    }

    private  void assertGeneratedClassDoesNotContain(Class sourceClass, String... unexpectedCodeLines) {
        File generatedFile = toGeneratedFile(sourceClass);
        assertGeneratedClassDoesNotContain(generatedFile, unexpectedCodeLines);
    }

    void assertGeneratedClassContainsBusInitialisation(Class sourceClass) {
        assertGeneratedClassContains(sourceClass,
                "        this.eventBus = EventBus.getDefault();"
        );
    }

    private final String[] codeBusRegister = new String[]{
            "        this.eventBus.register(this);"};

    private final String[] codeBusUnregister = new String[]{
            "        this.eventBus.unregister(this);"};

    void assertGeneratedClassDoesNotContainBusRegistration(Class sourceClass) {
        assertGeneratedClassDoesNotContain(sourceClass, codeBusRegister);
        assertGeneratedClassDoesNotContain(sourceClass, codeBusUnregister);
    }

    void assertGeneratedClassContainsBusRegistration(Class sourceClass) {
        assertGeneratedClassContains(sourceClass, codeBusRegister);
        assertGeneratedClassContains(sourceClass, codeBusUnregister);
    }


}
