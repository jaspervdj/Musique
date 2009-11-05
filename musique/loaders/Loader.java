/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.Model;

/** An abstract class used to load data. The sort
 *  of data is determined by which subclass you
 *  are using. Every Loader contains two important
 *  functions: loadInBackground and loadInForeground().
 *  They have the same result but are non-blocking
 *  and blocking, repectively.
 */
public abstract class Loader extends Model implements Runnable
{
    /** If there was a request to cancel the loading. */
    private boolean cancelRequest;

    /** Constructor.
     */
    public Loader()
    {
        cancelRequest = false;
    }

    /** This will load the data in the background
     *  by starting a new Thread behind the scenes.
     *  Therefore, this is non-blocking call.
     */
    public void loadInBackground()
    {
        Thread thread = new Thread( this, "Musique Loader Thread" );
        thread.start();
    }

    /** This will load the data in the foreground,
     *  aka the current Thread. That's why this is
     *  a blocking call.
     */
    public void loadInForeground()
    {
        if( !isDone() ) {
            load();
            fireStateChanged();
        }
    }

    @Override
    public void run()
    {
        loadInForeground();
    }

    /** An internally used function. Use loadInBackground()
     *  or loadInForeground() instead.
     */
    public abstract void load();

    /** See if the subject is already completely loaded.
     *  @return {@code true} if we're done loading.
     */
    public abstract boolean isDone();

    /** Request to cancel this loader. This should usually work,
     *  but it might not stop the loader instantly.
     */
    public void requestCancel()
    {
        cancelRequest = true;
    }

    /** Check if this loader has received a cancel request.
     *  @return If this loader has received a cancel request.
     */
    public boolean hasCancelRequest()
    {
        return cancelRequest;
    }
}
