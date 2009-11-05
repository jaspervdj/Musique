/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.CollectionDataListener;
import musique.collectiondata.CollectionSelectionModel;
import javax.swing.JPanel;

/** A Visualisation is a panel that shows a number of releases
 *  in a certain way.
 */
public abstract class Visualisation extends JPanel implements CollectionDataListener
{
    private CollectionDataModel dataModel;

    /** Constructor.
     *  @param dataModel CollectionDataModel to be visualised.
     */
    public Visualisation( CollectionDataModel dataModel )
    {
        super();
        this.dataModel = dataModel;
        dataModel.addCollectionDataListener( this );
    }

    /** Gets the data model of this visualisation.
     *  @return The data model of this visualisation.
     */
    public CollectionDataModel getDataModel()
    {
        return dataModel;
    }

    /** Gets the selection model of this visualisation.
     *  @return The selection model of this visualisation.
     */
    public CollectionSelectionModel getSelectionModel()
    {
        return dataModel.getSelectionModel();
    }

    /** Implementation that does nothing, but is
     *  just there for convenience reasons.
     */
    @Override
    public void yearAdded( String year, int index )
    {
    }

    /** Implementation that does nothing, but is
     *  just there for convenience reasons.
     */
    @Override
    public void releaseAdded( Release release, int index )
    {
    }

    /** Implementation that does nothing, but is
     *  just there for convenience reasons.
     */
    @Override
    public void yearRemoved( String year )
    {
    }

    /** Implementation that does nothing, but is
     *  just there for convenience reasons.
     */
    @Override
    public void releaseRemoved( Release release, String year )
    {
    }

}
