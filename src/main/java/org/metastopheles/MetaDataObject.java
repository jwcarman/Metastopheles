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

package org.metastopheles;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.*;

/**
 * A MetaDataObject contains data about a particular aspect of a bean class.
 * @author James Carman
 * @since 1.0
 */
public abstract class MetaDataObject implements Serializable
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    private static final long serialVersionUID = 1L;
    private Map<FacetKey<?>,Object> facetMap = new HashMap<FacetKey<?>, Object>();

//**********************************************************************************************************************
// Abstract Methods
//**********************************************************************************************************************

    /**
     * Returns the default source for annotation data for this MetaDataObject.
     * @return the default source for annotation data for this MetaDataObject
     */
    protected abstract List<AnnotatedElement> getAnnotationSources();

//**********************************************************************************************************************
// Other Methods
//**********************************************************************************************************************

    /**
     * Retrieves a facet value associated with this MetaDataObject.
     * @param key the facet key
     * @return the facet value
     */
    @SuppressWarnings("unchecked")
    public <T> T getFacet(FacetKey<T> key)
    {
        return (T) facetMap.get(key);
    }

    /**
     * Returns the annotation associated with this MetaDataObject's default annotation source.
     *
     * @param annotationType the annotation type
     * @return the annotation
     * @see #getAnnotationSources()
     */
    public <A extends Annotation> A getAnnotation(Class<A> annotationType)
    {
        for (AnnotatedElement annotationSource : getAnnotationSources())
        {
            A annotation = annotationSource == null ? null : annotationSource.getAnnotation(annotationType);
            if(annotation != null)
            {
                return annotation;
            }
        }
        return null;
    }

    /**
     * Returns a snapshot of the keys of the facets currently associated with this MetaDataObject.
     * @return a snapshot of the keys of the facets currently associated with this MetaDataObject
     */
    public Set<FacetKey<?>> getFacetKeys()
    {
        return new HashSet<FacetKey<?>>(facetMap.keySet());
    }

    /**
     * Associates a facet with this MetaDataObject.
     * @param key the facet key
     * @param value the facet value
     */
    public <T> void setFacet(FacetKey<T> key, T value)
    {
        facetMap.put(key, value);
    }
}
