package com.semanticRelationsExtractor.cache;

import com.semanticRelationsExtractor.tags.Tags;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oliver on 11/2/2016.
 */
public final class SemanticExtractionFilterCache {

    public static final Set<String> semanticExtractionAllowedTags = new HashSet<>();

    public static final Set<String> extendedHaveBeenVerbPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> extendedVerbPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> subjectNounPredicateExtractionAllowedTags = new HashSet<>();

    public static final Set<String> wordsToFilterCache = new HashSet<>();


    static {
        semanticExtractionAllowedTags.add(Tags.ADJECTIVE);
        semanticExtractionAllowedTags.add(Tags.ADVERB);
        semanticExtractionAllowedTags.add(Tags.DETERMINER);
        semanticExtractionAllowedTags.add(Tags.CONJUNCTION);
        semanticExtractionAllowedTags.add(Tags.IS_ARE);
        semanticExtractionAllowedTags.add(Tags.HAVE);
        semanticExtractionAllowedTags.add(Tags.MODAL_VERB);
        semanticExtractionAllowedTags.add(Tags.NOUN);
        semanticExtractionAllowedTags.add(Tags.NUMBER);
        semanticExtractionAllowedTags.add(Tags.PREPOSITION);
        semanticExtractionAllowedTags.add(Tags.PRONOUN_POSSESIVE);
        semanticExtractionAllowedTags.add(Tags.QUANTIFIER);
        semanticExtractionAllowedTags.add(Tags.TO);
        semanticExtractionAllowedTags.add(Tags.VERB);
        semanticExtractionAllowedTags.add(Tags.VERB_ING);
        semanticExtractionAllowedTags.add(Tags.VERB_ED);

        extendedVerbPredicateExtractionAllowedTags.add(Tags.HAVE);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.VERB);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.VERB_ED);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.MODAL_VERB);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE);
        extendedVerbPredicateExtractionAllowedTags.add(Tags.ADVERB);

        extendedHaveBeenVerbPredicateExtractionAllowedTags.add(Tags.HAVE);
        extendedHaveBeenVerbPredicateExtractionAllowedTags.add(Tags.IS_ARE);
        extendedHaveBeenVerbPredicateExtractionAllowedTags.add(Tags.ADJECTIVE);
        extendedHaveBeenVerbPredicateExtractionAllowedTags.add(Tags.VERB_ED);
        extendedHaveBeenVerbPredicateExtractionAllowedTags.add(Tags.ADVERB);

        subjectNounPredicateExtractionAllowedTags.add(Tags.ADJECTIVE);
        subjectNounPredicateExtractionAllowedTags.add(Tags.ADVERB);
        subjectNounPredicateExtractionAllowedTags.add(Tags.CONJUNCTION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NOUN);
        subjectNounPredicateExtractionAllowedTags.add(Tags.NUMBER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.PREPOSITION);
        subjectNounPredicateExtractionAllowedTags.add(Tags.QUANTIFIER);
        subjectNounPredicateExtractionAllowedTags.add(Tags.TO);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ING);
        subjectNounPredicateExtractionAllowedTags.add(Tags.VERB_ED);


        wordsToFilterCache.add("the");
        wordsToFilterCache.add("a");
        wordsToFilterCache.add("an");
        wordsToFilterCache.add("The");
        wordsToFilterCache.add("A");
        wordsToFilterCache.add("An");
    }

}
