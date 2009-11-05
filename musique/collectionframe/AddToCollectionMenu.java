/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectionframe.CollectionFrame;
import musique.musiqueframe.FrameListListener;
import musique.musiqueframe.FrameListModel;
import musique.collectiondata.Collection;
import musique.collectiondata.CollectionSelectionModel;
import musique.resources.I18nManager;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/** A Menu in which the user can select an open collection
 *  to add a release to. This menu will update itself
 *  automatically.
 */
public class AddToCollectionMenu extends JMenu implements FrameListListener
{
    private FrameListModel listModel;
    private CollectionSelectionModel selectionModel;

    /** Keep all frames in a map so we can remove them more easily. */
    private Map<CollectionFrame,JMenuItem> items;

    /** Constructor.
     *  @param listModel The FrameListModel to listen to.
     *  @param selectionModel The SelectionModel from which the to be added
     *                        releases should be fetched.
     */
    public AddToCollectionMenu( FrameListModel listModel, CollectionSelectionModel selectionModel )
    {
        super( I18nManager.getInstance().getMessage("add") );
        setMnemonic( I18nManager.getInstance().getMnemonic("add") );

        this.listModel = listModel;
        this.selectionModel = selectionModel;
        items = new HashMap<CollectionFrame,JMenuItem>();

        List<CollectionFrame> list = listModel.getFrames();
        for( CollectionFrame frame: list )
            addFrameToMenu( frame );

        if( getItemCount() == 0 )
            setEnabled( false );

        listModel.addFrameListListener( this );
    }

    /** Adds the given frame to the menu.
     *  @param frame Frame to be added.
     */
    private void addFrameToMenu( CollectionFrame frame )
    {
        /* We should, in fact, only add when dealing with
         * custom collections. Also, check that we do not
         * add our own frame. */
        CollectionSelectionModel otherSelectionModel = frame.getCollection().getDataModel().getSelectionModel();
        if( otherSelectionModel != selectionModel && frame instanceof CustomCollectionFrame ) {
            /* Create a new action and add it. */
            AddToCollectionAction action = new AddToCollectionAction( frame.getCollection(), selectionModel );
            JMenuItem menuItem = new JMenuItem( action );
            add( menuItem );
            items.put( frame, menuItem );
            setEnabled( true );
        }
    }

    @Override
    public void frameAdded( CollectionFrame frame )
    {
        addFrameToMenu( frame );
    }

    @Override
    public void frameRemoved( CollectionFrame frame )
    {
        JMenuItem menuItem = items.get( frame );
        if( menuItem != null ) {
            remove( menuItem );
            items.remove( frame );

            if( getItemCount() == 0 )
                setEnabled( false );
        }
    }
}
