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
