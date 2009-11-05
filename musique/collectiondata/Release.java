/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import musique.resources.I18nManager;
import musique.loaders.Loadable;

/** This class represents a music release, eg an album.
 */
public class Release extends Loadable implements Comparable
{
    /** Reference id of the release. */
    private int id;

    /** The title of the release. */
    private String title;

    /** The year in which this was released. Note that we use
     *  a String to represent a year instead of an int, because
     *  that way we could easier change 1962 to "early sixties" etc.
     */
    private String year;

    /** An URL of an image representing the Release. */
    private String imageURL;

    /** An URL of a small image. */
    private String smallImageURL;

    /** The release format. */
    private String format;

    /** The artist of this release. */
    private String artist;

    /** The label of this release. */
    private String label;

    /** A list of tracks in this release. */
    private Track[] tracks;

    /** Creates a new Release.
     *  @param title Title for the Release.
     */
    public Release( int id )
    {
        this.id = id;
        title = I18nManager.getInstance().getMessage("unknown");
        year = I18nManager.getInstance().getMessage("unknown");
        imageURL = null;
        smallImageURL = null;
        format = I18nManager.getInstance().getMessage("unknown");
        artist = I18nManager.getInstance().getMessage("unknown");
        label = I18nManager.getInstance().getMessage("unknown");
        tracks = new Track[0];
    }

    /** @return This Release's id.
     */
    public int getId()
    {
        return id;
    }

    /** @return The title of this Release.
     */
    public String getTitle()
    {
        return title;
    }

    /** Set the title for this Release.
     *  @param title The new title.
     */
    public void setTitle( String title )
    {
        if( title != null && !title.equals( this.title ) ) {
            this.title = title;
            fireStateChanged();
        }
    }

    /** @return The year in which this was released.
     */
    public String getYear()
    {
        return year;
    }

    /** Set the year for this Release.
     *  @param year The new year.
     */
    public void setYear( String year )
    {
        if( year != null && !year.equals( this.year ) ) {
            this.year = year;
            fireStateChanged();
        }
    }

    /** @return The imageURL for this Release.
     */
    public String getImageURL()
    {
        return imageURL;
    }

    /** Set the imageURL for this Release.
     *  @param imageURL The new imageURL.
     */
    public void setImageURL( String imageURL )
    {
        if( imageURL != null && !imageURL.equals( this.imageURL ) ) {
            this.imageURL = imageURL;
            fireStateChanged();
        }
    }

    /** @return The smallImageURL for this Release.
     */
    public String getSmallImageURL()
    {
        return smallImageURL;
    }

    /** Set the smallImageURL for this Release.
     *  @param smallImageURL The new smallImageURL.
     */
    public void setSmallImageURL( String smallImageURL )
    {
        if( smallImageURL != null && !smallImageURL.equals( this.smallImageURL ) ) {
            this.smallImageURL = smallImageURL;
            fireStateChanged();
        }
    }

    /** @return The format in which this was released.
     */
    public String getFormat()
    {
        return format;
    }

    /** Set the format for this Release.
     *  @param format The new format.
     */
    public void setFormat( String format )
    {
        if( format != null && !format.equals( this.format ) ) {
            this.format = format;
            fireStateChanged();
        }
    }

    /** @return The artist of this release.
     */
    public String getArtist()
    {
        return artist;
    }

    /** Set the artist for this Release.
     *  @param artist The new artist.
     */
    public void setArtist( String artist )
    {
        if( artist != null && !artist.equals( this.artist ) ) {
            this.artist = artist;
            fireStateChanged();
        }
    }

    /** @return The label of this release.
     */
    public String getLabel()
    {
        return label;
    }

    /** Set the label for this Release.
     *  @param label The new label.
     */
    public void setLabel( String label )
    {
        if( label != null && !label.equals( this.label ) ) {
            this.label = label;
            fireStateChanged();
        }
    }

    /** @return An array containing the tracks on this release.
     */
    public Track[] getTracks()
    {
        return tracks;
    }

    /** Set a new set of tracks on this release.
     *  @param tracks The new tracks.
     */
    public void setTracks( Track[] tracks )
    {
        if( this.tracks != tracks ) {
            this.tracks = tracks;
            fireStateChanged();
        }
    }

    /** Check equality to another Release.
     */
    @Override
    public boolean equals( Object other )
    {
        if( other instanceof Release ) {
            Release release = (Release) other;
            return this.id == release.id;
        } else {
            return false;
        }
    }

    /** Comparison operator.
     */
    @Override
    public int compareTo( Object other )
    {
        if( other instanceof Release ) {
            Release release = (Release) other;
            if( release == null )
                return -1;
            else
                return title.compareToIgnoreCase( release.getTitle() );
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    /** @return A prettier String.
     */
    @Override
    public String toString()
    {
        return getTitle();
    }
}
