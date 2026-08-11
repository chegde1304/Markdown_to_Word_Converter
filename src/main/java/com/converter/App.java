package com.converter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        PandocService pandocService = new PandocService();

        Path workingDir = Paths.get(System.getProperty("user.dir"));
        Path inputWord = workingDir.resolve("src/main/resources/input/document.docx");
        Path outputMarkdown = workingDir.resolve("output.md");
        Path mediaDirectory = workingDir.resolve("media");
        Path outputWordBack = workingDir.resolve("restored-document.docx");

        try {
            // Ensure media directory exists
            Files.createDirectories(mediaDirectory);

            System.out.println("Starting conversion: DOCX -> Markdown...");
            pandocService.convertWordToMarkdown(inputWord, outputMarkdown, mediaDirectory);
            System.out.println("Success! Created: " + outputMarkdown.getFileName());
            System.out.println("Images (if any) saved to: " + mediaDirectory.toAbsolutePath());

            System.out.println("\nStarting conversion: Markdown -> DOCX...");
            pandocService.convertMarkdownToWord(outputMarkdown, outputWordBack);
            System.out.println("Success! Created: " + outputWordBack.getFileName());

        } catch (Exception e) {
            System.err.println("Conversion failed!");
            e.printStackTrace();
        }
    }
}