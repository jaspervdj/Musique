/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.CollectionSelectionModel;
import musique.collectiondata.Release;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** A Panel that shows some information about a release.
 *  This includes an image and a track table.
 */
public class ReleaseInformationPanel extends JPanel implements ChangeListener
{
    private CollectionSelectionModel selectionModel;
    private Release release;

    private IconLabel icon;
    private JTable tracks;

    /** Constructor.
     *  @param selectionModel {@link CollectionSelectionModel} to get the selected Release from.
     */
    public ReleaseInformationPanel( CollectionSelectionModel selectionModel )
    { 
        super( new GridBagLayout() );
        this.selectionModel = selectionModel;
        this.release = null;

        selectionModel.addChangeListener( this );

        GridBagConstraints constraints = new GridBagConstraints();

        icon = new IconLabel();

        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets( 0, 0, 10, 0 );
        add( icon, constraints );

        tracks = new JTable( new ReleaseTracksTableModel( selectionModel.getSelection() ) );
        JPanel container = new JPanel( new BorderLayout() );
        container.add( tracks, BorderLayout.CENTER );
        container.add( tracks.getTableHeader(), BorderLayout.NORTH );

        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets( 10, 0, 0, 0 );
        add( container, constraints );

        setVisible( false );
    }

    /** This function gets called when the selection in the
     *  {@link CollectionSelectionModel} or the {@link Release}
     *  itself has changed. We react by updating ourselves.
     */
    @Override
    public void stateChanged( ChangeEvent event )
    {
        if( release != null )
            release.removeChangeListener( this );

        release = selectionModel.getSelection();
        if( release != null ) {
            icon.setImageURL( release.getImageURL() );
            tracks.setModel( new ReleaseTracksTableModel( selectionModel.getSelection() ) );
            release.addChangeListener( this );
            setVisible( true );
        } else {
            setVisible( false );
        }
    }
}
