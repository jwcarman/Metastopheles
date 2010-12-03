/*
 * Copyright (c) 2010 Carman Consulting, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metastopheles.annotation;

import org.metastopheles.MetaDataDecorator;
import org.metastopheles.MetaDataObject;

import java.lang.annotation.Annotation;

/**
 * An annotation-based metadata decorator.  Subclasses merely need to override the #{decorate} method.
 * @since 1.0
 * @author James Carman
 */
public abstract class AnnotationBasedMetaDataDecorator<T extends MetaDataObject, A extends Annotation> implements MetaDataDecorator<T>
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private final Class<A> annotationType;

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public AnnotationBasedMetaDataDecorator(Class<A> annotationType)
    {
        this.annotationType = annotationType;
    }

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    protected abstract void decorate(T metaData, Annotation annotation);

//**********************************************************************************************************************
// MetaDataDecorator Implementation
//**********************************************************************************************************************

    public void decorate(T metaData)
    {
        A annotation = metaData.getAnnotation(annotationType);
        if (annotation != null)
        {
            decorate(metaData, annotation);
        }
    }
}
