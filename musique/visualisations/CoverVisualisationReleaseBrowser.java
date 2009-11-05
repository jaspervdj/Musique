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

/** A Browser with which the user can select a release.
 */
public class CoverVisualisationReleaseBrowser extends CoverVisualisationBrowser
{
    /** Constructor.
     *  @param model CoverVisualisationModel to browse.
     */
    public CoverVisualisationReleaseBrowser( CoverVisualisationModel model )
    {
        super( model );

        Release release = model.getRelease();
        if( release != null ) {
            label.setText( release.getTitle() );
            label.setIcon( ReleaseIconManager.getInstance().getReleaseIcon( release ) );
        }
    }

    @Override
    public ActionListener createPreviousActionListener()
    {
        return new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                model.previousRelease();
            }
        };
    }

    @Override
    public ActionListener createNextActionListener()
    {
        return new ActionListener() {
            public void actionPerformed( ActionEvent event ) {
                model.nextRelease();
            }
        };
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        previous.setEnabled( model.getCurrentRelease() > 0 );

        /* Put release icon and title in the label. */
        Release release = model.getRelease();
        if( release != null ) {
            label.setText( release.getTitle() );
            label.setIcon( ReleaseIconManager.getInstance().getReleaseIcon( release ) );
        }
        
        next.setEnabled( model.getCurrentRelease() + 1 < model.getNumberOfReleases() );
    }
}
