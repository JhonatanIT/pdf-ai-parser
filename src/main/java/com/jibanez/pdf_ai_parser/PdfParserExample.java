package com.jibanez.pdf_ai_parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Log4j2
public class PdfParserExample {

    public static void main(String[] args) {

        try {

            File pdfDirectory = new File("D:\\pdf");
            File[] pdfFiles = pdfDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (pdfFiles != null) {
                for (File pdfFile : pdfFiles) {
                    log.info("Processing file: {}", pdfFile.getName());
                    PagePdfDocumentReader reader = new PagePdfDocumentReader(new FileSystemResource(pdfFile));
                    extractAndQueryGemini(reader);

                }
            } else {
                log.error("Directory not found or empty: D:\\pdf");
            }

        } catch (Exception e) {
            log.error("Error reading PDF file: {}", e.getMessage());
            throw new RuntimeException("Failed to read PDF file", e);
        }
    }

    private static void extractAndQueryGemini(PagePdfDocumentReader reader) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String documentText = reader.get().stream().map(Document::getText).reduce("", String::concat);

//        log.info("Extracted text from PDF: {}", documentText);
        var prompt = """
                Get the different values of "Estimate #" and "Total" in order:
                %s
                """.formatted(documentText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":%s}]}]}",
                new ObjectMapper().writeValueAsString(prompt));
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);


        String response = restTemplate.postForObject(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b:generateContent?key=<API_KEY>",
                request,
                String.class
        );

        var jsonResponse = new ObjectMapper().readTree(response);
        var textContent = jsonResponse.at("/candidates/0/content/parts/0/text").asText();
        log.info("Response from Gemini: {}", response);
        log.info("Gemini Analysis: {}", textContent);
    }
}
