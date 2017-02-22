package io.out;

import contest.Competition;
import contest.CompetitionEntry;
import contest.RankedCompetitionResult;
import contest.competitor.CompetitorImpl;
import contest.competitor.PerformanceImpl;
import contest.events.Event;
import contest.events.formulas.EventFormula;
import contest.winnerPlaces.WinnerByScorePlaceAssigner;
import contest.winnerPlaces.structures.Rank;
import contest.winnerPlaces.structures.RankRange;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-30
 */
public class RankedCompetitionResultWriterTests {

//    @Test
//    public void outputsToStream() throws SAXException, IOException {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        RankedCompetitionResultWriter writer = new RankedCompetitionResultWriter();
//        String evtTitle = "New folder";
//        String competitorName = "n23jnwe";
//        Rank fakeRank = new RankRange(1, 3);
//        RankedCompetitionResult result = new RankedCompetitionResult(
//                new CompetitionEntry(
//                        new CompetitorImpl(competitorName),
//                        Collections.singletonList(new PerformanceImpl(20, new Event() {
//                            @Override
//                            public String getTitle() {
//                                return evtTitle;
//                            }
//
//                            @Override
//                            public EventFormula getFormula() {
//                                throw new AssertionError("formula should not be written to file");
//                            }
//                        }))
//                ),
//                fakeRank);
//        Stream<RankedCompetitionResult> input = Stream.of(result).collect(Collectors.toList());
//        Competition competition = new Competition(input, new WinnerByScorePlaceAssigner());
//        writer.write(new OutputStreamWriter(output), competition);
//
//        assertTrue(output.toString().matches(evtTitle));
//        assertTrue(output.toString().matches(competitorName));
//    }
//
//    private RankedCompetitionResult produceResult() {
//
//    }
}
