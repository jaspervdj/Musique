/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.CollectionSelectionModel;
import musique.collectiondata.Release;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.AbstractAction;

/** An Action that is assiociated with two collections,
 *  as it adds a release from the first collection to the
 *  second collection.
 */
public class AddToCollectionAction extends AbstractAction
                                   implements ChangeListener
{
    /** Collection to add to. */
    private Collection collection;

    /** Selection to add from. */
    private CollectionSelectionModel selectionModel;

    /** Constructor.
     *  @param collection Collection to add releases to.
     *  @param selectionModel SelectionModel to add releases from.
     */
    public AddToCollectionAction( Collection collection, CollectionSelectionModel selectionModel )
    {
        super( collection.getName() );
        char mnemonic = collection.getName().toLowerCase().charAt(0);
        putValue( MNEMONIC_KEY, mnemonic - 'a' + KeyEvent.VK_A );

        this.collection = collection;
        this.selectionModel = selectionModel;

        Release selection = selectionModel.getSelection();
        setEnabled( selection != null && !collection.getDataModel().contains( selection ) );

        collection.addChangeListener( this );
        selectionModel.addChangeListener( this );
    }

    /** Get the collection add to.
     *  @return The collection to add to.
     */
    public Collection getCollection()
    {
        return collection;
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        Release release = selectionModel.getSelection();

        if( release != null ) {
            CollectionDataModel dataModel = collection.getDataModel();
            dataModel.addRelease( release );
            setEnabled( false );
        }
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        char mnemonic = collection.getName().toLowerCase().charAt(0);
        putValue( MNEMONIC_KEY, mnemonic - 'a' + KeyEvent.VK_A );
        putValue( AbstractAction.NAME, collection.getName() );

        Release release = selectionModel.getSelection();
        setEnabled( release != null && !collection.getDataModel().contains( release ) );
    }
}
