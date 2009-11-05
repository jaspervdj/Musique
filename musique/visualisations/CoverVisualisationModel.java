/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.Model;
import musique.collectiondata.CollectionDataModel;
import musique.collectiondata.CollectionDataListener;
import musique.collectiondata.CollectionSelectionModel;
import musique.collectiondata.Release;
import musique.loaders.ImageLoader;
import musique.resources.ResourceManager;
import java.awt.Image;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/** A Model that holds data for the CoverVisualisation.
 */
public class CoverVisualisationModel extends Model
                                     implements CollectionDataListener, ChangeListener
{
    /** The size to resize all covers to. */
    private static final int COVER_SIZE = 150;

    /** The datamodel to be visualised. */
    private CollectionDataModel dataModel;

    /** The selectionmodel of the datamodel. */
    private CollectionSelectionModel selectionModel;

    /** A number of ImageLoaders, one per Release. */
    private Map<Release,ImageLoader> imageLoaders;

    /** The years available. */
    private List<String> years;

    /** The current year index. */
    private int currentYear;

    /** The current release index. */
    private int currentRelease;

    /** The class used to animate this visualisation. */
    private CoverVisualisationAnimation animation;

    /** The current animation state. */
    private double animationOffset;

    /** The default cover to show when no cover is found. */
    private static Image defaultCover;

    static
    {
        defaultCover = ResourceManager.getInstance().getIcon( "cover" ).getImage();
    }

    /** Constructor.
     *  @param dataModel CollectionDataModel to represent.
     */
    public CoverVisualisationModel( CollectionDataModel dataModel )
    {
        super();

        this.dataModel = dataModel;
        this.selectionModel = dataModel.getSelectionModel();

        years = new ArrayList<String>();
        currentYear = 0;
        currentRelease = 0;

        animation = new CoverVisualisationAnimation( this );
        animationOffset = 0.0;

        imageLoaders = new HashMap<Release,ImageLoader>();

        /* Add years already present. */
        for( String year: dataModel.getYears() ) {
            years.add( year );
        }

        /* Add releases already present. */
        for( Release release: dataModel.getAllReleases() ) {
            loadImageForRelease( release );
            release.addChangeListener( this );
        }

        dataModel.addCollectionDataListener( this );
        selectionModel.addChangeListener( this );
    }

    /** Sets the animation offset.
     *  @param animationOffset A double between -1.0 and 1.0.
     */
    public void setAnimationOffset( double animationOffset )
    {
        if( this.animationOffset != animationOffset ) {
            this.animationOffset = animationOffset;
            fireStateChanged();
        }
    }

    /** Fetch the animation offset
     *  @return The animation offset.
     */
    public double getAnimationOffset()
    {
        return animationOffset;
    }

    /** Get the image for the release at the given offset.
     *  @param offset Offset from the currently selected release.
     *  @return The image, or {@code null} if not found.
     */
    public Image getImage( int offset )
    {
        Release release = getRelease( offset );

        /* There is no release selected. */
        if( release == null ) {
            return null;
        
        /* The selected release has no cover. */
        } else if( imageLoaders.get(release) == null ||
                   imageLoaders.get(release).getImage() == null ) {
            return defaultCover;

        /* The selected release has a cover. */
        } else {
            return imageLoaders.get(release).getImage();
        }
    }

    /** Get the the release at the given offset.
     *  @param offset Offset from the currently selected release.
     *  @return The release, or {@code null} if not found.
     */
    public Release getRelease( int offset )
    {
        if( years.size() <= 0 )
            return null;

        int index = offset + currentRelease;
        List<Release> releases = dataModel.getReleasesByYear( years.get(currentYear) );
        if( releases == null || index < 0 || index >= releases.size() )
            return null;
        else
            return releases.get( index );
    }

    /** Get the currently selected release.
     *  @return The selected release, or {@code null} if none is selected.
     */
    public Release getRelease()
    {
        return getRelease(0);
    }

    /** Get the number of releases in the currently selected year.
     *  @return The number of releases in the currently selected year.
     */
    public int getNumberOfReleases()
    {
        if( currentYear >= 0 && currentYear < years.size() ) {
            List<Release> releases = dataModel.getReleasesByYear( years.get(currentYear) );
            if( releases != null )
                return dataModel.getReleasesByYear( years.get(currentYear) ).size();
        }

        return 0;
    }

    /** Get the index of the currently selected release.
     *  @return The index of the currently selected release.
     */
    public int getCurrentRelease()
    {
        return currentRelease;
    }

    /** Get the number of years available.
     *  @return The number of years available.
     */
    public int getNumberOfYears()
    {
        return years.size();
    }

    /** Get the index of the currently selected year.
     *  @return The index of the currently selected year.
     */
    public int getCurrentYear()
    {
        return currentYear;
    }

    /** Go to the next release.
     *  This will also start an animation.
     */
    public void nextRelease()
    {
        if( years.size() > 0 && currentRelease+1 < dataModel.getReleasesByYear(years.get(currentYear)).size() ) {
            currentRelease++;
            animation.start( true );
            selectionModel.setSelection( getRelease() );
            fireStateChanged();
        }
    }

    /** Go to the previous release.
     *  This will also start an animation.
     */
    public void previousRelease()
    {
        if( years.size() > 0 && currentRelease > 0 ) {
            currentRelease--;
            animation.start( false );
            selectionModel.setSelection( getRelease() );
            fireStateChanged();
        }
    }
    
    /** Go to the next year.
     */
    public void nextYear()
    {
        currentYear = (currentYear+1) % years.size();
        currentRelease = 0;
        selectionModel.setSelection( getRelease() );
        setAnimationOffset(0.0);
        fireStateChanged();
    }

    /** Go to the previous year.
     */
    public void previousYear()
    {
        currentYear--;
        if( currentYear<0 )
            currentYear = years.size()-1;

        currentRelease = 0;
        selectionModel.setSelection( getRelease() );
        setAnimationOffset(0.0);
        fireStateChanged();
    }

    /** Start loading the image for a certain release.
     *  @param release Release to start loading the image for.
     */
    private void loadImageForRelease( Release release )
    {
        if( release.getSmallImageURL() != null && imageLoaders.get(release) == null ) {
            ImageLoader imageLoader = new ImageLoader( release.getSmallImageURL(), COVER_SIZE );
            imageLoaders.put( release, imageLoader );
            imageLoader.addChangeListener( this );
            imageLoader.loadInBackground();
        }
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        /* The selection changed. Most important when the selection
         * was changed in another visualisation. Anyway, we just
         * synchronize this selection with it. */
        if( event.getSource() == selectionModel ) {
            Release release = selectionModel.getSelection();
            if( release != null && years.size()>0 && release != getRelease() ) {
                currentYear = years.indexOf( release.getYear() );
                currentRelease = dataModel.getReleasesByYear( release.getYear() ).indexOf( release );
            }

        /* A release changed. This is interesting when the image
         * URL changed, because then we load the image. So, we
         * start loading the image. This will be canceled or
         * ignored anyway if it isn't necessary. */
        } else if( event.getSource() instanceof Release ) {
            Release release = (Release) event.getSource();
            loadImageForRelease( release );
            fireStateChanged();

        /* An ImageLoader changed. This means a certain image
         * will have reached a further loading state. We call
         * fireStateChanged() so our views can redraw. */
        } else if( event.getSource() instanceof ImageLoader ) {
            fireStateChanged();
        }
    }

    @Override
    public void yearAdded( String year, int index )
    {
        years.add( index, year );
        if( years.size() != 1 && index <= currentYear )
            currentYear++;
    }

    @Override
    public void releaseAdded( Release release, int index )
    {
        loadImageForRelease( release );
        if( years.size()>0 && release.getYear() == years.get(currentYear) )
            fireStateChanged();

        release.addChangeListener( this );
    }

    @Override
    public void yearRemoved( String year )
    {
        int index = years.indexOf( year );
        years.remove( index );
        if( index <= currentYear ) {

            currentYear--;
            if( currentYear < 0 )
                currentYear = years.size() - 1;

            fireStateChanged();
        }
    }

    @Override
    public void releaseRemoved( Release release, String year )
    {
        if( year == years.get(currentYear) ) {
            List<Release> releases = dataModel.getReleasesByYear( years.get(currentYear) );
            if( releases != null && currentRelease >= releases.size() )
                currentRelease--;

            fireStateChanged();
        }

        release.removeChangeListener( this );
    }
}
