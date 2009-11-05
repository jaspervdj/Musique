/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.collectiondata.CollectionDataModel;

/** Factory used to create visualisations.
 */
interface VisualisationFactory
{
    /** Gets a name for the visualisation.
     *  @return The name of the visualisation.
     */
    public String getName();

    /** Creates a new visualisation.
     *  @param dataModel CollectionDataModel to be used for the Visualisation.
     *  @return A new Visualisation.
     */
    public Visualisation createVisualisation( CollectionDataModel dataModel );
}
