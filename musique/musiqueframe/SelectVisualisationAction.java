/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.musiqueframe;

import musique.visualisations.VisualisationSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

/** An action to select a certain visualisation.
 */
public class SelectVisualisationAction extends AbstractAction
{
    VisualisationSelectionModel model;
    private int number;

    /** Constructor.
     *  @param model VisualisationSelectionModel to control.
     *  @param number The visualisation to select whn executed.
     */
    public SelectVisualisationAction( VisualisationSelectionModel model, int number )
    {
        super( model.getVisualisationName(number) );
        this.model = model;
        this.number = number;

        char mnemonic = model.getVisualisationName(number).toLowerCase().charAt(0);
        putValue( MNEMONIC_KEY, KeyEvent.VK_A + (mnemonic - 'a') );
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        model.setVisualisation( number );
    }
}
