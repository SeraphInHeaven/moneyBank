package util;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-07-03 5:45 PM
 */
public class FileOperation {

    public static void deleteFile(String filePath) {
        File dir = new File(filePath);
        if (dir.exists()) {
            File[] tmp = dir.listFiles();
            assert tmp != null;
            for (File aTmp : tmp) {
                if (aTmp.isDirectory()) {
                    deleteFile(filePath + "/" + aTmp.getName());
                } else {
                    aTmp.delete();
                }
            }
            dir.delete();
        }
    }
}
