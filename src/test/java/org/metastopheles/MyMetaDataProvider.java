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

import org.metastopheles.annotation.AnnotationBeanMetaDataFactory;
import org.metastopheles.annotation.MetaDataProvider;
import org.scannotation.ClasspathUrlFinder;

/**
 * @author James Carman
 */
@MetaDataProvider
public class MyMetaDataProvider
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    public static AttributeKey ATTRIBUTE_KEY = new AttributeKey<String>() {};

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    public void unusedMetaDataMethod(BeanMetaData meta, UnusedAnnotation unused)
    {
        System.out.println("This should never be called!");
    }
    
    public void beanMetaDataMethod(BeanMetaData meta, CustomAnnotation custom)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

    public void methodMetaDataMethod(MethodMetaData meta, CustomAnnotation annot)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

    public void propertyMetaDataMethod(PropertyMetaData meta, CustomAnnotation custom)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

//**********************************************************************************************************************
// main() method
//**********************************************************************************************************************

    public static void main(String[] args) throws Exception
    {
        long before = System.currentTimeMillis();
        final AnnotationBeanMetaDataFactory factory = new AnnotationBeanMetaDataFactory().appendAnnotationBasedDecorators(ClasspathUrlFinder.findClassPaths());
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        long after = System.currentTimeMillis();
        System.out.println("Initial lookup of meta data took " + ( ( after - before ) / 1000.0 ) + " seconds.");
        before = System.currentTimeMillis();
        factory.getBeanMetaData(CustomBean.class);
        after = System.currentTimeMillis();
        System.out.println("Subsequent lookup of meta data took " + ( ( after - before ) / 1000.0 ) + " seconds.");
        System.out.println("Attribute exists on class? " + (beanMetaData.getAttribute(ATTRIBUTE_KEY) != null));
        System.out.println("Attribute exists on class? " + (beanMetaData.getPropertyMetaData("name").getAttribute(ATTRIBUTE_KEY) != null));
        System.out.println("Attribute exists on class? " + (beanMetaData.getMethodMetaData(CustomBean.class.getMethod("someMethod")).getAttribute(ATTRIBUTE_KEY) != null));
    }
}
