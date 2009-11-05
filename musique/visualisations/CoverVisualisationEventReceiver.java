/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

/** A class that receives mouse events for
 *  a CoverVisualisationModel.
 */
public class CoverVisualisationEventReceiver extends MouseAdapter
                                             implements MouseWheelListener
{
    private CoverVisualisationModel model;

    /** Constructor.
     *  @param model CoverVisualisationModel to control.
     */
    public CoverVisualisationEventReceiver( CoverVisualisationModel model )
    {
        this.model = model;
    }

    @Override
    public void mouseClicked( MouseEvent event )
    {
        if( event.getButton() == MouseEvent.BUTTON1 )
            model.nextRelease();
        else
            model.previousRelease();
    }

    @Override
    public void mouseWheelMoved( MouseWheelEvent event )
    {
        if( event.getWheelRotation() > 0 )
            model.nextRelease();
        else
            model.previousRelease();
    }
}
