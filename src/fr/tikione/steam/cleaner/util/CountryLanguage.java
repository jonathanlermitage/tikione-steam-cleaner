package fr.tikione.steam.cleaner.util;

/**
 * UI language handler.
 */
public class CountryLanguage {

    /** Language code. */
    private String code;

    /** Language name. */
    private String desc;

    /**
     * Define a language to be used in the application's UI.
     *
     * @param code the two-letters language code (eg "fr" for French or "ru" for Russian).
     * @param desc the language name (eg "French" or "Russian")
     */
    public CountryLanguage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * The two-letters language code (eg "fr" for French or "ru" for Russian).
     *
     * @return the language code.
     */
    public String getCode() {
        return code;
    }

    /**
     * The language name (eg "French" or "Russian")
     *
     * @return the language name.
     */
    public String getDesc() {
        return desc;
    }
}
