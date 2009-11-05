/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import musique.Model;
import musique.loaders.ReleaseLoader;
import java.util.List;

/** A model that holds the currently selected release
 *  in the collection.
 */
public class CollectionSelectionModel extends Model
{
    /** The currently selected release - can be null. */
    private Release selection;

    /** Constructor.
     */
    public CollectionSelectionModel()
    {
        this.selection = null;
    }

    /** Ask for the currently selected object.
     *  @return The currently selected release.
     */
    public Release getSelection()
    {
        return selection;
    }

    /** Sets the selection.
     *  @param selection The new Release selection for this collection.
     */
    public void setSelection( Release selection )
    {
        if( this.selection != selection ) {
            this.selection = selection;
            fireStateChanged();

            /* Load the selection quickly if it hasn't been loaded yet. */
            if( selection != null && !selection.isLoaded() ) {
                ReleaseLoader releaseLoader = new ReleaseLoader( selection );
                releaseLoader.loadInBackground();
            }
        }
    }
}
