/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.visualisations.VisualisationSelectionModel;
import musique.visualisations.Visualisation;
import musique.collectiondata.Collection;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** A class that shows a collection. This panel is divided into
 *  three parts. One to show general information about the collection,
 *  a part in which a release can be selected, and a part in which
 *  information about the selected release is shown.
 */
public class CollectionPanel extends JPanel implements ChangeListener
{
    /** Model holding the selected visualisation. */
    private VisualisationSelectionModel visualisationSelectionModel;

    /** Collection shown in this panel. */
    private Collection collection;

    /** Left panel, showing general collection information. */
    private CollectionInformationPanel collectionInformation;

    /** A panel containing the visualisation. */
    private JPanel visualisationContainer;

    /** A CardLayout we can use to switch visualisations. */
    private CardLayout visualisationCardLayout;

    /** Right panel, showing information about the selected release. */
    private ReleaseInformationPanel releaseInformation;

    /** Constructor.
     *  @param visualisationSelectionModel The model containing the selected visualisation.
     *  @param collection Collectionn to show.
     */
    public CollectionPanel( VisualisationSelectionModel visualisationSelectionModel, Collection collection )
    {
        super( new GridLayout( 1, 3 ) );
        this.visualisationSelectionModel = visualisationSelectionModel;
        this.collection = collection;

        /* Left panel. */
        collectionInformation = new CollectionInformationPanel( collection );
        add( collectionInformation );

        /* Center panel. */
        visualisationCardLayout = new CardLayout();
        visualisationContainer = new JPanel( visualisationCardLayout );
        add( visualisationContainer );

        /* Add all visualisations. We select a proper one using
         * the card layout. */
        for( int i=0; i<visualisationSelectionModel.getNumberOfVisualisations(); i++ )
        {
            Visualisation visualisation = visualisationSelectionModel.createVisualisation( i, collection.getDataModel() );
            visualisationContainer.add( visualisation, visualisationSelectionModel.getVisualisationName(i) );
        }

        /* Show initally selected visualisation. */
        int selection = visualisationSelectionModel.getVisualisation();
        String name = visualisationSelectionModel.getVisualisationName( selection );
        visualisationCardLayout.show( visualisationContainer, name );

        /* Right panel. */
        releaseInformation = new ReleaseInformationPanel( collection.getDataModel().getSelectionModel() );
        add( new JScrollPane( releaseInformation ) );

        /* Listen to the VisualisationSelectionModel so we are
         * alerted when the user selects another visualisation.
         */
        visualisationSelectionModel.addChangeListener( this );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        /* Set new visualisation. */
        int selection = visualisationSelectionModel.getVisualisation();
        String name = visualisationSelectionModel.getVisualisationName( selection );
        visualisationCardLayout.show( visualisationContainer, name );
    }
}
