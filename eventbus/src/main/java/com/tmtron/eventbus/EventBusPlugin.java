package com.tmtron.eventbus;

import com.tmtron.eventbus.handler.EventBusHandler;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

public class EventBusPlugin extends AndroidAnnotationsPlugin {

    /**
     * this will be used to find the properties files in the resources folder
     * eventbus.properties, eventbus-api.properties
     * thus we use lower-case
     */
    private static final String NAME = "eventbus";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<AnnotationHandler<?>> getHandlers(AndroidAnnotationsEnvironment androidAnnotationEnv) {
        List<AnnotationHandler<?>> annotationHandlers = new ArrayList<>();
        annotationHandlers.add(new EventBusHandler(androidAnnotationEnv));
        return annotationHandlers;
    }
}
