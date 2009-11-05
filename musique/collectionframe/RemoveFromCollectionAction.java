/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.CollectionSelectionModel;
import musique.collectiondata.Release;
import musique.resources.I18nManager;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class RemoveFromCollectionAction extends AbstractAction
                                   implements ChangeListener
{
    /** Collection to remove from. */
    private Collection collection;
    /** The collection selection model. */
    private CollectionSelectionModel selectionModel;

    /** Constructor.
     *  @param collection Collection to remove releases from.
     */
    public RemoveFromCollectionAction( Collection collection )
    {
        super( I18nManager.getInstance().getMessage("remove") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("remove") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "DELETE" ) );

        this.collection = collection;
        selectionModel = collection.getDataModel().getSelectionModel();
        setEnabled( false );

        selectionModel.addChangeListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        Release release = selectionModel.getSelection();

        if( release != null ) {
            CollectionDataModel dataModel = collection.getDataModel();
            dataModel.removeRelease( release );
            setEnabled( false );
        }
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        Release release = selectionModel.getSelection();
        setEnabled( release != null );
    }
}
