package org.metastopheles;

/**
 * @author James Carman
 */
@CustomAnnotation
public class CustomBean
{
    private String name;

    @CustomAnnotation
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void someMethod()
    {
        
    }
}
