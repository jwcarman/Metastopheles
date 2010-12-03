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

package org.metastopheles.util;

import org.metastopheles.util.BeanAnnotation;
import org.metastopheles.util.MethodAnnotation;
import org.metastopheles.util.PropertyAnnotation;

/**
 * @author James Carman
 */
@BeanAnnotation
public class CustomBean
{
    private String name;

    @PropertyAnnotation
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @MethodAnnotation
    public void someMethod()
    {
        
    }
}
