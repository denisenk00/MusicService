package com.denysenko.musicservice;

import com.denysenko.musicservice.forms.Response;
import com.denysenko.musicservice.parsers.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class FileWriter {
    private Response response;
    private static Logger logger = LogManager.getLogger(JsonParser.class);

    public FileWriter(Response response) {
        this.response = response;
    }

    public byte[] infoToByteArr() {
        logger.debug("Method \"infoToByteArr\" was called");
        XWPFDocument document = new XWPFDocument();
        List<XWPFParagraph> listOfParagraph = new LinkedList<>();
        int amountOfParagraphs = response.getTracks().size() + 5;
        for (int i = 0; i < amountOfParagraphs; i++) {
            listOfParagraph.add(document.createParagraph());
        }
        listOfParagraph.get(0).createRun().setText("Album title: " + response.getTitle());
        listOfParagraph.get(1).createRun().setText("Poster: " + response.getPoster());
        listOfParagraph.get(2).createRun().setText("Singer: " + response.getSinger());
        listOfParagraph.get(3).createRun().setText("Tracks: ");
        int i = 4;
        for (Map.Entry<String, String> entry : response.getTracks().entrySet()) {
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().setText("Name: " + entry.getKey());
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().setText("Duration: " + entry.getValue());
            i++;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            document.write(out);
            logger.debug("Information about album was successfully recorded to a file");
            return out.toByteArray();
        } catch (IOException e) {
            logger.error("Information about album was not recorded to a file");
            return null;
        }
    }
}
