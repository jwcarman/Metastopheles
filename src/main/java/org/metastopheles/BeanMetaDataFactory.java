package org.metastopheles;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

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

    protected BeanMetaData createBeanMetaData(BeanDescriptor beanDescriptor)
    {
        return new BeanMetaData(beanDescriptor);
    }

    protected MethodMetaData createMethodMetaData(MethodDescriptor methodDescriptor)
    {
        return new MethodMetaData(methodDescriptor);
    }

    protected PropertyMetaData createPropertyMetaData(PropertyDescriptor propertyDescriptor)
    {
        return new PropertyMetaData(propertyDescriptor);
    }

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
                BeanMetaData beanMetaData = createBeanMetaData(beanInfo.getBeanDescriptor());
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
                        final PropertyMetaData propertyMetaData = createPropertyMetaData(descriptor);
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
                        final MethodMetaData methodMetaData = createMethodMetaData(descriptor);
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
