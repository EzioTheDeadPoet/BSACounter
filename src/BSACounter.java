import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BSACounter extends JFrame{
    private JPanel MainPanel;
    private JLabel textOutput;

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
        } catch (Exception e){
            e.printStackTrace();
        }
        BSACounter BSACounter = new BSACounter();
        int BSACount = countFiles(searchFiles(args[0]));
        BSACounter.textOutput.setText(BSACount+" BSA/BA2 Files have been found.");
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
