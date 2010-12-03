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

import org.metastopheles.AttributeKey;
import org.metastopheles.BeanMetaData;
import org.metastopheles.MethodMetaData;
import org.metastopheles.PropertyMetaData;

public class InstanceDecorators
{
//**********************************************************************************************************************
// Fields
//**********************************************************************************************************************

    public static final AttributeKey<Boolean> FOUND = new AttributeKey<Boolean>() {};
    private static int instanceCount = 0;

//**********************************************************************************************************************
// Static Methods
//**********************************************************************************************************************

    @PropertyDecorator
    public void decorate(PropertyMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @BeanDecorator
    public void decorate(BeanMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    @MethodDecorator
    public void decorate(MethodMetaData metaData, FindMe annotation)
    {
        metaData.setAttribute(FOUND, true);
    }

    public static int getInstanceCount()
    {
        return instanceCount;
    }

//**********************************************************************************************************************
// Constructors
//**********************************************************************************************************************

    public InstanceDecorators()
    {
        instanceCount++;
    }
}
