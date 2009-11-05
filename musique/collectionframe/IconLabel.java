/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.loaders.ImageLoader;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** A JLabel that shows an Icon. The main thing
 *  about this class is that the associated icon
 *  is set by URL and will be loaded in the background.
 */
public class IconLabel extends JLabel implements ChangeListener
{
    private String imageURL;
    private ImageLoader loader;

    /** Constructor. Initially, the label is empty.
     */
    public IconLabel()
    {
        super();
        imageURL = null;
        loader = null;
    }

    /** This method gets called when the Icon has changed.
     *  Usually this means the Icon is in a futher loading
     *  stage now. React by setting the Icon.
     *  @param event Event containing the change source.
     */
    @Override
    public void stateChanged( ChangeEvent event )
    {
        if( loader != null && loader.getImage() != null ) {
            setIcon( new ImageIcon(loader.getImage()) );
        }

        repaint();
    }

    /** Sets the URL for the image to be shown.
     *  This will cause the IconLabel to be
     *  refreshed when the Icon is done loading.
     *  @param imageURL The new image URL.
     */
    public void setImageURL( String imageURL )
    {
        if( imageURL == null || !imageURL.equals( this.imageURL ) ) {
            setIcon( null );
            this.imageURL = imageURL;

            if( loader != null )
                loader.removeChangeListener( this );

            if( imageURL != null ) {
                loader = new ImageLoader( imageURL );
                loader.addChangeListener( this );
                loader.loadInBackground();
            }
        }
    }
}
