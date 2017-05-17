package com.semanticRelationsExtractor.factories;

import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.tokens.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class InputDataListFactoryImpl implements InputDataListFactory {

    private final static Logger LOGGER = Logger.getLogger(InputDataListFactoryImpl.class.getName());

    private Tokenizer tokenizer;

    private MultiListFactory multiListFactory;

    public InputDataListFactoryImpl(Tokenizer tokenizer, MultiListFactory multiListFactory) {
        this.tokenizer = tokenizer;
        this.multiListFactory = multiListFactory;
    }

    @Override
    public List<InputData> create(List<String> inputDataAsStringList) {
        LOGGER.info("ENTERING create method of InputDataListFactoryImpl... ");
        LOGGER.info("*********************************************************************");

        List<InputData> inputDataList = new ArrayList<>();

        for (String inputDataAsString : inputDataAsStringList) {
            InputData inputData = new InputData();
            String[] sentenceAndTagsTwoItemsArray = inputDataAsString.split("#");

            String sentence = sentenceAndTagsTwoItemsArray[0];
            String subPath = sentenceAndTagsTwoItemsArray[1];

            LOGGER.info("Processing sentence < " + sentence + " > and tags < " +
                    subPath + " > ");

            List<String> tokensList = tokenizer.splitStringIntoList(sentence);
            List<String> tagsList = tokenizer.splitStringIntoList(subPath);


            if (sentenceAndTagsTwoItemsArray[0].contains(", ")) {
                inputData.setContainsSubSentences(true);
                //MULTILISTS ARE CREATED FIRST BEFORE COMMAS ARE REMOVED FROM TOKENS LIST AND TAGS LIST
                //SUB SENTENCES MULTILIST
                List<List<String>> subSentencesMultiList = multiListFactory.create(tokensList);
                inputData.setTokensMultiList(subSentencesMultiList);

                //TAGS MULTILIST
                List<List<String>> tagsMultiList = multiListFactory.create(tagsList);
                inputData.setTagsMultiList(tagsMultiList);

                LOGGER.info("Sentence contains " + subSentencesMultiList.size() + " subSentences.");

                //TOKENS LIST, TAGS LIST
                tokensList = removeCommasAndDots(tokensList);
                tagsList = removeCommasAndDots(tagsList);
                inputData.setTokensList(tokensList);
                inputData.setTagsList(tagsList);

                inputDataList.add(inputData);
            } else {
                inputData.setContainsSubSentences(false);
                LOGGER.info("Sentence does not contain any subSentences.");

                inputData.setTokensList(tokensList);
                inputData.setTagsList(tagsList);

                inputDataList.add(inputData);
            }
        }
        LOGGER.info("LEAVING create method of SubPathDataListFactoryImpl... ");
        LOGGER.info("*********************************************************************");

        return inputDataList;
    }

    private List<String> removeCommasAndDots(List<String> words) {
        List<String> newWords = new ArrayList<>();
        for (String word : words) {
            if (word.endsWith(",")) {
                String newWord = tokenizer.removeCommaAndDot(word);
                newWords.add(newWord);
            } else {
                newWords.add(word);
            }
        }
        return newWords;
    }
}