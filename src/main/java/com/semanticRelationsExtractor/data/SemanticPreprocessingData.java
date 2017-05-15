package com.semanticRelationsExtractor.data;

import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public final class SemanticPreprocessingData {

    private boolean canGoToExtraction = false;

    private List<String> tokensList;

    private List<String> tagsList;

    private int verbIndex = -1;

    private int modalVerbIndex = -1;

    private boolean containsBeforeVerbPreposition = false;

    private int afterVerbFirstPrepositionIndex = -1;

    private boolean containsAfterVerbVerbIng = false;

    private boolean containsSubject = false;

    public boolean canGoToExtraction() {
        return canGoToExtraction;
    }

    public void setCanGoToExtraction(boolean canGoToExtraction) {
        this.canGoToExtraction = canGoToExtraction;
    }

    public List<String> getTokensList() {
        return tokensList;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public List<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList = tagsList;
    }

    public int getVerbIndex() {
        return verbIndex;
    }

    public void setVerbIndex(int verbIndex) {
        this.verbIndex = verbIndex;
    }

    public int getModalVerbIndex() {
        return modalVerbIndex;
    }

    public void setModalVerbIndex(int modalVerbIndex) {
        this.modalVerbIndex = modalVerbIndex;
    }

    public boolean containsBeforeVerbPreposition() {
        return containsBeforeVerbPreposition;
    }

    public void setContainsBeforeVerbPreposition(boolean containsBeforeVerbPreposition) {
        this.containsBeforeVerbPreposition = containsBeforeVerbPreposition;
    }

    public boolean containsAfterVerbVerbIng() {
        return containsAfterVerbVerbIng;
    }

    public void setContainsAfterVerbVerbIng(boolean containsAfterVerbVerbIng) {
        this.containsAfterVerbVerbIng = containsAfterVerbVerbIng;
    }

    public int getAfterVerbFirstPrepositionIndex() {
        return afterVerbFirstPrepositionIndex;
    }

    public void setAfterVerbFirstPrepositionIndex(int afterVerbFirstPrepositionIndex) {
        this.afterVerbFirstPrepositionIndex = afterVerbFirstPrepositionIndex;
    }

    public boolean isContainsSubject() {
        return containsSubject;
    }

    public void setContainsSubject(boolean containsSubject) {
        this.containsSubject = containsSubject;
    }

}