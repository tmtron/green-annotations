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

import com.tmtron.greenannotations.handler.EventBusGreenRobotHandler;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will register all annotation handlers.
 * <p>
 * This class is referred to by the
 *  {@code org.androidannotations.plugin.AndroidAnnotationsPlugin} file
 * in {@code src/main/resources/META-INF/services/}
 * </p>
 */
public class GreenAnnotationsPlugin extends AndroidAnnotationsPlugin {

    /**
     * this will be used to find the properties files in the resources folder
     * greenannotations.properties, greenannotations-api.properties
     * thus we use lower-case
     */
    private static final String NAME = "greenannotations";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<AnnotationHandler<?>> getHandlers(AndroidAnnotationsEnvironment androidAnnotationEnv) {
        List<AnnotationHandler<?>> annotationHandlers = new ArrayList<>();
        annotationHandlers.add(new EventBusGreenRobotHandler(androidAnnotationEnv));
        return annotationHandlers;
    }
}
