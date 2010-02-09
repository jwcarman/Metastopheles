/*
 * Copyright (c) 2010 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    protected AnnotatedElement getDefaultAnnotationSource()
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

    public MethodMetaData getMethodMetaData(String methodName, Class<?>... parameterTypes)
    {
        try
        {
            return methodMethodMetaDataMap.get(getBeanDescriptor().getBeanClass().getMethod(methodName, parameterTypes));
        }
        catch (NoSuchMethodException e)
        {
            throw new IllegalArgumentException("Method " + methodName + " not found on class " + getBeanDescriptor().getBeanClass().getName() + ".", e);
        }
    }
    
    public PropertyMetaData getPropertyMetaData(String propertyName)
    {
        return propertyMetaDataMap.get(propertyName);
    }
}
