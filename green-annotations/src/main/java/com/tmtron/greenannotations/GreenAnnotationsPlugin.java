package com.tmtron.greenannotations;

import com.tmtron.greenannotations.handler.EventBusGreenRobotHandler;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

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
