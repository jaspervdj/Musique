/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.resources.I18nManager;
import musique.collectiondata.Collection;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import java.io.IOException;

/** A collection loader that doesn't really do anything, because
 *  we want a new, empty collection.
 */
public class NewCollectionLoader extends CollectionLoader
{
    private static int collectionNumber = 0;

    /** Constructor.
     *  @param collection The Collection to start loading.
     */
    public NewCollectionLoader( Collection collection )
    {
        super( collection );
        collectionNumber++;
    }

    /** Load the new collection. This simply creates an empty
     *  collection and gives it a new number.
     */
    @Override
    public void load()
    {
        Collection collection = getCollection();
        Object[] arguments = { new Integer( collectionNumber ) };
        collection.setName( I18nManager.getInstance().getMessage( "new_collection_name", arguments ) );
        collection.setInformation( I18nManager.getInstance().getMessage( "new_collection_information", arguments ) );
        collection.setLoadState( 100 );
    }

    @Override
    public Document loadDocument() throws IOException, JDOMException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element getMainElement()
    {
        throw new UnsupportedOperationException();
    }
}
