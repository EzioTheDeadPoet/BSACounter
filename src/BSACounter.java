import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BSACounter extends JFrame{
    private JPanel MainPanel;
    private JLabel textOutput;
    private static String MODLIST_TXT;
    private static String MODS_FOLDER;

    private BSACounter(){
        setLocationRelativeTo(null);
        setTitle("BSA/BA2 Counter");
        setSize(280,60);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(MainPanel);
        textOutput.setText("Searching...");
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BSACounter BSACounter = new BSACounter();
        MODS_FOLDER = args[0];
        if (args.length < 2) {
            int BSACount = countFiles(searchFiles(MODS_FOLDER));
            BSACounter.textOutput.setText(BSACount + " BSA/BA2 Files have been found.");
        } else {
            MODLIST_TXT = args[1];
            int BSACount = getTotalActiveCount();
            BSACounter.textOutput.setText(BSACount + " BSA/BA2 Files have been found.");
        }
    }

    private static int getTotalActiveCount(){
        int totalFiles = 0;
        for (String mod :findActiveMods()){
            totalFiles+=countFiles(searchFiles(MODS_FOLDER+"/"+mod));
        }
        return totalFiles;
    }

    private static List<String> findActiveMods() {
        List<String> activeMods = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(MODLIST_TXT));
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("+")) {
                    activeMods.add(line.substring(1));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return activeMods;
    }

    private static List<File> searchFiles(String path){
        File workingDir = new File(path);
        File[] workingFileList = workingDir.listFiles();
        List<File> fileList = new ArrayList<>();
        if (workingFileList != null) {  // In case of access error, list is null
            for (File f : workingFileList) {
                if (f.isDirectory()) {
                    fileList.addAll(searchFiles(f.getAbsolutePath()));
                } else {
                    if (f.getAbsolutePath().endsWith(".bsa")||f.getAbsolutePath().endsWith(".ba2")) {
                        fileList.add(f.getAbsoluteFile());
                    }
                }
            }
        }
        return fileList;
    }

    private static int countFiles(List<File> fileList) {
        int count = 0;
        if (fileList != null) {  // In case of access error, list is null
            for (File f : fileList) {
                if (!f.isDirectory()){
                    count++;
                }
            }
        }
        return count;
    }


}
