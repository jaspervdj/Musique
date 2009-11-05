/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.collectionframe.CollectionFrame;
import musique.collectiondata.Collection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.AbstractAction;
import java.util.Map;

/** An action that deiconifies ans shows a frame.
 */
public class ShowFrameAction extends AbstractAction implements ChangeListener
{
    private CollectionFrame frame;

    /** Constructor.
     *  @param frame Frame to show.
     */
    public ShowFrameAction( CollectionFrame frame )
    {
        super( frame.getTitle() );
        this.frame = frame;

        char mnemonic = frame.getTitle().toLowerCase().charAt(0);
        putValue( MNEMONIC_KEY, mnemonic - 'a' + KeyEvent.VK_A );

        frame.getCollection().addChangeListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        frame.setState( JFrame.NORMAL );
        frame.toFront();
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        char mnemonic = frame.getTitle().toLowerCase().charAt(0);
        putValue( MNEMONIC_KEY, mnemonic - 'a' + KeyEvent.VK_A );
        putValue( NAME, frame.getTitle() );
    }
}
