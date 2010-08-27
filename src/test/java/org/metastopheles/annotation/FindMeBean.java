package org.metastopheles.annotation;

@FindMe
public class FindMeBean
{
    private String name;

    @FindMe
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @FindMe
    public void someMethod()
    {

    }
}
