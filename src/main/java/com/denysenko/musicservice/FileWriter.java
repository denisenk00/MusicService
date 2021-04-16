package com.denysenko.musicservice;

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

@Component
public class FileWriter {
    private static Logger logger = LogManager.getLogger(JsonParser.class);

    public byte[] writeDocument(Album album) {
        logger.debug("Method \"infoToByteArr\" was called");
        XWPFDocument document = new XWPFDocument();
        List<XWPFParagraph> listOfParagraph = new LinkedList<>();
        int amountOfParagraphs = album.getTracks().size() + 5;
        for (int i = 0; i < amountOfParagraphs; i++) {
            listOfParagraph.add(document.createParagraph());
        }
        listOfParagraph.get(0).createRun().setText("Album title: " + album.getTitle());
        listOfParagraph.get(1).createRun().setText("Poster: " + album.getPoster());
        listOfParagraph.get(2).createRun().setText("Singer: " + album.getSinger());
        listOfParagraph.get(3).createRun().setText("Tracks: ");
        int i = 4;
        for (Track track : album.getTracks()){
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().setText("Name: " + track.getTitle());
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().addTab();
            listOfParagraph.get(i).createRun().setText("Duration: " + track.getDuration());
            i++;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            document.write(out);
            logger.debug("Information about album was successfully recorded to a file");
            return out.toByteArray();
        } catch (IOException e) {
            logger.error("Information about album was not recorded to a file", e);
            return null;
        }
    }
}
