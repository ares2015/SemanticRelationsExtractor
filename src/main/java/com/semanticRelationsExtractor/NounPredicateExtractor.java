package com.semanticRelationsExtractor;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/16/2017.
 */
public interface NounPredicateExtractor {

    void extract(SemanticExtractionData semanticExtractionData, SemanticPreprocessingData semanticPreprocessingData);

}
