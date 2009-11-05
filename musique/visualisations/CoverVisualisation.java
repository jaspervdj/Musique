/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.CollectionDataModel;
import java.awt.BorderLayout;

/** A Visualisation that lets the user browse covers.
 *  This is a container containing various panels to
 *  browse the covers.
 */
public class CoverVisualisation extends Visualisation
{
    private CoverVisualisationModel model;
    private CoverVisualisationView view;
    private CoverVisualisationBrowser yearBrowser, releaseBrowser;

    /** Constructor
     *  @param dataModel CollectionDataModel to be visualised.
     */
    public CoverVisualisation( CollectionDataModel dataModel )
    {
        super( dataModel );
        model = new CoverVisualisationModel( dataModel );

        setLayout( new BorderLayout() );

        yearBrowser = new CoverVisualisationYearBrowser( model );
        add( yearBrowser, BorderLayout.NORTH );

        view = new CoverVisualisationView( model );
        add( view, BorderLayout.CENTER );

        releaseBrowser = new CoverVisualisationReleaseBrowser( model );
        add( releaseBrowser, BorderLayout.SOUTH );
    }
}
