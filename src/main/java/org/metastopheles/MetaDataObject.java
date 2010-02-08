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

package org.metastopheles;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author James Carman
 * @since 1.0
 */
public abstract class MetaDataObject
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private Map<AttributeKey,Object> attributeMap = new HashMap<AttributeKey, Object>();

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    protected abstract AnnotatedElement getAnnotatedElement();

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(AttributeKey<T> key)
    {
        return (T) attributeMap.get(key);
    }
    
    public <T extends Annotation> T getAnnotation(Class<T> annotationType)
    {
        return getAnnotatedElement().getAnnotation(annotationType);
    }
    
    public <T> void setAttribute(AttributeKey<T> key, T value)
    {
        attributeMap.put(key, value);
    }
}
