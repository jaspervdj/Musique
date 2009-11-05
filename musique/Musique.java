/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique;

import java.awt.EventQueue;
import musique.musiqueframe.MusiqueFrame;

/** Main class of the musique project.
 */
public class Musique
{
    /** Main method of the musique project.
     *  Starts the application.
     */
    public static void main( String[] args )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run() {
                MusiqueFrame frame = new MusiqueFrame();
            }
        } );
    }
}
