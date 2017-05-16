package com.semanticRelationsExtractor.preprocessing;

import com.semanticRelationsExtractor.cache.ConstantTagsCache;
import com.semanticRelationsExtractor.data.InputData;
import com.semanticRelationsExtractor.tags.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public class CapitalizedTokensPreprocessorImpl implements CapitalizedTokensPreprocessor {

    @Override
    public void process(InputData inputData) {
        boolean containsSubSentences = inputData.containsSubSentences();
        if (containsSubSentences) {
            int numberOfSubSentences = inputData.getTagsMultiList().size();
            for (int i = 0; i < numberOfSubSentences; i++) {
                List<String> tokensList = inputData.getTokensMultiList().get(i);
                List<String> tagsList = inputData.getTagsMultiList().get(i);
                List<String> processedTokensList = new ArrayList<>();
                List<String> processedTagsList = new ArrayList<>();
                List<String> processedEncodedTagsList = new ArrayList<>();
                runCapitalizationLogic(tokensList, tagsList, processedTokensList, processedTagsList,
                        processedEncodedTagsList, inputData, containsSubSentences, i);
            }
        } else {
            List<String> tokensList = inputData.getTokensList();
            List<String> tagsList = inputData.getTagsList();

            List<String> processedTokensList = new ArrayList<>();
            List<String> processedTagsList = new ArrayList<>();
            List<String> processedEncodedTagsList = new ArrayList<>();
            runCapitalizationLogic(tokensList, tagsList, processedTokensList, processedTagsList,
                    processedEncodedTagsList, inputData, containsSubSentences, -1);
        }
    }

    private void runCapitalizationLogic(List<String> tokensList, List<String> tagsList, List<String> processedTokensList,
                                        List<String> processedTagsList, List<String> processedEncodedTagsList,
                                        InputData inputData, boolean containsSubSentence, int listIndex) {
        String mergedToken = "";

        outer:
        for (int i = 0; i < tokensList.size(); i++) {

            if (!"".equals(mergedToken)) {
                i = i - 1;
                processedTokensList.add(mergedToken);
                processedTagsList.add(Tags.NOUN);
                mergedToken = "";
            }
            if (!ConstantTagsCache.constantTagsCache.contains(tagsList.get(i)) && Character.isUpperCase(tokensList.get(i).charAt(0))) {
                while (Character.isUpperCase(tokensList.get(i).charAt(0))) {
                    if ("".equals(mergedToken)) {
                        mergedToken = tokensList.get(i);
                    } else {
                        mergedToken += " " + tokensList.get(i);
                    }
                    if (i == tokensList.size() - 1) {
                        processedTokensList.add(mergedToken);
                        processedTagsList.add(Tags.NOUN);
                        break outer;
                    }
                    i++;
                }
            } else {
                processedTokensList.add(tokensList.get(i));
                processedTagsList.add(tagsList.get(i));
            }
        }
        if (containsSubSentence) {
            inputData.getTokensMultiList().remove(listIndex);
            inputData.getTokensMultiList().add(processedTokensList);
            inputData.getTagsMultiList().remove(listIndex);
            inputData.getTagsMultiList().add(processedTagsList);
        } else {
            inputData.setTokensList(processedTokensList);
            inputData.setTagsList(processedTagsList);
        }
    }

}
