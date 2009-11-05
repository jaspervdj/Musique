/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.resources.I18nManager;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** A class that shows some information about a collection.
 *  The collection information and image are shown.
 */
public class CollectionInformationPanel extends JPanel implements ChangeListener
{
    /** The Collection to show information about. */
    private Collection collection;

    /** A border around this component. */
    private TitledBorder border;

    /** A text area containing the artist information. */
    private JTextArea information;

    /** An icon showing the artist. */
    private IconLabel icon;

    /** A progress bar showing the load state. */
    private JProgressBar progress;

    /** Constructor.
     *  @param collection Collection to show info for.
     */
    public CollectionInformationPanel( Collection collection )
    {
        super( new BorderLayout() );
        this.collection = collection;

        border = BorderFactory.createTitledBorder( I18nManager.getInstance().getMessage("unknown") );
        setBorder( border );

        information = new JTextArea( I18nManager.getInstance().getMessage("loading") );
        information.setEditable( false );
        information.setLineWrap( true );
        information.setWrapStyleWord( true );
        information.setOpaque( false );
        add( information, BorderLayout.NORTH );

        icon = new IconLabel();
        icon.setHorizontalAlignment( IconLabel.CENTER );
        add( icon, BorderLayout.CENTER );

        progress = new JProgressBar( 0, 100 );
        progress.setStringPainted( true );
        progress.setString( "Loading releases..." );
        add( progress, BorderLayout.SOUTH );

        collection.addChangeListener( this );

        border.setTitle( collection.getName() );
        information.setText( collection.getInformation() );
        icon.setImageURL( collection.getImageURL() );
    }

    /** This method gets called when the Collection changed.
     *  Usually this means the collection is in a futher
     *  loading stage now. React by setting all GUI components
     *  to the right values.
     *  @param event Event containing the change source.
     */
    @Override
    public void stateChanged( ChangeEvent event )
    {
        border.setTitle( collection.getName() );
        information.setText( collection.getInformation() );
        icon.setImageURL( collection.getImageURL() );
        progress.setValue( collection.getLoadState() );

        /* Border update needs repainting. */
        repaint();

        if( collection.isLoaded() )
            progress.setVisible( false );
    }
}
