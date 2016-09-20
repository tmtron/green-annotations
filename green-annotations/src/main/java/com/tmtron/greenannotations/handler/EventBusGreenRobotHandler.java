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
package com.tmtron.greenannotations.handler;

import com.helger.jcodemodel.*;
import com.tmtron.greenannotations.EventBusGreenRobot;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.HasLifecycleMethods;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler for the {@link EventBusGreenRobot} annotation.
 * <ul>
 * <li>when there is an item annotated with {@link EventBusGreenRobot}, we will
 * initialize it in the {@code _init} method and assign the default event bus: {@link EventBus#getDefault()}</li>
 * <li>in addition, when the parent class has
 *   <ul>
 *   <li>life-cylce methods (e.g. {@link org.androidannotations.annotations.EActivity},
 *   {@link org.androidannotations.annotations.EService}, etc.)</li>
 *    <li>AND at least one {@link Subscribe} annotation</li>
 * </ul>
 * we will also add {@link EventBus#register(Object)}/{@link EventBus#unregister(Object)} calls in the
 * {@code onStart}/{@code onStop} methods
 * </li>
 * </ul>
 */
public class EventBusGreenRobotHandler
        extends BaseAnnotationHandler<EComponentHolder>
        implements MethodInjectionHandler<EComponentHolder> {

    private final InjectHelper<EComponentHolder> injectHelper;

    public EventBusGreenRobotHandler(AndroidAnnotationsEnvironment environment) {
        super(EventBusGreenRobot.class, environment);
        injectHelper = new InjectHelper<>(validatorHelper, this);
    }

    @Override
    public void validate(Element element, ElementValidation validation) {
        injectHelper.validate(EventBusGreenRobot.class, element, validation);
        if (!validation.isValid()) {
            return;
        }

        /* the element in the base class must not be private, because
         * we must be able to assign the event-bus instance to it
         * in the child-class
         */
        validatorHelper.isNotPrivate(element, validation);

        List<String> allowedTypes = new ArrayList<>();
        // add allowed type: "org.greenrobot.greenannotations.EventBusGreenRobot"
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
        // the InjectHelper will call this function
        // * targetBlock is a block in the init-method (which is returned by @getInvocationBlock)
        // * fieldRef is the field reference (this.bus) that is annotated with this EventBusGreenRobot annotation
        // * element is the java-model element (which refers to the annotated element/field)

        initEventBusMember(targetBlock, fieldRef, param);

        if (holder instanceof HasLifecycleMethods) {
            if (hasSubscribeAnnotation(element)) {
                // we need to register/unregister the event-bus
                handleEventBusRegistration(fieldRef, (HasLifecycleMethods) holder);
            }
        }
    }

    /**
     * initializes the event-bus member variable
     * <p>we will add code to the in the {@code init_} method, to initialize
     * the event-bus member variable (which is annotated with {@link EventBusGreenRobot}
     * and is of type {@link org.greenrobot.eventbus.EventBus} with the
     * default event-bus: {@link EventBus#getDefault()}
     * </p>

     * @param targetBlock  the code block where the initialization code is added to
     * @param fieldRef  the reference to the event-bus member variable.
     * @param param  the annotated element
     */
    private void initEventBusMember(JBlock targetBlock, IJAssignmentTarget fieldRef, Element param) {
        TypeMirror fieldType = param.asType();
        // fieldTypeQualifiedName = "org.greenrobot.eventbus.EventBus"
        String fieldTypeQualifiedName = fieldType.toString();

        AbstractJClass eventBusClass = getJClass(fieldTypeQualifiedName);
        // expression = org.greenrobot.eventbus.EventBus.getDefault()
        IJExpression expression = eventBusClass.staticInvoke("getDefault");
        // statement = this.bus = org.greenrobot.eventbus.EventBus.getDefault()
        IJStatement statement = fieldRef.assign(expression);
        targetBlock.add(statement);
    }

    /**
     * returns true when the element has at least one method that uses
     * the @{@link Subscribe} annotation
     * <p>
     * then we must register/unregister the class with the EventBus
     * </p>
     *
     * @param element  the element to check for the @{@link Subscribe} annotation
     * @return if we must register/unregister the EventBus
     */
    private boolean hasSubscribeAnnotation(Element element) {
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
        return hasSubscribeAnnotation;
    }

    /**
     * adds register/unregister calls for the EventBus to the onStart/onStop methods
     *
     * @param fieldRef  is the field reference ({@code this.bus}) that is annotated the {@link EventBusGreenRobot} annotation
     * @param holderWithLifecycleMethods  the component holder which implements {@link HasLifecycleMethods}
     */
    private void handleEventBusRegistration(IJAssignmentTarget fieldRef
            , HasLifecycleMethods holderWithLifecycleMethods) {

        JBlock onStartBlock = holderWithLifecycleMethods.getOnStartAfterSuperBlock();
        JBlock onStopBlock = holderWithLifecycleMethods.getOnStopBeforeSuperBlock();
        onStartBlock.invoke(fieldRef, "register").arg(JExpr._this());
        onStopBlock.invoke(fieldRef, "unregister").arg(JExpr._this());
    }

    @Override
    public void validateEnclosingElement(Element element, ElementValidation valid) {
        validatorHelper.enclosingElementHasEnhancedComponentAnnotation(element, valid);
    }

}
