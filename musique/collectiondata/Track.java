/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

/** A Track from a music release.
 */
public class Track
{
    private String title;
    private String duration;

    /** Constructor.
     *  @param title Title for the track.
     *  @param duration Duration of the track (preferably in mm:ss format.)
     */
    public Track( String title, String duration )
    {
        this.title = title;
        this.duration = duration;
    }

    /** Fetch the title of this track.
     *  @return The title of this track.
     */
    public String getTitle()
    {
        return title;
    }

    /** Fetch the duration of this track.
     *  @return The duration of this track.
     */
    public String getDuration()
    {
        return duration;
    }
}
