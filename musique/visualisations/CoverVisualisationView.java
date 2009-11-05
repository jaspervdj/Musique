/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.resources.I18nManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JPanel;
import java.util.List;
import java.util.Map;

/** The actual view with the covers of the CoverVisualisation.
 */
public class CoverVisualisationView extends JPanel
                                    implements ChangeListener
{
    private static final int DISPLAYED_COVERS = 5;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private CoverVisualisationModel model;

    /** Constructor.
     *  @param model Model to be visualised.
     */
    public CoverVisualisationView( CoverVisualisationModel model )
    {
        this.model = model;

        setOpaque( true );
        setBackground( BACKGROUND_COLOR );
        setToolTipText( I18nManager.getInstance().getMessage("covervisualisation_tooltip") );

        model.addChangeListener( this );

        /* Use a separate class to listen to events. */
        CoverVisualisationEventReceiver eventReceiver = new CoverVisualisationEventReceiver( model );
        addMouseListener( eventReceiver );
        addMouseWheelListener( eventReceiver );
    }

    /** A function to get the Y Position at a certain offset.
     *  @param offset Offset to determine the Y Position for.
     *  @return A double between -1.0 and 1.0.
     */
    public double getYPosition( double offset )
    {
        double position = Math.sqrt( Math.abs(offset) / DISPLAYED_COVERS );
        return offset < 0? -position : position;
    }

    /** A function to get the scale at a certain offset.
     *  @param offset Offset to determine the scale for.
     *  @return A double between 0.0 and 1.0.
     */
    public double getScale( double offset )
    {
        return 1.0 - Math.abs(offset) / DISPLAYED_COVERS;
    }

    /** Paints a cover.
     *  @param graphics Graphical object to draw on.
     *  @param indexOffset Index offset of the cover to draw.
     */
    private void paintCover( Graphics2D graphics, int indexOffset )
    {
        Image image = model.getImage( indexOffset );

        /* Draw only valid images. */
        if( image != null ) {

            /* Real offset includes the animation offset. */
            double offset = indexOffset + model.getAnimationOffset();

            /* Translate to the image position. */
            AffineTransform transform = AffineTransform.getTranslateInstance( getWidth()/2, getHeight()/2 + getYPosition( offset ) *getHeight()/2 );

            double scale = getScale( offset );

            /* Scale to the image size. */
            transform.scale( scale, scale );

            /* Translate to the image destination upper left corner. */
            transform.translate( -image.getWidth(null)/2, -image.getHeight(null)/2 );

            /* Now blit the image. */
            graphics.drawImage( image, transform, null );
        }
    }

    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        Graphics2D graphics = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        /* Images below and above main image. */
        for( int i=DISPLAYED_COVERS; i>0; i-- ) {
            paintCover( graphics, i );
            paintCover( graphics, -i );
        }

        /* We draw a dark overlay, so the main image
         * will appear brighter. */
        graphics.setColor( new Color( BACKGROUND_COLOR.getRed(),
                                      BACKGROUND_COLOR.getGreen(), 
                                      BACKGROUND_COLOR.getBlue(),
                                      100 ) );

        graphics.fillRect( 0, 0, width, height );

        /* The main image. */
        paintCover( graphics, 0 );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        repaint();
    }
}
