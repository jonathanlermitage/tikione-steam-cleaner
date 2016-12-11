package fr.tikione.steam.cleaner.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UI language handler.
 */
@AllArgsConstructor
@Getter
public class CountryLanguage {
    
    /** Language code. */
    private final String code;
    
    /** Language name. */
    private final String desc;
}
