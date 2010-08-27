package org.metastopheles.annotation;

import org.metastopheles.BeanMetaData;
import org.metastopheles.BeanMetaDataFactory;
import org.metastopheles.MetaDataDecorator;
import org.metastopheles.MetaDataObject;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AnnotationBeanMetaDataFactory extends BeanMetaDataFactory
{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AnnotationBeanMetaDataFactory(Class baseClass)
    {
        this(ClasspathUrlFinder.findClassBase(baseClass));
    }
    
    public AnnotationBeanMetaDataFactory()
    {
        this(ClasspathUrlFinder.findClassPaths());
    }

    public AnnotationBeanMetaDataFactory(URL... urls)
    {
        AnnotationDB db = new AnnotationDB();
        try
        {
            db.setScanClassAnnotations(false);
            db.setScanMethodAnnotations(true);
            db.setScanFieldAnnotations(false);
            db.setScanParameterAnnotations(false);
            db.scanArchives(urls);
            final Map<String, Set<String>> index = db.getAnnotationIndex();
            Map<String, Object> targets = new TreeMap<String, Object>();
            scanForMetaDataMethods(index, targets, BeanMetaData.class, BeanDecorator.class, getBeanMetaDataDecorators());
            scanForMetaDataMethods(index, targets, PropertyMetaData.class, PropertyDecorator.class, getPropertyMetaDataDecorators());
            scanForMetaDataMethods(index, targets, MethodMetaData.class, MethodDecorator.class, getMethodMetaDataDecorators());

        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to scan classpath for annotations.", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends MetaDataObject> void scanForMetaDataMethods(Map<String, Set<String>> index, Map<String,Object> targets, Class<T> metaDataType, Class<? extends Annotation> markerAnnotationType, List<MetaDataDecorator<T>> decorators)
    {
        final Set<String> classes = index.get(markerAnnotationType.getName());
        if (classes == null || classes.isEmpty())
        {
            return;
        }

        for (String className : classes)
        {
            try
            {
                Class<?> c = Class.forName(className);
                for (Method method : c.getMethods())
                {
                    if (method.isAnnotationPresent(markerAnnotationType))
                    {
                        Class[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length == 2 && metaDataType.equals(parameterTypes[0]) && parameterTypes[1].isAnnotation())
                        {
                            Class<? extends Annotation> annotationType = (Class<? extends Annotation>) parameterTypes[1];
                            logger.debug("Adding decorator for method " + method);
                            if (Modifier.isStatic(method.getModifiers()))
                            {
                                decorators.add(new MethodBasedDecorator(annotationType, null, method));
                            }
                            else
                            {
                                Object target = targets.get(c.getName());
                                if (target == null)
                                {
                                    logger.info("Instantiating " + c.getName() + " instance to handle decorator methods found...");
                                    target = c.newInstance();
                                    targets.put(c.getName(), target);
                                }

                                decorators.add(new MethodBasedDecorator(annotationType, target, method));
                            }


                        }
                    }
                }
            }
            catch (ClassNotFoundException e)
            {
                logger.error("Unable to load class " + className + ", skipping annotations.", e);
            }
            catch (InstantiationException e)
            {
                logger.error("Unable to instantiate object of type " + className + ".", e);
            }
            catch (IllegalAccessException e)
            {
                logger.error("Unable to instantiate object of type " + className + ".", e);
            }
        }

    }

    private static class MethodBasedDecorator<T extends MetaDataObject, A extends Annotation> extends AnnotationBasedMetaDataDecorator<T, A>
    {
        private final Object target;
        private final Method method;

        private MethodBasedDecorator(Class<A> annotationType, Object target, Method method)
        {
            super(annotationType);
            this.target = target;
            this.method = method;
        }

        @Override
        protected void decorate(T metaData, Annotation annotation)
        {
            try
            {
                method.invoke(target, metaData, annotation);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException("Unable to access decorator method " + method + ".", e);
            }
            catch (InvocationTargetException e)
            {
                throw new RuntimeException("Decorator method " + method + " threw an exception.", e.getTargetException());
            }

        }
    }
}
