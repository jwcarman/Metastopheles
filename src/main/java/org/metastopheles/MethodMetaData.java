package org.metastopheles;

import java.beans.MethodDescriptor;
import java.lang.reflect.AnnotatedElement;

/**
 * @author James Carman
 * @since 1.0
 */
public class MethodMetaData extends MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final MethodDescriptor methodDescriptor;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public MethodMetaData(MethodDescriptor methodDescriptor)
    {
        this.methodDescriptor = methodDescriptor;
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    public MethodDescriptor getMethodDescriptor()
    {
        return methodDescriptor;
    }

    @Override
    protected AnnotatedElement getAnnotatedElement()
    {
        return methodDescriptor.getMethod();
    }
}
