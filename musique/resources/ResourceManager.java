/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.resources;

import java.net.URL;
import javax.swing.ImageIcon;

/** A simple class with static methods to obtain resources
 *  from the class path.
 */
public class ResourceManager
{
    /** We create a protected constructor so nobody
     *  accidentally creates another instance.
     */
    protected ResourceManager()
    {
    }

    /** Convert a fileName to an URL. The fileName should be relative
     *  to the resources directory.
     *  @param fileName File to look for.
     *  @return The URL of the file.
     */
    public URL getResource( String fileName )
    {
        return ResourceManager.class.getResource( "/musique/resources/" + fileName );
    }

    /** Construct an ImageIcon from a fileName.
     *  @param fileName The basename of the file, without the ".png" extension.
     *  @return An ImageIcon with the given image.
     */
    public ImageIcon getIcon( String fileName )
    {
        return new ImageIcon( getResource( fileName + ".png" ) );
    }

    /** A class to hold a singleton.
     */
    private static class ResourceManagerHolder
    {
        public static final ResourceManager INSTANCE = new ResourceManager();
    }

    /** Get the instance of this singleton class.
     *  @return The ResourceManager instance.
     */
    public static ResourceManager getInstance()
    {
        return ResourceManagerHolder.INSTANCE;
    }
}
