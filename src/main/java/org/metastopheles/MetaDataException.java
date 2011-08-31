package org.metastopheles;

/**
 * An exception class used throughout Metastopheles to indicate problems with metadata operations.
 * @since 1.0
 */
public class MetaDataException extends RuntimeException
{
//----------------------------------------------------------------------------------------------------------------------
// Constructors
//----------------------------------------------------------------------------------------------------------------------

    public MetaDataException()
    {
    }

    public MetaDataException(String message)
    {
        super(message);
    }

    public MetaDataException(Throwable cause)
    {
        super(cause);
    }

    public MetaDataException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
