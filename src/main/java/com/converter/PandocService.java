package com.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PandocService {

    /**
     * Converts a Word document to GitHub Flavored Markdown (gfm).
     * Extracts embedded images into a specified media directory.
     */
    public void convertWordToMarkdown(Path docxPath, Path mdPath, Path mediaDir) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("pandoc");
        command.add(docxPath.toAbsolutePath().toString());
        // Use GitHub Flavored Markdown for better table support
        command.add("-t");
        command.add("gfm");
        // Extract embedded images to the specified folder
        command.add("--extract-media=" + mediaDir.toAbsolutePath().toString());
        command.add("-o");
        command.add(mdPath.toAbsolutePath().toString());

        executeCommand(command);
    }

    /**
     * Converts a Markdown file back to a Word document.
     */
    public void convertMarkdownToWord(Path mdPath, Path docxPath) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("pandoc");
        command.add(mdPath.toAbsolutePath().toString());
        // Read as standard markdown
        command.add("-f");
        command.add("markdown");
        // Output to docx
        command.add("-t");
        command.add("docx");
        command.add("-o");
        command.add(docxPath.toAbsolutePath().toString());

        executeCommand(command);
    }

    /**
     * Helper method to run the process and capture output/errors.
     */
    private void executeCommand(List<String> command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        // Merge standard error and standard out so we can log it easily
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Read the output from the process (prevents the process from hanging if the output buffer fills up)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Pandoc Output] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Pandoc process exited with error code: " + exitCode);
        }
    }
}