/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.CollectionSelectionModel;
import musique.collectiondata.Release;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/** A {@link Visualisation} that allows the user to browse
 *  the releases using tabs, where each tabs contains a list
 *  of releases. The tabs are organised per year.
 */
public class TabbedVisualisation extends Visualisation
                                 implements ListSelectionListener, ChangeListener
{
    /** The JTabbedPane for keeping the tabs. */
    private JTabbedPane tabbedPane;

    /** We also keep the tabs in a Map for convenience reasons. */
    private Map<String,JScrollPane> tabs;

    /** We also keep the list models in a Map for convenience reasons. */
    private Map<String,TabbedVisualisationListModel> listModels;

    /** The last selected year. We need this so we can make sure
     *  only one item is selected in the different lists.  */
    private String lastSelectedYear;

    /** Constructor.
     *  @param dataModel CollectionDataModel to be visualised.
     */
    public TabbedVisualisation( CollectionDataModel dataModel )
    {
        super( dataModel );
        tabs = new HashMap<String,JScrollPane>();
        listModels = new HashMap<String,TabbedVisualisationListModel>();
        lastSelectedYear = null;

        setLayout( new BorderLayout() );
        tabbedPane = new JTabbedPane();

        for( String year: getDataModel().getYears() )
            addYearTab( year );

        tabbedPane.setTabLayoutPolicy( JTabbedPane.SCROLL_TAB_LAYOUT );

        add( tabbedPane, BorderLayout.CENTER );

        getDataModel().getSelectionModel().addChangeListener( this );
    }

    /** Adds a tab for a certain year.
     *  @param year Year to be added.
     *  @param index Index where the tab should be added.
     */
    private void addYearTab( String year, int index )
    {
        /* Create a new custom list model. */
        TabbedVisualisationListModel listModel = new TabbedVisualisationListModel( year, getDataModel() );

        /* Create a list and set the custom properties. Note
         * that we are listening to the list ourselves. */
        JList list = new JList( listModel );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        list.addListSelectionListener( this );
        list.setCellRenderer( new TabbedVisualisationListCellRenderer() );

        /* Add the list to the tabbed pane and to the maps
         * we keep. */
        JScrollPane scrollPane = new JScrollPane( list );
        tabs.put( year, scrollPane );
        listModels.put( year, listModel );
        int pos = index > tabbedPane.getTabCount() ? tabbedPane.getTabCount() : index;
        tabbedPane.insertTab( year, null, scrollPane, null, pos );
    }

    /** Adds a tab for a certain year.
     *  @param year Year to be added.
     */
    public void addYearTab( String year )
    {
        addYearTab( year, tabbedPane.getTabCount() );
    }

    @Override
    public void yearAdded( String year, int index )
    {
        addYearTab( year, index );
    }

    @Override
    public void releaseAdded( Release release, int index )
    {
        TabbedVisualisationListModel listModel = listModels.get( release.getYear() );
        if( listModel != null )
            listModel.releaseAdded( release, index );
    }

    @Override
    public void yearRemoved( String year )
    {
        tabbedPane.remove( tabs.get( year ) );
    }

    @Override
    public void releaseRemoved( Release release, String year )
    {
        TabbedVisualisationListModel listModel = listModels.get( year );
        listModel.releaseRemoved( release );
    }

    @Override
    public void valueChanged( ListSelectionEvent event )
    {
        JList source = (JList) event.getSource();
        getDataModel().getSelectionModel().setSelection( (Release)source.getSelectedValue() );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        CollectionSelectionModel selectionModel = getDataModel().getSelectionModel();
        Release release = selectionModel.getSelection();

        if( release != null ) {

            /* Erase the old selection when it's in a different list.
             * We want only one item selected. */
            if( lastSelectedYear != null && lastSelectedYear != release.getYear() ) {
                JList list = (JList) tabs.get( lastSelectedYear ).getViewport().getView();
                list.clearSelection();
            }

            /* Set the new selection - mainly used when the selection
             * is changed through another visualisation. Otherwise,
             * this really won't do anything. */
            tabbedPane.setSelectedComponent( tabs.get(release.getYear()) );
            JList list = (JList) tabs.get( release.getYear() ).getViewport().getView();
            list.setSelectedValue( release, true );
            lastSelectedYear = release.getYear();
        }
    }
}
