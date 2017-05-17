package com.semanticRelationsExtractor.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Oliver on 5/17/2017.
 */
public class InputDataReaderImpl implements InputDataReader {

    private final static Logger LOGGER = Logger.getLogger(InputDataReaderImpl.class.getName());

    @Override
    public List<String> read() {
        LOGGER.info("ENTERING read method of InputDataReaderImpl... ");
        LOGGER.info("*********************************************************************");
        List<String> inputDataAsStringList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("c:\\Users\\Oliver\\Documents\\SemanticExtraction\\SemanticExtractionRawTextData.txt"));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String inputDataString = br.readLine();
            while (inputDataString != null) {
                inputDataAsStringList.add(inputDataString);
                inputDataString = br.readLine();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("LEAVING read method of InputDataReaderImpl with  " + inputDataAsStringList.size() + " lines read.");
        LOGGER.info("*********************************************************************");
        return inputDataAsStringList;
    }
}
