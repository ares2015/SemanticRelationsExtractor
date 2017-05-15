package com.semanticRelationsExtractor.preprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public class CapitalizedTokensProcessorImpl implements CapitalizedTokensProcessor {

    @Override
    public void process(TrainingDataRow trainingDataRow) {
        boolean containsSubSentences = trainingDataRow.containsSubSentences();
        if (containsSubSentences) {
            int numberOfSubSentences = trainingDataRow.getTagsMultiList().size();
            for (int i = 0; i < numberOfSubSentences; i++) {
                List<String> tokensList = trainingDataRow.getTokensMultiList().get(i);
                List<String> tagsList = trainingDataRow.getTagsMultiList().get(i);
                List<String> encodedTagsList = trainingDataRow.getEncodedTagsMultiList().get(i);

                List<String> processedTokensList = new ArrayList<>();
                List<String> processedTagsList = new ArrayList<>();
                List<String> processedEncodedTagsList = new ArrayList<>();
                runCapitalizationLogic(tokensList, tagsList, encodedTagsList, processedTokensList, processedTagsList,
                        processedEncodedTagsList, trainingDataRow, containsSubSentences, i);
            }
        } else {
            List<String> tokensList = trainingDataRow.getTokensList();
            List<String> tagsList = trainingDataRow.getTagsList();
            List<String> encodedTagsList = trainingDataRow.getEncodedTagsList();

            List<String> processedTokensList = new ArrayList<>();
            List<String> processedTagsList = new ArrayList<>();
            List<String> processedEncodedTagsList = new ArrayList<>();
            runCapitalizationLogic(tokensList, tagsList, encodedTagsList, processedTokensList, processedTagsList,
                    processedEncodedTagsList, trainingDataRow, containsSubSentences, -1);
        }

    }

    private void runCapitalizationLogic(List<String> tokensList, List<String> tagsList, List<String> encodedTagsList,
                                        List<String> processedTokensList, List<String> processedTagsList, List<String> processedEncodedTagsList,
                                        TrainingDataRow trainingDataRow, boolean containsSubSentence, int listIndex) {
        String mergedToken = "";

        outer:
        for (int i = 0; i < tokensList.size(); i++) {

            if (!"".equals(mergedToken)) {
                i = i - 1;
                processedTokensList.add(mergedToken);
                processedTagsList.add(Tags.NOUN);
                processedEncodedTagsList.add(EncodedTags.NOUN);
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
                        processedEncodedTagsList.add(EncodedTags.NOUN);
                        break outer;
                    }
                    i++;
                }
            } else {
                processedTokensList.add(tokensList.get(i));
                processedTagsList.add(tagsList.get(i));
                processedEncodedTagsList.add(encodedTagsList.get(i));
            }
        }
        if (containsSubSentence) {
            trainingDataRow.getTokensMultiList().remove(listIndex);
            trainingDataRow.getTokensMultiList().add(processedTokensList);
            trainingDataRow.getTagsMultiList().remove(listIndex);
            trainingDataRow.getTagsMultiList().add(processedTagsList);
            trainingDataRow.getEncodedTagsMultiList().remove(listIndex);
            trainingDataRow.getEncodedTagsMultiList().add(processedEncodedTagsList);
        } else {
            trainingDataRow.setTokensList(processedTokensList);
            trainingDataRow.setTagsList(processedTagsList);
            trainingDataRow.setEncodedTagsList(processedEncodedTagsList);
        }
    }

}
