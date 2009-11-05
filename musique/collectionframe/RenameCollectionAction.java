/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.resources.I18nManager;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/** An action that can rename a Collection when performed.
 */
public class RenameCollectionAction extends AbstractAction
{
    private CollectionFrame collectionFrame;

    /** Constructor.
     *  @param CollectionFrame Frame of the to be renamed collection.
     */
    public RenameCollectionAction( CollectionFrame collectionFrame )
    {
        super( I18nManager.getInstance().getMessage("rename") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("rename") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "ctrl r" ) );

        this.collectionFrame = collectionFrame;
    }

    /** Called when an action is performed. Show a dialog to the
     *  user that allows him to edit the Collection name.
     */
    @Override
    public void actionPerformed( ActionEvent event )
    {
        Collection collection = collectionFrame.getCollection();
        String name = JOptionPane.showInputDialog( collectionFrame, "Rename the collection:", collection.getName() );
        if( name != null && name.length() != 0 ) {
            collection.setName( name );
        }
    }
}
