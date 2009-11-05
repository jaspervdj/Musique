/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Collections;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** This class represents the data (all the releases) in
 *  a collection. It is a Model, meaning that it can have
 *  views (of the type {@link CollectionDataListener}) who
 *  can react to changes in the data. Note that this class
 *  also listens to releases itself, to know when it has
 *  to alert the view of important changes in the Releases
 *  we keep.
 */
public class CollectionDataModel extends AbstractCollectionDataModel implements ChangeListener
{
    /** We use a {@link SortedMap} to store releases, so
     *  they get automatically grouped and ordered by year.
     */
    private SortedMap<String,List<Release>> releases;
    private CollectionSelectionModel selectionModel;

    /** Constructor.
     */
    public CollectionDataModel()
    {
        releases = new TreeMap<String,List<Release>>();
        selectionModel = new CollectionSelectionModel();
    }

    /** Fetch the selection model of this object.
     *  @return The {@link CollectionSelectionModel} associated with this object.
     */
    public CollectionSelectionModel getSelectionModel()
    {
        return selectionModel;
    }

    /** Return a (sorted) {@link Set} of years that have releases
     *  associated with them.
     *  @return A Set of strings representing the years.
     */
    public Set<String> getYears()
    {
        return releases.keySet();
    }

    /** Return a {@link List} of {@link Release}'s in the given
     *  year.
     *  @param year The year from which you want the releases.
     *  @return The releases released in the given year.
     */
    public List<Release> getReleasesByYear( String year )
    {
        return releases.get(year);
    }

    /** Find all releases in this collection.
     *  @return All releases in this collection.
     */
    public List<Release> getAllReleases()
    {
        List<Release> all = new LinkedList<Release>();

        for( String year: getYears() )
            all.addAll( releases.get(year) );

        return all;
    }

    /** Checks if this collection contains a certain
     *  release.
     *  @param release Release to check for.
     *  @return If this collection contains the release.
     */
    public boolean contains( Release release )
    {
        return getAllReleases().contains( release );
    }

    /** Add a {@link Release} to this collection. Every
     *  {@link CollectionDataListener} listening to this model
     *  will be informed.
     *  @param release Release to be added.
     */
    @SuppressWarnings("unchecked")
    public void addRelease( Release release )
    {
        List<Release> list = releases.get( release.getYear() );

        if( list == null ) {
            list = new LinkedList<Release>();
            releases.put( release.getYear(), list );

            /* A trick to find out the index. */
            int index = releases.subMap( releases.firstKey(), release.getYear() ).size();
            fireYearAdded( release.getYear(), index );
        }

        /* We want all releases sorted alphabetically. */
        list.add( release );
        Collections.sort( list );

        release.addChangeListener( this );
        fireReleaseAdded( release, list.indexOf(release) );
    }

    /** Remove a {@link Release} from this collection. Every
     *  {@link CollectionDataListener} listening to this model
     *  will be informed.
     *  @param release Release to be removed.
     */
    public void removeRelease( Release release )
    {
        Object[] years = getYears().toArray();
        int index = 0;
        boolean removed = false;

        /* We have to search in which year the release is located. */
        while( index < years.length && !removed ) {

            String year = (String)years[index];
            List<Release> list = releases.get( year );

            /* If this list contains the release... */
            if( list != null && list.contains( release ) ) {

                removed = true;
                list.remove( release );
                fireReleaseRemoved( release, year );

                /* If this item was selected, clear selection. */
                if( release == selectionModel.getSelection() )
                    selectionModel.setSelection( null );

                /* The year is empty, so remove it. */
                if( list.size() <= 0 ) {
                    releases.remove( year );
                    fireYearRemoved( year );
                }
            }

            index++;
        }

        release.removeChangeListener( this );
    }

    /** One of the releases we are keeping was changed.
     *  @param event An event holding more change details.
     */
    @Override
    public void stateChanged( ChangeEvent event )
    {
        /* React by examining if the year was changed. If so,
         * we will remove the release and then add it again
         * so it is stored under the correct year. */
        Release source = (Release) event.getSource();
        List<Release> list = releases.get( source.getYear() );

        if( list == null || !list.contains(source) ) {
            removeRelease( source );
            addRelease( source );
        }

        if( !source.isValid() ) {
            removeRelease( source );
        }
    }
}
