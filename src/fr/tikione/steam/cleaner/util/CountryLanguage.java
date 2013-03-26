package fr.tikione.steam.cleaner.util;

/**
 * UI language handler.
 */
public class CountryLanguage {

    private String code;

    private String desc;

    public CountryLanguage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
