package com.semanticRelationsExtractor.main;

import com.postagger.main.PosTagger;
import com.postagger.main.PosTaggerImpl;
import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.database.DatabaseInserter;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataFactory;
import com.semanticRelationsExtractor.preprocessing.CapitalizedTokensPreprocessor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractor.reader.InputDataReader;

import java.util.List;


/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private InputDataReader inputDataReader;

    private InputDataFactory inputDataFactory;

    private CapitalizedTokensPreprocessor capitalizedTokensPreprocessor;

    private PosTagger posTagger;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseInserter databaseInserter;

    public SemanticExtractionProcessorImpl(InputDataReader inputDataReader, InputDataFactory inputDataFactory,
                                           CapitalizedTokensPreprocessor capitalizedTokensPreprocessor,
                                           SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                           DatabaseInserter databaseInserter) {
        this.inputDataReader = inputDataReader;
        this.inputDataFactory = inputDataFactory;
        this.capitalizedTokensPreprocessor = capitalizedTokensPreprocessor;
//        this.posTagger = new PosTaggerImpl();
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseInserter = databaseInserter;
    }

    public static void main(String[] args) throws InterruptedException {
        PosTagger posTagger = new PosTaggerImpl();
        posTagger.tag("the Serbian frontier in Macedonia was left practically unguarded");
        posTagger.tag("David Beckham was football superstar");

    }


    @Override
    public void process() throws InterruptedException {
        List<String> inputDataStringList = inputDataReader.read();
        for (String inputDataAsString : inputDataStringList) {
            List<List<String>> tags = posTagger.tag(inputDataAsString);
            InputData inputData = inputDataFactory.create(inputDataAsString, tags.get(0));
            capitalizedTokensPreprocessor.process(inputData);
            if (inputData.containsSubSentences()) {
                for (int i = 0; i <= inputData.getTokensMultiList().size() - 1; i++) {
                    List<String> tokensList = inputData.getTokensMultiList().get(i);
                    List<String> tagsList = inputData.getTagsMultiList().get(i);
                    processSentence(tokensList, tagsList);
                }
            } else {
                List<String> tagsList = inputData.getTagsList();
                List<String> tokensList = inputData.getTokensList();
                processSentence(tokensList, tagsList);
            }
        }
    }


    private void processSentence(List<String> tokensList, List<String> tagsList) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            SemanticExtractionData semanticExtractionData = semanticRelationsExtractor.extract(semanticPreprocessingData);
            databaseInserter.insertSemanticData(semanticExtractionData);
        }
    }
}
