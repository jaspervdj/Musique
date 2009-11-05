/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.visualisations.VisualisationSelectionModel;
import musique.resources.I18nManager;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

/** A menu containing settings.
 */
public class SettingsMenu extends JMenu
{
    /** Constructor.
     */
    public SettingsMenu( VisualisationSelectionModel visualisationSelectionModel )
    {
        super( I18nManager.getInstance().getMessage("settings") );
        setMnemonic( I18nManager.getInstance().getMnemonic("settings") );

        ButtonGroup group = new ButtonGroup();
        for( int i=0; i<visualisationSelectionModel.getNumberOfVisualisations(); i++ ) {
            JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem( new SelectVisualisationAction( visualisationSelectionModel, i ) );
            group.add( radioButton );
            add( radioButton );

            if( i == visualisationSelectionModel.getVisualisation() )
                radioButton.setSelected( true );
        }        
    }
}
