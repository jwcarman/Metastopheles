package org.metastopheles;

import org.metastopheles.util.BeanAnnotation;
import org.metastopheles.util.CustomBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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
}
