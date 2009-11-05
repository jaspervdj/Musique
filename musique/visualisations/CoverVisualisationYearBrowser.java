/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.Icon;

/** A Browser with which the user can select a year.
 */
public class CoverVisualisationYearBrowser extends CoverVisualisationBrowser
{
    /** Constructor.
     *  @param model CoverVisualisationModel to browse.
     */
    public CoverVisualisationYearBrowser( CoverVisualisationModel model )
    {
        super( model );

        Release release = model.getRelease();
        if( release != null )
            label.setText( release.getYear() );
    }

    @Override
    public ActionListener createPreviousActionListener()
    {
        return new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                model.previousYear();
            }
        };
    }

    @Override
    public ActionListener createNextActionListener()
    {
        return new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                model.nextYear();
            }
        };
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        previous.setEnabled( model.getCurrentYear() > 0 );

        /* Use the release year as label. */
        Release release = model.getRelease();
        if( release != null )
            label.setText( release.getYear() );
        
        next.setEnabled( model.getCurrentYear() + 1 < model.getNumberOfYears() );
    }
}
