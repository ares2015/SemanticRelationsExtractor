package com.semanticRelationsExtractor.database;

import com.semanticRelationsExtractor.data.SemanticExtractionData;

/**
 * Created by Oliver on 5/17/2017.
 */
public interface DatabaseInserter {

    void insertSemanticData(SemanticExtractionData semanticExtractionData);

}
