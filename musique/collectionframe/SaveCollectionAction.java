/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.collectionframe;

import musique.collectiondata.Collection;
import musique.loaders.CollectionWriter;
import musique.resources.I18nManager;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/** An action that can be used to save a collection to a file.
 */
public class SaveCollectionAction extends AbstractAction
{
    private CustomCollectionFrame collectionFrame;

    /** Constructor.
     *  @param collectionFrame Frame of the collection that should be saved.
     */
    public SaveCollectionAction( CustomCollectionFrame collectionFrame )
    {
        super( I18nManager.getInstance().getMessage("save") );
        putValue( MNEMONIC_KEY, I18nManager.getInstance().getMnemonic("save") );
        putValue( ACCELERATOR_KEY, KeyStroke.getKeyStroke( "ctrl S" ) );

        this.collectionFrame = collectionFrame;
    }

    /** Presents a file chooser dialog to the user and allow him to select
     *  a filename, then possibly save the collection.
     */
    public void saveCollection()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile( new File( collectionFrame.getCollection().getName() + ".xml" ) );

        FileNameExtensionFilter filter = new FileNameExtensionFilter( "Musique XML", "xml", "musique" );
        chooser.setFileFilter(filter);

        int returnVal = chooser.showSaveDialog( collectionFrame );

        File chosen = chooser.getSelectedFile();

        if( returnVal == JFileChooser.APPROVE_OPTION && chosen != null ) {

            /* If the file exists, we will ask for confirmation. */
            boolean write = true;
            if( chosen.exists() ) {
                int overwrite = JOptionPane.showConfirmDialog( collectionFrame,
                                                               I18nManager.getInstance().getMessage("overwrite_warning"),
                                                               I18nManager.getInstance().getMessage("warning"),
                                                               JOptionPane.YES_NO_OPTION );
                if( overwrite != 0 )
                    write = false;
            }

            if( write ) {
                CollectionWriter writer = new CollectionWriter( collectionFrame.getCollection(), chosen );
                writer.write();
                collectionFrame.setSaved( true );
            }
        }
    }

    @Override
    public void actionPerformed( ActionEvent event )
    {
        saveCollection();
    }
}
