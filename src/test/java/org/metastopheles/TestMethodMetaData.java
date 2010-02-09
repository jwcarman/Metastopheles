package org.metastopheles;

import org.metastopheles.util.CustomBean;
import org.metastopheles.util.MethodAnnotation;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import static org.testng.Assert.assertSame;

public class TestMethodMetaData extends AbstractMetaDataObjectTestCase<MethodMetaData>
{
    @Override
    protected MethodMetaData createPrototype()
    {
        return factory.getBeanMetaData(CustomBean.class).getMethodMetaData("someMethod");
    }

    @Override
    protected AnnotatedElement getExpectedAnnotationSource(MethodMetaData prototype)
    {
        return prototype.getMethodDescriptor().getMethod();
    }

    @Test
    public void testBeanMetaDataReference()
    {
        final BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        assertSame(beanMetaData.getMethodMetaData("someMethod").getBeanMetaData(), beanMetaData);
    }

    @Override
    protected Class<? extends Annotation> getExpectedAnnotationType(MethodMetaData prototype)
    {
        return MethodAnnotation.class;
    }
}
