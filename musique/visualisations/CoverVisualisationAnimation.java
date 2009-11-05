/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/** A class used by CoverVisualisationModel to get animations.
 */
public class CoverVisualisationAnimation implements ActionListener
{
    private CoverVisualisationModel model;
    private Timer timer;
    boolean next;
    int ticks;

    /** Constructor.
     *  @param model CoverVisualisationModel to animate.
     */
    public CoverVisualisationAnimation( CoverVisualisationModel model )
    {
        this.model = model;
        timer = new Timer( 50, this );
        next = false;
    }

    /** Start the animation.
     *  @param next If we are going to the next release.
     */
    public void start( boolean next )
    {
        this.next = next;

        /* Kill previous animation. */
        timer.stop();

        /* Start new animation. */
        ticks = 0;
        model.setAnimationOffset( next? 1.0:-1.0 );
        timer.start();
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        ticks++;
        double offset = 1.0 - ((double)ticks)/10;
        model.setAnimationOffset( next? offset:-offset );
        if( ticks >= 10 )
            timer.stop();
    }
}
