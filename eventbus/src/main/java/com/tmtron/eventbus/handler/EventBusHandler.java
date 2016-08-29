package com.tmtron.eventbus.handler;

import com.helger.jcodemodel.IJAssignmentTarget;
import com.helger.jcodemodel.JBlock;
import com.tmtron.eventbus.EventBusPlugin;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.holder.EComponentHolder;

import javax.lang.model.element.Element;

public class EventBusHandler
        extends BaseAnnotationHandler<EComponentHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    public EventBusHandler(AndroidAnnotationsEnvironment environment) {
        super(EventBusPlugin.class, environment);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        // TODO: implement
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        // TODO: implement
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        // TODO: implement
        return null;
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        // TODO: implement
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        // TODO: implement
    }

    private boolean elementExtendsRuntimeExceptionDao(Element element) {
        // TODO: implement
        return true;
    }
}
