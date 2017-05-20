package fr.tikione.ini;

import fr.tikione.ini.util.AbstractLineReader;
import fr.tikione.ini.util.FileHelper;
import fr.tikione.ini.util.IniHelper;
import fr.tikione.ini.util.InputstreamLineReader;
import fr.tikione.ini.util.StringHelper;
import java.io.CharConversionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * The <code>Ini</code> class represents an INI content: a text-file composed of comments and a basic structure of sections and keys,
 * plus many extra features.
 *
 * <div id="specification_content">
 * <p id="specification_main_title">
 *  <strong>The implemented INI specification version:</strong><br />
 *  (this specification is partially based on the Wikipedia "INI file" documentation; see <a href="http://en.wikipedia.org/wiki/INI_file">
 *  http://en.wikipedia.org/wiki/INI_file</a>)
 * </p>
 * <ul>
 *  <li>
 *   Parameters (key / value) format:<br /><br />
 *   The basic element contained in an INI file is the parameter. Every parameter has a name and a value, delimited by an equals sign (=).
 *   The name appears to the left of the equals sign. Leading and trailing white-space/tabs are omitted automatically (while getting and
 *   setting a value), so you don't have to use quoted values (embraced with double quotes and/or apostrophes): double quotes and
 *   apostrophes are regarded as ordinary characters.<br />
 *   Key may not be duplicated in the same section.<br /><br />
 *   &nbsp;&nbsp;&nbsp;&nbsp;<code>key = the value</code><br /><br />
 *  </li>
 *  <li>
 *   Sections:<br /><br />
 *   Parameters may be grouped into arbitrarily named sections. The section name appears on a line by itself, in square brackets ("[" and
 *   "]"). All parameters after the section declaration are associated with that section. There is no explicit "end of section" delimiter,
 *   sections end at the next section declaration or the end of the file.<br />Sections may not be nested, but they can be duplicated.
 *   <br />
 *   Section names can only be composed of alphanumerics (a-z, A-Z, 0-9), and underscore character ("_").<br /><br />
 *   &nbsp;&nbsp;&nbsp;&nbsp;<code>[section]</code><br /><br />
 *  </li>
 *  <li>
 *   Comments:<br /><br />
 *   Semicolons (;) and number sign (#) indicate the start of a comment. Comments continue to the end of the line. Everything between the
 *   comment mark (; or #) and the End of Line is ignored. Comments have to start on a blank line. A comment which starts after a key /
 *   value sequence (on the same line) would be construed as a part of the value, not as a comment. A comment must not start after a
 *   section declaration (on the same line).<br /><br />
 *   &nbsp;&nbsp;&nbsp;&nbsp;<code>; comment text</code><br />
 *   &nbsp;&nbsp;&nbsp;&nbsp;<code># other comment text</code><br /><br />
 *  </li>
 *  <li>
 *   Escapes:<br /><br />
 *   EOF (end-of-file) and EOL (end-of-line) terminate sequences.<br />
 *   There is an optional extra support for special escape sequences (Unicode escapes to UTF8, and backslash at end of line means line
 *   concatenation).<br /><br />
 *   <strong>Warning: </strong> theses functionalities are deactivated by default. You can easily reactive them via the
 *   <code>fr.tikione.ini.Config</code> class.
 *   <br /><br />
 *  </li>
 *  <li>
 *   Other features:<br /><br />
 *   <ul>
 *    <li>Environment variable shortcuts:<br />
 *    The following key value <code>${&#64;env/<em>PATH</em>}</code> will refer to the "PATH" system environment value
 *    (<code>System.getenv(String)</code> function).<br /><br />
 *    </li>
 *    <li>System property shortcuts:<br />
 *    The following key value <code>${&#64;prop/<em>os.name</em>}</code> will refer to the "os.name" JVM property
 *    (<code>System.getProperty(String)</code> function), so - in this example, the operating system name.<br /><br />
 *    </li>
 *    <li>Section/key shortcuts:<br />
 *    The following key value <code>${<em>foo/bar</em>}</code> will refer to the "bar" key value in the "foo" section of the current INI
 *    file.<br />
 *    The following key value <code>${<em>bar</em>}</code> will refer to the "bar" key value in the global section of the current INI
 *    file.<br /><br />
 *    Note that any infinitive loop (ex: due to a pair of shortcuts that calls them one another) will throw an
 *    <code>InfinitiveLoopException</code> exception.<br /><br />
 *    </li>
 *   </ul>
 *   Note that a key value can be composed of one or many shortcuts, in addition of classical values, like:<br />
 *   <code>key = the os is ${&#64;prop/<em>os.name</em>} and the ${dirs/tmp} folder is ${&#64;env/<em>TMP</em>}</code>
 *  </li>
 * <p>
 *   Finally, note that an INI parser's configuration can be customized via the <code>fr.tikione.ini.Config</code> class. Every
 * <code>Ini</code> class contains a <code>Config</code> object that you can edit via the <code>getConfig()</code> method.
 * </p>
 * </ul>
 * </div>
 */
public class Ini {

    /** The map to store sections, keys/values and comments. Sections cannot be nested. */
    private Map<String, Map<String, Entry>> contents;

    /** Global section name. */
    private static final String GLOBAL_SECTION = "";

    /** The INI content probable new line characters (CR, LF or CRLF). */
    private String computedNewLine;

    /** Configuration that provides default functionalities. */
    private Config config;

    /** Default string comparator */
    private static final Comparator<Object> STR_COMPARATOR = java.text.Collator.getInstance();

    /** The prefix for comments <code>Entry</code> keys stored in the internal <code>contents</code> map. */
    private static final String INTERNAL_COMMENT_KEYNAME_PREFIX = "\n";

    /**
     * Create a new instance of <code>Ini</code>. Create a new instance of parser and load the internal
     * <code>/fr.tikione.ini.properties</code> file for configuration.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal properties file.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    public Ini()
            throws IOException,
                   PatternSyntaxException {
        this(Config.getDefaultConfigProperties());
    }

    /**
     * Create a new instance of <code>Ini</code> by copy.
     *
     * @param ini the <code>Ini</code> object to copy.
     */
    public Ini(Ini ini) {
        config = ini.getConfig().clone();
        computedNewLine = ini.getNewLineStr();
        contents = new LinkedHashMap<>(ini.getConfig().getDefNbSec());
        List<String> copySections = ini.getSectionsNames();
        for (String copySection : copySections) {
            contents.put(copySection, ini.getSectionContentClone(copySection));
        }
    }

    /**
     * Create a new instance of <code>Ini</code>. Create a new instance of parser and load the given properties object for configuration.
     *
     * @param tikioneProperties the configuration properties object to fill the internal <code>/fr.tikione.ini.properties</code> file.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    public Ini(Properties tikioneProperties)
            throws PatternSyntaxException {
        config = new Config(tikioneProperties);
        computedNewLine = FileHelper.getPlatformNewLine();
        contents = new LinkedHashMap<>(config.getDefNbSec(), config.getDefSecLoadfactor());
    }

    /**
     * Copy all of the sections and keys from the specified <code>Ini</code> to the current <code>Ini</code>. Comments are omitted and
     * existing keys are overwritten.
     *
     * @param from <code>Ini</code> to be copied.
     */
    public void addAll(Ini from) {
        List<String> sections = from.getSectionsNames();
        List<String> keys;
        for (String section : sections) {
            keys = from.getKeysNames(section);
            for (String key : keys) {
                setKeyValue(section, key, from.getRawKeyValue("", section, key));
            }
            keys.clear();
        }
    }

    /**
     * Try to get all keys values of all sections, in order to throw an exception if a critical error is detected.
     *
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public void checkContent()
            throws CharConversionException,
                   InfinitiveLoopException {
        checkContent(config);
    }

    /**
     * Try to get all keys values of all sections, in order to throw an exception if a critical error is detected.
     *
     * @param config the configuration to use during this test.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public void checkContent(Config config)
            throws CharConversionException,
                   InfinitiveLoopException {
        List<String> sections = getSectionsNames();
        List<String> keys;
        for (String section : sections) {
            keys = getKeysNames(section);
            for (String key : keys) {
                getKeyValue(null, section, key, config);
            }
            keys.clear();
        }
    }

    /**
     * Get the configuration <code>Config</code> object.
     *
     * @return the <code>Config</code> object.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Get key value from the INI content with a configuration that disable shortcuts (section/key shortcuts, environment variables
     * shortcuts, system properties shortcuts) and other configurable functionalities.
     *
     * @param defval the default value
     * @param section the section to find the key.
     * @param key the key name.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     */
    protected String getRawKeyValue(String defval, String section, String key) {
        String value;
        if (contents.containsKey(section) && contents.get(section).containsKey(key) && !contents.get(section).get(key).isComment()) {
            value = contents.get(section).get(key).getValue();
        } else {
            value = defval;
        }
        return value;
    }

    /**
     * Get key value from the INI content.
     * Find section/key shortcuts, environment variables shortcuts, system properties shortcuts, replace them by the targeted values, etc.
     * Finally, return the complete string which represents the entire value.
     *
     * @param loop the position in the shortcuts pursuit counter.
     * @param defval the default value.
     * @param section the section to find the key.
     * @param key the key name.
     * @param config the configuration to (de)activate functionalities.
     * @param raw if <code>true</code>, shortcuts functionalities (environment variables, system properties, section/key references) are
     *        deactivated, otherwise they depend on the given configuration.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    protected String getKeyValue(int loop, String defval, String section, String key, Config config, boolean raw)
            throws CharConversionException,
                   InfinitiveLoopException {
        String res = null;
        if (loop > config.getDefMaxShortcutsPursuit()) {
            // Throws an exception if an infinitive loopcounter is detected.
            throw new InfinitiveLoopException(
                    "There is probably an infinitive loop due to a pair of shortcuts that calls them one another. INI section: \""
                    + section + "\", key: \"" + key + "\"");
        }
        int loopCounter = loop + 1;

        // Get value.
        if (contents.containsKey(section) && contents.get(section).containsKey(key) && !contents.get(section).get(key).isComment()) {
            // The key is in the current file (bugfix introduced in v1.2.0: and is not a comment).
            Entry iniEntry = contents.get(section).get(key);
            res = iniEntry.getValue();
            if (iniEntry.isConcat()) {
                res = res.replaceAll(Matcher.quoteReplacement(config.getDefLineConcat()) + Entry.NEW_LINE_SEPARATOR_REGEX, "");
            }
        }
        // Convert Unicode escapes if needed.
        if (config.isReadUnicodeEscConvEnabled()) {
            res = StringHelper.convertUnicodeEscapesToUTF8(res);
        }
        if (StringHelper.strIsEmpty(res)) {
            res = defval;
        } else {
            boolean doEnvShortcuts;
            boolean doSysShortcuts;
            boolean doSeckeyShortcuts;
            boolean doShortcuts;
            if (raw) {
                doEnvShortcuts = false;
                doSysShortcuts = false;
                doSeckeyShortcuts = false;
                doShortcuts = false;
            } else {
                doEnvShortcuts = config.isReadEnvShortcutsEnabled();
                doSysShortcuts = config.isReadSysShortcutsEnabled();
                doSeckeyShortcuts = config.isReadSeckeyShortcutsEnabled();
                doShortcuts = doEnvShortcuts || doSysShortcuts || doSeckeyShortcuts;
            }

            // Searchs for shortcuts and replaces them by the targeted values.
            if (doShortcuts) {
                int defNbRefs = config.getDefNbShortcuts();
                boolean isShortcuts = StringHelper.checkRegex(res, config.getPaShortcut());
                if (isShortcuts) {

                    // Environment variables shortcuts.
                    if (doEnvShortcuts) {
                        Collection<String> shortcuts = StringHelper.getGroupsFromRegex(res, config.getPaEnvShortcut(), defNbRefs);
                        if (!shortcuts.isEmpty()) {
                            String left = config.getPaEnvShortcutLeft();
                            String right = config.getPaEnvShortcutRight();
                            int leftLen = left.length();
                            for (String s : shortcuts) {
                                // Extracts environment name value.
                                String info = System.getenv(StringHelper.tTrim(
                                        s.substring(s.indexOf(left) + leftLen, s.indexOf(right))));
                                // Gets environment name value.
                                res = StringHelper.replaceFirst(res, config.getPaEnvShortcut(), info == null ? "" : info);
                            }
                        }
                    }

                    // System properties shortcuts.
                    if (doSysShortcuts) {
                        Collection<String> shortcuts = StringHelper.getGroupsFromRegex(res, config.getPaPropShortcut(), defNbRefs);
                        if (!shortcuts.isEmpty()) {
                            String left = config.getPaPropShortcutLeft();
                            String right = config.getPaPropShortcutRight();
                            int leftLen = left.length();
                            for (String s : shortcuts) {
                                // Extracts system property name value.
                                String info = System.getProperty(StringHelper.tTrim(
                                        s.substring(s.indexOf(left) + leftLen, s.indexOf(right))));
                                // Gets system property name value.
                                res = StringHelper.replaceFirst(res, config.getPaPropShortcut(), info == null ? "" : info);
                            }
                        }
                    }

                    // Section/key shortcuts.
                    if (doSeckeyShortcuts) {
                        Collection<String> shortcuts = StringHelper.getGroupsFromRegex(res, config.getPaSeckeyShortcut(), defNbRefs);
                        if (!shortcuts.isEmpty()) {
                            String left = config.getPaSeckeyShortcutLeft();
                            String right = config.getPaSeckeyShortcutRight();
                            String middle = config.getPaSeckeyShortcutMiddle();
                            int leftLen = left.length();
                            for (String s : shortcuts) {
                                // Extracts section (in any) and key names.
                                String content = s.substring(s.indexOf(left) + leftLen, s.indexOf(right));
                                int indexOfSeparator = content.indexOf(middle);

                                // Gets section and key names.
                                // - If section name is empty and the '/' character is present, section is the global section, otherwise
                                //   it is the current section.
                                // - The key must always have a name.
                                // When section and key are defined, we use the good method to enter in the recursive loopcounter in order
                                // to find the key value.
                                String refSection, refKey;
                                if (-1 == indexOfSeparator) { // Referenced section name is empty.
                                    refSection = "";
                                    refKey = StringHelper.tTrim(content);
                                } else {
                                    refSection = StringHelper.tTrim(content.substring(0, indexOfSeparator));
                                    refKey = StringHelper.tTrim(content.substring(indexOfSeparator + 1));
                                }
                                String info = getKeyValue(loopCounter, defval, refSection, refKey, config, raw);
                                // Saves the found reference name value.
                                res = StringHelper.replaceFirst(res, config.getPaSeckeyShortcut(), info);
                            }
                        }
                    }
                }
            }
        }

        if (StringHelper.strIsEmpty(res)) {
            res = defval;
        }
        return res;
    }

    /**
     * Get key value from the INI content.
     *
     * @param section the section to find the key.
     * @param key the key name.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public String getKeyValue(String section, String key)
            throws CharConversionException,
                   InfinitiveLoopException {
        return getKeyValue(config.getDefDefaultKeyValue(), section, key, config);
    }

    /**
     * Get key value from the INI content.
     *
     * @param defVal the default key value. Useful if no value found.
     * @param section the section to find the key.
     * @param key the key name.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public String getKeyValue(String defVal, String section, String key)
            throws CharConversionException,
                   InfinitiveLoopException {
        return getKeyValue(defVal, section, key, config);
    }

    /**
     * Get key value from the INI content.
     *
     * @param defVal the default key value. Useful if no value found.
     * @param section the section to find the key.
     * @param key the key name.
     * @param config the configuration to (de)activate functionalities.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public String getKeyValue(String defVal, String section, String key, Config config)
            throws CharConversionException,
                   InfinitiveLoopException {
        return getKeyValue(0, defVal, section, key, config, false);
    }

    /**
     * Get key value from the INI content.
     *
     * @param section the section to find the key.
     * @param key the key name.
     * @param config the configuration to (de)activate functionalities.
     * @return the key value if exists, the <code>defVal</code> string otherwise.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public String getKeyValue(String section, String key, Config config)
            throws CharConversionException,
                   InfinitiveLoopException {
        return getKeyValue(0, config.getDefDefaultKeyValue(), section, key, config, false);
    }

    /**
     * Get a list of all key names in a section. The result <code>List</code> is an <code>ArrayList</code>, so the INI keys order is
     * preserved.
     *
     * @param section the section to get keys.
     * @return the <code>List</code> into which the keys have been put.
     */
    public List<String> getKeysNames(String section) {
        Set<String> keys = contents.get(section).keySet();
        List<String> res = new ArrayList<>(keys.size());
        for (String key : keys) {
            if (!contents.get(section).get(key).isComment()) {
                res.add(key);
            }
        }
        return res;
    }

    /**
     * Return the new line characters combination CR (Carriage Return), LF (Line Feed) or CRLF according to the INI content that
     * has been read. It is based on the number of apparition of each new line character.<br />
     * If CR and LF have appears the same number of times, the result is CRLF, otherwise the most used character is returned.<br />
     * This method needs the INI content to be loaded to calculate the right number of each new line characters apparition, otherwise, the
     * result is the platform's new line characters;
     *
     * @return the new line character(s).
     */
    public String getNewLineStr() {
        return computedNewLine;
    }

    /**
     * Make a copy of a section content.
     *
     * @param section the section to clone content.
     * @return the section content clone.
     */
    protected Map<String, Entry> getSectionContentClone(String section) {
        Map<String, Entry> baseSection = contents.get(section);
        Map<String, Entry> copySection = new LinkedHashMap<>(baseSection.size());
        Set<String> keys = contents.get(section).keySet();
        for (String key : keys) {
            Entry copyIniEntry = baseSection.get(key);
            if (copyIniEntry.isComment()) {
                copySection.put(key, new Entry(copyIniEntry.getValue()));
            } else {
                copySection.put(key, new Entry(copyIniEntry.getValue(), copyIniEntry.isConcat()));
            }
        }
        return copySection;
    }

    /**
     * Get a <code>Map</code> of all keys and values of a section. The result <code>Map</code> is a <code>LinkedHashMap</code>, so the
     * INI sections and keys order is preserved.
     *
     * @param section the section to get keys and values.
     * @return the <code>Map</code> into which the keys and values have been put.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public Map<String, String> getSectionValues(String section)
            throws CharConversionException,
                   InfinitiveLoopException {
        return getSectionValues(section, config);
    }

    /**
     * Get a <code>Map</code> of all keys and values of a section. The result <code>Map</code> is a <code>LinkedHashMap</code>, so the
     * INI sections and keys order is preserved.
     *
     * @param section the section to get keys and values.
     * @param config the configuration to (de)activate functionalities.
     * @return the <code>Map</code> into which the keys and values have been put.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     * @throws InfinitiveLoopException if a pair of shortcuts calls them one another, that could initiate an infinitive loop.
     */
    public Map<String, String> getSectionValues(String section, Config config)
            throws CharConversionException,
                   InfinitiveLoopException {
        Map<String, String> res = new LinkedHashMap<>(contents.size());
        List<String> keys = getKeysNames(section);
        for (String key : keys) {
            res.put(key, getKeyValue("", section, key, config));
        }
        return res;
    }

    /**
     * Get a list of all section names, the global section included. The result <code>List</code> is an <code>ArrayList</code>, so the
     * INI sections order is preserved.
     * @return the <code>List</code> into which the sections names have been put.
     */
    public List<String> getSectionsNames() {
        List<String> res = new ArrayList<>(contents.size());
        res.addAll(contents.keySet());
        return res;
    }

    /**
     * Indicate if a key exists in the INI content and specified section.
     *
     * @param section the section to find the key.
     * @param key the key to test existence.
     * @return <code>true</code> if the key exists, <code>false</code> otherwise.
     */
    public boolean hasKey(String section, String key) {
        boolean exist;
        if (contents.containsKey(section)) {
            exist = contents.get(section).containsKey(key);
        } else {
            exist = false;
        }
        return exist;
    }

    /**
     * Indicate if a section exists in the INI content.
     *
     * @param section the section to test existence.
     * @return <code>true</code> if the section exists, <code>false</code> otherwise.
     */
    public boolean hasSection(String section) {
        return contents.containsKey(section);
    }

    /**
     * Load and parse file content with platform's default file encoding.
     *
     * @param file the file to read content.
     * @throws IOException if an I/O error occurs.
     */
    public void load(File file)
            throws IOException {
        load(file, FileHelper.getPlatformEncoding());
    }

    /**
     * Load and parse file content.
     *
     * @param file the file to read content.
     * @param encoding file encoding.
     * @throws IOException if an I/O error occurs.
     */
    public void load(File file, String encoding)
            throws IOException {
        try (InputStream input = new FileInputStream(file)) {
            load(input, encoding);
        }
    }

    /**
     * Load and parse input stream content with platform's default file encoding. This method doesn't close the input stream when ending.
     *
     * @param inputstream the input stream to read content.
     * @throws IOException if an I/O error occurs.
     */
    public void load(InputStream inputstream)
            throws IOException {
        load(inputstream, FileHelper.getPlatformEncoding());
    }

    /**
     * Load and parse input stream content. This method doesn't close the input stream when ending.
     *
     * @param inputstream the input stream to read content.
     * @param encoding file encoding.
     * @throws IOException if an I/O error occurs.
     */
    public void load(InputStream inputstream, String encoding)
            throws IOException {
        try (InputstreamLineReader input = new InputstreamLineReader(inputstream, encoding)) {
            load(input);
        }
    }

    /**
     * Load and parse reader content. This method doesn't close the reader when ending.
     *
     * @param reader the reader to work with.
     * @throws IOException if an I/O error occurs.
     */
    @SuppressWarnings("NestedAssignment")
    public void load(AbstractLineReader reader)
            throws IOException {
        String readLine;
        String currSection = GLOBAL_SECTION;
        int commentNumber = 0;
        int defNbSecKeys = config.getDefNbSecKeys();
        float defNbSecKeysLoadfactor = config.getDefSecKeysLoadfactor();
        String[] defSectionStarts = config.getDefSectionStarts();
        String[] defSectionEnds = config.getDefSectionEnds();
        String[] defCommentStarts = config.getDefCommentStarts();
        String[] defAffectations = config.getDefAffectations();
        contents.clear();
        contents.put(currSection, new LinkedHashMap<String, Entry>(defNbSecKeys, defNbSecKeysLoadfactor));

        boolean isLineConcat = false;
        boolean parseLineConcat = config.isParseLineConcatEnabled();
        List<String> linePart = new ArrayList<>(4);
        String currKey = null;
        boolean skip = false;

        // Search for keys in global section.
        while ((readLine = reader.readLine()) != null) {
            readLine = StringHelper.tTrim(readLine);
            if (parseLineConcat && isLineConcat) {
                linePart.add(readLine);
                if (!readLine.endsWith(config.getDefLineConcat())) {
                    isLineConcat = false;
                    contents.get(currSection).put(
                            currKey, new Entry(StringHelper.decomposeCollection(linePart, Entry.NEW_LINE_SEPARATOR), true));
                    skip = true;
                }
            }
            if (!skip && !isLineConcat) {
                String word = IniHelper.extractSectionNameFromLine(readLine, defSectionStarts, defSectionEnds);
                if (StringHelper.strIsNotEmpty(word)) {
                    // It's a section: adds a section if this is its first declaration, otherwise fills it.
                    currSection = word;
                    if (!contents.containsKey(currSection)) {
                        contents.put(currSection, new LinkedHashMap<String, Entry>(defNbSecKeys, defNbSecKeysLoadfactor));
                    }
                } else {
                    // Check for comment line.
                    if (StringHelper.strStartsWith(readLine, defCommentStarts) || StringHelper.strIsEmpty(readLine)) {
                        // It's a commment line, adds it to the contents map as a comment line.
                        commentNumber++;
                        contents.get(currSection).put(INTERNAL_COMMENT_KEYNAME_PREFIX + commentNumber, new Entry(readLine));
                    } else {
                        String[] keyAndVal = IniHelper.extractKeyNameAndValueFromLine(readLine, defAffectations);
                        if (keyAndVal != null && keyAndVal[0] != null) {
                            if (parseLineConcat && readLine.endsWith(config.getDefLineConcat())) {
                                // It's a multiline key, prepare or complete the multiline Entry.
                                if (!isLineConcat) {
                                    linePart.clear();
                                }
                                currKey = keyAndVal[0];
                                linePart.add(keyAndVal[1]);
                                isLineConcat = true;
                            } else {
                                // It's a monoline key, add a key name + key value. Otherwise, it would be an invalid line (no affectation).
                                contents.get(currSection).put(keyAndVal[0], new Entry(keyAndVal[1], false));
                            }
                        }
                    }
                }
            }
            skip = false;
        }
        this.computedNewLine = reader.getNewLineStr();
    }

    /**
     * Remove a key.
     *
     * @param section the key section to find the key.
     * @param key the section key.
     * @return <code>true</code> if the key existed and has been removed, <code>false</code> if the key didn't exist.
     */
    public boolean removeKey(String section, String key) {
        boolean found;
        if (contents.containsKey(section) && contents.get(section).containsKey(key)) {
            contents.get(section).remove(key);
            found = true;
        } else {
            found = false;
        }
        return found;
    }

    /**
     * Remove all section's content and, optionally, the section declaration itself.
     *
     * @param section the section name.
     * @param removeSecDecl if <code>true</code>, the section declaration is removed too, otherwise it is not removed.
     * @return <code>true</code> if the section existed and has been removed, <code>false</code> if the section didn't exist.
     */
    public boolean removeSection(String section, boolean removeSecDecl) {
        boolean found;
        if (contents.containsKey(section)) {
            if (removeSecDecl) {
                contents.remove(section);
            } else {
                contents.get(section).clear();
            }
            found = true;
        } else {
            found = false;
        }
        return found;
    }

    /**
     * Rename a key if exists.
     *
     * @param section the section to find the key.
     * @param oldKey the key name to rename
     * @param newKey the new key name.
     * @return <code>true</code> if the key existed and has been renamed, <code>false</code> if the key didn't exist.
     */
    public boolean renameKey(String section, String oldKey, String newKey) {
        boolean renamed;
        if (hasKey(section, oldKey) && !hasKey(section, newKey)) {
            Map<String, Entry> newKeys = new LinkedHashMap<>(contents.get(section).size());
            List<String> keys = getKeysNames(section);
            for (String key : keys) {
                if (key.equals(oldKey)) {
                    newKeys.put(newKey, contents.get(section).get(key));
                } else {
                    newKeys.put(key, contents.get(section).get(key));
                }
            }
            contents.get(section).clear();
            contents.get(section).putAll(newKeys);
            renamed = true;
        } else {
            renamed = false;
        }
        return renamed;
    }

    /**
     * Rename a section if exists.
     *
     * @param oldSection the section name to rename.
     * @param newSection the new section name.
     * @return <code>true</code> if the section existed and has been renamed, <code>false</code> if the section didn't exist.
     */
    public boolean renameSection(String oldSection, String newSection) {
        boolean renamed;
        if (hasSection(oldSection) && !hasSection(newSection)) {
            Map<String, Map<String, Entry>> newContents = new LinkedHashMap<>(contents.size());
            List<String> sections = getSectionsNames();
            for (String section : sections) {
                if (section.equals(oldSection)) {
                    newContents.put(newSection, contents.get(section));
                } else {
                    newContents.put(section, contents.get(section));
                }
            }
            contents = newContents;
            renamed = true;
        } else {
            renamed = false;
        }
        return renamed;
    }

    /**
     * Set the configuration <code>Config</code> object.
     *
     * @param config the new configuration <code>Config</code> object (a protective copy of the given <code>Config</code> object is made
     *        first).
     */
    public void setConfig(Config config) {
        this.config = config.clone();
    }

    /**
     * Write a value into the <code>Ini</code> content.
     *
     * @param section the section to find the key.
     * @param key the key name.
     * @param value the key value.
     * @throws IllegalArgumentException if an argument value is forbidden (New Line and Carriage Return).
     */
    public void setKeyValue(String section, String key, String value)
            throws IllegalArgumentException {
        // Check for forbidden characters in section, key and value.
        if (section.indexOf(StringHelper.LINE_FEED) > -1) {
            throw new IllegalArgumentException("New Line is forbidden into INI section name");
        } else if (key.indexOf(StringHelper.LINE_FEED) > -1) {
            throw new IllegalArgumentException("New Line is forbidden into INI key name");
        } else if (value.indexOf(StringHelper.LINE_FEED) > -1) {
            throw new IllegalArgumentException("New Line is forbidden into INI key value");
        } else if (section.indexOf(StringHelper.CARRIAGE_RETURN) > -1) {
            throw new IllegalArgumentException("Carriage Return is forbidden into INI section name");
        } else if (key.indexOf(StringHelper.CARRIAGE_RETURN) > -1) {
            throw new IllegalArgumentException("Carriage Return is forbidden into INI key name");
        } else if (value.indexOf(StringHelper.CARRIAGE_RETURN) > -1) {
            throw new IllegalArgumentException("Carriage Return is forbidden into INI key value");
        }
        // Value storage.
        if (contents.containsKey(section)) {
            if (contents.get(section).containsKey(key)) {
                contents.get(section).get(key).setValue(value);
            } else {
                contents.get(section).put(key, new Entry(value, false)); // FIXED https://sourceforge.net/p/tikione/bugs/1/
            }
        } else {
            contents.put(section, new LinkedHashMap<String, Entry>(config.getDefNbSecKeys()));
            contents.get(section).put(key, new Entry(value, false));
        }
    }

    /**
     * Sort the content by section and key name. The sorting is done by default locale-sensitive <code>String</code> comparison.
     */
    public void sort() {
        sort(Ini.STR_COMPARATOR, Ini.STR_COMPARATOR);
    }

    /**
     * Sort the content by section and key name.
     *
     * @param comparator the comparator to determine the order of the sections and keys.
     */
    public void sort(Comparator<Object> comparator) {
        sort(comparator, comparator);
    }

    /**
     * Sort the content by section and key name.
     *
     * @param sectionComparator the comparator to determine the order of the sections.
     * @param keyComparator the comparator to determine the order of the keys.
     */
    public void sort(Comparator<Object> sectionComparator, Comparator<Object> keyComparator) {
        // Don't sort if empty.
        if ((contents.size() > 1)) {
            // Search for comments.
            Collection<String[]> comments = new ArrayList<>(config.getDefNbComments());
            Collection<String> sections = contents.keySet();
            for (String s : sections) {
                Collection<String> keys = contents.get(s).keySet();
                for (String k : keys) {
                    if (contents.get(s).get(k).isComment()) {
                        comments.add(new String[]{s, k});
                    }
                }
            }

            // Remove comments.
            for (String[] c : comments) {
                // Reminder: comm[0] and comm[1] are respectively the section name and the key name where the comment line is stored.
                contents.get(c[0]).remove(c[1]);
            }

            // Sort keys in each section.
            sections = contents.keySet();
            for (String s : sections) {
                if ((null != contents.get(s)) && (0 < contents.get(s).size())) {
                    Map<String, Entry> sortedKeys = new TreeMap<>(keyComparator);
                    sortedKeys.putAll(contents.get(s));
                    contents.get(s).clear();
                    contents.get(s).putAll(sortedKeys);
                }
            }

            // Sort sections.
            Map<String, Map<String, Entry>> sortedSections = new TreeMap<>(sectionComparator);
            sortedSections.putAll(contents);
            contents = sortedSections;
        }
    }

    /**
     * Store the INI content with platform's default file encoding and new line character(s).
     *
     * @param file the destination file to write content.
     * @throws IOException if an I/O error occurs.
     */
    public void store(File file)
            throws IOException {
        store(file, FileHelper.getPlatformEncoding(), FileHelper.getPlatformNewLine());
    }

    /**
     * Store the INI content.
     *
     * @param file the destination file to write content.
     * @param encoding the file encoding.
     * @param newLine the new line character(s).
     * @throws IOException if an I/O error occurs.
     */
    public void store(File file, String encoding, String newLine)
            throws IOException {
        try (OutputStream output = new FileOutputStream(file)) {
            store(output, encoding, newLine);
        }
    }

    /**
     * Store the INI content with platform's default file encoding and new line character(s). This method doesn't close the output
     * stream when ending.
     *
     * @param outputStream the destination output stream to write content.
     * @throws IOException if an I/O error occurs.
     */
    public void store(OutputStream outputStream)
            throws IOException {
        store(outputStream, FileHelper.getPlatformEncoding(), FileHelper.getPlatformNewLine());
    }

    /**
     * Store the INI content. This method doesn't close the output stream when ending.
     *
     * @param outputStream the destination output stream to write content.
     * @param encoding the file encoding.
     * @param newLine the new line character(s).
     * @throws IOException if an I/O error occurs.
     */
    public void store(OutputStream outputStream, String encoding, String newLine)
            throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, encoding)) {
            StringBuilder line = new StringBuilder(128);
            Collection<String> sections = contents.keySet();
            String defSectionStart = config.getDefSectionStart(0);
            String defSectionEnd = config.getDefSectionEnd(0);
            String defAffectation = config.getDefAffectation(0);
            int affLen = config.getDefAffectations()[0].length();
            for (String section : sections) {
                // Write section (if it is not the global section).
                if (!section.equals(GLOBAL_SECTION)) {
                    line.append(defSectionStart).append(section).append(defSectionEnd);
                    writer.write(line.toString());
                    writer.write(newLine);
                    line.delete(0, line.length());
                }
                // Write section's content.
                Collection<String> keys = contents.get(section).keySet();
                for (String key : keys) {
                    String nlRepl = getNewLineStr() + StringHelper.generateStrConcat(" ", affLen + key.length());
                    Entry entry = contents.get(section).get(key);
                    if (entry.isComment()) {
                        line.append(entry.getValue());
                    } else {
                        if (entry.isConcat()) {
                            line.append(key);
                            line.append(defAffectation);
                            line.append(entry.getValue().replaceAll(Entry.NEW_LINE_SEPARATOR_REGEX, nlRepl));
                        } else {
                            line.append(key);
                            line.append(defAffectation);
                            line.append(contents.get(section).get(key).getValue());
                        }
                    }
                    writer.write(line.toString());
                    writer.write(newLine);
                    line.delete(0, line.length());
                }
            }
            writer.flush();
        }
    }

    /**
     * An entry to store an information from an INI content. It can represent a comment or a key value.
     *
     * @author <a href="mailto:jonathan.lermitage@entreprise38.org">Jonathan Lermitage</a>
     * @since  JDK1.5
     */
    @SuppressWarnings("PublicInnerClass")
    public static class Entry implements Cloneable {

        /** Indicate if the entry is a comment or not. */
        private final boolean comment;

        /** The entry value. */
        private String value;

        /** Indicate if the entry is a lines concatenation or not. */
        private final boolean concat;

        /** The line separator for multiline key values. */
        public static final String NEW_LINE_SEPARATOR = "\n";

        /** The line separator for multiline key values, for use with regular expressions. */
        public static final String NEW_LINE_SEPARATOR_REGEX = "\\n";

        /**
         * Create a new instance of <code>Entry</code> for a comment.
         *
         * @param value the comment value.
         */
        public Entry(String value) {
            this.value = value;
            this.comment = true;
            this.concat = false;
        }

        /**
         * Create a new instance of <code>Entry</code> for a single line or multiline key value.
         *
         * @param value the value.
         * @param concat  <code>true</code> if the entry is a multiline value, <code>false</code> if single line value.
         */
        public Entry(String value, boolean concat) {
            this.value = value;
            this.comment = false;
            this.concat = concat;
        }

        /**
         * Create a new instance of <code>Entry</code> by copy.
         *
         * @param iniEntry the <code>Entry</code> to copy all fields.
         */
        public Entry(Entry iniEntry) {
            this.value = iniEntry.getValue();
            this.comment = iniEntry.isComment();
            this.concat = iniEntry.isConcat();
        }

        /**
         * Creates and returns a copy of this <code>Entry</code> object.
         *
         * @return a clone of this instance.
         */
        @Override
        public Entry clone() {
            try {
                return (Entry) super.clone();
            } catch (CloneNotSupportedException cnse) {
                // Cannot happen.
                throw new InternalError(cnse.toString());
            }
        }

        /**
         * Compares the specified object with this <code>Entry</code> for equality. Returns <code>true</code> if the given object
         * is also an <code>Entry</code> and the two <code>Entry</code> represent the same data. More formally, two
         * <code>Entry</code> e1 and e2 represent the same data if <code>e1.isComment()==equals(e2.isComment())</code>,
         * <code>e1.isConcat()==equals(e2.isConcat())</code> and <code>e1.getValue().equals(e2.getValue())</code>.
         *
         * @param obj the object to be compared for equality with this <code>Entry</code>.
         * @return <code>true</code> if the specified object is equal to this <code>Entry</code>, otherwise <code>false</code>.
         */
        @Override
        public boolean equals(Object obj) {
            boolean isEqual;
            if (obj == null) {
                isEqual = false;
            } else if (getClass() == obj.getClass()) {
                final Entry other = (Entry) obj;
                if (this.comment != other.isComment()) {
                    isEqual = false;
                } else if ((this.value == null) ? (other.getValue() != null) : !this.value.equals(other.getValue())) {
                    isEqual = false;
                } else if (this.concat != other.isConcat()) {
                    isEqual = false;
                } else {
                    isEqual = true;
                }
            } else {
                isEqual = false;
            }
            return isEqual;
        }

        /**
         * Returns the hash code value for this <code>Entry</code>.
         *
         * The hash code of an <code>Entry</code> is defined to be the hash code of the concatenation of its fields: two
         * booleans to indicate if the instance is a comment and if there is line concatenation, and a string to represent the entry
         * value.
         * <p>
         * For example, the hash code for an <code>Entry</code> that is not a comment, use line concatenation and value is "foo" is:
         * <blockquote><pre>"falsetruefoo".hashCode()</pre></blockquote>
         * This ensures that e1.equals(e2) implies that e1.hashCode()==e2.hashCode() for any two <code>Entry</code> e1 and e2, as
         * required by the general contract of <code>Object.hashCode</code>.
         *
         * @return the hash code value for this <code>Entry</code>.
         */
        @Override
        public int hashCode() {
            String hashstr = Boolean.toString(this.comment) + Boolean.toString(this.concat) + (this.value == null ? "" : this.value);
            return hashstr.hashCode();
        }

        /**
         * Get the entry value. Contains the entry value if it is not a comment, otherwise the comment marker plus the comment itself.
         *
         * @return the entry value.
         */
        public String getValue() {
            return value;
        }

        /**
         * Set the entry value. Contains the entry value if it is not a comment, otherwise the comment marker plus the comment itself.
         * @param value the entry value.
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Indicate if the entry is a comment or not.
         *
         * @return <code>true</code> if the entry is a comment, otherwise <code>false</code>.
         */
        public boolean isComment() {
            return this.comment;
        }

        /**
         * Indicate if the entry is a lines concatenation or not.
         *
         * @return <code>true</code> if the entry is a lines concatenation, otherwise <code>false</code>.
         */
        public boolean isConcat() {
            return concat;
        }
    }
}
