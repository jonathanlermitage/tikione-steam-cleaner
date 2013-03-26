package fr.tikione.steam.cleaner.util;

import fr.tikione.ini.util.StringHelper;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.gui.dialog.JFrameMain;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFrame;

/**
 * File utilities.
 */
public class FileUtils {

    /** Suppresses default constructor, ensuring non-instantiability. */
    private FileUtils() {
    }

    /**
     * List all files and folders of a specific folder with a recursive search. While searching, this method shows advancement by
     * setting and updating the given progress-bar.
     *
     * @param jframe the frame to show progress.
     * @param files the collection of files and folder to complete with results.
     * @param folders the base paths to initiate research from.
     * @param depth the recursive search depth.
     * @param dangerousFolders list of folders patterns to exclude.
     */
    public static void listDir(JFrame jframe, Collection<File> files, Collection<File> folders, int depth,
            final List<Pattern> dangerousFolders) {
        String frameTitle = jframe.getTitle();
        try {
            for (File folder : folders) {
                Log.info("Folder to scan: '" + folder.getAbsolutePath() + '\'');
                try {
                    files.addAll(org.apache.commons.io.FileUtils.listFiles(folder, null, false));
                    File[] subFolders;
                    subFolders = folder.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File pathname) {
                            boolean accept;
                            if (pathname.isDirectory()) {
                                accept = true;
                                String abslowPath = pathname.getAbsolutePath().toLowerCase(Main.SYS_LOCALE);
                                EXCLUDE_DANGEROUS:
                                for (Pattern dangerousPatt : dangerousFolders) {
                                    if (StringHelper.checkRegex(abslowPath, dangerousPatt)) {
                                        accept = false;
                                        Log.info("Skipped hazardous place: '" + pathname + "'");
                                        break EXCLUDE_DANGEROUS;
                                    }
                                }
                            } else {
                                accept = false;
                            }
                            return accept;
                        }
                    });
                    if (depth > 0 && subFolders != null) {
                        FOLDER_LISTING:
                        for (File subFolder : subFolders) {
                            if (JFrameMain.isCLOSING_APP()) {
                                break FOLDER_LISTING;
                            }
                            files.add(subFolder);
                            listDirNoRecount(jframe, files, subFolder, depth - 1, dangerousFolders);
                        }
                    }
                } catch (Exception ex) {
                    Log.error(ex);
                }
            }
        } finally {
            jframe.setTitle(frameTitle);
        }
    }

    /**
     * List all files and folders of a specific folder with a recursive search.
     *
     * @param files the collection of files and folder to complete with results.
     * @param folder the base path to initiate research from.
     * @param depth the recursive search depth.
     * @param dangerousFolders list of folders patterns to exclude.
     */
    private static void listDirNoRecount(final JFrame jframe, Collection<File> files, File folder, int depth,
            final List<Pattern> dangerousFolders) {
        jframe.setTitle(folder.getAbsolutePath() + File.separatorChar);
        files.addAll(org.apache.commons.io.FileUtils.listFiles(folder, null, false));
        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                boolean accept;
                if (pathname.isDirectory()) {
                    accept = true;
                    String abslowPath = pathname.getAbsolutePath().toLowerCase(Main.SYS_LOCALE);
                    EXCLUDE_DANGEROUS:
                    for (Pattern dangerousPatt : dangerousFolders) {
                        if (StringHelper.checkRegex(abslowPath, dangerousPatt)) {
                            accept = false;
                            Log.info("Skipped hazardous place: '" + pathname + "'");
                            break EXCLUDE_DANGEROUS;
                        }
                    }
                } else {
                    accept = false;
                }
                return accept;
            }
        });
        if (depth > 0 && subFolders != null) {
            FILE_LISTING:
            for (File subFolder : subFolders) { // FIXED avoid NPE on (protected) subFolders if custom dir.
                if (JFrameMain.isCLOSING_APP()) {
                    break FILE_LISTING;
                }
                files.add(subFolder);
                listDirNoRecount(jframe, files, subFolder, depth - 1, dangerousFolders);
            }
        }
    }

    /**
     * Check if a file-name verifies one of the patterns in the patterns-collection.
     *
     * @param file the file to check.
     * @param redistsPatterns the patterns-collection.
     * @return the file if it verifies a pattern, otherwise null.
     */
    public static Redist checkFile(File file, List<Redist> redistsPatterns) {
        Redist checkedFiles = null;
        CHECK:
        for (Redist redist : redistsPatterns) {
            Pattern pattern = redist.getCompiledPattern();
            String fileName = file.getName();
            if (pattern.matcher(fileName.toLowerCase(Main.SYS_LOCALE)).find()) {
                Redist redistFound = new Redist(file, redist.getDescription());
                checkedFiles = redistFound;
                break CHECK;
            }
        }
        return checkedFiles;
    }

    /**
     * Delete a folder on the hard-drive.
     *
     * @param folder the folder to delete.
     * @return true if deletion is successful, otherwise false.
     */
    public static boolean deleteFolder(File folder) {
        boolean deleted;
        try {
            if (folder.exists()) {
                org.apache.commons.io.FileUtils.deleteDirectory(folder);
            }
            deleted = true;
        } catch (IOException ex) {
            Log.error(ex);
            deleted = false;
        }
        return deleted;
    }
}
