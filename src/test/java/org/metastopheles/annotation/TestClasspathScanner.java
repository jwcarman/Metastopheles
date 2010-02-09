package org.metastopheles.annotation;

import org.metastopheles.BeanMetaData;
import org.metastopheles.BeanMetaDataFactory;
import org.metastopheles.util.CustomBean;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author James Carman
 */
public class TestClasspathScanner
{
    @Test
    public void testDecoratorMethodsPickedUp()
    {
        BeanMetaDataFactory factory = new BeanMetaDataFactory();
        ClasspathScanner scanner = new ClasspathScanner();
        scanner.appendTo(factory);
        BeanMetaData metaData = factory.getBeanMetaData(CustomBean.class);
        assertTrue(CustomMetaDataProvider.isAttributePresent(metaData));
        assertTrue(CustomMetaDataProvider.isAttributePresent(metaData.getPropertyMetaData("name")));
        assertTrue(CustomMetaDataProvider.isAttributePresent(metaData.getMethodMetaData("someMethod")));
    }

}
