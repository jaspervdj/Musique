/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.collectionframe.CollectionFrame;
import musique.collectiondata.Collection;
import musique.loaders.CollectionLoader;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/** An action used to open a new CollectionFrame
 *  about an artist or label.
 */
public abstract class OpenAction extends AbstractAction
{
    private MusiqueFrame musiqueFrame;

    /** Constructor.
     *  @param musiqueFrame The main musique frame.
     *  @param title Title for this action.
     */
    public OpenAction( MusiqueFrame musiqueFrame, String title )
    {
        super( title );
        this.musiqueFrame = musiqueFrame;
    }

    /** Get the main musique frame.
     *  @return The main Musique frame.
     */
    public MusiqueFrame getMusiqueFrame()
    {
        return musiqueFrame;
    }

    /** Called when the action is used. Open up a collection window.
     *  @param event Data about the event.
     */
    public void actionPerformed( ActionEvent event )
    {
        Collection collection = new Collection();
        CollectionLoader loader = createCollectionLoader( collection );

        if( loader != null ) {
            CollectionFrame collectionFrame = createCollectionFrame( musiqueFrame, collection );
            musiqueFrame.getFrameListModel().addFrame( collectionFrame );
            loader.loadInBackground();
        }
    }

    /** Get a loader for the collection.
     *  @param collection Collection to create the loader for.
     *  @return A fitting CollectionLoader.
     */
    public abstract CollectionLoader createCollectionLoader( Collection collection );

    /** Get a frame for the collection. This can be overwritten if you
     *  want a different kind of frame.
     *  @param musiqueFrame The main musique window.
     *  @param collection Collection to create a frame for.
     */
    public CollectionFrame createCollectionFrame( MusiqueFrame musiqueFrame, Collection collection )
    {
        return new CollectionFrame( musiqueFrame, collection );
    }
}
