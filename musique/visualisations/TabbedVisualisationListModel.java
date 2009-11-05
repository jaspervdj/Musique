/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.Release;
import musique.collectiondata.CollectionSelectionModel;
import javax.swing.AbstractListModel;
import java.util.List;

/** Note that this class does not listen directly to the CollectionDataModel
 *  but rather, relies on the TabbedVisualisation to pass events. This is
 *  because of synchronisation issues- we don not want releases to be added
 *  to a year that doesn't exist yet.
 */
public class TabbedVisualisationListModel extends AbstractListModel
{
    private String year;
    private CollectionDataModel dataModel;
    private CollectionSelectionModel selectionModel;

    /** Constructor.
     *  @param year Year of the releases in this list model.
     *  @param dataModel CollectionDataModel containing the releases.
     */
    public TabbedVisualisationListModel( String year, CollectionDataModel dataModel )
    {
        this.year = year;
        this.dataModel = dataModel;
        this.selectionModel = dataModel.getSelectionModel();
    }

    @Override
    public Object getElementAt( int index )
    {
        List<Release> list = dataModel.getReleasesByYear(year);
        if( list == null || index >= list.size() ) {
            return null;
        } else {
            return list.get(index);
        }
    }

    @Override
    public int getSize()
    {
        List<Release> list = dataModel.getReleasesByYear(year);
        if( list == null )
            return 0;
        else
            return list.size();
    }

    /** Called by the TabbedVisualisation when a release is added.
     *  @param release Release that was added.
     *  @param index Index of the added release.
     */
    public void releaseAdded( Release release, int index )
    {
        List<Release> list = dataModel.getReleasesByYear(year);
        if( list != null ) {
            fireIntervalAdded( this, index, index );
        }
    }

    /** Called by the TabbedVisualisation when a release is removed.
     *  @param release Release that was removed.
     */
    public void releaseRemoved( Release release )
    {
        fireIntervalRemoved( this, 0, getSize() );
    }
}
