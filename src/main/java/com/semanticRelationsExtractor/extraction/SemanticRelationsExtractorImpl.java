package com.semanticRelationsExtractor.extraction;


import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.predicate.NounPredicateExtractor;
import com.semanticRelationsExtractor.extraction.predicate.VerbPredicateExtractor;
import com.semanticRelationsExtractor.extraction.subject.SubjectExtractor;

/**
 * Created by Oliver on 2/17/2017.
 */
public class SemanticRelationsExtractorImpl implements SemanticRelationsExtractor {

    private SubjectExtractor subjectExtractor;

    private VerbPredicateExtractor verbPredicateExtractor;

    private NounPredicateExtractor nounPredicateExtractor;

    public SemanticRelationsExtractorImpl(SubjectExtractor subjectExtractor, VerbPredicateExtractor verbPredicateExtractor,
                                          NounPredicateExtractor nounPredicateExtractor) {
        this.subjectExtractor = subjectExtractor;
        this.verbPredicateExtractor = verbPredicateExtractor;
        this.nounPredicateExtractor = nounPredicateExtractor;
    }

    @Override
    public SemanticExtractionData extract(SemanticPreprocessingData semanticPreprocessingData) {
        SemanticExtractionData semanticExtractionData = new SemanticExtractionData();
        subjectExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        verbPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        nounPredicateExtractor.extract(semanticExtractionData, semanticPreprocessingData);
        return semanticExtractionData;
    }

}
