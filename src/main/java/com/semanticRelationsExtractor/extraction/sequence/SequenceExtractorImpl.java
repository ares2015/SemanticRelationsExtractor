package com.semanticRelationsExtractor.extraction.sequence;

import java.util.List;
import java.util.Set;

/**
 * Created by Oliver on 5/29/2017.
 */
public class SequenceExtractorImpl implements SequenceExtractor {

    @Override
    public String extract(List<String> tokensList, List<String> tagsList, int sequenceStartIndex, Set<String> allowedTags) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = sequenceStartIndex; i <= tagsList.size() - 1; i++) {
            String tag = tagsList.get(i);
            if (allowedTags.contains(tag)) {
                stringBuilder.append(tokensList.get(i));
                stringBuilder.append(" ");
            } else {
                break;
            }
        }
        return stringBuilder.toString();
    }
}
