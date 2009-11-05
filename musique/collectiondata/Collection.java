/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectiondata;

import musique.resources.I18nManager;
import musique.Model;
import musique.loaders.Loadable;

/** A Collection represents a number of releases, and
 *  some info about the collection itself.
 */
public class Collection extends Loadable
{
    /** The CollectionDataModel containing the releases. */
    private CollectionDataModel dataModel;

    /** Fields containing collection information. */
    private String name, information, imageURL;

    /** Constructor. */
    public Collection()
    {
        dataModel = new CollectionDataModel();

        name = I18nManager.getInstance().getMessage("unknown");
        information = I18nManager.getInstance().getMessage("loading");
        imageURL = null;
    }

    /** Get the CollectionDataModel holding the releases for
     *  this Collection.
     *  @return The associated CollectionDataModel.
     */
    public CollectionDataModel getDataModel()
    {
        return dataModel;
    }

    /** Get the name of this collection.
     *  @return The name of this collection.
     */
    public String getName()
    {
        return name;
    }

    /** Set the name for this collection.
     *  @param name The new name for this collection.
     */
    public void setName( String name )
    {
        if( !name.equals( this.name ) ) {
            this.name = name;
            fireStateChanged();
        }
    }

    /** Get the information about this collection.
     *  @return The information about this collection.
     */
    public String getInformation()
    {
        return information;
    }

    /** Set the information for this collection.
     *  @param information The new information for this collection.
     */
    public void setInformation( String information )
    {
        if( !information.equals( this.information ) ) {
            this.information = information;
            fireStateChanged();
        }
    }

    /** Get the image URL this collection.
     *  @return The image URL of this collection.
     */
    public String getImageURL()
    {
        return imageURL;
    }

    /** Set the image URL for this collection.
     *  @param imageURL The new image URL for this collection.
     */
    public void setImageURL( String imageURL )
    {
        if( imageURL != null && !imageURL.equals( this.imageURL ) ) {
            this.imageURL = imageURL;
            fireStateChanged();
        }
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
