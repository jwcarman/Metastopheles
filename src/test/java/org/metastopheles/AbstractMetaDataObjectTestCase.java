package org.metastopheles;

import org.metastopheles.util.UnusedAnnotation;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
    private static final AttributeKey<String> ATTRIBUTE_KEY = new AttributeKey<String>() {};
    private static final String ATTRIBUTE_VALUE = "ATTRIBUTE_VALUE";
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
    
    @Test
    public void testAttributes()
    {
        T prototype = createPrototype();
        assertEquals(prototype.getAttributeKeys().size(), 0);
        assertNull(prototype.getAttribute(ATTRIBUTE_KEY));
        prototype.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
        assertEquals(prototype.getAttributeKeys().size(), 1 );
        assertSame(prototype.getAttributeKeys().iterator().next(), ATTRIBUTE_KEY);
        assertSame(prototype.getAttribute(ATTRIBUTE_KEY), ATTRIBUTE_VALUE);
    }

    @Test
    public void testDefaultAnnotationSource()
    {
        T prototype = createPrototype();
        assertEquals(prototype.getDefaultAnnotationSource(), getExpectedAnnotationSource(prototype));
    }

    @Test
    public void testAnnotations()
    {
        T prototype = createPrototype();
        Class<? extends Annotation> annotationType = getExpectedAnnotationType(prototype);
        assertNotNull(prototype.getAnnotation(annotationType));
        assertNull(prototype.getAnnotation(UnusedAnnotation.class));
    }
}
