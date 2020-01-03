package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        Path pathToDirectory = Paths.get("src\\main\\resources");
        pathToDirectory = pathToDirectory.resolve(path);
        File rootDirectory = new File(pathToDirectory.toUri());
        File[] filesFound = rootDirectory.listFiles();
        long countFiles = 0;
        if (filesFound == null) {
            return countFiles;
        }

        for (File file : filesFound) {
            if (file.isDirectory()) {
                countFiles += countFilesInDirectory(file.toString());
            } else {
                countFiles++;
            }
        }

        return countFiles;
    }


    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        Path pathToDirectory = Paths.get("src\\main\\resources");
        pathToDirectory = pathToDirectory.resolve(path);
        File rootDirectory = new File(pathToDirectory.toUri());
        File[] dirsFound = rootDirectory.listFiles();
        long countDirs = 1;

        if (dirsFound == null) {
            return countDirs;
        }

        for (File dir : dirsFound) {
            if (dir.isDirectory()) {
                countDirs += countDirsInDirectory(dir.toString());
            }
        }

        return countDirs;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        Path fromPath = Paths.get(from);
        Path toPath = Paths.get(to);

        File[] filesToCopy = new File(from).listFiles(pathname -> pathname.getName().endsWith(".txt"));

        if (filesToCopy != null) {
            for (File file : filesToCopy) {
                try {
                    if (!Files.exists(toPath)) {
                        Files.createDirectories(toPath);
                    }
                    Files.copy(fromPath.resolve(Paths.get(file.toString())), toPath.resolve(Paths.get(file.getName())), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        Path pathToFile = Paths.get("target\\classes").resolve(path);
        name = name.endsWith(".txt") ? name : name + ".txt";
        try {
            if (!Files.exists(pathToFile)) {
                Files.createDirectories(pathToFile);
            }
            return new File(pathToFile.resolve(name).toUri()).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder contentFromFile = new StringBuilder();
        try (FileReader fileReader = new FileReader("src\\main\\resources\\" + fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.ready()) {
                contentFromFile.append(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentFromFile.toString();
    }
}
