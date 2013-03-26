package fr.tikione.steam.cleaner.util;

import java.io.File;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A redistributable package file or folder.
 */
public class Redist {

    private String pattern;

    private final String description;

    private Pattern compiledPattern;

    private File file;

    public Redist(String name, String description) {
        this.pattern = name;
        this.description = description;
        this.compiledPattern = Pattern.compile(name);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.file);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Redist other = (Redist) obj;
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }
    
    public Redist(File file, String description) {
        this.file = file;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getPattern() {
        return pattern;
    }

    public Pattern getCompiledPattern() {
        return compiledPattern;
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

    public File getFile() {
        return file;
    }
}
