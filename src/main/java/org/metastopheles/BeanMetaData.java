package org.metastopheles;

import java.beans.BeanDescriptor;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author James Carman
 * @since 1.0
 */
public class BeanMetaData extends MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final BeanDescriptor beanDescriptor;
    private final Map<String, PropertyMetaData> propertyMetaDataMap = new HashMap<String,PropertyMetaData>();
    private final Map<Method,MethodMetaData> methodMethodMetaDataMap = new HashMap<Method,MethodMetaData>();

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public BeanMetaData(BeanDescriptor beanDescriptor)
    {
        this.beanDescriptor = beanDescriptor;
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public BeanDescriptor getBeanDescriptor()
    {
        return beanDescriptor;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Override
    protected AnnotatedElement getAnnotatedElement()
    {
        return beanDescriptor.getBeanClass();
    }

    void addMethodMetaData(MethodMetaData methodMetaData)
    {
        methodMethodMetaDataMap.put(methodMetaData.getMethodDescriptor().getMethod(), methodMetaData);
    }

    void addPropertyMetaData(PropertyMetaData propertyMetaData)
    {
        propertyMetaDataMap.put(propertyMetaData.getPropertyDescriptor().getName(), propertyMetaData);
    }

    public MethodMetaData getMethodMetaData(Method method)
    {
        return methodMethodMetaDataMap.get(method);
    }

    public PropertyMetaData getPropertyMetaData(String propertyName)
    {
        return propertyMetaDataMap.get(propertyName);
    }
}
