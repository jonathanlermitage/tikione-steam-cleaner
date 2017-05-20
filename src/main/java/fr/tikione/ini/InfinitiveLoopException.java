package fr.tikione.ini;

import java.io.Serializable;

/**
 * Indicates an infinitive loop. It can be a pair of shortcuts that calls them one another, that could initiate an infinitive loop.
 */
public class InfinitiveLoopException
        extends Exception
        implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 4052546955101609848L;

    /**
     * Constructs an instance of <code>InfinitiveLoopException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InfinitiveLoopException(String msg) {
        super(msg);
    }
}
