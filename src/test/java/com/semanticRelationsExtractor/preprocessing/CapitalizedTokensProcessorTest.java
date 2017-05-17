package com.semanticRelationsExtractor.preprocessing;

import com.semanticRelationsExtractor.data.InputData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oliver on 5/17/2017.
 */
public class CapitalizedTokensProcessorTest {

    private CapitalizedTokensPreprocessor capitalizedTokensProcessor = new CapitalizedTokensPreprocessorImpl();

    @Test
    public void test() {
        List<String> tokensList = new ArrayList<>();
        tokensList.add("New");
        tokensList.add("York");
        tokensList.add("City");
        tokensList.add("Police");
        tokensList.add("Department");
        tokensList.add("is");
        tokensList.add("the");
        tokensList.add("best");
        tokensList.add("one");
        tokensList.add("in");
        tokensList.add("the");
        tokensList.add("United");
        tokensList.add("States");
        tokensList.add("of");
        tokensList.add("America");

        List<String> tagsList = new ArrayList<>();
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("IA");
        tagsList.add("DET");
        tagsList.add("AJ");
        tagsList.add("NR");
        tagsList.add("PR");
        tagsList.add("DET");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("N");

        List<String> encodedTagsList = new ArrayList<>();
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("I");
        encodedTagsList.add("D");
        encodedTagsList.add("J");
        encodedTagsList.add("#");
        encodedTagsList.add("P");
        encodedTagsList.add("D");
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("P");
        encodedTagsList.add("N");

        InputData inputData = new InputData();
        inputData.setTokensList(tokensList);
        inputData.setTagsList(tagsList);


        capitalizedTokensProcessor.process(inputData);

        assertEquals(10, inputData.getTokensList().size());
        assertEquals(10, inputData.getTagsList().size());
        assertEquals("New York City Police Department", inputData.getTokensList().get(0));
        assertEquals("United States", inputData.getTokensList().get(7));
    }

    @Test
    public void test2() {
        List<String> tokensList = new ArrayList<>();
        tokensList.add("George");
        tokensList.add("Bush");
        tokensList.add("met");
        tokensList.add("Vladimir");
        tokensList.add("Putin");
        tokensList.add("in");
        tokensList.add("Bratislava");
        tokensList.add("in");
        tokensList.add("2005");

        List<String> tagsList = new ArrayList<>();
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("V");
        tagsList.add("N");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("N");
        tagsList.add("PR");
        tagsList.add("NR");

        List<String> encodedTagsList = new ArrayList<>();
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("V");
        encodedTagsList.add("N");
        encodedTagsList.add("N");
        encodedTagsList.add("P");
        encodedTagsList.add("N");
        encodedTagsList.add("P");
        encodedTagsList.add("#");

        InputData inputData = new InputData();
        inputData.setTokensList(tokensList);
        inputData.setTagsList(tagsList);

        capitalizedTokensProcessor.process(inputData);

        assertEquals(7, inputData.getTokensList().size());
        assertEquals(7, inputData.getTagsList().size());
        assertEquals("George Bush", inputData.getTokensList().get(0));
        assertEquals("Vladimir Putin", inputData.getTokensList().get(2));
        assertEquals("Bratislava", inputData.getTokensList().get(4));
    }
}
