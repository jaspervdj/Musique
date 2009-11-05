/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.collectionframe.CollectionFrame;
import java.util.EventListener;

/** A class to listen to the list of open frames.
 */
public interface FrameListListener extends EventListener
{
    /** This gets called whenever a frame is added.
     *  @param frame The added frame.
     */
    public abstract void frameAdded( CollectionFrame frame );

    /** This gets called whenever a frame is removed.
     *  @param frame The removed frame.
     */
    public abstract void frameRemoved( CollectionFrame frame );
}
