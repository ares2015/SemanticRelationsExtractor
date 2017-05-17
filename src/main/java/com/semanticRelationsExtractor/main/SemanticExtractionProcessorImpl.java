package com.semanticRelationsExtractor.main;

import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.database.DatabaseInserter;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.factories.InputDataListFactory;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import com.semanticRelationsExtractor.reader.InputDataReader;

import java.util.List;
import java.util.Optional;

/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private InputDataReader inputDataReader;

    private InputDataListFactory inputDataListFactory;

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    private DatabaseInserter databaseInserter;

    public SemanticExtractionProcessorImpl(InputDataReader inputDataReader, InputDataListFactory inputDataListFactory,
                                           SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
                                           DatabaseInserter databaseInserter) {
        this.inputDataReader = inputDataReader;
        this.inputDataListFactory = inputDataListFactory;
        this.semanticPreprocessor = semanticPreprocessor;
        this.semanticRelationsExtractor = semanticRelationsExtractor;
        this.databaseInserter = databaseInserter;
    }

    public static void main(String[] args) throws InterruptedException {
//        PosTagger posTagger = new PosTaggerImpl();
//        posTagger.tag("the Serbian frontier in Macedonia was left practically unguarded");
//        posTagger.tag("David Beckham was football superstar");

    }


    @Override
    public void process() {
        List<String> inputDataStringList = inputDataReader.read();
        List<InputData> inputDataList = inputDataListFactory.create(inputDataStringList);
        for (InputData inputData : inputDataList) {
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
//        semanticsWriter.write(semanticExtractionDataList);
    }


    private Optional<SemanticExtractionData> processSentence(List<String> tokensList, List<String> tagsList) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            return Optional.of(semanticRelationsExtractor.extract(semanticPreprocessingData));
        }
        return Optional.empty();
    }
}
