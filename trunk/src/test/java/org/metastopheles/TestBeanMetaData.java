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

import org.metastopheles.util.BeanAnnotation;
import org.metastopheles.util.CustomBean;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;
import static org.testng.Assert.*;

/**
 * @author James Carman
 * @since 1.0
 */
public class TestBeanMetaData extends AbstractMetaDataObjectTestCase<BeanMetaData>
{
//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Override
    protected BeanMetaData createPrototype()
    {
        return factory.getBeanMetaData(CustomBean.class);
    }

    @Override
    protected AnnotatedElement getExpectedAnnotationSource(BeanMetaData prototype)
    {
        return prototype.getBeanDescriptor().getBeanClass();
    }

    @Override
    protected Class<? extends Annotation> getExpectedAnnotationType(BeanMetaData prototype)
    {
        return BeanAnnotation.class;
    }

    @Test
    public void testGetPropertyNames()
    {
        BeanMetaData metaData = factory.getBeanMetaData(CustomBean.class);
        Set<String> propertyNames = metaData.getPropertyNames();
        assertEquals(propertyNames.size(), 1);
        assertTrue(propertyNames.contains("name"));
        propertyNames.clear();
        Set<String> otherPropertyNames = metaData.getPropertyNames();
        assertNotSame(propertyNames, otherPropertyNames);
        assertEquals(otherPropertyNames.size(), 1);
        assertTrue(otherPropertyNames.contains("name"));
    }
}
