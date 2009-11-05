/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import java.util.EventListener;

/** An interface to listens to the data of a Collection,
 *  so it can react when releases are added or removed.
 *  Do not forget to register this component using
 *  {@link CollectionDataModel#addCollectionDataListener(CollectionDataListener)}.
 */
public interface CollectionDataListener extends EventListener
{
    /** This is called whenever a new year is added
     *  in the {@link CollectionDataModel}.
     *  @param year The new release year.
     *  @param index The index of the new year in all the years.
     */
    public void yearAdded( String year, int index );

    /** This is called whenever a year is empty and
     *  should be removed.
     *  @param year The empty year.
     */
    public void yearRemoved( String year );

    /** This is called whenever a Release was added
     *  in the {@link CollectionDataModel}
     *  @param release The release that was added.
     *  @param index The index of the release in the list of releases that year.
     */
    public void releaseAdded( Release release, int index );

    /** This is called whenever a Release was removed
     *  from the {@link CollectionDataModel}. We also
     *  pass the year from which it was removed, because
     *  the release.getYear() might be wrong at this point.
     *  @param release The release that was removed.
     *  @param year Year from which the Release was removed.
     */
    public void releaseRemoved( Release release, String year );
}
