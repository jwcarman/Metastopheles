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

import java.beans.PropertyDescriptor;
import java.lang.reflect.AnnotatedElement;

/**
 * @author James Carman
 * @since 1.0
 */
public class PropertyMetaData extends MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private PropertyDescriptor propertyDescriptor;
    private final BeanMetaData beanMetaData;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public PropertyMetaData(BeanMetaData beanMetaData, PropertyDescriptor propertyDescriptor)
    {
        this.beanMetaData = beanMetaData;
        this.propertyDescriptor = propertyDescriptor;
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public BeanMetaData getBeanMetaData()
    {
        return beanMetaData;
    }

    /**
     * Returns the property descriptor
     * @return the property descriptor
     */
    public PropertyDescriptor getPropertyDescriptor()
    {
        return propertyDescriptor;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    /**
     * The default annotation source for a property is its getter method.
     * @return the property's getter method
     */
    @Override
    protected AnnotatedElement getDefaultAnnotationSource()
    {
        return propertyDescriptor.getReadMethod();
    }
}
