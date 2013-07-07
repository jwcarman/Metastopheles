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
import org.metastopheles.util.MethodAnnotation;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertSame;

public class TestMethodMetaData extends AbstractMetaDataObjectTestCase<MethodMetaData>
{
//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Override
    protected MethodMetaData createPrototype()
    {
        return factory.getBeanMetaData(CustomBean.class).getMethodMetaData("someMethod");
    }

    @Override
    protected List<AnnotatedElement> getExpectedAnnotationSources(MethodMetaData prototype)
    {
        return Arrays.<AnnotatedElement>asList(prototype.getMethodDescriptor().getMethod());
    }

    @Override
    protected Class<? extends Annotation> getExpectedAnnotationType(MethodMetaData prototype)
    {
        return MethodAnnotation.class;
    }

    @Test
    public void testBeanMetaDataReference()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getMethodMetaData("someMethod").getBeanMetaData(), beanMetaData);
    }
}
