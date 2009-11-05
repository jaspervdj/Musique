/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.resources.I18nManager;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.MediaTracker;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
/* To display error messages if something goes wrong. */
import javax.swing.JOptionPane;
import java.util.Map;
import java.util.HashMap;

/** A {@link Loader} class that can be used to load
 *  images in the foreground or background.
 */
public class ImageLoader extends Loader
{
    /** Cache. */
    private static Map<String,Image> cache = new HashMap<String,Image>();

    /** The URL to be loaded. */
    private String url;

    /** We use an ImageIcon to load the image. */
    private ImageIcon icon;

    /** Maximum image size. */
    private int maxSize;

    /** Constructor.
     *  @param url URL to load.
     *  @param maxSize Maximum size of the image (width or height).
     */
    public ImageLoader( String url, int maxSize )
    {
        this.url = url;
        this.maxSize = maxSize;
    }

    /** Constructor.
     *  @param url URL to load.
     */
    public ImageLoader( String url )
    {
        this( url, 200 );
    }

    /** Function in which the loading happens. Use loadInForeground()
     *  or loadInBackground() instead.
     */
    @Override
    public void load()
    {
        if( cache.get( url ) == null ) {
            try{
                ImageIcon tempIcon = new ImageIcon( new URL( url ) );
                Image image = tempIcon.getImage();

                /* Scale down when too large. */
                int width = image.getWidth( null );
                int height = image.getHeight( null );

                if( width > height )
                    image = getScaledImage( image, maxSize, -1 );
                else
                    image = getScaledImage( image, -1, maxSize );

                icon = new ImageIcon( image );
                cache.put( url, image );

            } catch( MalformedURLException exception ) {
                JOptionPane.showMessageDialog( null, I18nManager.getInstance().getMessage("url_error"), "MalformedURLException Error", JOptionPane.ERROR_MESSAGE );
            }
        } else {
            icon = new ImageIcon( cache.get( url ) );
        }
    }

    public Image getScaledImage( Image original, int width, int height )
    {
        double aspect = (double) original.getWidth(null) / original.getHeight(null);
        width = (int)( width < 0 ? aspect * height : width );
        height = (int)( height < 0 ? width / aspect : height );

        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        graphics.drawImage( original, 0, 0, width, height, null);
        graphics.dispose();
        return image;
    }

    /** Obtain the result of the loading
     *  @return The loaded image.
     */
    public Image getImage()
    {
        return isDone() ? icon.getImage() : null;
    }

    @Override
    public boolean isDone()
    {
        return (icon != null) && (icon.getImageLoadStatus() == MediaTracker.COMPLETE);
    }
}
