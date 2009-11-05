/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.Release;
import musique.resources.ResourceManager;
import javax.swing.Icon;

/** A static class that is used to get icons for
 *  various release formats.
 */
public class ReleaseIconManager
{
    /** The format names. */
    private static final String[] formats = { "cd", "vinyl", "cassette" };

    /** Regexes to determine the formats. These regexes should match
     *  the {@code} formats array. */
    private static final String[] regexes = { ".*cd.*", ".*(vinyl)|(lp)|(12\").*", ".*(cassette)|(k7).*" };

    /** An array containing the actual icons. */
    private Icon[] icons;

    /** An icon for unknown release formats. */
    private Icon unknownIcon;

    /** Protected constructor.
     */
    protected ReleaseIconManager()
    {
        icons = new Icon[formats.length];
        for( int i=0; i<icons.length; i++ )
            icons[i] = ResourceManager.getInstance().getIcon( formats[i] );

        unknownIcon = ResourceManager.getInstance().getIcon("unknown");
    }

    /** Get an icon for a certain release.
     *  @param release Release to get an icon for.
     *  @return An icon that matches the release.
     */
    public Icon getReleaseIcon( Release release )
    {
        if( release != null && release.getFormat() != null ) {

            /* Try to match regexes. */
            String format = release.getFormat().toLowerCase();
            int index = 0;
            while( index<regexes.length && !format.matches(regexes[index]) )
                index++;

            /* An icon was found. */
            if( index < regexes.length )
                return icons[index];
        }

        /* No icon found - return the unknownIcon. */
        return unknownIcon;
    }

    /** A class to hold a singleton.
     */
    private static class ReleaseIconManagerHolder
    {
        public static final ReleaseIconManager INSTANCE = new ReleaseIconManager();
    }

    /** Get the instance of this singleton class.
     *  @return The ReleaseIconManager instance.
     */
    public static ReleaseIconManager getInstance()
    {
        return ReleaseIconManagerHolder.INSTANCE;
    }
}
