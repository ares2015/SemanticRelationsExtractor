package com.semanticRelationsExtractor.extraction;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.predicate.verb.VerbPredicateExtractor;
import com.semanticRelationsExtractor.extraction.predicate.verb.VerbPredicateExtractorImpl;
import com.semanticRelationsExtractor.extraction.predicate.verb.sequence.VerbPredicateSequenceExtractor;
import com.semanticRelationsExtractor.extraction.predicate.verb.sequence.VerbPredicateSequenceExtractorImpl;
import com.semanticRelationsExtractor.tags.Tags;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oliver on 5/18/2017.
 */
public class VerbPredicateExtractorTest {

    private VerbPredicateSequenceExtractor verbPredicateSequenceExtractor = new VerbPredicateSequenceExtractorImpl();


    private VerbPredicateExtractor verbPredicateExtractor = new VerbPredicateExtractorImpl(verbPredicateSequenceExtractor);

    @Test
    public void test() {
        List<String> tags = new ArrayList<>();

        tags.add(Tags.NOUN);
        tags.add(Tags.MODAL_VERB);
        tags.add(Tags.VERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.ADVERB);
        tags.add(Tags.PREPOSITION);
        tags.add(Tags.DETERMINER);
        tags.add(Tags.NOUN);
        tags.add(Tags.PREPOSITION);
        tags.add(Tags.NOUN);

        String sentence = "cheetahs can run very quickly on the savannahs of Africa";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setVerbIndex(2);
        semanticPreprocessingData.setModalVerbIndex(1);
        verbPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("can run very quickly ", semanticExtractionData.getExtendedVerbPredicate());
        assertEquals("can run ", semanticExtractionData.getAtomicVerbPredicate());
    }

    @Test
    public void testHaveBeenSequence() {
        List<String> tags = new ArrayList<>();

        tags.add(Tags.NOUN);
        tags.add(Tags.HAVE);
        tags.add(Tags.IS_ARE);
        tags.add(Tags.VERB_ED);
        tags.add(Tags.ADVERB);
        tags.add(Tags.ADVERB);

        String sentence = "Jagr has been injured very badly";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setHaveBeenSequenceStartIndex(1);
        semanticPreprocessingData.setVerbIndex(1);
        verbPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("has been ", semanticExtractionData.getExtendedVerbPredicate());
        assertEquals("has been ", semanticExtractionData.getAtomicVerbPredicate());
    }

    @Test
    public void testHaveVerbEd() {
        List<String> tags = new ArrayList<>();

        tags.add(Tags.NOUN);
        tags.add(Tags.HAVE);
        tags.add(Tags.VERB_ED);
        tags.add(Tags.ADVERB);
        tags.add(Tags.ADVERB);

        String sentence = "Jagr has played very badly";
        List<String> tokens = Arrays.asList(sentence.split("\\ "));
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        semanticPreprocessingData.setTokensList(tokens);
        semanticPreprocessingData.setTagsList(tags);
        semanticPreprocessingData.setHaveVerbEdSequenceStartIndex(1);
        semanticPreprocessingData.setHaveVerbEdSequenceEndIndex(2);
        semanticPreprocessingData.setVerbIndex(1);
        verbPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        assertEquals("has played very badly ", semanticExtractionData.getExtendedVerbPredicate());
        assertEquals("has played ", semanticExtractionData.getAtomicVerbPredicate());
    }
}
