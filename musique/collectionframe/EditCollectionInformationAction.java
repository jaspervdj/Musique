/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.resources.I18nManager;
import musique.collectiondata.Collection;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/** An action that can edit the information about a
 *  Collection when performed.
 */
public class EditCollectionInformationAction extends AbstractAction
{
    private CollectionFrame collectionFrame;

    /** Constructor.
     *  @param CollectionFrame Frame of the to be edited collection.
     */
    public EditCollectionInformationAction( CollectionFrame collectionFrame )
    {
        super( I18nManager.getInstance().getMessage("change_information") ); 
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("change_information") );

        this.collectionFrame = collectionFrame;
    }

    /** Called when an action is performed. Show a dialog to the
     *  user that allows him to edit the Collection information.
     */
    @Override
    public void actionPerformed( ActionEvent event )
    {
        Collection collection = collectionFrame.getCollection();
        String information = JOptionPane.showInputDialog( collectionFrame,
                                                          I18nManager.getInstance().getMessage("prompt_information"),
                                                          collection.getInformation() );
        if( information != null && information.length() != 0 ) {
            collection.setInformation( information );
        }
    }
}
