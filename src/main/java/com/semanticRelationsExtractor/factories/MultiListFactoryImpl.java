package com.semanticRelationsExtractor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class MultiListFactoryImpl implements MultiListFactory {

    private final static Logger LOGGER = Logger.getLogger(MultiListFactoryImpl.class.getName());

    @Override
    public List<List<String>> create(List<String> words) {
        LOGGER.info("ENTERING create method of MultiListFactoryImpl... ");
        LOGGER.info("*********************************************************************");

        List<List<String>> multiList = new ArrayList<List<String>>();
        List<String> singleList = new ArrayList<>();
        int index = 0;
        for (String word : words) {
            //IF TOKEN DOES NOT END WITH COMMA, JUST ADD IT TO THE SUBPATH
            if (!(word.endsWith(","))) {
                singleList.add(word);
            } else {
                //ADD THE ACTUAL TOKEN AND ADD ACTUAL SUBPATH TO THE SUBPATHS LIST.
                //AND CREATE NEW EMPTY SUBPATH, INTO WHICH NEXT TOKENS WILL BE INSERTED
                String wordWithoutComma = word.substring(0, word.length() - 1);
                singleList.add(wordWithoutComma);
                multiList.add(singleList);
                LOGGER.info("Created singleList with size " + singleList.size() + " and added into multiList.");
                singleList = new ArrayList<>();
            }
            //ALWAYS ADD SUBPATH STARTING WITH THE LAST COMMA IN SENTENCE AND ENDING WITH THE
            //LAST TOKEN IN SENTENCE
            if (index == words.size() - 1) {
                multiList.add(singleList);
                LOGGER.info("Created singleList with size " + singleList.size() + " and added into multiList.");
            }
            index++;
        }
        LOGGER.info("LEAVING create method of MultiListFactoryImpl with  " + multiList.size() + " " +
                " subPaths created.");
        LOGGER.info("*********************************************************************");

        return multiList;
    }

}
