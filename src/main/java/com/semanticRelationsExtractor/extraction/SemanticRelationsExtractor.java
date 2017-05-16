package com.semanticRelationsExtractor.extraction;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface SemanticRelationsExtractor {

    SemanticExtractionData extract(SemanticPreprocessingData semanticPreprocessingData);

}
