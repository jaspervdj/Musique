/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.resources.I18nManager;
import musique.collectiondata.CollectionDataModel;

public class TreeVisualisationFactory implements VisualisationFactory
{
    @Override
    public String getName()
    {
        return I18nManager.getInstance().getMessage("tree");
    }

    @Override
    public Visualisation createVisualisation( CollectionDataModel dataModel )
    {
        return new TreeVisualisation( dataModel );
    }
}
