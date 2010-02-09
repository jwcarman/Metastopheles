/*
 * Copyright (c) 2010 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class ClasspathScanner
{
//**********************************************************************************************************************
// Static Methods
//**********************************************************************************************************************

    private static ClassLoader contextClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    private final ClassLoader cl;
    private final URL[] urls;

    @SuppressWarnings("unchecked")
    private MetaDataDecorator decorator(Object provider, Method method, Class<? extends Annotation> annotationType)
    {
        return (MetaDataDecorator)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{MetaDataDecorator.class}, new MetaDataDecoratorInvoker(provider, method, annotationType));
    }

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************


    @SuppressWarnings("unchecked")
    public ClasspathScanner(ClassLoader cl, URL[] urls)
    {
        this.cl = cl;
        this.urls = urls;
    }

    public void appendTo(BeanMetaDataFactory factory)
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
                            factory.getPropertyMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                        else if (BeanMetaData.class.isAssignableFrom(metaDataType))
                        {
                            factory.getBeanMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                        else if (MethodMetaData.class.isAssignableFrom(metaDataType))
                        {
                            factory.getMethodMetaDataDecorators().add(decorator(provider, method, annotationType));
                        }
                    }
                }
            }
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

    public ClasspathScanner()
    {
        this(contextClassLoader(), ClasspathUrlFinder.findClassPaths());
    }
    
    public ClasspathScanner(String... paths)
    {
        this(contextClassLoader(), ClasspathUrlFinder.findClassPaths(paths));
    }

    public ClasspathScanner(URL[] urls)
    {
        this(contextClassLoader(), urls);
    }

    public ClasspathScanner(ClassLoader classLoader)
    {
        this(classLoader, ClasspathUrlFinder.findClassPaths());
    }

    public ClasspathScanner(ClassLoader classLoader, String... paths)
    {
        this(classLoader, ClasspathUrlFinder.findClassPaths(paths));
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
