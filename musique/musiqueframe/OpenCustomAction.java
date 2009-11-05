/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.resources.I18nManager;
import musique.collectionframe.CollectionFrame;
import musique.collectionframe.CustomCollectionFrame;
import musique.collectiondata.Collection;
import musique.loaders.CollectionLoader;
import musique.loaders.CustomCollectionLoader;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** An action used to open a new CollectionFrame
 *  about an custom collection.
 */
public class OpenCustomAction extends OpenAction
{
    /** Constructor.
     *  @param musiqueFrame Main MusiqueFrame instance.
     */
    public OpenCustomAction( MusiqueFrame musiqueFrame )
    {
        super( musiqueFrame, I18nManager.getInstance().getMessage("open_collection") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("open_collection") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "ctrl O" ) );
    }

    /** Get a loader for the collection.
     *  @param collection Collection to create the loader for.
     *  @return A fitting CollectionLoader.
     */
    public CollectionLoader createCollectionLoader( Collection collection )
    {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter( "Musique XML", "xml", "musique" );
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog( getMusiqueFrame() );

        if( returnVal == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null )
            return new CustomCollectionLoader( collection, chooser.getSelectedFile() );
        else
            return null;
    }

    @Override
    public CollectionFrame createCollectionFrame( MusiqueFrame musiqueFrame, Collection collection )
    {
        return new CustomCollectionFrame( musiqueFrame, collection );
    }
}
