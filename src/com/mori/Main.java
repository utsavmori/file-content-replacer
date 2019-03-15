package com.mori;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        if (args.length == 3) {
            String path = convertToUnixLikePath(args[0]);
            if (new File(path).isDirectory()) {
                List<File> listOfFiles = getFilesInCurrentDirectory(path);
                for (File file : listOfFiles) {
                    replaceCharSeqInFile(file, args[1], args[2]);
                }
            } else {
                replaceCharSeqInFile(new File(args[0]), args[1], args[2]);
            }

        }
    }

    static String convertToUnixLikePath(String path) {
        return path.replace("\\", "/");
    }

    static List<File> getFilesInCurrentDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();
        ArrayList<File> filesList = new ArrayList<>();
        for (File f : files) {
            if (f.isFile()) {
                filesList.add(f);
            }
        }
        return filesList;
    }

    static void replaceCharSeqInFile(File file, String oldSequence, String newSequence) {
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            List<String> newLines = new LinkedList<>();
            String line = reader.readLine();
            while (line != null) {
                newLines.add(line.replaceAll(oldSequence, newSequence) + System.lineSeparator());
                line = reader.readLine();
            }
            writer = new FileWriter(file);
            writer.write("");
            writer.close();
            writer = new FileWriter(file, true);
            for (String lin : newLines) {
                writer.write(lin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
