package org.metastopheles;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author James Carman
 * @since 1.0
 */
public abstract class MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private Map<AttributeKey,Object> attributeMap = new HashMap<AttributeKey, Object>();

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    protected abstract AnnotatedElement getAnnotatedElement();

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(AttributeKey<T> key)
    {
        return (T) attributeMap.get(key);
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationType)
    {
        return getAnnotatedElement().getAnnotation(annotationType);
    }
    
    public <T> void setAttribute(AttributeKey<T> key, T value)
    {
        attributeMap.put(key, value);
    }
}
