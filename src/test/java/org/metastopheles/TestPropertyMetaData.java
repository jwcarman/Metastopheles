package org.metastopheles;

import org.metastopheles.util.CustomBean;
import org.metastopheles.util.PropertyAnnotation;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import static org.testng.Assert.assertSame;

/**
 * @author James Carman
 * @since 1.0
 */
public class TestPropertyMetaData extends AbstractMetaDataObjectTestCase<PropertyMetaData>
{

    @Override
    protected PropertyMetaData createPrototype()
    {
        return factory.getBeanMetaData(CustomBean.class).getPropertyMetaData("name");
    }

    @Override
    protected AnnotatedElement getExpectedAnnotationSource(PropertyMetaData prototype)
    {
        return prototype.getPropertyDescriptor().getReadMethod();
    }

    @Test
    public void testBeanMetaDataReference()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getPropertyMetaData("name").getBeanMetaData(), beanMetaData);
    }

    @Override
    protected Class<? extends Annotation> getExpectedAnnotationType(PropertyMetaData prototype)
    {
        return PropertyAnnotation.class;
    }
}
