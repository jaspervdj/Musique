/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.Model;
import musique.collectiondata.CollectionDataModel;

/** A class to represent the currently selected visualisation.
 */
public class VisualisationSelectionModel extends Model
{
    private static final VisualisationFactory[] VISUALISATION_FACTORIES = { new TreeVisualisationFactory(),
                                                                            new TabbedVisualisationFactory(),
                                                                            new CoverVisualisationFactory() };

    private int selection;

    /** Constructor.
     */
    public VisualisationSelectionModel()
    {
        selection = 0;
    }

    /** Fetch the number of available visualisations.
     *  @return The number of visualisations.
     */
    public int getNumberOfVisualisations()
    {
        return VISUALISATION_FACTORIES.length;
    }

    /** Fetch the name for a visualisation.
     *  @return The visualisation name.
     */
    public String getVisualisationName( int number )
    {
        return VISUALISATION_FACTORIES[number].getName();
    }

    /** Obtain the selected visualisation number.
     *  @return The selected visualisation.
     */
    public int getVisualisation()
    {
        return selection;
    }

    /** Set a visualisation.
     *  @param number Number of the visualisation.
     */
    public void setVisualisation( int number )
    {
        if( selection != number ) {
            selection = number;
            fireStateChanged();
        }
    }

    /** Create a visualisation based on the selection.
     */
    public Visualisation createVisualisation( int number, CollectionDataModel dataModel )
    {
        return VISUALISATION_FACTORIES[number].createVisualisation( dataModel );
    }
}
