/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bldoop;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author dmnic
 */
public class MyFileChooser {

    private static final JFileChooser FC = new JFileChooser();

    public MyFileChooser() {

    }

    public File selectFolder(String title, File initialPath) {
        FC.setDialogTitle(title);
        FC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (initialPath != null) {
             FC.setCurrentDirectory(initialPath);
        }
        int result = FC.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION: {
                File selectedFolder = FC.getSelectedFile();
                return selectedFolder;
            }
            default:
                return null;
        }        
    }
    public File selectFolder (String title){
        return selectFolder (title, null);
    }
    
    public File selectFolderFromRoot (String title){
        File cDrive = new File("C:\\");
        FC.setCurrentDirectory(cDrive);
        FC.changeToParentDirectory();
        File root = FC.getSelectedFile();
        return selectFolder(title);
    }
}
