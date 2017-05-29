package com.semanticRelationsExtractor.extraction;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.predicate.noun.NounPredicateExtractor;
import com.semanticRelationsExtractor.extraction.predicate.noun.NounPredicateExtractorImpl;
import com.semanticRelationsExtractor.tags.Tags;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oliver on 5/18/2017.
 */
public class NounPredicateExtractorTest {

    private NounPredicateExtractor nounPredicateExtractor = new NounPredicateExtractorImpl();

    @Test
    public void test() {

        List<String> tags = new ArrayList<>();

        tags.add(Tags.ADJECTIVE);
        tags.add(Tags.NOUN);
        tags.add(Tags.VERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.ADJECTIVE);
        tags.add(Tags.NOUN);
        tags.add(Tags.NOUN);

        String sentence = "brave firemen fight furiously dangerous forest fire";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setVerbIndex(2);
        semanticPreprocessingData.setAfterVerbFirstPrepositionIndex(-1);
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        nounPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("furiously dangerous forest fire ", semanticExtractionData.getExtendedNounPredicate());
        assertEquals("fire", semanticExtractionData.getAtomicNounPredicate());
    }

    @Test
    public void testWithAfterVerbPreposition() {

        List<String> tags = new ArrayList<>();

        tags.add(Tags.ADJECTIVE);
        tags.add(Tags.NOUN);
        tags.add(Tags.VERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.NOUN);
        tags.add(Tags.NOUN);
        tags.add(Tags.PREPOSITION);
        tags.add(Tags.NOUN);

        String sentence = "brave firemen fight furiously forest fire in California";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setVerbIndex(2);
        semanticPreprocessingData.setAfterVerbFirstPrepositionIndex(6);


        nounPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("furiously forest fire in California ", semanticExtractionData.getExtendedNounPredicate());
        assertEquals("fire", semanticExtractionData.getAtomicNounPredicate());
    }
}
