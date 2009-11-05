/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.collectionframe.CollectionFrame;
import musique.collectiondata.Collection;
import musique.resources.I18nManager;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.Map;
import java.util.HashMap;

/** A Menu in which the user can select a frame. The
 *  frame is then shown on the screen, deiconified.
 */
public class FramesMenu extends JMenu implements FrameListListener
{
    private FrameListModel model;
    private Map<CollectionFrame,JMenuItem> items;

    /** Constructor.
     *  @param model FrameListModel containing the frames to be shown in the menu.
     */
    public FramesMenu( FrameListModel model )
    {
        super( I18nManager.getInstance().getMessage("windows") );
        setMnemonic( I18nManager.getInstance().getMnemonic("windows") );

        this.model = model;
        items = new HashMap<CollectionFrame,JMenuItem>();
        setEnabled( false );
        model.addFrameListListener( this );
    }

    @Override
    public void frameAdded( CollectionFrame frame )
    {
        JMenuItem menuItem = new JMenuItem( new ShowFrameAction(frame) );
        add( menuItem );
        items.put( frame, menuItem );
        setEnabled( true );
        revalidate();
    }

    @Override
    public void frameRemoved( CollectionFrame frame )
    {
        remove( items.get( frame ) );
        items.remove( frame );
        if( items.isEmpty() )
            setEnabled( false );
        revalidate();
    }
}
