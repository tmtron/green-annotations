package com.tmtron.eventbus;

import com.tmtron.eventbus.handler.EventBusHandler;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.handler.AnnotationHandler;
import org.androidannotations.plugin.AndroidAnnotationsPlugin;

import java.util.ArrayList;
import java.util.List;

public class EventBusPlugin extends AndroidAnnotationsPlugin {

    private static final String NAME = "EventBus";

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
