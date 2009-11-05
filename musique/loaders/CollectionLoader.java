/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.collectiondata.Collection;
import musique.collectiondata.Release;
import musique.resources.I18nManager;
import org.jdom.Document;
import org.jdom.Element;
import java.util.List;

/** An {@link XMLLoader} user to load various collection
 *  types.
 */
public abstract class CollectionLoader extends DiscogsXMLLoader
{
    /** The Collection we are dealing with. */
    private Collection collection;

    /** Constructor.
     *  @param collection The Collection to start loading.
     */
    public CollectionLoader( Collection collection )
    {
        super( collection );
        this.collection = collection;
    }

    /** Get the associated Collection.
     *  @return The collection we are loading.
     */
    public Collection getCollection()
    {
        return collection;
    }

    /** Loads all collection data for the collection.
     */
    @Override
    public void load()
    {
        /* Load the Document. */
        super.load();

        /* Get the main element. */
        if( getDocument() != null ) {
            Element main = getMainElement();

            /* Extract information. */
            if( main != null ) {
                collection.setName( loadName( main ) );
                collection.setInformation( loadInformation( main ) );
                collection.setImageURL( loadImageURL(main) );
                fetchReleases( main );
            }
        }

        collection.setLoadState( 100 );
    }

    /** Load the Collection name from an element
     *  @param main Element that contains a name element.
     *  @return The name, or null if not found.
     */
    public String loadName( Element main )
    {
        String name = main.getChildTextNormalize( "name" );
        return name != null ? name : I18nManager.getInstance().getMessage("no_name");
    }

    /** Load the Collection information from an element. The
     *  information is searched for in a {@code <profile>}
     *  element. If this is not the case, override this function.
     *  @param main Element that contains a information element.
     *  @return The information, or null if not found.
     */
    public String loadInformation( Element main )
    {
        String information = main.getChildTextNormalize( "profile" );
        return information != null ? information : I18nManager.getInstance().getMessage("no_information");
    }

    /** Load all the releases in this collection, and add
     *  them to the collection.
     *  @param main Element that contains a {@code <releases>} element.
     */
    public void fetchReleases( Element main )
    {
        Element releases = main.getChild( "releases" );

        if( releases != null ) {
            List list = releases.getChildren( "release" );
            for( Object object: list ) {
                Element element = (Element) object;
                String id = element.getAttributeValue("id");
                Release release = new Release( Integer.parseInt(id) );

                release.setTitle( element.getChildTextNormalize("title") );
                /* If we are lucky, the year is already present in this file. */
                release.setYear( element.getChildTextNormalize("year") );
                release.setFormat( element.getChildTextNormalize("format") );
                release.setArtist( element.getChildTextNormalize("artist") );
                release.setLabel( element.getChildTextNormalize("label") );

                collection.getDataModel().addRelease( release );
            }
        }

        List<Release> allReleases = collection.getDataModel().getAllReleases();
        int index = 0;

        /* Because this can take some time, we will honor all
         * cancel requests. */
        while( index < allReleases.size() && !hasCancelRequest() ) {

            /* Create a ReleaseLoader to further load the release. */
            ReleaseLoader releaseLoader = new ReleaseLoader( allReleases.get( index ) );
            releaseLoader.loadInForeground();

            /* Set the load state. */
            collection.setLoadState( 100 * index / allReleases.size() );
            index++;
        }
    }
}
