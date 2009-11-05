/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.collectionframe.CollectionFrame;
import java.util.List;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.EventListenerList;

/** A Model that represents a list of open collectionframes.
 */
public class FrameListModel extends WindowAdapter
{
    private EventListenerList listenerList;
    private List<CollectionFrame> frames;

    /** Constructor. Creates an empty list.
     */
    public FrameListModel()
    {
        listenerList = new EventListenerList();
        frames = new ArrayList<CollectionFrame>();
    }

    /** Add a FrameListListener to listen to this object.
     *  @param listener Listener to add.
     */
    public void addFrameListListener( FrameListListener listener )
    {
        listenerList.add( FrameListListener.class, listener );
    }
    
    /** Remove a FrameListListener from liseting to this object.
     *  @param listener Listener to remove.
     */
    public void removeFrameListListener( FrameListListener listener )
    {
        listenerList.remove( FrameListListener.class, listener );
    }

    /** Get a list of frames.
     *  @return A list of open collectionframes.
     */
    public List<CollectionFrame> getFrames()
    {
        return frames;
    }

    /** Adds a {@link CollectionFrame} to the list.
     *  @param frame The frame to add.
     */
    public void addFrame( CollectionFrame frame )
    {
        frames.add( frame );
        frame.addWindowListener( this );
        fireFrameAdded( frame );
    }

    /** Removes a {@link CollectionFrame} from the list.
     *  @param frame The frame to remove.
     */
    public void removeFrame( CollectionFrame frame )
    {
        frames.remove( frame );
        frame.removeWindowListener( this );
        fireFrameRemoved( frame );
    }

    /** Send a request to close all frames, and
     *  dispose them if possible.
     *  @return If all frames could be closed.
     */
    public boolean requestDispose()
    {
        int index = 0;
        while( index < frames.size() && frames.get(index).canDispose() )
            index++;

        if( index >= frames.size() ) {
            /* Allright, now close all windows. */
            for( CollectionFrame frame: frames )
                frame.dispose();
        }

        return index >= frames.size();
    }

    /** When a frame is closed, remove it.
     */
    @Override
    public void windowClosed( WindowEvent event )
    {
        CollectionFrame frame = (CollectionFrame) event.getWindow();
        removeFrame( frame );
    }

    /** Alerts all objects listening to this object that a
     *  frame was added.
     *  @param frame Frame that was added.
     */
    protected void fireFrameAdded( final CollectionFrame frame )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for (int i=listeners.length-2; i >= 0; i-=2) {
                    if (listeners[i] == FrameListListener.class) {
                        FrameListListener listener = (FrameListListener) listeners[i+1];
                        listener.frameAdded ( frame );
                    }
                }
            }
        } );
    } 

    /** Alerts all objects listening to this object that a
     *  frame was removed.
     *  @param frame Frame that was removed.
     */
    protected void fireFrameRemoved( final CollectionFrame frame )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                Object[] listeners = listenerList.getListenerList ();
                for (int i=listeners.length-2; i >= 0; i-=2) {
                    if (listeners[i] == FrameListListener.class) {
                        FrameListListener listener = (FrameListListener) listeners[i+1];
                        listener.frameRemoved ( frame );
                    }
                }
            }
        } );
    } 
}
