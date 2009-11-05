/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.visualisations.VisualisationSelectionModel;
import musique.resources.ResourceManager;
import musique.resources.I18nManager;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** The main window for the Musique application.
 */
public class MusiqueFrame extends JFrame
{
    private VisualisationSelectionModel visualisationSelectionModel;
    private FrameListModel frameListModel;

    /** Constructor.
     */
    public MusiqueFrame()
    {
        super("Musique");
        visualisationSelectionModel = new VisualisationSelectionModel();
        frameListModel = new FrameListModel();

        /* We use actions, so we can re-use them. */
        AbstractAction openArtistAction = new OpenArtistAction( this );
        AbstractAction openLabelAction = new OpenLabelAction( this );
        AbstractAction openCustomAction = new OpenCustomAction( this );
        AbstractAction openNewAction = new OpenNewAction( this );

        JMenuBar menuBar = new JMenuBar();

        /* Create and add the File menu. */
        JMenu fileMenu = new JMenu( I18nManager.getInstance().getMessage("file") );
        fileMenu.setMnemonic( I18nManager.getInstance().getMnemonic("file") );
        fileMenu.add( openArtistAction );
        fileMenu.add( openLabelAction );
        fileMenu.addSeparator();
        fileMenu.add( openCustomAction );
        fileMenu.add( openNewAction );
        fileMenu.addSeparator();
        fileMenu.add( new QuitAction(this) );
        menuBar.add( fileMenu );

        /* Create and add the Settings menu. */
        JMenu settingsMenu = new SettingsMenu( visualisationSelectionModel );
        menuBar.add( settingsMenu );

        /* Create and add the Frames menu. */
        JMenu framesMenu = new FramesMenu( frameListModel );
        menuBar.add( framesMenu );

        setJMenuBar( menuBar );

        /* Now add a simple welcome screen with two buttons. */
        setLayout( new BorderLayout() );
        Container container = getContentPane();
        container.add( new JLabel( ResourceManager.getInstance().getIcon("musique") ), BorderLayout.NORTH );
        JPanel bottom = new JPanel( );
        bottom.add( new JButton( openArtistAction ) );
        bottom.add( new JButton( openLabelAction ) );
        container.add( bottom, BorderLayout.SOUTH );

        pack();
        setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        setVisible( true );

        /* When this window is closed, we first want to check if all
         * other windows can close, sometimes we have to present a
         * "Are you sure you want to quit" dialog * etc. That's why
         * we request a dispose to the frameListModel, which will in
         * turn request a dispose to all it's frames. */
        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent event ) {
                if( frameListModel.requestDispose() )
                    System.exit( 0 );
            }
        } );
    }

    /** Get the VisualisationSelectionModel for this frame.
     *  @return The associated VisualisationSelectionModel.
     */
    public VisualisationSelectionModel getVisualisationSelectionModel()
    {
        return visualisationSelectionModel;
    }

    /** Get the FrameListModel for this frame.
     *  @return The associated FrameListModel.
     */
    public FrameListModel getFrameListModel()
    {
        return frameListModel;
    }
}
