package com.semanticRelationsExtractor.preprocessing;

import com.semanticRelationsExtractor.cache.SemanticExtractionFilterCache;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.tags.Tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 2/17/2017.
 */
public class SemanticPreprocessorImpl implements SemanticPreprocessor {

    @Override
    public SemanticPreprocessingData preprocess(List<String> tokens, List<String> tags) {
        SemanticPreprocessingData semanticPreprocessingData = new SemanticPreprocessingData();
        int modalVerbIndex = -1;
        boolean containsBeforeVerbPreposition = false;
        int afterVerbFirstPrepositionIndex = -1;
        boolean containsAfterVerbVerbIng = false;
        boolean containsSubject = false;
        boolean containsNounAdjectivePredicate = false;
        boolean containsAdverbPredicate = false;

        FilteredSentence filteredSentence = filterSentence(tags, tokens);
        List<String> filteredTags = filteredSentence.getFilteredTags();
        List<String> filteredTokens = filteredSentence.getFilteredTokens();

        int mainVerbIndex = findMainVerbIndex(filteredTags, Tags.VERB);
        if (mainVerbIndex == -1) {
            mainVerbIndex = findMainVerbIndex(filteredTags, Tags.VERB_ED);
            if (mainVerbIndex == -1) {
                return semanticPreprocessingData;
            }
        }

        for (int i = 0; i < filteredTags.size(); i++) {
            String tag = filteredTags.get(i);

            if (Tags.MODAL_VERB.equals(tag)) {
                modalVerbIndex = i;
            }
            if ((Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && afterVerbFirstPrepositionIndex == -1 && i > mainVerbIndex) {
                afterVerbFirstPrepositionIndex = i;
            }
            if ((Tags.PREPOSITION.equals(tag) || Tags.TO.equals(tag)) && i < mainVerbIndex) {
                containsBeforeVerbPreposition = true;
            }
            if (Tags.VERB_ING.equals(tag) && mainVerbIndex < i) {
                containsAfterVerbVerbIng = true;
            }
            if ((Tags.NOUN.equals(tag) || Tags.VERB_ED.equals(tag)) && i < mainVerbIndex) {
                containsSubject = true;
            }
            if (mainVerbIndex > -1 && i > mainVerbIndex && ((Tags.NOUN.equals(tag) || Tags.ADJECTIVE.equals(tag)) || Tags.VERB_ED.equals(tag) ||
                    Tags.VERB_ING.equals(tag))) {
                containsNounAdjectivePredicate = true;
            }
            if (mainVerbIndex > -1 && i > mainVerbIndex && Tags.ADVERB.equals(tag)) {
                containsAdverbPredicate = true;
            }
        }
        if (mainVerbIndex == -1 || !containsSubject || (!containsNounAdjectivePredicate && containsAdverbPredicate) ||
                (Tags.IS_ARE.equals(tags.get(mainVerbIndex)) && !containsNounAdjectivePredicate)) {
            return semanticPreprocessingData;
        } else {
            semanticPreprocessingData.setTagsList(filteredTags);
            semanticPreprocessingData.setTokensList(filteredTokens);
            semanticPreprocessingData.setContainsBeforeVerbPreposition(containsBeforeVerbPreposition);
            semanticPreprocessingData.setVerbIndex(mainVerbIndex);
            semanticPreprocessingData.setModalVerbIndex(modalVerbIndex);
            semanticPreprocessingData.setAfterVerbFirstPrepositionIndex(afterVerbFirstPrepositionIndex);
            semanticPreprocessingData.setContainsAfterVerbVerbIng(containsAfterVerbVerbIng);
            semanticPreprocessingData.setCanGoToExtraction(true);
            return semanticPreprocessingData;
        }
    }

    private FilteredSentence filterSentence(List<String> tags, List<String> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        List<String> filteredTags = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            if (SemanticExtractionFilterCache.semanticExtractionAllowedTags.contains(tag)) {
                if (!Tags.DETERMINER.equals(tag) && !Tags.PRONOUN_POSSESIVE.equals(tag)) {
                    filteredTags.add(tag);
                    filteredTokens.add(tokens.get(i));
                }
            } else {
                break;
            }
        }
        return new FilteredSentence(filteredTags, filteredTokens);
    }

    private int findMainVerbIndex(List<String> tags, String verbTag) {
        boolean verbFound = false;
        int verbIndex = -1;
        for (int i = 0; i < tags.size(); i++) {
            String tag = tags.get(i);
            if ((verbTag.equals(tag) || Tags.IS_ARE.equals(tag)) && !verbFound) {
                verbIndex = i;
            } else if ((verbTag.equals(tag) || Tags.IS_ARE.equals(tag)) && verbFound) {
                verbIndex = -1;
            }
        }
        return verbIndex;
    }

    private class FilteredSentence {

        List<String> filteredTags;

        List<String> filteredTokens;

        public FilteredSentence(List<String> filteredTags, List<String> filteredTokens) {
            this.filteredTags = filteredTags;
            this.filteredTokens = filteredTokens;
        }

        public List<String> getFilteredTokens() {
            return filteredTokens;
        }

        public List<String> getFilteredTags() {
            return filteredTags;
        }
    }

}