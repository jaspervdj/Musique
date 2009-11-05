/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.resources.I18nManager;
import musique.collectiondata.Collection;
import musique.loaders.CollectionLoader;
import musique.loaders.ArtistCollectionLoader;
import javax.swing.JOptionPane;

/** An action used to open a new CollectionFrame
 *  about an artist.
 */
public class OpenArtistAction extends OpenAction
{
    /** Constructor.
     *  @param musiqueFrame Main MusiqueFrame instance.
     */
    public OpenArtistAction( MusiqueFrame musiqueFrame )
    {
        super( musiqueFrame, I18nManager.getInstance().getMessage("open_artist") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("open_artist") );
    }

    /** Get a loader for the collection.
     *  @param collection Collection to create the loader for.
     *  @return A fitting CollectionLoader.
     */
    @Override
    public CollectionLoader createCollectionLoader( Collection collection )
    {
        String artist = JOptionPane.showInputDialog( getMusiqueFrame(),
                                                     I18nManager.getInstance().getMessage("prompt_artist"),
                                                     "dEUS" );
        if( artist != null )
            return new ArtistCollectionLoader( collection, artist );
        else
            return null;
    }
}
