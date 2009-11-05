/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.collectiondata.Release;
import musique.collectiondata.CollectionDataListener;
import musique.musiqueframe.MusiqueFrame;
import musique.resources.I18nManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** A Frame to show a custom collection. The difference with
 *  a {@link CollectionFrame} is that this frame allows more
 *  actions.
 */
public class CustomCollectionFrame extends CollectionFrame 
                                   implements ChangeListener, CollectionDataListener
{
    /** If the changes were saved. */
    private boolean saved;

    /** Constructor.
     *  @param collection Collection to show in this frame.
     */
    public CustomCollectionFrame( MusiqueFrame musiqueFrame, Collection collection )
    {
        super( musiqueFrame, collection );
        saved = true;

        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );

        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent event ) {
                if( canDispose() )
                    dispose();
            }
        } );

        getCollection().getDataModel().addCollectionDataListener( this );
    }

    /** Creates the Actions JMenu. Override this method by adding
     *  more menu items.
     *  @return The JMenu for "Actions".
     */
    @Override
    public JMenu createActionsMenu()
    {
        JMenu actionsMenu = super.createActionsMenu();

        actionsMenu.add( new RemoveFromCollectionAction( getCollection() ) );
        actionsMenu.add( new RenameCollectionAction( this ) );
        actionsMenu.add( new EditCollectionInformationAction( this ) );
        actionsMenu.add( new SaveCollectionAction( this ) );

        return actionsMenu;
    }

    /** Check if the frame was saved.
     *  @return If the collection does not have unsaved changes.
     */
    public boolean isSaved()
    {
        return saved;
    }

    /** Set the frame as being saved.
     *  @param saved If the frame was saved.
     */
    public void setSaved( boolean saved )
    {
        this.saved = saved;
    }

    /** Called when any changes are made to the
     *  collection.
     */
    public void changesMade()
    {
        if( getCollection().isLoaded() )
            saved = false;
    }

    @Override
    public boolean canDispose()
    {
        /* Unsaved changes, warn the user. */
        boolean willDispose = true;
        if( !saved ) {
            int result = JOptionPane.showConfirmDialog( this,
                                                        I18nManager.getInstance().getMessage("save_warning"),
                                                        I18nManager.getInstance().getMessage("warning"),
                                                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE );

            /* YES - save */
            if( result == 0 ) {
                SaveCollectionAction saveCollectionAction = new SaveCollectionAction( this );
                saveCollectionAction.saveCollection();
            } else if( result == 2 ) {
                willDispose = false;
            }
        }

        return willDispose;
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        changesMade();
    }

    @Override
    public void releaseAdded( Release release, int index )
    {
        changesMade();
    }

    @Override
    public void releaseRemoved( Release release, String year )
    {
        changesMade();
    }

    @Override
    public void yearAdded( String year, int index )
    {
    }

    @Override
    public void yearRemoved( String year )
    {
    }
}
