/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.resources.I18nManager;
import musique.collectionframe.CollectionFrame;
import musique.collectiondata.Collection;
import musique.collectionframe.CustomCollectionFrame;
import musique.loaders.CollectionLoader;
import musique.loaders.NewCollectionLoader;
import javax.swing.KeyStroke;

/** An action used to open a new CollectionFrame
 *  about an custom collection.
 */
public class OpenNewAction extends OpenAction
{
    /** Constructor.
     *  @param musiqueFrame Main MusiqueFrame instance.
     */
    public OpenNewAction( MusiqueFrame musiqueFrame )
    {
        super( musiqueFrame, I18nManager.getInstance().getMessage("new_collection") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("new_collection") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "ctrl N" ) );
    }

    /** Get a loader for the collection.
     *  @param collection Collection to create the loader for.
     *  @return A fitting CollectionLoader.
     */
    public CollectionLoader createCollectionLoader( Collection collection )
    {
        return new NewCollectionLoader( collection );
    }

    @Override
    public CollectionFrame createCollectionFrame( MusiqueFrame musiqueFrame, Collection collection )
    {
        return new CustomCollectionFrame( musiqueFrame, collection );
    }
}
