package com.semanticRelationsExtractor.tokens;

import java.util.List;

/**
 * Created by Oliver on 5/17/2017.
 */
public interface Tokenizer {

    List<String> splitStringIntoList(String sentence);

    String removeCommaAndDot(final String token);

}
