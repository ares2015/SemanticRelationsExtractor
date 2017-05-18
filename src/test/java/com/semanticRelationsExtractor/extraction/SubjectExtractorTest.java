package com.semanticRelationsExtractor.extraction;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.subject.SubjectExtractor;
import com.semanticRelationsExtractor.extraction.subject.SubjectExtractorImpl;
import com.semanticRelationsExtractor.tags.Tags;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oliver on 5/18/2017.
 */
public class SubjectExtractorTest {

    private SubjectExtractor subjectExtractor = new SubjectExtractorImpl();

    @Test
    public void test() {
        List<String> tags = new ArrayList<>();

        tags.add(Tags.ADJECTIVE);
        tags.add(Tags.NOUN);
        tags.add(Tags.VERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.NOUN);
        tags.add(Tags.NOUN);

        String sentence = "brave firemen fight furiously forest fire";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setVerbIndex(2);
        semanticPreprocessingData.setContainsBeforeVerbPreposition(false);
        subjectExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("brave firemen ", semanticExtractionData.getExtendedSubject());
        assertEquals("firemen", semanticExtractionData.getAtomicSubject());
    }

    @Test
    public void testWithPreposition() {
        List<String> tags = new ArrayList<>();

        tags.add(Tags.ADJECTIVE);
        tags.add(Tags.NOUN);
        tags.add(Tags.PREPOSITION);
        tags.add(Tags.NOUN);
        tags.add(Tags.VERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.NOUN);
        tags.add(Tags.NOUN);

        String sentence = "brave firemen in California fight furiously forest fire";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setVerbIndex(4);
        semanticPreprocessingData.setContainsBeforeVerbPreposition(true);
        subjectExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("brave firemen in California ", semanticExtractionData.getExtendedSubject());
        assertEquals("", semanticExtractionData.getAtomicSubject());
    }
}
