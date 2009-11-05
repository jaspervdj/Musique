/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.resources.I18nManager;
import musique.collectiondata.Collection;
import musique.collectiondata.Release;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;

/** A class that can be used to write a collection to a file.
 */
public class CollectionWriter
{
    private Collection collection;
    private File file;

    /** Constructor.
     *  @param collection Collection to write.
     *  @param file File to write to.
     */
    public CollectionWriter( Collection collection, File file )
    {
        this.collection = collection;
        this.file = file;
    }

    /** Writes the collection to the specified file.
     */
    public void write()
    {
        Element root = new Element("collection");

        Element info = new Element("info");
        info.addContent( collection.getInformation() );
        root.addContent( info );

        Element name = new Element("name");
        name.addContent( collection.getName() );
        root.addContent( name );

        /* Add every release element. */
        Element releases = new Element("releases");
        for( Release release: collection.getDataModel().getAllReleases() ) {
            Element releaseElement = new Element("release");
            releaseElement.setAttribute( "id", "" + release.getId() );

            Element title = new Element("title");
            title.addContent( release.getTitle() );
            releaseElement.addContent( title );
            
            Element year = new Element("year");
            year.addContent( release.getYear() );
            releaseElement.addContent( year );

            Element artist = new Element("artist");
            artist.addContent( release.getArtist() );
            releaseElement.addContent( artist );

            Element label = new Element("label");
            label.addContent( release.getLabel() );
            releaseElement.addContent( label );

            releases.addContent( releaseElement );
        }
        root.addContent( releases );

        /* Construct document for output. */
        Document document = new Document(root);
        XMLOutputter outputter = new XMLOutputter( Format.getPrettyFormat() );

        try{
            /* Open output stream to file. */
            FileOutputStream outputStream = new FileOutputStream( file );

            try{
                /* Write document. */
                outputter.output( document, outputStream );

            } finally {
                /* Close output stream. */
                outputStream.close();
            }

        /* Something went wrong. */
        } catch( IOException exception ) {
            I18nManager instance = I18nManager.getInstance();
            JOptionPane.showMessageDialog( null, 
                                           instance.getMessage("io_error"),
                                           instance.getMessage("error"),
                                           JOptionPane.ERROR_MESSAGE );
        }
    }
}
