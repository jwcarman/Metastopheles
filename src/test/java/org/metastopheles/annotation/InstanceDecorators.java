package org.metastopheles.annotation;

import org.metastopheles.AttributeKey;
import org.metastopheles.BeanMetaData;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;

public class InstanceDecorators
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
    public void decorate(PropertyMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @BeanDecorator
    public void decorate(BeanMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @MethodDecorator
    public void decorate(MethodMetaData metaData, FindMe annotation)
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

    public InstanceDecorators()
    {
        instanceCount++;
    }
}
