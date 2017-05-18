package com.semanticRelationsExtractor.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oled on 5/16/2017.
 */
public class InputData {

    private boolean containsSubSentences;

    private List<String> tokensList = new ArrayList<String>();

    private List<String> tagsList = new ArrayList<>();

    private List<List<String>> tokensMultiList;

    private List<List<String>> tagsMultiList = new ArrayList<>();

    public boolean containsSubSentences() {
        return containsSubSentences;
    }

    public void setContainsSubSentences(boolean containsSubSentences) {
        this.containsSubSentences = containsSubSentences;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public List<String> getTokensList() {
        return tokensList;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public List<List<String>> getTokensMultiList() {
        return tokensMultiList;
    }

    public void setTokensMultiList(List<List<String>> tokensMultiList) {
        this.tokensMultiList = tokensMultiList;
    }

    public List<List<String>> getTagsMultiList() {
        return tagsMultiList;
    }

}
