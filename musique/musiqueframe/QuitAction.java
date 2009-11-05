/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.resources.I18nManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/** An Action that closes a frame.
 */
public class QuitAction extends AbstractAction
{
    private MusiqueFrame frame;

    /** Constructor.
     *  @param frame Frame to close when the action is used.
     */
    public QuitAction( MusiqueFrame frame )
    {
        super( I18nManager.getInstance().getMessage("quit") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("quit") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "ctrl Q" ) );

        this.frame = frame;
    }

    /** Called when the action is used. Close the frame.
     *  @param event Data about the event.
     */
    public void actionPerformed( ActionEvent event )
    {
        if( frame.getFrameListModel().requestDispose() )
            System.exit(0);
    }
}
