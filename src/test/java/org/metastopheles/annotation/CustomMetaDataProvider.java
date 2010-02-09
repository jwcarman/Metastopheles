package org.metastopheles.annotation;

import org.metastopheles.AttributeKey;
import org.metastopheles.BeanMetaData;
import org.metastopheles.MetaDataObject;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;
import org.metastopheles.util.BeanAnnotation;
import org.metastopheles.util.MethodAnnotation;
import org.metastopheles.util.PropertyAnnotation;
import org.metastopheles.util.UnusedAnnotation;

/**
 * @author James Carman
 */
@MetaDataProvider
public class CustomMetaDataProvider
{
    private static AttributeKey<String> ATTRIBUTE_KEY = new AttributeKey<String>() {};
    private static final String ATTRIBUTE_VALUE = CustomMetaDataProvider.class.getSimpleName() + ".ATTRIBUTE_VALUE";

    public void onMethodAnnotation(MethodMetaData meta, MethodAnnotation annotation)
    {
        meta.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
    }

    public void onPropertyAnnotation(PropertyMetaData meta, PropertyAnnotation annotation)
    {
        meta.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
    }

    public void onBeanAnnotation(BeanMetaData meta, BeanAnnotation annotation)
    {
        meta.setAttribute(ATTRIBUTE_KEY, ATTRIBUTE_VALUE);
    }

    public void onUnusedAnnotation(BeanMetaData meta, UnusedAnnotation annotation)
    {

    }
    
    public static boolean isAttributePresent(MetaDataObject object)
    {
        return ATTRIBUTE_VALUE.equals(object.getAttribute(ATTRIBUTE_KEY));
    }
}
