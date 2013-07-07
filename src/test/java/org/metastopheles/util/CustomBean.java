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

/**
 * @author James Carman
 */
@BeanAnnotation
public class CustomBean
{
//----------------------------------------------------------------------------------------------------------------------
// Fields
//----------------------------------------------------------------------------------------------------------------------

    private String name;

    @PropertyAnnotation
    private String annotatedField;

    private String otherName;

//----------------------------------------------------------------------------------------------------------------------
// Getter/Setter Methods
//----------------------------------------------------------------------------------------------------------------------

    public String getAnnotatedField()
    {
        return annotatedField;
    }

    public void setAnnotatedField(String annotatedField)
    {
        this.annotatedField = annotatedField;
    }

    @PropertyAnnotation
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

//----------------------------------------------------------------------------------------------------------------------
// Other Methods
//----------------------------------------------------------------------------------------------------------------------

    public String getMismatchedField()
    {
        return otherName;
    }

    public void setMismatchedField(String mismatchedField)
    {
        this.otherName = mismatchedField;
    }

    @MethodAnnotation
    public void someMethod()
    {
        
    }
}
