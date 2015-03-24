package fr.tikione.steam.cleaner.util;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * This class performs analysis on files.
 */
public class FileComparator {

	private final List<File> files;

	private final List<Redist> redistsPatterns;

	private final List<Redist> checkedFiles;

	private final boolean onFiles;

	public FileComparator(List<File> files, List<Redist> redistsPatterns, List<Redist> checkedFiles, boolean onFiles) {
		this.files = Collections.unmodifiableList(files);
		this.redistsPatterns = Collections.unmodifiableList(redistsPatterns);
		this.checkedFiles = checkedFiles;
		this.onFiles = onFiles;
	}

	public final void start()
			throws InterruptedException {
		int nbfiles = files.size();
		Log.info("debug: ThreadedFileComparator >> number of files or folders to check: " + nbfiles);
		if (nbfiles > 0) {
			int startIdx = 0;
			int endIdx = nbfiles - 1;
			Log.info("debug: ThreadedFileComparator >> startIdx=" + startIdx + ", endIdx=" + endIdx);
			for (int pos = startIdx; pos <= endIdx; pos++) {
				Redist candidate = FileUtils.checkFile(files.get(pos), redistsPatterns);
				if (candidate != null) {
					if (onFiles ? candidate.getFile().isFile() : candidate.getFile().isDirectory()) {
						if (!checkedFiles.contains(candidate)) {
							checkedFiles.add(candidate);
						}
					}
				}
			}
		}
	}
}
