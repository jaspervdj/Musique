/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.resources.I18nManager;
import musique.collectiondata.Collection;
import musique.loaders.CollectionLoader;
import musique.loaders.LabelCollectionLoader;
import javax.swing.JOptionPane;

/** An action used to open a new CollectionFrame
 *  about a label.
 */
public class OpenLabelAction extends OpenAction
{
    /** Constructor.
     *  @param musiqueFrame Main MusiqueFrame instance.
     */
    public OpenLabelAction( MusiqueFrame musiqueFrame )
    {
        super( musiqueFrame, I18nManager.getInstance().getMessage("open_label") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("open_label") );
    }

    /** Get a loader for the collection.
     *  @param collection Collection to create the loader for.
     *  @return A fitting CollectionLoader.
     */
    @Override
    public CollectionLoader createCollectionLoader( Collection collection )
    {
        String label = JOptionPane.showInputDialog( getMusiqueFrame(),
                                                    I18nManager.getInstance().getMessage("prompt_label"),
                                                    "silken tofu" );
        if( label != null )
            return new LabelCollectionLoader( collection, label );
        else
            return null;
    }
}
