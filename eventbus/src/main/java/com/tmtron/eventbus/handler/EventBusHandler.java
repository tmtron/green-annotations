package com.tmtron.eventbus.handler;

import com.helger.jcodemodel.*;
import com.tmtron.eventbus.annotations.EventBus;
import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.ElementValidation;
import org.androidannotations.handler.BaseAnnotationHandler;
import org.androidannotations.handler.MethodInjectionHandler;
import org.androidannotations.helper.InjectHelper;
import org.androidannotations.holder.EComponentHolder;
import org.androidannotations.holder.HasLifecycleMethods;
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

        validatorHelper.isNotPrivate(element, validation);

        List<String> allowedTypes = new ArrayList<>();
        // add allowed type: "org.greenrobot.eventbus.EventBus"
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
        // * fieldRef is the field reference (this.bus) that is annotated with this EventBus annotation
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
     * initializes the EventBus member variable in the init_ method
     * with the default EventBus
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
     * the @Subsribe annotation
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
     * adds register/unregister calls for the eventbus to the onStart/onStop methods
     */
    private void handleEventBusRegistration(IJAssignmentTarget fieldRef, HasLifecycleMethods holder) {
        HasLifecycleMethods holderWithLifecycleMethods = holder;
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
