package fr.tikione.steam.cleaner.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.File;
import java.util.regex.Pattern;

/**
 * A description of a redistributable package file or folder found on the system storage -OR-
 * a file or folder name pattern that represents a single or a set of redistributable packages.
 */
@Getter
@EqualsAndHashCode(exclude = {"description", "compiledPattern"})
public class Redist {
    
    private final String description;
    
    private Pattern compiledPattern;
    
    private File file;
    
    /**
     * Define a file or folder name pattern that represents a single or a set of redistributable packages. Used to find
     * redistributable packages files and folders on the system storage.
     *
     * @param pattern the pattern (a regular expression) that represents the redistributable package.
     * @param description a description of the redistributable package.
     */
    public Redist(String pattern, String description) {
        this.description = description;
        this.compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * Define description of a redistributable package file or folder found on the system storage.
     *
     * @param file the redistributable package found on the system storage.
     * @param description a description of the redistributable package.
     */
    public Redist(File file, String description) {
        this.file = file;
        this.description = description;
    }
    
    public double getSize() {
        long fsize;
        if (file.isFile()) {
            fsize = org.apache.commons.io.FileUtils.sizeOf(file);
        } else if (file.isDirectory()) {
            fsize = org.apache.commons.io.FileUtils.sizeOfDirectory(file);
        } else {
            fsize = 0;
        }
        double floatSize = fsize;
        floatSize /= (1024.0 * 1024.0);
        return floatSize;
    }
}
