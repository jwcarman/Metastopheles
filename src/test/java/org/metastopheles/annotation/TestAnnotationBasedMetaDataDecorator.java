package org.metastopheles.annotation;

import org.metastopheles.AttributeKey;
import org.metastopheles.BeanMetaData;
import org.metastopheles.BeanMetaDataFactory;
import org.metastopheles.MetaDataObject;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import static org.testng.Assert.*;

public class TestAnnotationBasedMetaDataDecorator
{
    public static final AttributeKey<Boolean> FOUND = new AttributeKey<Boolean>() {};

    @Test
    public void testAnnotationsFound()
    {
        final BeanMetaDataFactory factory = new BeanMetaDataFactory();
        factory.getBeanMetaDataDecorators().add(new FindMeDecorator<BeanMetaData>());
        factory.getMethodMetaDataDecorators().add(new FindMeDecorator<MethodMetaData>());
        factory.getPropertyMetaDataDecorators().add(new FindMeDecorator<PropertyMetaData>());
        BeanMetaData meta = factory.getBeanMetaData(FindMeBean.class);
        assertTrue(meta.getAttribute(FOUND));
        assertTrue(meta.getPropertyMetaData("name").getAttribute(FOUND));
        assertTrue(meta.getMethodMetaData("someMethod").getAttribute(FOUND));
    }

    private static class FindMeDecorator<T extends MetaDataObject> extends AnnotationBasedMetaDataDecorator<T,FindMe>
    {
        private FindMeDecorator()
        {
            super(FindMe.class);
        }

        @Override
        protected void decorate(T metaData, Annotation annotation)
        {
            metaData.setAttribute(FOUND, true);
        }
    }
}
