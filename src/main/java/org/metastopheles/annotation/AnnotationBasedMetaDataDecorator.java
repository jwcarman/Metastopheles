package org.metastopheles.annotation;

import org.metastopheles.MetaDataDecorator;
import org.metastopheles.MetaDataObject;

import java.lang.annotation.Annotation;

/**
 * An annotation-based metadata decorator.  Subclasses merely need to override the #{decorate} method.
 * @since 1.0
 * @author James Carman
 */
public abstract class AnnotationBasedMetaDataDecorator<T extends MetaDataObject, A extends Annotation> implements MetaDataDecorator<T>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final Class<A> annotationType;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public AnnotationBasedMetaDataDecorator(Class<A> annotationType)
    {
        this.annotationType = annotationType;
    }

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    protected abstract void decorate(T metaData, Annotation annotation);

//**********************************************************************************************************************
// MetaDataDecorator Implementation
//**********************************************************************************************************************

    public void decorate(T metaData)
    {
        A annotation = metaData.getAnnotation(annotationType);
        if (annotation != null)
        {
            decorate(metaData, annotation);
        }
    }
}
