package com.jibanez.pdf_ai_parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class PdfParserExample {

    public static void main(String[] args) {

        try {
            File pdfDirectory = new File("C:\\Users\\usuario\\ftp\\uploads");
            File[] pdfFiles = pdfDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            File templatePdf = new File("C:\\Users\\usuario\\ftp\\templates\\CERTAINTEED.pdf");

            if (pdfFiles != null) {
                for (File pdfFile : pdfFiles) {
                    if (pdfFile.length() > 0) {
                        log.info("Processing file: {}", pdfFile.getName());

//                        PagePdfDocumentReader reader = new PagePdfDocumentReader(new FileSystemResource(pdfFile));
//                        String textContent = extractAndQueryGemini(reader);

                        //Test content for testing
                        String textContent = """
Estimate #--Total--Description
19358--$20,930.19--ROOFING SYSTEM - CERTAINTEED; Install new shingles. Architectural Style. (25 SQ); Brands: Certainteed; Install Hip and Ridge per LF (70 LF); CUSTOMIZED ITEMS; Tear off roofing material down to deck; *Additional charges if we find more than 2 layer.; Roofing System Pitch; 7/12 Predominant Pitch; Install Underlayment Felt (25 SQ); ADDITIONAL ITEMS; Install New ridge vent (70 LF); Debris Removal included; Install Ice & Water Shield for: Gutters, valleys and penetrations.; Remove and Replace Pipes Flashing (2); Install Drip Edge; Remove and Replace Shingles wall flashing below the siding/Step flashing; Valley Flashing per linear feet (60 LF); Discount; FINAL NOTATIONS; 1. Any and all roof systems, shingles, membranes, and underlayments require city and permitting approval. If any structural/excess work is required to mobilize or complete this contract; Change of Order Approval will be required.; 2. Existing Wall or Structural remediation associated with the roof is not included unless otherwise noted. We will notify you prior to any construction regarding changes or concerns to the estimated scope of work.; 3. Any and all existing wood structure, tiles, coping, or decking may require replacement depending on existing condition, which may result in additional expenses.; 4. Due to construction debris and environmental conditions, roof surfaces will attract dust and particles over time, foot-traffic, and construction.; 5. All projects associated with Premier Group are done as per city and manufacturer recommendation or approval.; 6. Lightning protection systems are not included in our scope unless stated in the contract. The property owner is responsible for compliance and should consult a specialist for installation or inspection.
19357--$19,325.02--ROOFING SYSTEM - GAF; Install new shingles. Architectural Style. (25 SQ); Brand: GAF; Install Hip and Ridge per LF (70 LF); CUSTOMIZED ITEMS; Tear off roofing material down to deck; *Additional charges if we find more than 2 layer.; Roofing System Pitch; 7/12 Predominant Pitch; Install Underlayment Felt (25 SQ); ADDITIONAL ITEMS; Install New ridge vent (70 LF); Debris Removal included; Install Ice & Water Shield for: Gutters, valleys and penetrations.; Remove and Replace Pipes Flashing (2); Install Drip Edge; Remove and Replace Shingles wall flashing below the siding/Step flashing/Transitions; Valley Flashing per linear feet (60 LF); Discount; FINAL NOTATIONS; 1. Any and all roof systems, shingles, membranes, and underlayments require city and permitting approval. If any structural/excess work is required to mobilize or complete this contract; Change of Order Approval will be required.; 2. Existing Wall or Structural remediation associated with the roof is not included unless otherwise noted. We will notify you prior to any construction regarding changes or concerns to the estimated scope of work.; 3. Any and all existing wood structure, tiles, coping, or decking may require replacement depending on existing condition, which may result in additional expenses.; 4. Due to construction debris and environmental conditions, roof surfaces will attract dust and particles over time, foot-traffic, and construction.; 5. All projects associated with Premier Group are done as per city and manufacturer recommendation or approval.; 6. Lightning protection systems are not included in our scope unless stated in the contract. The property owner is responsible for compliance and should consult a specialist for installation or inspection.
19356--$17,987.25--ROOFING SYSTEM - IKO; CAMBRIDGE; Install new shingles. Architectural Style. (25 SQ); Brand: IKO Cambridge; Install Hip and Ridge per LF (70 LF); CUSTOMIZED ITEMS; Tear off roofing material down to deck; *Additional charges if we find more than 2 layer.; Roofing System Pitch; 7/12 Predominant Pitch; Install Underlayment Felt (25 SQ); ADDITIONAL ITEMS; Install New ridge vent (70 LF); Debris Removal included; Install Ice & Water Shield for: Gutters, valleys and penetrations.; Remove and Replace Pipes Flashing (2); Install Drip Edge; Remove and Replace Shingles wall flashing below the siding/Step flashing; Valley Flashing per linear feet (60 LF); Discount; FINAL NOTATIONS; 1. Any and all roof systems, shingles, membranes, and underlayments require city and permitting approval. If any structural/excess work is required to mobilize or complete this contract; Change of Order Approval will be required.; 2. Existing Wall or Structural remediation associated with the roof is not included unless otherwise noted. We will notify you prior to any construction regarding changes or concerns to the estimated scope of work.; 3. Any and all existing wood structure, tiles, coping, or decking may require replacement depending on existing condition, which may result in additional expenses.; 4. Due to construction debris and environmental conditions, roof surfaces will attract dust and particles over time, foot-traffic, and construction.; 5. All projects associated with Premier Group are done as per city and manufacturer recommendation or approval.; 6. Lightning protection systems are not included in our scope unless stated in the contract. The property owner is responsible for compliance and should consult a specialist for installation or inspection.
19434--$159.96--Aditional Protection; Testing; NDL (No Dollar Limit) Warranty for Roofing System from Manufacturer. (Minimum charge for flat roofs under 45 SQ)
19430--$2,239.74--Aditional Protection; Remove and Replace Gutters, Aluminum 5" (80 LF); Remove and Replace Downspouts 3" x 4" (101 LF); Testing; NDL (No Dollar Limit) Warranty for Roofing System from Manufacturer. (Minimum charge for flat roofs under 45 SQ); Polyisocyanurate Thermal Insulation
19432--$2,369.70--Aditional Protection; Remove and Replace Gutters, Aluminum 5" (80 LF); Remove and Replace Downspouts 3" x 4" (101 LF); Testing; NDL (No Dollar Limit) Warranty for Roofing System from Manufacturer. (Minimum charge for flat roofs under 45 SQ); Polyisocyanurate Thermal Insulation; Remove and replace 3/4" Plywood or sheathing
19433--$385.26--Aditional Protection; Polyisocyanurate Thermal Insulation; Remove and replace 3/4" Plywood or sheathing
                                """;

                        List<DataPDF> dataPDFList = DataPDF.parseFromText(textContent);

                        modifyPdf(templatePdf, dataPDFList);
                    } else {
                        log.warn("Skipping empty file: {}", pdfFile.getName());
                    }
                }
            } else {
                log.error("Directory not found or empty: C:\\Users\\usuario\\ftp\\uploads");
            }

        } catch (Exception e) {
            log.error("Error reading PDF file: {}", e.getMessage());
            throw new RuntimeException("Failed to read PDF file", e);
        }
    }

    private static String extractAndQueryGemini(PagePdfDocumentReader reader) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String documentText = reader.get().stream().map(Document::getText).reduce("", String::concat);

//        log.info("Extracted text from PDF: {}", documentText);
        var prompt = """
                Get the different values of "Estimate #", "Total", and "Description" in order,
                If found different values of "Description" join them with a semicolon,
                Each line should be "Estimate #"--"Total"--"Description",
                If don't find the value of "Total" or "Description", skip the line:
                %s
                """.formatted(documentText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":%s}]}]}",
                new ObjectMapper().writeValueAsString(prompt));
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-8b:generateContent?key=AIzaSyAcqRhJ4JRfdnVFD2q40xNG7s98YMfyza8",
                request,
                String.class
        );

        var jsonResponse = new ObjectMapper().readTree(response);
        var textContent = jsonResponse.at("/candidates/0/content/parts/0/text").asText();
        log.info("Response from Gemini: {}", response);
        log.info("Gemini Analysis: {}", textContent);

        return textContent;
    }

    private static void modifyPdf(File sourceFile, List<DataPDF> content) {
        try {
            String outputPath = sourceFile.getParent() + File.separator + "modified_" + sourceFile.getName();
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(sourceFile), new PdfWriter(outputPath));
            PdfPage page = pdfDoc.getFirstPage();
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

            Paragraph p = new Paragraph(content.getFirst().estimateNumber())
                    .setFontColor(new DeviceRgb(0.973f, 0.973f, 0.973f))
//                    .setFontColor(ColorConstants.CYAN)
                    .setFont(PdfFontFactory.createFont("src/main/resources/fonts/Montserrat-Regular.ttf"))
                    .setFontSize(12)
                    .setBold()
                    .setFixedPosition(270, 802, 400);

            document.add(p);
            document.close();
            pdfDoc.close();
            log.info("PDF modified successfully: {}", outputPath);
        } catch (Exception e) {
            log.error("Error modifying PDF: {}", e.getMessage());
            throw new RuntimeException("Failed to modify PDF", e);
        }
    }
}

record DataPDF(String estimateNumber, String total, String description) {

    public static List<DataPDF> parseFromText(String textContent) {
        return Arrays.stream(textContent.split("\n"))
                .skip(1)
                .map(line -> {
                    String[] parts = line.split("--");
                    if (parts.length == 3) {
                        return new DataPDF(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
