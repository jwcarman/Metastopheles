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
import org.testng.annotations.Test;

import java.io.Serializable;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

public class TestBeanMetaDataFactory
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private static final FacetKey<String> FACET_KEY = new FacetKey<String>() {};
    private static final String FACET_VALUE = "FACET_VALUE";

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Test
    public void testBeanMetaData()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getBeanDescriptor().getBeanClass(), CustomBean.class);
    }

    @Test
    public void testInvalidBeanType()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        factory.getBeanMetaData(Serializable.class);
    }

    @Test
    public void testMetaDataCached()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        assertSame(factory.getBeanMetaData(CustomBean.class), factory.getBeanMetaData(CustomBean.class));  
    }

    @Test
    public void testMethodMetaData()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertNotNull(beanMetaData.getMethodMetaData("someMethod"));
        assertNull(beanMetaData.getMethodMetaData("hashCode"));
        assertNull(beanMetaData.getMethodMetaData("equals", Object.class));
        try
        {
            beanMetaData.getMethodMetaData("nonExistentMethod");
            fail("Non existent methods should throw IllegalArgumentException");
        }
        catch(IllegalArgumentException e)
        {
            // Do nothing, expected result.
        }
    }

    @Test
    public void testWithBeanDecorator()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        factory.getBeanMetaDataDecorators().add(new MetaDataDecorator<BeanMetaData>()
        {
            public void decorate(BeanMetaData metaData)
            {
                metaData.setFacet(FACET_KEY, FACET_VALUE);
            }
        });
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getFacet(FACET_KEY), FACET_VALUE);
    }

    @Test
    public void testWithMethodDecorator()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        factory.getMethodMetaDataDecorators().add(new MetaDataDecorator<MethodMetaData>()
        {
            public void decorate(MethodMetaData metaData)
            {
                metaData.setFacet(FACET_KEY, FACET_VALUE);
            }
        });
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        MethodMetaData propertyMetaData = beanMetaData.getMethodMetaData("someMethod");
        assertSame(propertyMetaData.getFacet(FACET_KEY), FACET_VALUE);
    }
    
    @Test
    public void testWithPropertyDecorator()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        factory.getPropertyMetaDataDecorators().add(new MetaDataDecorator<PropertyMetaData>()
        {
            public void decorate(PropertyMetaData metaData)
            {
                metaData.setFacet(FACET_KEY, FACET_VALUE);
            }
        });
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        PropertyMetaData propertyMetaData = beanMetaData.getPropertyMetaData("name");
        assertSame(propertyMetaData.getFacet(FACET_KEY), FACET_VALUE);
    }

    @Test
    public void testClear()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        BeanMetaData original = factory.getBeanMetaData(CustomBean.class);
        factory.clear();
        BeanMetaData subsequent = factory.getBeanMetaData(CustomBean.class);
        assertNotSame(original, subsequent);
    }

//**********************************************************************************************************************
// Inner Classes
//**********************************************************************************************************************

    private abstract class InvisibleInnerClass
    {
    }
}
