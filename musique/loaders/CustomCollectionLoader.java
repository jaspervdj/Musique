/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.collectiondata.Collection;
import musique.resources.I18nManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

/** A Loader class used to load custom colletions.
 */
public class CustomCollectionLoader extends CollectionLoader
{
    /** File to load from. */
    private File file;

    /** Constructor.
     */
    public CustomCollectionLoader( Collection collection, File file )
    {
        super( collection );
        this.file = file;
    }

    /** An implementation of loadDocument to load a custom
     *  document from the hard drive.
     *  @return The corresponding document.
     */
    @Override
    public Document loadDocument() throws IOException, JDOMException
    {
        return new SAXBuilder().build( new FileInputStream( file ) );
    }

    /** Load the Collection information from an element. The
     *  information is searched for in a {@code <info>}
     *  element.
     *  @param main Element that contains a information element.
     *  @return The information, or null if not found.
     */
    @Override
    public String loadInformation( Element main )
    {
        String information = main.getChildTextNormalize( "info" );
        return information != null ? information : I18nManager.getInstance().getMessage("no_information");
    }

    /** Get the main element for the root XML.
     *  @return The main element.
     */
    @Override
    public Element getMainElement()
    {
        Element root = getDocument().getRootElement();
        return root;
    }
}
