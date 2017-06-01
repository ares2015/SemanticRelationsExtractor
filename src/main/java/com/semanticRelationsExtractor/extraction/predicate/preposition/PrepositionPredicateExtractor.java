package com.semanticRelationsExtractor.extraction.predicate.preposition;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 6/1/2017.
 */
public interface PrepositionPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
