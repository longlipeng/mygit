package com.huateng.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.LabeledCSVParser;

public class CsvFileParser {

    private LabeledCSVParser csvParser;// csvParser

    private int currLineNum = -1;// The current line number for reading.

    private String[] currLine = null;// The data of current line.

    /*
     * Param: in InputStream throws IOException
     */

    public CsvFileParser(InputStream in) throws IOException {

        csvParser = new LabeledCSVParser(new ExcelCSVParser(in));
        currLineNum = csvParser.getLastLineNumber();
    }
    
    public CsvFileParser(Reader reader) throws IOException{
    	 csvParser = new LabeledCSVParser(new ExcelCSVParser(reader));
         currLineNum = csvParser.getLastLineNumber();
    }

    /*
     * Get the titles of csv file.
     *
     * return String[]
     */
    public String[] getTitles() throws IOException {
        return csvParser.getLabels();
    }

    /*
     * Checkout whether it has more data.
     *
     * @return ture: has data，false nodata.
     */
    public boolean hasMore() throws IOException {
        currLine = csvParser.getLine();
        currLineNum = csvParser.getLastLineNumber();
        if (null == currLine)
            return false;
        return true;
    }

    /*
     * Get the data of filed by field name. param:String filedName
     *
     * @return:String
     */
    public String getByFieldName(String fieldName) {

        return csvParser.getValueByLabel(fieldName);
    }

    /*
     * Close the parser.
     */
    public void close() throws IOException {
        csvParser.close();

    }

    /*
     * Read the current line.Return the data of current line.
     *
     * @return String[]
     */
    public String[] readLine() throws IOException {
        currLine = csvParser.getLine();

        currLineNum = csvParser.getLastLineNumber();

        return currLine;
    }

    /*
     * Get current line number.
     *
     * @return int
     */
    public int getCurrLineNum() {
        return currLineNum;

    }

    /*
     * Get the data of the current line.
     *
     * @return String[]
     */
    public String[] getCurrLine() {
        return currLine;
    }

}
