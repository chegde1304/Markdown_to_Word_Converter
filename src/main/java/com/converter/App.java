package com.converter;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        PandocService pandocService = new PandocService();

        Path workingDir = Paths.get(System.getProperty("user.dir"));
        Path inputDir = workingDir.resolve("src/main/resources/input");
        Path mediaDirectory = workingDir.resolve("media");

        try {
            // Ensure necessary directories exist
            Files.createDirectories(mediaDirectory);
            Files.createDirectories(inputDir); // Creates the input folder if it doesn't exist

            System.out.println("Scanning for files in: " + inputDir.toAbsolutePath());

            // Read all files in the input directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir)) {
                boolean foundFiles = false;

                for (Path file : stream) {
                    if (Files.isRegularFile(file)) {
                        foundFiles = true;
                        String fileName = file.getFileName().toString();
                        String lowerCaseName = fileName.toLowerCase();

                        // Extract the base name (e.g., "document" from "document.docx")
                        int dotIndex = fileName.lastIndexOf('.');
                        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

                        if (lowerCaseName.endsWith(".docx")) {
                            // --- WORD TO MARKDOWN ---
                            Path outputMarkdown = workingDir.resolve(baseName + ".md");
                            System.out.println("\n[Detected DOCX] Converting Word to Markdown: " + fileName);

                            pandocService.convertWordToMarkdown(file, outputMarkdown, mediaDirectory);

                            System.out.println("Success! Created: " + outputMarkdown.getFileName());

                        } else if (lowerCaseName.endsWith(".md")) {
                            // --- MARKDOWN TO WORD ---
                            Path outputWord = workingDir.resolve(baseName + ".docx");
                            System.out.println("\n[Detected MD] Converting Markdown to Word: " + fileName);

                            pandocService.convertMarkdownToWord(file, outputWord);

                            System.out.println("Success! Created: " + outputWord.getFileName());

                        } else {
                            System.out.println("\n[Skipping] Unsupported file type: " + fileName);
                        }
                    }
                }

                if (!foundFiles) {
                    System.out.println("\nNo files found in the input directory. Please add a .docx or .md file and try again.");
                }
            }

        } catch (Exception e) {
            System.err.println("\nConversion process encountered an error!");
            e.printStackTrace();
        }
    }
}