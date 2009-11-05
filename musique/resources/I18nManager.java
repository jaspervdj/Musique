/* Copyright (c) 2009 by Jasper Van der Jeugt
 * This java source file is part of the Musique project, a graphical
 * front-end to Discogs, created for a school assignment.
 */
package musique.resources;

import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.Locale;
import java.text.MessageFormat;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import javax.swing.JComponent;

/** A class responsible for the internationalization
 *  of messages.
 */
public class I18nManager
{
    /** The ResourceBundle. */
    private static final String resourceBundleName = "musique.resources.MessageBundle";
    private Locale locale;
    private ResourceBundle resourceBundle;

    /** Protected constructor.
     */
    protected I18nManager()
    {
        locale = Locale.getDefault();
        resourceBundle = ResourceBundle.getBundle( resourceBundleName, locale );

        /* It seems that openjdk won't translate certain messages
         * without the next few lines. */
        UIManager.getDefaults().setDefaultLocale( locale );
        UIManager.getDefaults().addResourceBundle( resourceBundleName );
        JComponent.setDefaultLocale( locale );
    }

    /** Get the currently used Locale.
     *  @return The current locale.
     */
    public Locale getLocale()
    {
        return locale;
    }

    /** Get a simple message.
     *  @param key The message key.
     *  @return The message in the correct Locale.
     */
    public String getMessage( String key )
    {
        return resourceBundle.getString( key );
    }

    /** Get a not so simple message.
     *  @param key The message key.
     *  @param arguments The message arguments to be given to the key.
     *  @return The message in the correct Locale.
     */
    public String getMessage( String key, Object[] arguments )
    {
        return MessageFormat.format( getMessage(key), arguments );
    }

    /** Get the mnemonic key of a message.
     * 
     *  This will first try to take the mnemonic from the
     *  key (key + "_mn"). If such a key is not found, it
     *  will take the first character from the message
     *  returned by the key.
     *
     *  Therefore, if the mnemonic is the first character
     *  of the message, it is not nessecary to specify a
     *  mnemonic in the properties file.
     *
     *  @param key The mnemonic key.
     *  @return A localized mnemonic for that key.
     */
    public int getMnemonic( String key )
    {
        /* First try the _mn key */
        String string = null;
        try {
            string = resourceBundle.getString( key + "_mn" );

        /* Not found, try the regular key */
        } catch( MissingResourceException exception ) {
            string = resourceBundle.getString( key );
        }

        /* Calculate the mnemonic based on the character. */
        return string.toLowerCase().charAt(0) - 'a' + KeyEvent.VK_A;
    }

    /** A class to hold a singleton.
     */
    private static class I18nManagerHolder
    {
        public static final I18nManager INSTANCE = new I18nManager();
    }

    /** Get the instance of this singleton class.
     *  @return The I18nManager instance.
     */
    public static I18nManager getInstance()
    {
        return I18nManagerHolder.INSTANCE;
    }
}
