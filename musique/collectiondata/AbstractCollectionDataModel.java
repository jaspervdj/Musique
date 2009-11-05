/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import java.awt.EventQueue;
import javax.swing.event.EventListenerList;

/** An abstract model class that is able to register
 *  listeners and report changes to them.
 */
public class AbstractCollectionDataModel
{
    private EventListenerList listenerList;

    /** Constructor.
     */
    public AbstractCollectionDataModel()
    {
        listenerList = new EventListenerList();
    }

    /** Register a CollectionDataListener to listen to changes in the model.
     *  @param listener CollectionDataListener to add.
     */
    public void addCollectionDataListener( CollectionDataListener listener )
    {
        listenerList.add( CollectionDataListener.class, listener );
    }
    
    /** Remove a CollectionDataListener so it doesn't receive events from this
     *  object anymore.
     *  @param listener CollectionDataListener to remove.
     */
    public void removeCollectionDataListener( CollectionDataListener listener )
    {
        listenerList.remove( CollectionDataListener.class, listener );
    }
    
    /** Alert all listeners that a year has been added.
     *  @param year Year that was added.
     *  @param index Index of the year (for ordering).
     */
    protected void fireYearAdded( final String year, final int index )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for( int i=listeners.length-2; i>=0; i-=2 ) {
                    CollectionDataListener listener = (CollectionDataListener) listeners[i+1];
                    if( listeners[i] == CollectionDataListener.class ) {
                        listener.yearAdded( year, index );
                    }
                }
            }
        } );
    } 

    /** Alert all listeners that a year has been removed.
     *  @param year Year that was removed.
     */
    protected void fireYearRemoved( final String year )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for( int i=listeners.length-2; i>=0; i-=2 ) {
                    CollectionDataListener listener = (CollectionDataListener) listeners[i+1];
                    if( listeners[i] == CollectionDataListener.class ) {
                        listener.yearRemoved( year );
                    }
                }
            }
        } );
    } 

    /** Alert all listeners that a release has been added.
     *  @param release Release that was added.
     *  @param index Index of the release (for ordering).
     */
    protected void fireReleaseAdded( final Release release, final int index )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for( int i=listeners.length-2; i>=0; i-=2 ) {
                    CollectionDataListener listener = (CollectionDataListener) listeners[i+1];
                    if( listeners[i] == CollectionDataListener.class ) {
                        listener.releaseAdded( release, index );
                    }
                }
            }
        } );
    } 

    /** Alert all listeners that a release has been removed
     *  @param release Release that was removed.
     *  @param year Year from which the release was removed.
     */
    protected void fireReleaseRemoved( final Release release, final String year )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for( int i=listeners.length-2; i>=0; i-=2 ) {
                    CollectionDataListener listener = (CollectionDataListener) listeners[i+1];
                    if( listeners[i] == CollectionDataListener.class ) {
                        listener.releaseRemoved( release, year );
                    }
                }
            }
        } );
    } 
}
