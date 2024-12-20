package com.TIA.stunting_predict.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class StuntingModelService {

    public String runPythonScript(String gender, int umur, double tinggi, double berat) {
        try {
            // Command to run the Python script with arguments
            String[] command = {
                    "src/main/java/com/TIA/stunting_predict/stunting_app/venv/bin/python", // ganti dengan venv masing masing
                    "src/main/java/com/TIA/stunting_predict/stunting_app/app_cmd.py",
                    "--gender", gender,
                    "--umur", String.valueOf(umur),
                    "--tinggi", String.valueOf(tinggi),
                    "--berat", String.valueOf(berat)
            };

            // Start the process
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Combine standard output and error streams
            Process process = processBuilder.start();

            // Capture the output of the script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to complete and get the exit status
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python script exited with code: " + exitCode);
            }

            return output.toString().trim();

        } catch (Exception e) {
            throw new RuntimeException("Error running Python script", e);
        }
    }
}
