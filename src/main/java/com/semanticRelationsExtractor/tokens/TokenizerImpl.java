package com.semanticRelationsExtractor.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oliver on 5/17/2017.
 */
public class TokenizerImpl implements Tokenizer {

    @Override
    public List<String> splitStringIntoList(String sentence) {
        String[] tokTmp;
        tokTmp = sentence.split("\\ ");
        final List<String> tokens = removeEmptyStringInSentence(tokTmp);
        List<String> filteredTokens = new ArrayList<>();
        for (String token : tokens) {
            token = removeEmptyCharsFromToken(token);
            filteredTokens.add(token);
        }
        return filteredTokens;
    }

    /**
     * If token ends with "," then it is converted to the char array
     * and comma is removed by copying the char array into new array
     * ignoring the last comma element.
     *
     * @param token ending with comma.
     * @return String token without comma.
     */
    @Override
    public String removeCommaAndDot(final String token) {
        char[] charTmp;
        charTmp = token.toCharArray();
        final char[] charToken = new char[charTmp.length - 1];
        for (int i = 0; i < charTmp.length - 1; i++) {
            charToken[i] = charTmp[i];
        }
        final String tokenWithoutComma = new String(charToken);
        return tokenWithoutComma;
    }

    /**
     * Removes empty String that is located in sentences with index > 0
     * that is created by empty space behind each sentence (space between
     * end of the sentence and the start of the new sentence). It would be more
     * efficient to remove only first token (empty string) but this loop guarantees
     * that all accidental empty strings are removed.
     *
     * @param tokens Array of tokens with empty strings.
     * @return List of tokens without empty strings.
     */
    private List<String> removeEmptyStringInSentence(final String[] tokens) {
        final List<String> listTokens = new ArrayList<String>();
        for (final String token : tokens) {
            if (!token.equals("")) {
                listTokens.add(token);
            }
        }
        return listTokens;
    }

    private String removeEmptyCharsFromToken(String token) {
        char[] charTmp;
        charTmp = token.toCharArray();
        int numberOfEmptyChars = 0;
        for (int i = 0; i <= charTmp.length - 1; i++) {
            if (charTmp[i] == '\uFEFF') {
                numberOfEmptyChars++;
            }
        }
        return new String(Arrays.copyOfRange(charTmp, numberOfEmptyChars, charTmp.length));
    }
}
