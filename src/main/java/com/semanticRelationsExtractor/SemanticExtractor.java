package com.semanticRelationsExtractor;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;

/**
 * Created by Oliver on 2/17/2017.
 */
public interface SemanticExtractor {

    SemanticExtractionData extract(SemanticPreprocessingData semanticPreprocessingData);

}
