/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.Release;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** A Visualisation that represents the releases in a
 *  tree, grouped by year.
 */
public class TreeVisualisation extends Visualisation
                               implements TreeSelectionListener, ChangeListener
{
    private JTree tree;
    private TreeVisualisationTreeModel treeModel;

    /** Constructor.
     *  @param dataModel CollectionDataModel to be visualised.
     */
    public TreeVisualisation( CollectionDataModel dataModel )
    {
        super( dataModel );

        setLayout( new BorderLayout() );
        treeModel = new TreeVisualisationTreeModel( getDataModel() );

        tree = new JTree( treeModel );

        /* We want a pretty tree. */
        tree.setCellRenderer( new TreeVisualisationTreeCellRenderer() );

        /* Only one release can be selected simultaneously. */
        tree.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );

        /* Hide the tree root. */
        tree.setRootVisible( false );
        tree.expandPath( new TreePath( treeModel.getRoot() ) );

        add( new JScrollPane(tree), BorderLayout.CENTER );

        getDataModel().getSelectionModel().addChangeListener( this );
        tree.addTreeSelectionListener( this );
    }

    @Override
    public void yearAdded( String year, int index )
    {
        tree.expandPath( new TreePath( treeModel.getRoot() ) );
    }

    @Override
    public void valueChanged( TreeSelectionEvent event )
    {
        DefaultMutableTreeNode releaseNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
        if( releaseNode.isLeaf() && releaseNode.getUserObject() instanceof Release )
            getSelectionModel().setSelection( (Release) releaseNode.getUserObject() );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        Release release = getSelectionModel().getSelection();
        if( release != null ) {
            TreePath path = new TreePath( treeModel.getPathToRoot( treeModel.getTreeNodeByRelease( release ) ) );
            tree.setSelectionPath( path );
            tree.expandPath( path );
        }
    }
}
