/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.Model;

/** This class represents something that can be loaded.
 *  It is, therefore, associated with a Loader.
 */
public abstract class Loadable extends Model
{
    /** Loader assigned to load this object. */
    private Loader loader;

    /** Flag indicating if this object is valid -
     *  if it is not valid, it should be closed
     *  or thrown away. */
    private boolean valid;

    /** A load percentage between 1 and 100. */
    private int loadState;

    /** Constructor.
     */
    public Loadable()
    {
        loader = null;
        valid = true;
        loadState = 0;
    }

    /** Set the loader for this object. The Loader
     *  should do that himself.
     *  @param loader The Loader that is loading this object.
     */
    public void setLoader( Loader loader )
    {
        this.loader = loader;
    }

    /** Fetch the Loader that is loading this object.
     *  @return The Loader that is loading this object.
     */
    public Loader getLoader()
    {
        return loader;
    }

    /** Check if this object is valid. When it is
     *  not valid, you should close it.
     *  @return If this object is still valid.
     */
    public boolean isValid()
    {
        return valid;
    }

    /** Set the valid flag for this object.
     *  @param valid If this object is still valid.
     */
    public void setValid( boolean valid )
    {
        if( valid != this.valid ) {
            this.valid = valid;
            fireStateChanged();
        }
    }

    /** Check the load state of this object.
     *  @return A number between 1 and 100 - 100 means fully loaded.
     */
    public int getLoadState()
    {
        return loadState;
    }

    /** Set the load state of this object.
     *  @param loadState A number between 1 and 100 - 100 means fully loaded.
     */
    public void setLoadState( int loadState )
    {
        if( loadState != this.loadState ) {
            this.loadState = loadState;
            fireStateChanged();
        }
    }

    /** See if this object is completely loaded.
     *  @return {@code true} is this object is completely loaded.
     */
    public boolean isLoaded()
    {
        return loadState == 100;
    }
}
