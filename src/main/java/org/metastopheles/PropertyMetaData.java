package org.metastopheles;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AnnotatedElement;

/**
 * @author James Carman
 * @since 1.0
 */
public class PropertyMetaData extends MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private PropertyDescriptor propertyDescriptor;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public PropertyMetaData(PropertyDescriptor propertyDescriptor)
    {
        this.propertyDescriptor = propertyDescriptor;
    }

//**********************************************************************************************************************
// Getter/Setter Methods
//**********************************************************************************************************************

    public PropertyDescriptor getPropertyDescriptor()
    {
        return propertyDescriptor;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @Override
    protected AnnotatedElement getAnnotatedElement()
    {
        return propertyDescriptor.getReadMethod();
    }
}
