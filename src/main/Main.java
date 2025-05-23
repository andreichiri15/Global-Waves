package main;

import commands.CommandRunner;
import library.Library;
import library.filetypes.Song;
import library.User;
import library.filetypes.Podcast;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commands.InputCommands;
import fileio.input.LibraryInput;

import json.generator.Result;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < library.getUsers().size(); i++) {
            users.add(new User(library.getUsers().get(i)));
        }
        ArrayList<Song> songs = new ArrayList<>();
        for (int i = 0; i < library.getSongs().size(); i++) {
            songs.add(new Song(library.getSongs().get(i)));
        }
        ArrayList<Podcast> podcasts = new ArrayList<>();
        for (int i = 0; i < library.getPodcasts().size(); i++) {
            podcasts.add(new Podcast(library.getPodcasts().get(i)));
        }
        Library myLibrary = new Library(songs, podcasts, users);

        String inputPath = CheckerConstants.TESTS_PATH + filePathInput;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InputCommands[] inputCommands = objectMapper.readValue(new File(inputPath),
                InputCommands[].class);

        ArrayList<Result> results = CommandRunner.getInstance().
                runCommand(inputCommands, myLibrary);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), results);
    }
}
