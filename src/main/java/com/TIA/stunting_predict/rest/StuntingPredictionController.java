package com.TIA.stunting_predict.rest;


import com.TIA.stunting_predict.services.StuntingModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/predict")
public class StuntingPredictionController {

    @Autowired
    private StuntingModelService stuntingModelService;

    @PostMapping
    public ResponseEntity<Map<String, String>> predict(@RequestParam String gender,
                                                       @RequestParam int umur,
                                                       @RequestParam double tinggi,
                                                       @RequestParam double berat) {
        try {
            // Call the service to run the Python script
            String predictionResult = stuntingModelService.runPythonScript(gender, umur, tinggi, berat);

            // Prepare the response
            Map<String, String> response = new HashMap<>();
            response.put("Status Stunting", predictionResult);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle errors and return a meaningful message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
