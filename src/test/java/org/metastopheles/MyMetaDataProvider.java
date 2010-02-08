package org.metastopheles;

import org.metastopheles.annotation.AnnotationBeanMetaDataFactory;
import org.metastopheles.annotation.MetaDataProvider;
import org.scannotation.ClasspathUrlFinder;

/**
 * @author James Carman
 */
@MetaDataProvider
public class MyMetaDataProvider
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    public static AttributeKey ATTRIBUTE_KEY = new AttributeKey<String>() {};

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    public void unusedMetaDataMethod(BeanMetaData meta, UnusedAnnotation unused)
    {
        System.out.println("This should never be called!");
    }
    
    public void beanMetaDataMethod(BeanMetaData meta, CustomAnnotation custom)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

    public void methodMetaDataMethod(MethodMetaData meta, CustomAnnotation annot)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

    public void propertyMetaDataMethod(PropertyMetaData meta, CustomAnnotation custom)
    {
        meta.setAttribute(ATTRIBUTE_KEY, MyMetaDataProvider.class.getName());
    }

//**********************************************************************************************************************
// main() method
//**********************************************************************************************************************

    public static void main(String[] args) throws Exception
    {
        long before = System.currentTimeMillis();
        final AnnotationBeanMetaDataFactory factory = new AnnotationBeanMetaDataFactory().appendAnnotationBasedDecorators(ClasspathUrlFinder.findClassPaths());
        BeanMetaData beanMetaData = factory.getBeanMetaData(CustomBean.class);
        long after = System.currentTimeMillis();
        System.out.println("Initial lookup of meta data took " + ( ( after - before ) / 1000.0 ) + " seconds.");
        before = System.currentTimeMillis();
        factory.getBeanMetaData(CustomBean.class);
        after = System.currentTimeMillis();
        System.out.println("Subsequent lookup of meta data took " + ( ( after - before ) / 1000.0 ) + " seconds.");
        System.out.println("Attribute exists on class? " + (beanMetaData.getAttribute(ATTRIBUTE_KEY) != null));
        System.out.println("Attribute exists on class? " + (beanMetaData.getPropertyMetaData("name").getAttribute(ATTRIBUTE_KEY) != null));
        System.out.println("Attribute exists on class? " + (beanMetaData.getMethodMetaData(CustomBean.class.getMethod("someMethod")).getAttribute(ATTRIBUTE_KEY) != null));
    }
}
