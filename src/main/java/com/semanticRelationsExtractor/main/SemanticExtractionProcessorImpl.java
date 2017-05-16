package com.semanticRelationsExtractor.main;

import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

//    private SemanticsWriter semanticsWriter;

    private List<InputData> inputDataList;

    private List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<>();

//    public SemanticExtractionProcessorImpl(SemanticPreprocessor semanticPreprocessor, SemanticRelationsExtractor semanticRelationsExtractor,
//                                SemanticsWriter semanticsWriter, List<TrainingDataRow> trainingDataRowList) {
//        this.semanticPreprocessor = semanticPreprocessor;
//        this.semanticRelationsExtractor = semanticRelationsExtractor;
//        this.semanticsWriter = semanticsWriter;
//        this.trainingDataRowList = trainingDataRowList;
//    }

    public static void main(String[] args) throws InterruptedException {
//        PosTagger posTagger = new PosTaggerImpl();
//        posTagger.tag("the Serbian frontier in Macedonia was left practically unguarded");
//        posTagger.tag("David Beckham was football superstar");

    }


    @Override
    public void process() {
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

    private void processSentence(List<String> tokensList, List<String> tagsList) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            SemanticExtractionData semanticExtractionData = semanticRelationsExtractor.extract(semanticPreprocessingData);
            semanticExtractionDataList.add(semanticExtractionData);
        }
    }
}
