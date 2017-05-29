package com.semanticRelationsExtractor.extraction.predicate.verb;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface VerbPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
