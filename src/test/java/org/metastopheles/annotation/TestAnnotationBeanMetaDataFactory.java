package org.metastopheles.annotation;

import org.metastopheles.BeanMetaData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestAnnotationBeanMetaDataFactory
{
    private AnnotationBeanMetaDataFactory factory;
//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Test
    public void testInstanceDecorators()
    {
        BeanMetaData meta = factory.getBeanMetaData(FindMeBean.class);
        assertTrue(meta.getAttribute(InstanceDecorators.FOUND));
        assertTrue(meta.getPropertyMetaData("name").getAttribute(InstanceDecorators.FOUND));
        assertTrue(meta.getMethodMetaData("someMethod").getAttribute(InstanceDecorators.FOUND));
        assertEquals(InstanceDecorators.getInstanceCount(), 1);
    }

    @Test
    public void testStaticDecorators()
    {
        BeanMetaData meta = factory.getBeanMetaData(FindMeBean.class);
        assertTrue(meta.getAttribute(StaticDecorators.FOUND));
        assertTrue(meta.getPropertyMetaData("name").getAttribute(StaticDecorators.FOUND));
        assertTrue(meta.getMethodMetaData("someMethod").getAttribute(StaticDecorators.FOUND));
        assertEquals(StaticDecorators.getInstanceCount(), 0);
    }


    @BeforeClass
    protected void setUp() throws Exception
    {
        factory = new AnnotationBeanMetaDataFactory();
    }
}
