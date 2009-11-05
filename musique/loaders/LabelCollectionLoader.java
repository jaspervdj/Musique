/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.collectiondata.Collection;
import be.ugent.caagt.discogs.DiscogsManager;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import java.io.IOException;

/** A Loader class used to load label colletions.
 */
public class LabelCollectionLoader extends CollectionLoader
{
    /** The artist name. */
    private String label;

    /** Constructor.
     */
    public LabelCollectionLoader( Collection collection, String label )
    {
        super( collection );
        this.label = label;
    }

    /** An implementation of loadDocument to load an
     *  label document for the artist name set in the
     *  constructor.
     *  @return The corresponding document.
     */
    @Override
    public Document loadDocument() throws IOException, JDOMException
    {
        return DiscogsManager.getLabelXML( label );
    }

    /** Get the main element for the label XML.
     *  @return The main element.
     */
    @Override
    public Element getMainElement()
    {
        Element root = getDocument().getRootElement();
        if( root != null )
            return root.getChild("label");
        else
            return null;
    }
}
