/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import java.awt.Component;
import java.awt.Color;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/** A CellRenderer used to get prettier lists for the
 *  TabbedVisualisation.
 */
public class TabbedVisualisationListCellRenderer extends DefaultListCellRenderer
{
    /** Constructor.
     */
    public TabbedVisualisationListCellRenderer()
    {
        super();
    }

    /** Override the function that creates a label to draw the
     *  cell, to create a prettier list.
     */
    @Override
    public Component getListCellRendererComponent( JList list, Object value, int index,
                                                   boolean isSelected, boolean cellHasFocus )
    {
        super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );

        Release release = (Release) value;
        setIcon( ReleaseIconManager.getInstance().getReleaseIcon( release ) );

        if( isSelected )
            setBackground( list.getSelectionBackground() );
        else
            setBackground( index%2==0? Color.WHITE : Color.LIGHT_GRAY );

        return this;
    }
}
