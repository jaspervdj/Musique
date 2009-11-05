/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.loaders;

import musique.collectiondata.Release;
import musique.collectiondata.Track;
import musique.resources.I18nManager;
import be.ugent.caagt.discogs.DiscogsManager;
import org.jdom.Document;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import java.io.IOException;
import java.util.List;

/** A {@link DiscogsManager} used to load Release
 *  information from an url to an XML file.
 */
public class ReleaseLoader extends DiscogsXMLLoader
{
    /** The release we're loading. */
    private Release release;

    /** Constructor.
     *  @param release The release that will be loaded. It's id should be set.
     */
    public ReleaseLoader( Release release )
    {
        super( release );
        this.release = release;
    }

    /** Load the actual document.
     *  @return The JDOM xml document.
     */
    @Override
    public Document loadDocument() throws IOException, JDOMException
    {
        return DiscogsManager.getReleaseXML( release.getId() );
    }

    /** Fetch the main element from the XML data.
     *  @return The main element.
     */
    @Override
    public Element getMainElement()
    {
        Element root = getDocument().getRootElement();
        if( root != null )
            return root.getChild("release");
        else
            return null;
    }

    /** Here, the actual loading happens. Use loadInForeground()
     *  or loadInBackground() instead.
     */
    @Override
    public void load()
    {
        super.load();

        if( getDocument() != null ) {
            Element main = getMainElement();

            if( main != null ) {
                release.setYear( loadYear(main) );
                release.setImageURL( loadImageURL(main) );
                release.setSmallImageURL( loadImageURL(main,true) );
                release.setArtist( loadArtist(main) );
                release.setLabel( loadLabel(main) );
                release.setFormat( loadFormat(main) );
                fetchTracks( main );
            }
        }

        release.setLoadState( 100 );
    }

    /** Load the release year from the main XML element.
     *  @param main The main XML element.
     *  @return The year in which the release was released.
     */
    public String loadYear( Element main )
    {
        Element year = main.getChild("released");
        if( year != null ) {
            String string = year.getTextNormalize();

            /* Sometimes the released data is given in another format.
             * Convert to a simple year format. */
            if( string.matches( "[0-9]*-[0-9]*-[0-9]*" ) )
                string = string.substring( 0, string.indexOf('-') );

            return string;
        } else {
            return null;
        }
    }

    /** Load the release artist from the main XML element.
     *  @param main The main XML element.
     *  @return The artist of this release
     */
    public String loadArtist( Element main )
    {
        Element artists = main.getChild("artists");
        if( artists != null ) {

            /* Actual artist will go here. */
            String string = null;

            List artistList = artists.getChildren("artist");

            /* Multiple artists. Instead of picking one we actually
             * mark it "multiple artists", that should be more
             * appropriate. */
            if( artistList.size() > 1 ) {
                string = I18nManager.getInstance().getMessage("multiple_artists");

            /* One artist element found, so put it there. */
            } else if( artistList.size() == 1 ) {
                string = ((Element)artistList.get(0)).getChildTextNormalize("name");
            }

            return string;
        } else {
            return null;
        }
    }

    /** Load the release label from the main XML element.
     *  @param main The main XML element.
     *  @return The label of this release
     */
    public String loadLabel( Element main )
    {
        Element labels = main.getChild("labels");
        if( labels != null ) {

            /* Actual label will go here. */
            String string = null;

            List labelList = labels.getChildren("label");

            /* We cannnot really choose a label. */
            if( labelList.size() > 1 ) {
                string = I18nManager.getInstance().getMessage("multiple_labels");

            /* There's only one label, take it! */
            } else if( labelList.size() == 1 ) {
                Attribute name = ((Element)labelList.get(0)).getAttribute("name");
                if( name != null )
                    string = name.getValue();
            }

            return string;
        } else {
            return null;
        }
    }

    /** Load the release format from the main XML element.
     *  @param main The main XML element.
     *  @return The format of this release
     */
    public String loadFormat( Element main )
    {
        Element formats = main.getChild("formats");
        if( formats != null ) {

            /* Actual format will go here. */
            String string = null;

            List formatList = formats.getChildren("format");

            /* One format element found, so put it there. */
            if( formatList.size() == 1 ) {
                Attribute name = ((Element)formatList.get(0)).getAttribute("name");
                if( name != null )
                    string = name.getValue();
            }

            return string;
        } else {
            return null;
        }
    }

    /** Load and set the tracks from the main element.
     *  @param main The main XML element.
     */
    public void fetchTracks( Element main )
    {
        Element trackList = main.getChild( "tracklist" );
        if( trackList != null ) {

            List list = trackList.getChildren( "track" );
            Track[] tracks = new Track[list.size()];

            for( int i=0; i<list.size(); i++) {
                Element element = (Element) list.get(i);
                String title = I18nManager.getInstance().getMessage("unknown"),
                       duration = I18nManager.getInstance().getMessage("unknown");

                title = element.getChildTextNormalize("title");
                duration = element.getChildTextNormalize("duration");

                Track track = new Track( title, duration );
                tracks[i] = track;
            }

            release.setTracks( tracks );
        }
    }
}
