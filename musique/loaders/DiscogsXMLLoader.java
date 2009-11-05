/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import be.ugent.caagt.discogs.DiscogsManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import java.io.IOException;
/* To display error messages if something goes wrong. */

/** A {@link Loader} that can be used to load various
 *  Discogs XML documents.
 */
public abstract class DiscogsXMLLoader extends Loader
{
    static
    {
        /* Set the API key staically for the request operations. */
        DiscogsManager.setAPIKey( "68bf12f801" );
    }

    /** The Object to be loaded. */
    private Loadable loadable;

    /** The XML Document. */
    private Document document;

    /** Constructor.
     */
    public DiscogsXMLLoader( Loadable loadable )
    {
        this.loadable = loadable;
        loadable.setLoader( this );

        document = null;
    }

    /** Get the document associated with the XMLLoader, or
     *  null if it hasn't been loaded yet.
     *  @return This object's document.
     */
    public Document getDocument()
    {
        return document;
    }

    /** Load data. Here, the xml document is loaded and set for
     *  further use. Subclasses will probably make use of this
     *  by calling {@code super.load()}.
     */
    @Override
    public void load()
    {
        try {
            document = loadDocument();
            
        /* Setting the loadable to valid will cause the error to
         * be handled properly later. */
        } catch( IOException exception ) {
            loadable.setValid( false );
        } catch( JDOMException exception ) {
            loadable.setValid( false );
        }
    }

    /** A function that needs to be overwritten for specific
     *  loaders. It should return the Document corresponding
     *  to the object.
     *  @return The Document corresponding to the Loader.
     */
    public abstract Document loadDocument() throws IOException, JDOMException;

    /** A function that needs to be overwritten for specific
     *  loaders. It should return the main element with
     *  all the information in it. This is not necessarily
     *  the root element.
     *  @return The main element in the XML.
     */
    public abstract Element getMainElement();

    /** Load an image URL from an element. The function assumes
     *  a {@code <images> <image uri="url" /> </images>} structure
     *  in the main element.
     *  @param main The main element.
     *  @return The image URL, or null if not found.
     */
    public String loadImageURL( Element main ){
        return loadImageURL( main, false );
    }

    /** Load an image URL from an element. The function assumes
     *  a {@code <images> <image uri="url" /> </images>} structure
     *  in the main element.
     *  @param main The main element.
     *  @param thumb If we should load a small image (which is quicker...)
     *  @return The image URL, or null if not found.
     */
    public String loadImageURL( Element main, boolean thumb ){
        Element images = main.getChild("images");
        if( images != null ) {
            Element image = images.getChild("image");
            if( image != null ) {
                return image.getAttributeValue( thumb? "uri150":"uri" );
            }
        }

        return null;
    }

    @Override
    public boolean isDone()
    {
        return loadable.isLoaded();
    }
}
