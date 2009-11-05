/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Release;
import musique.collectiondata.Track;
import musique.resources.I18nManager;
import javax.swing.table.AbstractTableModel;

/** A TableModel to show tracks from a certain {@link Release}.
 */
public class ReleaseTracksTableModel extends AbstractTableModel
{
    private Track[] tracks;

    /** Note that these are the I18nManager keys, not the actual names. */
    private final static String[] COLUMN_NAMES = { "number", "title", "duration" };

    /** Constructor.
     *  @param release Release to create a track table for.
     */
    public ReleaseTracksTableModel( Release release )
    {
        if( release != null )
            tracks = release.getTracks();
        else
            tracks = null;
    }

    /** Fetch the number of rows in this model.
     *  @return The number of rows.
     */
    @Override
    public int getRowCount()
    {
        if( tracks == null )
            return 0;
        else
            return tracks.length;
    }

    /** Fetch the number of columns in this model.
     *  @return The number of columns.
     */
    @Override
    public int getColumnCount()
    {
        return 3;
    }

    /** Fetch the value at the given position. This
     *  would be the number, title or duration for
     *  a certain track.
     *  @param row Row of the requested value.
     *  @param column Column of the requested value.
     *  @return The corresponding data.
     */
    @Override
    public Object getValueAt( int row, int column )
    {
        if( tracks == null ) {
            return null;
        } else if( column == 0 ) {
            return row + 1;
        } else if( column == 1 ) {
            return tracks[row].getTitle();
        } else if( column == 2 ) {
            return tracks[row].getDuration();
        } else {
            return null;
        }
    }

    /** Fetch the column name for a given column.
     *  @param column Column to get the name for.
     *  @return Name of the requested column.
     */
    @Override
    public String getColumnName( int column )
    {
        return I18nManager.getInstance().getMessage( COLUMN_NAMES[column] );
    }
}
