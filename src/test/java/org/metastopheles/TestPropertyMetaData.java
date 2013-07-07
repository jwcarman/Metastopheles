/*
 * Copyright (c) 2010 Carman Consulting, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metastopheles;

import org.metastopheles.util.CustomBean;
import org.metastopheles.util.PropertyAnnotation;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author James Carman
 * @since 1.0
 */
public class TestPropertyMetaData extends AbstractMetaDataObjectTestCase<PropertyMetaData>
{
//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Override
    protected PropertyMetaData createPrototype()
    {
        return factory.getBeanMetaData(CustomBean.class).getPropertyMetaData("name");
    }

    @Override
    protected List<AnnotatedElement> getExpectedAnnotationSources(PropertyMetaData prototype)
    {
        Field field = null;
        try
        {
            field = prototype.getBeanMetaData().getBeanDescriptor().getBeanClass().getDeclaredField(prototype.getPropertyDescriptor().getName());
        }
        catch (NoSuchFieldException e)
        {
            //Ignore...
        }
        return Arrays.<AnnotatedElement>asList(prototype.getPropertyDescriptor().getReadMethod(), field);
    }

    @Override
    protected Class<? extends Annotation> getExpectedAnnotationType(PropertyMetaData prototype)
    {
        return PropertyAnnotation.class;
    }

    @Test
    public void testBeanMetaDataReference()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getPropertyMetaData("name").getBeanMetaData(), beanMetaData);
    }

    @Test
    public void testWithFieldAnnotation()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertNotNull(beanMetaData.getPropertyMetaData("annotatedField").getAnnotation(PropertyAnnotation.class));
    }

    @Test
    public void testWithNoAnnotation()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertNull(beanMetaData.getPropertyMetaData("mismatchedField").getAnnotation(PropertyAnnotation.class));
    }

}
