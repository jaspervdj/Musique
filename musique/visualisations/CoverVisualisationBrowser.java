/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.visualisations;

import musique.resources.ResourceManager;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.Icon;

/** A panel with a label, and a next and previous button,
 *  so the user can browse through items.
 */
public abstract class CoverVisualisationBrowser extends JPanel implements ChangeListener
{
    protected CoverVisualisationModel model;
    protected JButton previous, next;
    protected JLabel label;

    /** Constructor.
     *  @param model CoverVisualisationModel to browse.
     */
    public CoverVisualisationBrowser( CoverVisualisationModel model )
    {
        super( new BorderLayout() );
        this.model = model;

        ResourceManager resourceManager = ResourceManager.getInstance();

        /* Previous button. */
        previous = new JButton();
        previous.setIcon( resourceManager.getIcon("previous") );
        previous.setEnabled( false );
        add( previous, BorderLayout.WEST );
        previous.addActionListener( createPreviousActionListener() );

        /* Main label. */
        label = new JLabel();
        label.setHorizontalAlignment( JLabel.CENTER );
        add( label, BorderLayout.CENTER );

        /* Next button. */
        next = new JButton();
        next.setIcon( resourceManager.getIcon("next") );
        next.setEnabled( false );
        add( next, BorderLayout.EAST );
        next.addActionListener( createNextActionListener() );

        model.addChangeListener( this );
    }

    /** Create an ActionListener to respond to the event
     *  generated when the user clicks the previous button.
     *  @return An appropriate ActionListener.
     */
    public abstract ActionListener createPreviousActionListener();

    /** Create an ActionListener to respond to the event
     *  generated when the user clicks the next button.
     *  @return An appropriate ActionListener.
     */
    public abstract ActionListener createNextActionListener();
}
