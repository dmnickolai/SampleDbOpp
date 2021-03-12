/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bldoop;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import photoUtils.*;

/**
 *
 * @author dmnic
 */
public class BldDB
        implements TreeWalkerActionListener {

    Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private PhotoDbHelper dbHelper;
    private MediaFileFilter myFilter;
    private int processedFileCount = 0;

    public int getProcessedFileCount() {
        return processedFileCount;
    }

    BldDB() {
        MyFileChooser MC = new MyFileChooser();
        dbHelper = new PhotoDbHelper(true);
        File myFile = MC.selectFolderFromRoot("Select Folder to Examine");
        //File myFile = new File("F:\\Test Photos");
        PhotoOnlyFinder pof = new PhotoOnlyFinder(myFile, this);
        System.out.println("Folder exammined " + pof.getNumFolders());
        System.out.println("Files examined " + pof.getNumTotal());
        System.out.println("Files selected " + processedFileCount);
    }

    @Override
    public void selectedFileListener(File thisFile) {
        processedFileCount += 1;
        //Get time stamp from file
        String dateTaken = "";
        try {
            TimestampExtractor tse = new TimestampExtractor();
            dateTaken = tse.getExifDate(thisFile);

            if (dateTaken.isEmpty()) {
                return;
            }

            //System.out.println(dateTaken);
            // Is this timestamp in the database
            ResultSet rs = dbHelper.getRowByTimeStamp(dateTaken);
            if (rs.next()) {
                // this file is not an orphan
               //System.out.println(dateTaken + " file is in DB");
            }
            else {
                System.out.println(thisFile.getAbsolutePath() + " file is NOT in DB");
            }
            // if duplicate, enter in duplicates
     
        } catch (SQLException ex) {
            System.out.println("SQL Ex =" + "(" + ex.getErrorCode() + ") " + ex.getMessage() + " on " + thisFile.getName() );
           
        } catch (Exception e) {
            System.out.println(e.getMessage() + " on " + thisFile.getName());
        }

        //Select record from database
        // if record count >0, this file is in the database
        // else copy to a file to process later
    }

    void displayResult(File f) {
        if (f != null) {
            System.out.println("Returned " + f.getAbsolutePath());
        } else {
            System.out.println("Returned NULL");
        }
    }

}
