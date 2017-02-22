package io.out;

import contest.Competition;
import io.FileWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-30
 */
public class RankedCompetitionResultWriter implements FileWriter<Competition> {
    @Override
    public void write(OutputStreamWriter file, Competition input) {
        // Sorry. This code is not tested with JUnit, because of lack of time.
        // Extended class hierarchy is not provided as well
        try (BufferedWriter bufferedWriter = new BufferedWriter(file)) {
            write(bufferedWriter, input);
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    private void write(BufferedWriter file, Competition input) throws XMLStreamException {
        XMLOutputFactory out = XMLOutputFactory.newInstance();
        final XMLStreamWriter writer = out.createXMLStreamWriter(file);
        writer.writeStartDocument();
        writer.writeProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"style.xsl\"");
        writer.writeStartElement("competition");
        writer.writeStartElement("events");
        input.getRankedResults().limit(1).forEach(res -> {
            res.getCompetitionEntry().getPerformance().stream().forEach(perf -> {
                try {
                    writer.writeStartElement("event");
                    writer.writeCharacters(perf.getEvent().getTitle());
                    writer.writeEndElement();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            });
        });
        writer.writeEndElement();
        input.getRankedResults()
                .forEachOrdered(result -> {
                    try {
                        writer.writeStartElement("entry");

                        // Rank
                        writer.writeStartElement("rank");
                        writer.writeCharacters(result.getRank().getRankTitle());
                        writer.writeEndElement();

                        // Score
                        writer.writeStartElement("score");
                        writer.writeCharacters(String.valueOf(result.getScore()));
                        writer.writeEndElement();

                        // Name
                        writer.writeStartElement("name");
                        writer.writeCharacters(result.getCompetitionEntry().getCompetitor().getName());
                        writer.writeEndElement();

                        // Separate scores
                        writer.writeStartElement("performances");
                        result.getCompetitionEntry().getPerformance().stream().forEachOrdered(performance -> {
                            try {
                                String xmlTag = performance
                                        .getEvent()
                                        .getTitle()
                                        .replace(' ', '-')
                                        .toLowerCase()
                                        .replaceAll("^(\\d)", "_$1");
                                writer.writeStartElement(xmlTag);
                                writer.writeCharacters(String.valueOf(performance.getPoints()));
                                writer.writeEndElement();
                            } catch (XMLStreamException e) {
                                e.printStackTrace();
                            }
                        });
                        writer.writeEndElement();

                        writer.writeEndElement();
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                });
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
    }
}
