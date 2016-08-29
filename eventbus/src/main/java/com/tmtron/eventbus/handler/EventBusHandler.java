package com.tmtron.eventbus.handler;

import com.helger.jcodemodel.*;
import com.tmtron.eventbus.annotations.EventBus;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.greenrobot.eventbus.Subscribe;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class EventBusHandler
        extends BaseAnnotationHandler<EComponentHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public EventBusHandler(AndroidAnnotationsEnvironment environment) {
        super(EventBus.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        injectHelper.validate(EventBus.class, element, validation);
        if (!validation.isValid()) {
            return;
        }

        List<String> allowedTypes = new ArrayList<>();
        // org.greenrobot.eventbus.EventBus
        allowedTypes.add(org.greenrobot.eventbus.EventBus.class.getCanonicalName());
        validatorHelper.allowedType(element, allowedTypes, validation);
    }

    @Override
    public void process(Element element, EComponentHolder holder) {
        injectHelper.process(element, holder);
    }

    @Override
    public JBlock getInvocationBlock(EComponentHolder holder) {
        return holder.getInitBodyInjectionBlock();
    }

    @Override
    public void assignValue(JBlock targetBlock, IJAssignmentTarget fieldRef, EComponentHolder holder, Element element, Element param) {
        TypeMirror fieldType = param.asType();
        String fieldTypeQualifiedName = fieldType.toString();

        AbstractJClass eventBusClass = getJClass(fieldTypeQualifiedName);
        IJExpression expression = eventBusClass.staticInvoke("getDefault");
        IJStatement statement = fieldRef.assign(expression);
        targetBlock.add(statement);

        // bus
        //System.err.println("element name: " + element.toString());
        // com.tmtron.dscontrol.MainActivity
        //System.err.println("enclosing element name: " + element.getEnclosingElement().toString());

        boolean hasSubscribeAnnotation = false;
        List<? extends Element> enclosedElements = element.getEnclosingElement().getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind() != ElementKind.METHOD) {
                continue;
            }

            if (enclosedElement.getAnnotation(Subscribe.class) != null) {
                hasSubscribeAnnotation = true;
                break;
            }
        }
        if (hasSubscribeAnnotation) {
            // we need to register/unregister the event-bus
            if (element.getEnclosingElement().getAnnotation(EActivity.class) != null) {
//                JBlock registerBlock = null;
//                JBlock unregisterBlock = null;
//
//                registerBlock = holder.getOnStartAfterSuperBlock();
//                unregisterBlock = holder.getOnStopBeforeSuperBlock();
//
//                registerBlock.invoke(element, "registerReceiver").arg(receiverField).arg(intentFilterField);
//                unregisterBlock.invoke(broadcastManager, "unregisterReceiver").arg(receiverField);
            }
        }

    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }

}
