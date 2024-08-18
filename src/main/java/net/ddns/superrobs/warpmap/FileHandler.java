package net.ddns.superrobs.warpmap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ddns.superrobs.logging.Logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class FileHandler {

    private static final String FILEPATH = "./plugins/SuperWarp";
    private static final String FILENAME = "WarpMap.json";

    private static final String BACKUPPATH = "./plugins/SuperWarp/Backups";

    public static void backup(){
        if(!new File(FILEPATH + FILENAME).exists()) {
            return;
        }

        int i = 0;
        while(new File(BACKUPPATH + FILENAME + i).exists()) {
            i++;
        }
        try{
            Path newBackupPath = Path.of(BACKUPPATH + FILENAME + i);
            Files.createFile(newBackupPath);
            Files.copy(Path.of(FILEPATH + FILENAME), newBackupPath);
        } catch(IOException e){
            Logging.log(Level.SEVERE, "Backup failed due to IOException");
            e.printStackTrace();
        }
    }

    public static void save() {
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(new File(FILEPATH + FILENAME), CategoryManager.getCategories());
        } catch (IOException e) {
            //try to create the folder and file, then retry
            try{
                Files.createDirectories(Paths.get(FILEPATH));
                Files.createFile(Path.of(FILEPATH+FILENAME));
                mapper.writeValue(new File(FILEPATH + FILENAME), CategoryManager.getCategories());
            } catch (IOException ex) {
                String json;
                try{
                    json = mapper.writeValueAsString(CategoryManager.getCategories());
                } catch (JsonProcessingException exc) {
                    json = "Error when parsing JSON: " + exc.getMessage();
                }
                Logging.log(Level.SEVERE,
                        "WarpMap could not be saved, dumping for recovery:\n" + json
                                + "\n\n\n" + ex.getMessage());
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }

        Logging.log(Level.INFO, "saved warpMap");
    }

    static List<Category> load() {
        Logging.log(Level.INFO, "loading warpMap");
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<Category> result = mapper.readValue(new File(FILEPATH+FILENAME),
                    new TypeReference<>() {});
            Logging.log(Level.INFO, "successfully loaded WarpMap");
            return result;
        } catch(IOException e){
            Logging.log(Level.SEVERE, "Error when parsing JSON: " + e.getMessage());
            Logging.log(Level.WARNING, "no WarpMap found, generating a new one");
            return new ArrayList<>();
        }
    }
}
