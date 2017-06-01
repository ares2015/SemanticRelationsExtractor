package com.semanticRelationsExtractor.extraction.sequence;

import com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.semanticRelationsExtractor.extraction.predicate.verb.sequence.VerbPredicateSequenceExtractor;
import com.semanticRelationsExtractor.extraction.predicate.verb.sequence.VerbPredicateSequenceExtractorImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oliver on 5/29/2017.
 */
public class VerbPredicateSequenceExtractorTest {

    private VerbPredicateSequenceExtractor verbPredicateSequenceExtractor = new VerbPredicateSequenceExtractorImpl();

    @Test
    public void testModalSequence() {
        String sentence = "Ronaldo couldn't have run furiously towards goalie";
        List<String> tokensList = Arrays.asList(sentence.split("\\ "));
        String tags = "N MV_NOT H V AV PR N";
        List<String> tagsList = Arrays.asList(tags.split("\\ "));
        assertEquals("couldn't have run ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.modalVerbSequenceAtomicAllowedTags));
        assertEquals("couldn't have run furiously ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.modalVerbSequenceExtendedAllowedTags));
    }

    @Test
    public void testHaveBeenSequence() {
        String sentence = "Ronaldo has been successfully best player of world";
        List<String> tokensList = Arrays.asList(sentence.split("\\ "));
        String tags = "N H IA AV AJ N PR N";
        List<String> tagsList = Arrays.asList(tags.split("\\ "));
        assertEquals("has been ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.haveBeenSequenceAtomicAllowedTags));
        assertEquals("has been successfully ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.haveBeenSequenceExtendedAllowedTags));
    }

    @Test
    public void testHaveVerbEdSequence() {
        String sentence = "Ronaldo has scored successfully goal in final";
        List<String> tokensList = Arrays.asList(sentence.split("\\ "));
        String tags = "N H Ved AV N PR N";
        List<String> tagsList = Arrays.asList(tags.split("\\ "));
        assertEquals("has scored ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.haveVerbEdSequenceAtomicAllowedTags));
        assertEquals("has scored successfully ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.haveVerbEdSequenceExtendedAllowedTags));
    }

    @Test
    public void testDoVerbSequence() {
        String sentence = "Ronaldo didn't score quickly goal in final";
        List<String> tokensList = Arrays.asList(sentence.split("\\ "));
        String tags = "N DO_NOT V AV N PR N";
        List<String> tagsList = Arrays.asList(tags.split("\\ "));
        assertEquals("didn't score ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.doVerbSequenceAtomicAllowedTags));
        assertEquals("didn't score quickly ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.doVerbSequenceExtendedAllowedTags));
    }

    @Test
    public void testSimpleVerbSequence() {
        String sentence = "Ronaldo scored quickly goal in final";
        List<String> tokensList = Arrays.asList(sentence.split("\\ "));
        String tags = "N Ved AV N PR N";
        List<String> tagsList = Arrays.asList(tags.split("\\ "));
        assertEquals("scored quickly ", verbPredicateSequenceExtractor.extract(tokensList, tagsList, 1, SemanticExtractionFilterCache.simpleVerbSequenceExtendedAllowedTags));
    }


}
