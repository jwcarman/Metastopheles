package org.metastopheles;

/**
 * @author James Carman
 * @since 1.0
 */
public interface MetaDataDecorator<T extends MetaDataObject>
{
    public void decorate(T metaData);
}
