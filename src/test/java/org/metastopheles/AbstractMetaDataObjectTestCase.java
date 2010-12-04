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

import org.metastopheles.util.UnusedAnnotation;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * @author James Carman
 * @since 1.0
 */
public abstract class AbstractMetaDataObjectTestCase<T extends MetaDataObject>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private static final FacetKey<String> FACET_KEY = new FacetKey<String>() {};
    private static final String FACET_VALUE = "FACET_VALUE";
    protected BeanMetaDataFactory factory;

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    protected abstract T createPrototype();
    protected abstract AnnotatedElement getExpectedAnnotationSource(T prototype);
    protected abstract Class<? extends Annotation> getExpectedAnnotationType(T prototype);

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @BeforeClass
    public void setUp()
    {
        factory = new BeanMetaDataFactory();
    }

    @SuppressWarnings("unchecked")
    private T serializedCopy(T prototype) throws IOException, ClassNotFoundException
    {
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(prototype);
        oout.close();
        bout.close();
        final ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        final ObjectInputStream oin = new ObjectInputStream(bin);
        T copy = (T)oin.readObject();
        oin.close();
        bin.close();
        return copy;
    }

    @Test
    public void testAnnotations()
    {
        T prototype = createPrototype();
        Class<? extends Annotation> annotationType = getExpectedAnnotationType(prototype);
        assertNotNull(prototype.getAnnotation(annotationType));
        assertNull(prototype.getAnnotation(UnusedAnnotation.class));
    }
    
    @Test
    public void testFacets()
    {
        T prototype = createPrototype();
        assertEquals(prototype.getFacetKeys().size(), 0);
        assertNull(prototype.getFacet(FACET_KEY));
        prototype.setFacet(FACET_KEY, FACET_VALUE);
        assertEquals(prototype.getFacetKeys().size(), 1 );
        assertSame(prototype.getFacetKeys().iterator().next(), FACET_KEY);
        assertSame(prototype.getFacet(FACET_KEY), FACET_VALUE);
    }

    @Test
    public void testDefaultAnnotationSource()
    {
        T prototype = createPrototype();
        assertEquals(prototype.getDefaultAnnotationSource(), getExpectedAnnotationSource(prototype));
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException
    {
        T prototype = createPrototype();
        T copy = serializedCopy(prototype);
        assertSame(copy, prototype);
    }
}
