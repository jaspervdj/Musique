/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import musique.collectiondata.CollectionDataListener;
import musique.collectiondata.CollectionDataModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/** A TreeModel used with the TreeVisualisation.
 */
public class TreeVisualisationTreeModel extends DefaultTreeModel
                                        implements CollectionDataListener
{
    /* Convenience. */
    private DefaultMutableTreeNode root;
    /* Keep them in a map, too, for convenience reasons. */
    private Map<String,DefaultMutableTreeNode> yearNodes;
    /* Another map. */
    private Map<Release,DefaultMutableTreeNode> releaseNodes;

    /** Constructor.
     *  @param dataModel CollectionDataModel to be shown in the tree.
     */
    public TreeVisualisationTreeModel( CollectionDataModel dataModel )
    {
        super( new DefaultMutableTreeNode() );
        yearNodes = new HashMap<String,DefaultMutableTreeNode>();
        releaseNodes = new HashMap<Release,DefaultMutableTreeNode>();
        root = (DefaultMutableTreeNode) getRoot();

        /* Add the release intially present. */
        for( String year: dataModel.getYears() ) {

            DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode( year );
            List<Release> list = dataModel.getReleasesByYear( year );

            for( Release release: list ) {
                DefaultMutableTreeNode releaseNode = new DefaultMutableTreeNode( release );
                insertNodeInto( releaseNode, yearNode, yearNode.getChildCount() );
                releaseNodes.put( release, releaseNode );
            }

            yearNodes.put( year, yearNode );
            insertNodeInto( yearNode, root, root.getChildCount() );
        }

        dataModel.addCollectionDataListener( this );
    }

    /** Finds the DefaultMutableTreeNode that contains the given Release.
     *  @param release Release to look for.
     *  @return The corresponding DefaultMutableTreeNode, or null if not found.
     */
    public DefaultMutableTreeNode getTreeNodeByRelease( Release release )
    {
        return releaseNodes.get( release );
    }

    @Override
    public void yearAdded( String year, int index )
    {
        DefaultMutableTreeNode yearNode = new DefaultMutableTreeNode( year );
        yearNodes.put( year, yearNode );
        int pos = index > root.getChildCount() ? root.getChildCount() : index;
        insertNodeInto( yearNode, root, pos );
    }

    @Override
    public void releaseAdded( Release release, int index )
    {
        DefaultMutableTreeNode yearNode = yearNodes.get( release.getYear() );
        DefaultMutableTreeNode releaseNode = new DefaultMutableTreeNode( release );
        int pos = index > yearNode.getChildCount() ? yearNode.getChildCount() : index;
        insertNodeInto( releaseNode, yearNode, pos );
        releaseNodes.put( release, releaseNode );
    }

    @Override
    public void yearRemoved( String year )
    {
        removeNodeFromParent( yearNodes.get( year ) );
    }

    @Override
    public void releaseRemoved( Release release, String year )
    {
        DefaultMutableTreeNode releaseNode = releaseNodes.get( release );
        if( releaseNode.getParent() != null )
            removeNodeFromParent( releaseNode );
    }
}
