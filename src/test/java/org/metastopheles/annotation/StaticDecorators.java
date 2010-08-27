package org.metastopheles.annotation;

import org.metastopheles.AttributeKey;
import org.metastopheles.BeanMetaData;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticDecorators
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************
 
    public static final AttributeKey<Boolean> FOUND = new AttributeKey<Boolean>() {};
    private static int instanceCount = 0;

//**********************************************************************************************************************
// Static Methods
//**********************************************************************************************************************

    @PropertyDecorator
    public static void decorate(PropertyMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @BeanDecorator
    public static void decorate(BeanMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @MethodDecorator
    public static void decorate(MethodMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    public static int getInstanceCount()
    {
        return instanceCount;
    }

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public StaticDecorators()
    {
        instanceCount++;
    }
}
