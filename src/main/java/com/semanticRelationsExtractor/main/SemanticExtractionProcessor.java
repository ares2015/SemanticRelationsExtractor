package com.semanticRelationsExtractor.main;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Oliver on 5/15/2017.
 */
public interface SemanticExtractionProcessor {

    void process() throws InterruptedException, ExecutionException, FileNotFoundException;

}
