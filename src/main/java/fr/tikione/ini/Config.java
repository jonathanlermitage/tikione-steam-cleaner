package fr.tikione.ini;

import fr.tikione.ini.util.MapHelper;
import fr.tikione.ini.util.StringHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Utility class for INI parser's configuration. In addition, it handles the internal configuration <code>Properties</code> object.
 */
public class Config implements Cloneable {

    /** Prefix for TikiOne INI internal properties: configuration. */
    private static final String CF_PFX_R = "fr.tikione.ini.rule.";

    /** Prefix for TikiOne INI internal properties: performance. */
    private static final String CF_PFX_S = "fr.tikione.ini.stat.";

    //<editor-fold defaultstate="collapsed" desc="Keys of /fr.tikione.ini.properties file: performance.">
    /** Average number of sections per file (for performance purpose). */
    public static final String CONF_INI_STAT_NB_SECTIONS_PERFILE = CF_PFX_S + "nb.sections.perfile";

    /** Sections <code>Map</code> load factor (for performance purpose). */
    public static final String CONF_INI_STAT_NB_SECTIONS_PERFILE_MAPLOADFACTOR = CF_PFX_S + "loadfactor.sections.perfile";

    /** Average number of keys per section (for performance purpose). */
    public static final String CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE = CF_PFX_S + "nb.keys.persection.perfile";

    /** Keys <code>Map</code> (one keys <code>Map</code> per section) load factor (for performance purpose). */
    public static final String CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE_MAPLOADFACTOR = CF_PFX_S + "loadfactor.keys.persection.perfile";

    /** Average number of shortcuts per line (for performance purpose). */
    public static final String CONF_INI_STAT_NB_SHRTCT_PERLINE = CF_PFX_S + "nb.shortcuts.perline";

    /** Average number of comment lines per file (for performance purpose). */
    public static final String CONF_INI_STAT_NB_COMMENTS_PERFILE = CF_PFX_S + "nb.comments.perfile";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Keys of /fr.tikione.ini.properties file: basic configuration.">
    /** String used to represent default value when getting a key value. */
    public static final String CONF_INI_RULE_KEY_GETVALUE_DEFAULTVALUE = CF_PFX_R + "keys.getvalue.defaultvalue";

    /** Strings that signal the start of a comment line. */
    public static final String CONF_INI_RULE_SECTION_START_SYMBOL = CF_PFX_R + "section.start.symbols";

    /** Strings that signal the start of a comment line. */
    public static final String CONF_INI_RULE_SECTION_END_SYMBOL = CF_PFX_R + "section.end.symbols";

    /** Strings that signal the start of a comment line. */
    public static final String CONF_INI_RULE_COMMENTS_START_SYMBOL = CF_PFX_R + "comments.start.symbols";

    /** Strings used to separate keys from values. */
    public static final String CONF_INI_RULE_AFFECTATION_SYMBOL = CF_PFX_R + "affectations.symbols";

    /** Strings list separator for the {@link #CONF_INI_RULE_COMMENTS_START_SYMBOL} property. */
    public static final String CONF_INI_RULE_SECTION_START_SYMBOLS_SEPARATOR = CF_PFX_R + "section.start.symbols.separator";

    /** Strings list separator for the {@link #CONF_INI_RULE_COMMENTS_START_SYMBOL} property. */
    public static final String CONF_INI_RULE_SECTION_END_SYMBOLS_SEPARATOR = CF_PFX_R + "section.end.symbols.separator";

    /** Strings list separator for the {@link #CONF_INI_RULE_COMMENTS_START_SYMBOL} property. */
    public static final String CONF_INI_RULE_COMMENTS_START_SYMBOLS_SEPARATOR = CF_PFX_R + "comments.start.symbols.separator";

    /** Strings list separator for the {@link #CONF_INI_RULE_AFFECTATION_SYMBOL} property. */
    public static final String CONF_INI_RULE_AFFECTATION_SYMBOLS_SEPARATOR = CF_PFX_R + "affectations.symbols.separator";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Keys of /fr.tikione.ini.properties file: advanced configuration.">
    /** String used to signal a line concatenation when parsing the INI content. */
    public static final String CONF_INI_RULE_LINE_CONCAT_SYMBOL = CF_PFX_R + "lineconcat.symbol";

    /** Maximum number of shortcuts pursuit, to prevent infinitive loops. */
    public static final String CONF_INI_RULE_MAX_KEYREF_PURSUIT = CF_PFX_R + "max.shortcuts.pursuit";

    /** Activate recognition of JVM environment variable shortcuts when getting a key value. */
    public static final String CONF_INI_RULE_KEY_GETVALUE_ENVVAR_SHRTCT = CF_PFX_R + "keys.getvalue.follow.envvar.shortcuts";

    /** Activate recognition of section/key shortcuts when getting a key value. */
    public static final String CONF_INI_RULE_KEY_GETVALUE_SECKEYREF_SHRTCT = CF_PFX_R + "keys.getvalue.follow.seckeyref.shortcuts";

    /** Activate recognition of system property shortcuts when getting a key value. */
    public static final String CONF_INI_RULE_KEY_GETVALUE_SYSPROP_SHRTCT = CF_PFX_R + "keys.getvalue.follow.sysprop.shortcuts";

    /** Activate conversion of Unicode escapes when reading key values, except line concatenation. */
    public static final String CONF_INI_RULE_KEY_GETVALUE_CONVERT_UNICODE = CF_PFX_R + "keys.getvalue.convert.unicode.escape";

    /** Activate conversion of backslash at end of line to line concatenation when parsing the Ini content. */
    public static final String CONF_INI_RULE_KEY_PARSE_CONVERT_LNINECONCAT = CF_PFX_R + "keys.parse.convert.lineconcat.escape";

    /** The regular expression used to recognize any type of shortcut (section/key reference, system property, and environment variable).
     *  It doesn't need to define a capturing group. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ANY = CF_PFX_R + "keys.parse.shortcut.any";

    /** The regular expression used to recognize environment variable shortcuts. It needs to define a capturing group to identify the
     * sequence to replace by the shortcut value. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR = CF_PFX_R + "keys.pattern.shortcut.envvar";

    /** The character(s) just before the environment variable name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_LEFT = CF_PFX_R + "keys.pattern.shortcut.envvar.left";

    /** The character(s) just after the environment variable name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_RIGHT = CF_PFX_R + "keys.pattern.shortcut.envvar.right";

    /** The regular expression used to recognize system property shortcuts. It needs to define a capturing group to identify the sequence
     * to replace by the shortcut value. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP = CF_PFX_R + "keys.pattern.shortcut.sysprop";

    /** The character(s) just before the system property name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_LEFT = CF_PFX_R + "keys.pattern.shortcut.sysprop.left";

    /** The character(s) just after the system property name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_RIGHT = CF_PFX_R + "keys.pattern.shortcut.sysprop.right";

    /** The regular expression used to recognize section/key shortcuts. It needs to define a capturing group to identify the sequence to
     *  replace by the shortcut value. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF = CF_PFX_R + "keys.pattern.shortcut.seckeyref";

    /** The character(s) just before the section/key name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_LEFT = CF_PFX_R + "keys.pattern.shortcut.seckeyref.left";

    /** The character(s) between the section and key names. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_MIDDLE = CF_PFX_R + "keys.pattern.shortcut.seckeyref.middle";

    /** The character(s) just after the section/key name. */
    public static final String CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_RIGHT = CF_PFX_R + "keys.pattern.shortcut.seckeyref.right";
    //</editor-fold>

    /** The internal INI configuration properties file. */
    private static final String INTERNAL_PROPERTIES_FILE_URL = "/fr.tikione.ini.properties";

    /** The default INI configuration <code>Properties</code> object. */
    private static final Properties DEFAULT_CONFIG = new Properties();

    /** An object to allow synchronization on the {@link #DEFAULT_CONFIG} object. */
    private static final Object DEFAULT_CONFIG_SYNC = new Object();

    /** Indicate if the {@link #DEFAULT_CONFIG} object has been initialized. */
    private static boolean initialized = false;

    /** The map to store properties values as strings. */
    protected final Map<String, String> propsString = new HashMap<>(11);

    /** The map to store properties values as tables of strings. */
    protected final Map<String, String[]> propsStringTable = new HashMap<>(4);

    /** The map to store properties values as booleans. */
    protected final Map<String, Boolean> propsBoolean = new HashMap<>(5);

    /** The map to store properties values as floats. */
    protected final Map<String, Float> propsFloat = new HashMap<>(2);

    /** The map to store properties values as integers. */
    protected final Map<String, Integer> propsInteger = new HashMap<>(5);

    /** The map to store properties values as compiled patterns. */
    protected final Map<String, Pattern> propsPattern = new LinkedHashMap<>(4);

    /**
     * Create a new instance of <code>Config</code> with default INI configuration properties.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal properties file.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    public Config()
            throws IOException,
                   PatternSyntaxException {
        initialize(false);
        internalLoadConfig(getDefaultConfigProperties());
    }

    /**
     * Create a new instance of <code>Config</code> by copy.
     *
     * @param config the <code>Config</code> to copy all properties.
     */
    public Config(Config config) {
        propsString.putAll(config.propsString);
        MapHelper.deepCopyMapStringTables(propsStringTable, config.propsStringTable);
        MapHelper.deepCopyMapBooleans(propsBoolean, config.propsBoolean);
        MapHelper.deepCopyMapFloats(propsFloat, config.propsFloat);
        MapHelper.deepCopyMapIntegers(propsInteger, config.propsInteger);
        MapHelper.deepCopyMapPatterns(propsPattern, config.propsPattern);
    }

    /**
     * Create a new instance of <code>Config</code> with the specified configuration properties.
     *
     * @param props the configuration properties to load.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    public Config(Properties props)
            throws PatternSyntaxException {
        internalLoadConfig(props);
    }

    /**
     * Creates and returns a copy of this <code>Config</code> object.
     *
     * @return a clone of this instance.
     */
    @Override
    public Config clone() {
        try {
            Config copy = (Config) super.clone();
            copy.propsString.putAll(propsString);
            MapHelper.deepCopyMapStringTables(copy.propsStringTable, propsStringTable);
            MapHelper.deepCopyMapBooleans(copy.propsBoolean, propsBoolean);
            MapHelper.deepCopyMapFloats(copy.propsFloat, propsFloat);
            MapHelper.deepCopyMapIntegers(copy.propsInteger, propsInteger);
            MapHelper.deepCopyMapPatterns(copy.propsPattern, propsPattern);
            return copy;
        } catch (CloneNotSupportedException cnse) {
            // Cannot happen.
            throw new InternalError(cnse.toString());
        }
    }

    /**
     * Compares the specified object with this <code>Config</code> for equality. Returns <code>true</code> if the given object
     * is also an <code>Config</code> and the two <code>Config</code> represent the same data
     *
     * @param obj the object to be compared for equality with this <code>Config</code>.
     * @return <code>true</code> if the specified object is equal to this <code>Config</code>, otherwise <code>false</code>.
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual;
        if (obj == null) {
            isEqual = false;
        } else if (getClass() == obj.getClass()) {
            final Config other = (Config) obj;
            if (!this.propsString.equals(other.propsString)) {
                isEqual = false;
            } else if (!MapHelper.mapsStringTablesEqual(this.propsStringTable, other.propsStringTable)) {
                isEqual = false;
            } else if (!this.propsBoolean.equals(other.propsBoolean)) {
                isEqual = false;
            } else if (!this.propsFloat.equals(other.propsFloat)) {
                isEqual = false;
            } else if (!this.propsInteger.equals(other.propsInteger)) {
                isEqual = false;
            } else if (!this.propsPattern.keySet().equals(other.propsPattern.keySet())) { // Evaluate keys only.
                isEqual = false;
            } else {
                isEqual = true;
                CHECK_PATTERNS:
                for (Entry<String, Pattern> entry : this.propsPattern.entrySet()) { // Evaluate patterns.
                    if (!entry.getValue().pattern().equals(other.propsPattern.get(entry.getKey()).pattern())) {
                        isEqual = false;
                        break CHECK_PATTERNS;
                    }
                }
            }
        } else {
            isEqual = false;
        }
        return isEqual;
    }

    /**
     * Returns the hash code value for this <code>Config</code>.
     *
     * The properties stored in a <code>Config</code> object are stored in various maps. The hash code of an <code>Config</code> is
     * defined to be the hash code of the concatenation of the hash code of each map, separated by a Line Feed character.
     * <p>
     * Because of the number of properties stored in a <code>Config</code>, this implementation may be time-consuming, and you
     * should not put <code>Config</code> objects in maps as keys (it will work, but it may slow down your application).
     *
     * @return the hash code value for this <code>Config</code>.
     */
    @Override
    public int hashCode() {
        StringBuilder hashstr = new StringBuilder(1024);
        hashstr.append(propsString.hashCode()).append("\n");
        int propsStringTableHashcode = 0;
        for (String[] values : propsStringTable.values()) {
            for (String val : values) {
                propsStringTableHashcode += val.hashCode();
            }
        }
        for (String key : propsStringTable.keySet()) {
            propsStringTableHashcode += key.hashCode();
        }
        hashstr.append(propsStringTableHashcode).append("\n");
        hashstr.append(propsBoolean.hashCode()).append("\n");
        hashstr.append(propsFloat.hashCode()).append("\n");
        hashstr.append(propsInteger.hashCode()).append("\n");
        hashstr.append(propsInteger.keySet().hashCode()).append("\n");
        for (Entry<String, Pattern> entry : this.propsPattern.entrySet()) {
            hashstr.append(entry.getKey()).append("\n").append(entry.getValue().pattern()).append("\n");
        }
        return hashstr.toString().hashCode();
    }

    /**
     * (Re)Initialize the default INI configuration <code>Properties</code> object from <code>/fr.tikione.ini.properties</code>.
     * Initialization is synchronized on a global <code>Properties</code> object, so you can invoke this method in a thread while you are
     * building some INI objects or invoking {@link #getDefaultConfigProperties()} in other threads.
     *
     * @param reinitialization if <code>true</code> then force a re-read the internal properties file.
     * @throws IOException if an I/O error occurs while retrieving the internal properties file.
     */
    public static void initialize(boolean reinitialization)
            throws IOException {
        if (reinitialization || !initialized) {
            synchronized (DEFAULT_CONFIG_SYNC) {
                try (InputStream propertiesStream = Ini.class.getResourceAsStream(INTERNAL_PROPERTIES_FILE_URL)) {
                    DEFAULT_CONFIG.clear();
                    DEFAULT_CONFIG.load(propertiesStream);
                }
                initialized = true;
            }
        }
    }

    /**
     * (Re)Initialize the default INI configuration with a provided <code>Properties</code> object.
     * Initialization is synchronized on a global <code>Properties</code> object, so you can invoke this method in a thread while you are
     * building some INI objects or invoking {@link #getDefaultConfigProperties()} in other threads.
     *
     * @param tikioneProperties the provided <code>Properties</code> object.
     */
    public static void initialize(Properties tikioneProperties) {
        synchronized (DEFAULT_CONFIG_SYNC) {
            DEFAULT_CONFIG.clear();
            DEFAULT_CONFIG.putAll(tikioneProperties);
            initialized = true;
        }
    }

    /**
     * Get a copy of the default configuration <code>Properties</code> object loaded from <code>/fr.tikione.ini.properties</code>, or an
     * other <code>Properties</code> object if the {@link #initialize(java.util.Properties)} method has been called before.
     * Because of initialization synchronization, you can safely invoke this method in a thread while you are invoking
     * the {@link #initialize(java.util.Properties)} or {@link #initialize(boolean)} methods.
     *
     * @return a copy of the default <code>Properties</code> object.
     * @throws IOException if an I/O error occurs while retrieving the internal properties file.
     */
    public static Properties getDefaultConfigProperties()
            throws IOException {
        initialize(false);
        Properties res;
        synchronized (DEFAULT_CONFIG_SYNC) {
            res = new Properties(DEFAULT_CONFIG);
        }
        return res;
    }

    /**
     * (Re)configure the configuration with the specified properties.
     *
     * @param props the configuration properties to load.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    public void loadConfig(Properties props)
            throws PatternSyntaxException {
        internalLoadConfig(props);
    }

    /**
     * (Re)configure the configuration with the specified properties. For internal use and avoid overridable method call in constructor.
     *
     * @param props the configuration properties to load.
     * @throws PatternSyntaxException if a regular expression (stored in the configuration properties) cannot compile.
     */
    private void internalLoadConfig(Properties props)
            throws PatternSyntaxException {
        // Load basic config.
        propsInteger.put(CONF_INI_STAT_NB_SECTIONS_PERFILE,
                getConfigPropertyAsInt(props, CONF_INI_STAT_NB_SECTIONS_PERFILE));
        propsFloat.put(CONF_INI_STAT_NB_SECTIONS_PERFILE_MAPLOADFACTOR,
                getConfigPropertyAsFloat(props, CONF_INI_STAT_NB_SECTIONS_PERFILE_MAPLOADFACTOR));
        propsInteger.put(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE,
                getConfigPropertyAsInt(props, CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE));
        propsFloat.put(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE_MAPLOADFACTOR,
                getConfigPropertyAsFloat(props, CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE_MAPLOADFACTOR));
        propsInteger.put(CONF_INI_STAT_NB_SHRTCT_PERLINE,
                getConfigPropertyAsInt(props, CONF_INI_STAT_NB_SHRTCT_PERLINE));
        propsInteger.put(CONF_INI_STAT_NB_COMMENTS_PERFILE,
                getConfigPropertyAsInt(props, CONF_INI_STAT_NB_COMMENTS_PERFILE));
        String secStartSymbols = getConfigProperty(props, CONF_INI_RULE_SECTION_START_SYMBOL);
        String secStartSymbolsSep = getConfigProperty(props, CONF_INI_RULE_SECTION_START_SYMBOLS_SEPARATOR);
        String secEndSymbols = getConfigProperty(props, CONF_INI_RULE_SECTION_END_SYMBOL);
        String secEndSymbolsSep = getConfigProperty(props, CONF_INI_RULE_SECTION_END_SYMBOLS_SEPARATOR);
        String commStartSymbols = getConfigProperty(props, CONF_INI_RULE_COMMENTS_START_SYMBOL);
        String commStartSymbolsSep = getConfigProperty(props, CONF_INI_RULE_COMMENTS_START_SYMBOLS_SEPARATOR);
        String affectationSymbols = getConfigProperty(props, CONF_INI_RULE_AFFECTATION_SYMBOL);
        String affectationSymbolsSep = getConfigProperty(props, CONF_INI_RULE_AFFECTATION_SYMBOLS_SEPARATOR);
        propsStringTable.put(CONF_INI_RULE_SECTION_START_SYMBOL,
                StringHelper.tSplit(secStartSymbols, secStartSymbolsSep));
        propsStringTable.put(CONF_INI_RULE_SECTION_END_SYMBOL,
                StringHelper.tSplit(secEndSymbols, secEndSymbolsSep));
        propsStringTable.put(CONF_INI_RULE_COMMENTS_START_SYMBOL,
                StringHelper.tSplit(commStartSymbols, commStartSymbolsSep));
        propsStringTable.put(CONF_INI_RULE_AFFECTATION_SYMBOL,
                StringHelper.tSplit(affectationSymbols, affectationSymbolsSep));
        // Load advanced config.
        propsString.put(CONF_INI_RULE_LINE_CONCAT_SYMBOL,
                getConfigProperty(props, CONF_INI_RULE_LINE_CONCAT_SYMBOL));
        propsInteger.put(CONF_INI_RULE_MAX_KEYREF_PURSUIT,
                getConfigPropertyAsInt(props, CONF_INI_RULE_MAX_KEYREF_PURSUIT));
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_ENVVAR_SHRTCT,
                getConfigPropertyAsBoolean(props, CONF_INI_RULE_KEY_GETVALUE_ENVVAR_SHRTCT));
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_SYSPROP_SHRTCT,
                getConfigPropertyAsBoolean(props, CONF_INI_RULE_KEY_GETVALUE_SYSPROP_SHRTCT));
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_SECKEYREF_SHRTCT,
                getConfigPropertyAsBoolean(props, CONF_INI_RULE_KEY_GETVALUE_SECKEYREF_SHRTCT));
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_CONVERT_UNICODE,
                getConfigPropertyAsBoolean(props, CONF_INI_RULE_KEY_GETVALUE_CONVERT_UNICODE));
        propsBoolean.put(CONF_INI_RULE_KEY_PARSE_CONVERT_LNINECONCAT,
                getConfigPropertyAsBoolean(props, CONF_INI_RULE_KEY_PARSE_CONVERT_LNINECONCAT));
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR,
                Pattern.compile(getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR)));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_LEFT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_LEFT));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_RIGHT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_RIGHT));
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP,
                Pattern.compile(getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP)));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_LEFT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_LEFT));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_RIGHT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_RIGHT));
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF,
                Pattern.compile(getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF)));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_LEFT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_LEFT));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_MIDDLE,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_MIDDLE));
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_RIGHT,
                getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_RIGHT));
        propsString.put(CONF_INI_RULE_KEY_GETVALUE_DEFAULTVALUE,
                getConfigProperty(props, CONF_INI_RULE_KEY_GETVALUE_DEFAULTVALUE));
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ANY,
                Pattern.compile(getConfigProperty(props, CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ANY)));
    }

    /**
     * Extract a property value from a <code>Properties</code> object. The property's leading and trailing white-space characters
     * (<code>&#92;u0020</code>) and tabulations (<code>&#92;u0009</code>) are removed.
     *
     * @param props the <code>Properties</code> object.
     * @param name the property's name.
     * @return the property value.
     */
    private String getConfigProperty(Properties props, String name) {
        return StringHelper.tTrim(props.getProperty(name));
    }

    /**
     * Extract a property value as an integer from a <code>Properties</code> object. The property's leading and trailing white-space
     * characters (<code>&#92;u0020</code>) and tabulations (<code>&#92;u0009</code>) are removed before computing the integer.
     *
     * @param props the <code>Properties</code> object.
     * @param name the property's name.
     * @return the property value as an integer.
     */
    private int getConfigPropertyAsInt(Properties props, String name) {
        return Integer.parseInt(getConfigProperty(props, name));
    }

    /**
     * Extract a property value as a float from a <code>Properties</code> object. The property's leading and trailing white-space
     * characters (<code>&#92;u0020</code>) and tabulations (<code>&#92;u0009</code>) are removed before computing the integer.
     *
     * @param props the <code>Properties</code> object.
     * @param name the property's name.
     * @return the property value as a float.
     */
    private float getConfigPropertyAsFloat(Properties props, String name) {
        return Float.parseFloat(getConfigProperty(props, name));
    }

    /**
     * Extract a property value as a boolean from a <code>Properties</code> object. The property's leading and trailing white-space
     * characters (<code>&#92;u0020</code>) and tabulations (<code>&#92;u0009</code>) are removed before computing the boolean.
     *
     * @param props the <code>Properties</code> object.
     * @param name the property's name.
     * @return the property value as a boolean.
     */
    private boolean getConfigPropertyAsBoolean(Properties props, String name) {
        return Boolean.parseBoolean(getConfigProperty(props, name));
    }

    /**
     * Enable recognition and following of environment variable shortcuts when getting a key value.
     *
     * @param enable <code>true</code> to enable, <code>false</code> to disable.
     * @return the current <code>Config</code>.
     */
    public Config enableReadEnvShortcuts(boolean enable) {
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_ENVVAR_SHRTCT, enable);
        return this;
    }

    /**
     * Enable recognition and following of section/key shortcuts when getting a key value.
     *
     * @param enable <code>true</code> to enable, <code>false</code> to disable.
     * @return the current <code>Config</code>.
     */
    public Config enableReadSeckeyShortcuts(boolean enable) {
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_SECKEYREF_SHRTCT, enable);
        return this;
    }

    /**
     * Enable recognition and following of system property shortcuts when getting a key value.
     *
     * @param enable <code>true</code> to enable, <code>false</code> to disable.
     * @return the current <code>Config</code>.
     */
    public Config enableReadSysShortcuts(boolean enable) {
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_SYSPROP_SHRTCT, enable);
        return this;
    }

    /**
     * Enable conversion of Unicode escapes to UTF8 when getting a key value.
     *
     * @param enable <code>true</code> to enable, <code>false</code> to disable.
     * @return the current <code>Config</code>.
     */
    public Config enableReadUnicodeEscConv(boolean enable) {
        propsBoolean.put(CONF_INI_RULE_KEY_GETVALUE_CONVERT_UNICODE, enable);
        return this;
    }

    /**
     * Enable conversion of backslash at end of line to line concatenation when parsing the INI content.
     *
     * @param enable <code>true</code> to enable, <code>false</code> to disable.
     * @return the current <code>Config</code>.
     */
    public Config enableParseLineConcat(boolean enable) {
        propsBoolean.put(CONF_INI_RULE_KEY_PARSE_CONVERT_LNINECONCAT, enable);
        return this;
    }

    /**
     * Indicate if recognition and following of environment variable shortcuts when getting a key value is enabled.
     *
     * @return <code>true</code> if enabled, <code>false</code> if disabled.
     */
    public boolean isReadEnvShortcutsEnabled() {
        return propsBoolean.get(CONF_INI_RULE_KEY_GETVALUE_ENVVAR_SHRTCT).booleanValue();
    }

    /**
     * Indicate if recognition and following of section/key shortcuts when getting a key value is enabled.
     *
     * @return <code>true</code> if enabled, <code>false</code> if disabled.
     */
    public boolean isReadSeckeyShortcutsEnabled() {
        return propsBoolean.get(CONF_INI_RULE_KEY_GETVALUE_SECKEYREF_SHRTCT).booleanValue();
    }

    /**
     * Indicate if recognition and following of system property shortcuts when getting a key value is enabled.
     *
     * @return <code>true</code> if enabled, <code>false</code> if disabled.
     */
    public boolean isReadSysShortcutsEnabled() {
        return propsBoolean.get(CONF_INI_RULE_KEY_GETVALUE_SYSPROP_SHRTCT).booleanValue();
    }

    /**
     * Indicate if conversion of Unicode escapes to UTF8 when getting a key value is enabled.
     *
     * @return <code>true</code> if enabled, <code>false</code> if disabled.
     */
    public boolean isReadUnicodeEscConvEnabled() {
        return propsBoolean.get(CONF_INI_RULE_KEY_GETVALUE_CONVERT_UNICODE).booleanValue();
    }

    /**
     * Indicate if conversion of backslash at end of line to line concatenation when parsing the INI content is enabled.
     *
     * @return <code>true</code> if enabled, <code>false</code> if disabled.
     */
    public boolean isParseLineConcatEnabled() {
        return propsBoolean.get(CONF_INI_RULE_KEY_PARSE_CONVERT_LNINECONCAT).booleanValue();
    }

    /**
     * Get a copy of the INI strings used to separate keys from values.
     *
     * @return a copy of the INI strings used to separate keys from values.
     */
    public String[] getDefAffectations() {
        return propsStringTable.get(CONF_INI_RULE_AFFECTATION_SYMBOL).clone();
    }

    /**
     * Get one of the INI strings used to separate keys from values.
     *
     * @param index the number (index) of the string to get.
     * @return a copy of the INI strings used to separate keys from values.
     */
    public String getDefAffectation(int index) {
        return propsStringTable.get(CONF_INI_RULE_AFFECTATION_SYMBOL)[index];
    }

    /**
     * Get a copy of the INI strings that signal the start of a comment line.
     *
     * @return a copy of the INI strings that signal the start of a comment line.
     */
    public String[] getDefCommentStarts() {
        return propsStringTable.get(CONF_INI_RULE_COMMENTS_START_SYMBOL).clone();
    }

    /**
     * Get the INI string used to represent default value when getting a key value.
     *
     * @return the INI string used to signal a line concatenation when parsing the INI content.
     */
    public String getDefDefaultKeyValue() {
        return propsString.get(CONF_INI_RULE_KEY_GETVALUE_DEFAULTVALUE);
    }

    /**
     * Get one of the INI strings that signal the start of a comment line.
     *
     * @param index the number (index) of the string to get.
     * @return a copy of the INI strings that signal the start of a comment line.
     */
    public String getDefCommentStart(int index) {
        return propsStringTable.get(CONF_INI_RULE_COMMENTS_START_SYMBOL)[index];
    }

    /**
     * Get the maximum number of shortcuts pursuit (environment variable shortcuts, system property shortcuts, section/key reference
     * shortcuts). To prevent infinitive loops due to a pair of shortcuts that calls them one another.
     *
     * @return the maximum number of shortcuts pursuit.
     */
    public int getDefMaxShortcutsPursuit() {
        return propsInteger.get(CONF_INI_RULE_MAX_KEYREF_PURSUIT).intValue();
    }

    /**
     * Get a copy of the INI strings used to represent a section ending.
     *
     * @return a copy of the INI strings used to represent a section ending.
     */
    public String[] getDefSectionEnds() {
        return propsStringTable.get(CONF_INI_RULE_SECTION_END_SYMBOL).clone();
    }

    /**
     * Get one of the INI strings used to represent a section ending.
     *
     * @param index the number (index) of the string to get.
     * @return a copy of the INI strings used to represent a section ending.
     */
    public String getDefSectionEnd(int index) {
        return propsStringTable.get(CONF_INI_RULE_SECTION_END_SYMBOL)[index];
    }

    /**
     * Get the INI string used to signal a line concatenation when parsing the INI content.
     *
     * @return the INI string used to signal a line concatenation when parsing the INI content.
     */
    public String getDefLineConcat() {
        return propsString.get(CONF_INI_RULE_LINE_CONCAT_SYMBOL);
    }

    /**
     * Get a copy of the INI strings used to represent a section starting.
     *
     * @return a copy of the INI strings used to represent a section starting.
     */
    public String[] getDefSectionStarts() {
        return propsStringTable.get(CONF_INI_RULE_SECTION_START_SYMBOL).clone();
    }

    /**
     * Get one of the INI strings used to represent a section starting.
     *
     * @param index the number (index) of the string to get.
     * @return a copy of the INI strings used to represent a section starting.
     */
    public String getDefSectionStart(int index) {
        return propsStringTable.get(CONF_INI_RULE_SECTION_START_SYMBOL)[index];
    }

    /**
     * Get the usual number of comment lines of one INI file.
     *
     * @return the usual number of comment lines of one INI file.
     */
    public int getDefNbComments() {
        return propsInteger.get(CONF_INI_STAT_NB_COMMENTS_PERFILE).intValue();
    }

    /**
     * Get the usual number of shortcuts (environment variable shortcuts, system property shortcuts, section/key shortcuts) in
     * a line.
     *
     * @return the usual number of references in a line.
     */
    public int getDefNbShortcuts() {
        return propsInteger.get(CONF_INI_STAT_NB_SHRTCT_PERLINE).intValue();
    }

    /**
     * Get the usual number of sections in one INI file.
     *
     * @return the usual number of sections in one INI file.
     */
    public int getDefNbSec() {
        return propsInteger.get(CONF_INI_STAT_NB_SECTIONS_PERFILE).intValue();
    }

    /**
     * Get the sections <code>Map</code> load factor.
     *
     * @return the load factor.
     */
    public float getDefSecLoadfactor() {
        return propsFloat.get(CONF_INI_STAT_NB_SECTIONS_PERFILE_MAPLOADFACTOR).floatValue();
    }

    /**
     * Get the usual number of keys in a section of one INI file.
     *
     * @return the usual number of keys in a section of one INI file.
     */
    public int getDefNbSecKeys() {
        return propsInteger.get(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE).intValue();
    }

    /**
     * Get the keys <code>Map</code> (one keys <code>Map</code> per section) load factor.
     *
     * @return the load factor.
     */
    public float getDefSecKeysLoadfactor() {
        return propsFloat.get(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE_MAPLOADFACTOR).floatValue();
    }

    /**
     * Set the INI strings used to separate keys from values.
     *
     * @param defAffectations the INI strings used to separate keys from values.
     */
    public void setDefAffectations(String[] defAffectations) {
        propsStringTable.put(CONF_INI_RULE_AFFECTATION_SYMBOL, defAffectations.clone());
    }

    /**
     * Set the INI strings that signal the start of a comment line.
     *
     * @param defCommentStarts the INI strings that signal the start of a comment line.
     */
    public void setDefCommentStarts(String[] defCommentStarts) {
        propsStringTable.put(CONF_INI_RULE_COMMENTS_START_SYMBOL, defCommentStarts.clone());
    }

    /**
     * Set the INI string used to represent default value when getting a key value.
     *
     * @param defDefaultValue the default value when getting a key value.
     */
    public void setDefDefaultKeyValue(String defDefaultValue) {
        propsString.put(CONF_INI_RULE_KEY_GETVALUE_DEFAULTVALUE, defDefaultValue);
    }

    /**
     * Set the maximum number of shortcuts pursuit (environment variable shortcuts, system property shortcuts, section/key reference
     * shortcuts). To prevent infinitive loops due to a pair of shortcuts that calls them one another.
     *
     * @param defMaxShortcutsPursuit the maximum number of shortcuts pursuit.
     */
    public void setDefMaxShortcutsPursuit(int defMaxShortcutsPursuit) {
        propsInteger.put(CONF_INI_RULE_MAX_KEYREF_PURSUIT, defMaxShortcutsPursuit);
    }

    /**
     * Set the INI strings used to represent a section ending.
     *
     * @param defSectionEnds the INI strings used to represent a section ending.
     */
    public void setDefSectionEnds(String[] defSectionEnds) {
        propsStringTable.put(CONF_INI_RULE_SECTION_END_SYMBOL, defSectionEnds.clone());
    }

    /**
     * Set the INI string used to signal a line concatenation when parsing the INI content.
     *
     * @param defLineConcat the INI string used to signal a line concatenation when parsing the INI content.
     */
    public void setDefLineConcat(String defLineConcat) {
        propsString.put(CONF_INI_RULE_LINE_CONCAT_SYMBOL, defLineConcat);
    }

    /**
     * Set the INI strings used to represent a section starting.
     *
     * @param defSectionStarts the INI strings used to represent a section starting.
     */
    public void setDefSectionStarts(String[] defSectionStarts) {
        propsStringTable.put(CONF_INI_RULE_SECTION_START_SYMBOL, defSectionStarts.clone());
    }

    /**
     * Set the usual number of comment lines of one INI file.
     *
     * @param defNbComments the usual number of comment lines of one INI file.
     */
    public void setDefNbComments(int defNbComments) {
        propsInteger.put(CONF_INI_STAT_NB_COMMENTS_PERFILE, defNbComments);
    }

    /**
     * Set the usual number of shortcuts (environment variable shortcuts, system property shortcuts, section/key shortcuts) in
     * a line.
     *
     * @param defNbShortcuts the usual number of shortcuts in a line.
     */
    public void setDefNbShortcuts(int defNbShortcuts) {
        propsInteger.put(CONF_INI_STAT_NB_SHRTCT_PERLINE, defNbShortcuts);
    }

    /**
     * Set the usual number of sections in one INI file.
     *
     * @param defNbSec the usual number of sections in one INI file.
     */
    public void setDefNbSec(int defNbSec) {
        propsInteger.put(CONF_INI_STAT_NB_SECTIONS_PERFILE, defNbSec);
    }

    /**
     * Set the sections <code>Map</code> load factor.
     *
     * @param defSecLoadfactor the load factor.
     */
    public void setDefSecLoadfactor(float defSecLoadfactor) {
        propsFloat.put(CONF_INI_STAT_NB_SECTIONS_PERFILE_MAPLOADFACTOR, defSecLoadfactor);
    }

    /**
     * Set the usual number of keys in a section of one INI file.
     *
     * @param defNbSecKeys the usual number of keys in a section of one INI file.
     */
    public void setDefNbSecKeys(int defNbSecKeys) {
        propsInteger.put(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE, defNbSecKeys);
    }

    /**
     * Set the keys <code>Map</code> (one keys <code>Map</code> per section) load factor.
     *
     * @param defSecKeysLoadfactor the load factor.
     */
    public void setDefSecKeysLoadfactor(float defSecKeysLoadfactor) {
        propsFloat.put(CONF_INI_STAT_NB_KEYS_PERSECTION_PERFILE_MAPLOADFACTOR, defSecKeysLoadfactor);
    }

    /**
     * Get the regular expression used to recognize environment variable shortcuts.
     *
     * @return the compiled pattern.
     */
    public Pattern getPaEnvShortcut() {
        return propsPattern.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR);
    }

    /**
     * Get the character(s) just before the environment variable name.
     *
     * @return the characters just before the environment variable name.
     */
    public String getPaEnvShortcutLeft() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_LEFT);
    }

    /**
     * Set the character(s) just after the environment variable name.
     *
     * @return the character(s) just after the environment variable name.
     */
    public String getPaEnvShortcutRight() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_RIGHT);
    }

    /**
     * Get the regular expression used to recognize system property shortcuts.
     *
     * @return the compiled pattern.
     */
    public Pattern getPaPropShortcut() {
        return propsPattern.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP);
    }

    /**
     * Get the character(s) just before the system property name.
     *
     * @return the character(s) just before the system property name.
     */
    public String getPaPropShortcutLeft() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_LEFT);
    }

    /**
     * Get the character(s) just after the system property name.
     *
     * @return the character(s) just after the system property name.
     */
    public String getPaPropShortcutRight() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_RIGHT);
    }

    /**
     * Get the regular expression used to recognize section/key shortcuts.
     *
     * @return the compiled pattern.
     */
    public Pattern getPaSeckeyShortcut() {
        return propsPattern.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF);
    }

    /**
     * Get the character(s) just before the section/key name.
     *
     * @return the character(s) just before the section/key name.
     */
    public String getPaSeckeyShortcutLeft() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_LEFT);
    }

    /**
     * Get the character(s) between the section and key names.
     *
     * @return the character(s) between the section and key names.
     */
    public String getPaSeckeyShortcutMiddle() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_MIDDLE);
    }

    /**
     * Get the character(s) just after the section/key name.
     *
     * @return the character(s) just after the section/key name.
     */
    public String getPaSeckeyShortcutRight() {
        return propsString.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_RIGHT);
    }

    /**
     * Get the regular expression used to recognize any type of shortcut (section/key reference, system
     * property, and environment variable).
     *
     * @return the compiled pattern.
     */
    public Pattern getPaShortcut() {
        return propsPattern.get(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ANY);
    }

    /**
     * Set the regular expression used to recognize environment variable shortcuts. It needs to define a capturing group to identify the
     * sequence to replace by the shortcut value.
     *
     * @param paEnvShortcut the regular expression.
     * @throws PatternSyntaxException if the regular expression cannot compile.
     */
    public void setPaEnvShortcut(String paEnvShortcut)
            throws PatternSyntaxException {
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR, Pattern.compile(paEnvShortcut));
    }

    /**
     * Set the characters just before the environment variable name.
     *
     * @param paEnvShortcutLeft the characters just before the environment variable name.
     */
    public void setPaEnvShortcutLeft(String paEnvShortcutLeft) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_LEFT, paEnvShortcutLeft);
    }

    /**
     * Set the character(s) just after the environment variable name.
     *
     * @param paEnvShortcutRight the character(s) just after the environment variable name.
     */
    public void setPaEnvShortcutRight(String paEnvShortcutRight) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ENVVAR_RIGHT, paEnvShortcutRight);
    }

    /**
     * Set the regular expression used to recognize system property shortcuts. It needs to define a capturing group to identify the
     * sequence to replace by the shortcut value.
     *
     * @param paPropShortcut the regular expression.
     * @throws PatternSyntaxException if the regular expression cannot compile.
     */
    public void setPaPropShortcut(String paPropShortcut)
            throws PatternSyntaxException {
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP, Pattern.compile(paPropShortcut));
    }

    /**
     * Set the character(s) just before the system property name.
     *
     * @param paPropShortcutLeft the character(s) just before the system property name.
     */
    public void setPaPropShortcutLeft(String paPropShortcutLeft) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_LEFT, paPropShortcutLeft);
    }

    /**
     * Set the character(s) just after the system property name.
     *
     * @param paPropShortcutRight the character(s) just after the system property name.
     */
    public void setPaPropShortcutRight(String paPropShortcutRight) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SYSPROP_RIGHT, paPropShortcutRight);
    }

    /**
     * Set the regular expression used to recognize section/key shortcuts. It needs to define a capturing group to identify the sequence
     * to replace by the shortcut value.
     *
     * @param paSeckeyShortcut the regular expression.
     * @throws PatternSyntaxException if the regular expression cannot compile.
     */
    public void setPaSeckeyShortcut(String paSeckeyShortcut)
            throws PatternSyntaxException {
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF, Pattern.compile(paSeckeyShortcut));
    }

    /**
     * set the character(s) just before the section/key name.
     *
     * @param paSeckeyShortcutLeft the character(s) just before the section/key name.
     */
    public void setPaSeckeyShortcutLeft(String paSeckeyShortcutLeft) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_LEFT, paSeckeyShortcutLeft);
    }

    /**
     * Set the character(s) between the section and key names.
     *
     * @param paSeckeyShortcutMiddle the character(s) between the section and key names.
     */
    public void setPaSeckeyShortcutMiddle(String paSeckeyShortcutMiddle) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_MIDDLE, paSeckeyShortcutMiddle);
    }

    /**
     * Set the character(s) just after the section/key name.
     *
     * @param paSeckeyShortcutRight the character(s) just after the section/key name.
     */
    public void setPaSeckeyShortcutRight(String paSeckeyShortcutRight) {
        propsString.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_SECKEYREF_RIGHT, paSeckeyShortcutRight);
    }

    /**
     * Set the regular expression used to recognize any type of shortcut (section/key reference, system property, and environment
     * variable). It doesn't need to define a capturing group.
     *
     * @param paShortcut the regular expression.
     * @throws PatternSyntaxException if the regular expression cannot compile.
     */
    public void setPaShortcut(String paShortcut)
            throws PatternSyntaxException {
        propsPattern.put(CONF_INI_RULE_KEY_PATTERN_SHRTCUT_ANY, Pattern.compile(paShortcut));
    }
}
