/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.musiqueframe.MusiqueFrame;
import musique.resources.I18nManager;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** A Frame to show a collection.
 */
public class CollectionFrame extends JFrame implements ChangeListener
{
    private Collection collection;
    private CollectionPanel collectionPanel;
    private MusiqueFrame musiqueFrame;

    /** Constructor.
     *  @param collection Collection to show in this frame.
     */
    public CollectionFrame( MusiqueFrame musiqueFrame, Collection collection )
    {
        super();
        this.musiqueFrame = musiqueFrame;
        this.collection = collection;

        setTitle( collection.getName() );

        CollectionPanel collectionPanel = new CollectionPanel( musiqueFrame.getVisualisationSelectionModel(), collection );
        setContentPane( collectionPanel );

        JMenuBar menuBar = new JMenuBar();
        menuBar.add( createActionsMenu() );
        setJMenuBar( menuBar );

        collection.addChangeListener( this );

        setPreferredSize( new Dimension( 640, 480 ) );
        pack();
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setVisible( true );
    }

    /** Creates the JMenu for Actions.
     *  @return The JMenu to be added to the menu bar.
     */
    public JMenu createActionsMenu()
    {
        JMenu actionsMenu = new JMenu( I18nManager.getInstance().getMessage("actions") );
        actionsMenu.setMnemonic( I18nManager.getInstance().getMnemonic("actions") );

        actionsMenu.add( new AddToCollectionMenu( musiqueFrame.getFrameListModel(), collection.getDataModel().getSelectionModel() ) );

        return actionsMenu;
    }

    /** Get the collection associated with this Frame.
     *  @return The collection.
     */
    public Collection getCollection()
    {
        return collection;
    }

    /** The state of the collection has changed.
     *  React by setting the title of this frame
     *  to the name of the collection. Also, if
     *  the collection is invalid, throw it away.
     *  @param event The ChangeEvent containing the change parameters.
     */
    public void stateChanged( ChangeEvent event )
    {
        if( !collection.isValid() ) {
            JOptionPane.showMessageDialog( this,
                                           I18nManager.getInstance().getMessage("error_retrieving_data"),
                                           I18nManager.getInstance().getMessage("error"),
                                           JOptionPane.ERROR_MESSAGE );
            dispose();
        } else {
            setTitle( collection.getName() );
        }
    }

    /** Ask nicely if this window can be disposed.
     *  @return When true, you can safely dispose this window.
     */
    public boolean canDispose()
    {
        return true;
    }

    @Override
    public void dispose()
    {
        collection.getLoader().requestCancel();
        collection.removeChangeListener( this );
        super.dispose();
    }
}
