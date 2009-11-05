/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.Icon;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

/** A CellRenderer for the TreeVisualisation. This way, we can have
 *  a prettier tree with icons etc.
 */
public class TreeVisualisationTreeCellRenderer extends DefaultTreeCellRenderer
{
    /** Constructor.
     */
    public TreeVisualisationTreeCellRenderer()
    {
        super();
    }

    /** The function used to draw the Tree elements. Override it
     *  to get custom icons.
     */
    @Override
    public Component getTreeCellRendererComponent( JTree tree, Object value, boolean selected,
                                                   boolean expanded, boolean leaf, int row,
                                                   boolean hasFocus )
    {
        super.getTreeCellRendererComponent( tree, value, selected, expanded, leaf, row, hasFocus );

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        if( node.getUserObject() instanceof Release ) {
            Release release = (Release) node.getUserObject();

            Icon icon = ReleaseIconManager.getInstance().getReleaseIcon( release );
            setIcon( icon );
        }

        return this;
    }
}
