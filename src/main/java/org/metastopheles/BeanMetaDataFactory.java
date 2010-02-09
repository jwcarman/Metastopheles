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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * @author James Carman
 * @since 1.0
 */
public class BeanMetaDataFactory
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final Map<Class, BeanMetaData> metaDataMap = new WeakHashMap<Class, BeanMetaData>();

    private List<MetaDataDecorator<BeanMetaData>> beanMetaDataDecorators = new LinkedList<MetaDataDecorator<BeanMetaData>>();
    private List<MetaDataDecorator<MethodMetaData>> methodMetaDataDecorators = new LinkedList<MetaDataDecorator<MethodMetaData>>();
    private List<MetaDataDecorator<PropertyMetaData>> propertyMetaDataDecorators = new LinkedList<MetaDataDecorator<PropertyMetaData>>();

    private static final Map<String,BeanMetaDataFactory> factoryRegistry = new HashMap<String,BeanMetaDataFactory>();
    private final String id = UUID.randomUUID().toString();

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public BeanMetaDataFactory()
    {
        factoryRegistry.put(id, this);
    }

    static BeanMetaDataFactory get(String id)
    {
        return factoryRegistry.get(id);
    }
    
    public List<MetaDataDecorator<BeanMetaData>> getBeanMetaDataDecorators()
    {
        return beanMetaDataDecorators;
    }

    public void setBeanMetaDataDecorators(List<MetaDataDecorator<BeanMetaData>> beanMetaDataDecorators)
    {
        this.beanMetaDataDecorators = beanMetaDataDecorators;
    }

    public List<MetaDataDecorator<MethodMetaData>> getMethodMetaDataDecorators()
    {
        return methodMetaDataDecorators;
    }

    public void setMethodMetaDataDecorators(List<MetaDataDecorator<MethodMetaData>> methodMetaDataDecorators)
    {
        this.methodMetaDataDecorators = methodMetaDataDecorators;
    }

    public List<MetaDataDecorator<PropertyMetaData>> getPropertyMetaDataDecorators()
    {
        return propertyMetaDataDecorators;
    }

    public void setPropertyMetaDataDecorators(List<MetaDataDecorator<PropertyMetaData>> propertyMetaDataDecorators)
    {
        this.propertyMetaDataDecorators = propertyMetaDataDecorators;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    public synchronized BeanMetaData getBeanMetaData(Class beanClass)
    {
        if (metaDataMap.containsKey(beanClass))
        {
            return metaDataMap.get(beanClass);
        }
        else
        {
            try
            {
                BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                BeanMetaData beanMetaData = new BeanMetaData(id, beanInfo.getBeanDescriptor());
                for (MetaDataDecorator<BeanMetaData> decorator : beanMetaDataDecorators)
                {
                    decorator.decorate(beanMetaData);
                }
                final Set<Method> visitedMethods = new HashSet<Method>();
                for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors())
                {
                    visitedMethods.add(descriptor.getReadMethod());
                    visitedMethods.add(descriptor.getWriteMethod());
                    if (!"class".equals(descriptor.getName()))
                    {
                        final PropertyMetaData propertyMetaData = new PropertyMetaData(beanMetaData, descriptor);
                        beanMetaData.addPropertyMetaData(propertyMetaData);
                        for (MetaDataDecorator<PropertyMetaData> decorator : propertyMetaDataDecorators)
                        {
                            decorator.decorate(propertyMetaData);
                        }
                    }
                }
                for (MethodDescriptor descriptor : beanInfo.getMethodDescriptors())
                {
                    if (!visitedMethods.contains(descriptor.getMethod()) && !Object.class.equals(descriptor.getMethod().getDeclaringClass()))
                    {
                        final MethodMetaData methodMetaData = new MethodMetaData(beanMetaData, descriptor);
                        beanMetaData.addMethodMetaData(methodMetaData);
                        for (MetaDataDecorator<MethodMetaData> decorator : methodMetaDataDecorators)
                        {
                            decorator.decorate(methodMetaData);
                        }
                    }
                }
                metaDataMap.put(beanClass, beanMetaData);
                return beanMetaData;
            }
            catch (IntrospectionException e)
            {
                throw new RuntimeException("Unable to lookup bean information for bean class " + beanClass.getName() + ".", e);
            }
        }
    }
}
