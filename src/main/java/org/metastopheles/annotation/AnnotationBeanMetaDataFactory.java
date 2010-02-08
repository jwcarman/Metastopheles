package org.metastopheles.annotation;

import org.metastopheles.BeanMetaData;
import org.metastopheles.BeanMetaDataFactory;
import org.metastopheles.MetaDataDecorator;
import org.metastopheles.MetaDataObject;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

/**
 * @author James Carman
 * @since 1.0
 */
public class AnnotationBeanMetaDataFactory extends BeanMetaDataFactory
{
//**********************************************************************************************************************
// Static Methods
//**********************************************************************************************************************

    private ClassLoader contextClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    @SuppressWarnings("unchecked")
    private MetaDataDecorator decorator(Object provider, Method method, Class<? extends Annotation> annotationType)
    {
        return (MetaDataDecorator)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{MetaDataDecorator.class}, new MetaDataDecoratorInvoker(provider, method, annotationType));
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @SuppressWarnings("unchecked")
    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators(ClassLoader cl, URL[] urls)
    {
        AnnotationDB annotationDB = new AnnotationDB();
        annotationDB.setScanClassAnnotations(true);
        annotationDB.setScanMethodAnnotations(false);
        annotationDB.setScanFieldAnnotations(false);
        annotationDB.setScanParameterAnnotations(false);
        try
        {
            annotationDB.scanArchives(urls);
            for (String className : annotationDB.getAnnotationIndex().get(MetaDataProvider.class.getName()))
            {
                Class c = cl.loadClass(className);
                final Object provider = c.newInstance();
                for (Method method : c.getDeclaredMethods())
                {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 2 && Annotation.class.isAssignableFrom(parameterTypes[1]))
                    {
                        final Class<?> metaDataType = parameterTypes[0];
                        Class<? extends Annotation> annotationType = (Class<? extends Annotation>) parameterTypes[1];
                        if (PropertyMetaData.class.isAssignableFrom(metaDataType))
                        {
                            getPropertyMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                        else if (BeanMetaData.class.isAssignableFrom(metaDataType))
                        {
                            getBeanMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                        else if (MethodMetaData.class.isAssignableFrom(metaDataType))
                        {
                            getMethodMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                    }
                }
            }
            return this;
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Unable to load class.", e);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to scan classpaths.", e);
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException("Unable to instantiate metadata provider.", e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Unable to instantiate metadata provider.", e);
        }
    }

    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators()
    {
        return appendAnnotationBasedDecorators(contextClassLoader(), ClasspathUrlFinder.findClassPaths());
    }
    
    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators(String... paths)
    {
        return appendAnnotationBasedDecorators(contextClassLoader(), ClasspathUrlFinder.findClassPaths(paths));
    }

    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators(URL[] urls)
    {
        return appendAnnotationBasedDecorators(contextClassLoader(), urls);
    }

    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators(ClassLoader classLoader)
    {
        return appendAnnotationBasedDecorators(classLoader, ClasspathUrlFinder.findClassPaths());
    }

    public AnnotationBeanMetaDataFactory appendAnnotationBasedDecorators(ClassLoader classLoader, String... paths)
    {
        return appendAnnotationBasedDecorators(classLoader, ClasspathUrlFinder.findClassPaths(paths));
    }

//**********************************************************************************************************************
// Inner Classes
//**********************************************************************************************************************

    private static class MetaDataDecoratorInvoker implements InvocationHandler
    {
        private final Object provider;
        private final Method providerMethod;
        private final Class<? extends Annotation> annotationType;

        private MetaDataDecoratorInvoker(Object provider, Method providerMethod, Class<? extends Annotation> annotationType)
        {
            this.providerMethod = providerMethod;
            this.provider = provider;
            this.annotationType = annotationType;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            MetaDataObject meta = (MetaDataObject) args[0];
            Annotation annotation = meta.getAnnotation(annotationType);
            if (annotation != null)
            {
                return providerMethod.invoke(provider, args[0], annotation);
            }
            return null;
        }
    }
}
