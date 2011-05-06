package org.metastopheles.annotation;

import org.metastopheles.FacetKey;
import org.metastopheles.PropertyMetaData;

public class AugmentedConcreteDecorators extends AbstractDecorators
{
    public static final FacetKey<Boolean> FOUND = new FacetKey<Boolean>() {};

    public AugmentedConcreteDecorators()
    {
        super(FOUND);
    }

    @PropertyDecorator
    public void onProperty(PropertyMetaData propertyMetaData, FindMe findMe)
    {
        // Do nothin!
    }
}
