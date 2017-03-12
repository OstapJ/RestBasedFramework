package com.epam.rest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import cucumber.api.DataTable;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class BaseDataTable implements Iterable {
    private DataTable dataTable;
    private List<Map<String, String>> maps;

    public static BaseDataTable wrap(DataTable dataTable) {
        return new BaseDataTable(dataTable);
    }

    /**
     * Initializing constructor.
     *
     * @param dataTable
     */
    public BaseDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
        this.maps = dataTable.asMaps(String.class, String.class);
    }

    /**
     * Return cucumber data table structure
     *
     * @return cucumber data structure
     */
    public DataTable getDataTable() {
        return this.dataTable;
    }

    public Set<String> headers() {
        return Sets.<String>newHashSet(dataTable.topCells());
    }

    /**
     * Returns the number of rows of data present in this object.
     *
     * @return
     */
    public int getNumberOfRows() {
        return maps.size();
    }

    /**
     * Returns the value for the specified header from the data table. Returns null if no matching header is found.
     *
     * @param header
     * @return
     */
    public String getValue(String header) {
        return getValue(0, header);
    }

    /**
     * Returns the value for the specified row and header from the data table. Returns null if no matching header is
     * found.
     *
     * @param rowIndex
     * @param header
     * @return
     */
    public String getValue(int rowIndex, String header) {
        String result = maps.get(rowIndex).get(header);

        // sanitize nulls and blank strings to null
        if (StringUtils.isBlank(result)) {
            result = null;
        }
        // else check for some special flags and substitute their values
        else {
            if (result.equals(BaseConstants.DATATABLE_KEYWORD_EMPTY)) {
                result = "";
            } else if (result.startsWith(BaseConstants.DATATABLE_KEYWORD_CURRENT_DATETIME)) // keep
                                                                                            // before
                                                                                            // date
                                                                                            // match
                                                                                            // below
                                                                                            // since
                                                                                            // they
                                                                                            // both
                                                                                            // start
                                                                                            // the
                                                                                            // same
            {
                Date date = Utilities.getCurrentDate();

                // check for moving datetime forwards or backwards (by minutes)
                Pattern p = Pattern.compile(BaseConstants.DATATABLE_KEYWORD_CURRENT_DATETIME + "([-+])(\\d{1,4})");
                Matcher m = p.matcher(result);
                if (m.matches()) {
                    boolean positive = m.group(1).equals("+");
                    int adjustment = Integer.parseInt(m.group(2));
                    date = Utilities.moveDate(date, Calendar.MINUTE, (positive ? adjustment : -adjustment));
                }

                result = Utilities.formatAsDateTime(date);
            } else if (result.startsWith(BaseConstants.DATATABLE_KEYWORD_CURRENT_TIME)) {
                Date date = Utilities.getCurrentDate();
                Pattern p = Pattern.compile(BaseConstants.DATATABLE_KEYWORD_CURRENT_TIME + "([-+])(\\d{1,4})");
                Matcher m = p.matcher(result);
                if (m.matches()) {
                    boolean positive = m.group(1).equals("+");
                    int adjustment = Integer.parseInt(m.group(2));
                    date = Utilities.moveDate(date, Calendar.MINUTE, (positive ? adjustment : -adjustment));
                }

                result = Utilities.formatAsTime(date);
            } else if (result.startsWith(BaseConstants.DATATABLE_KEYWORD_CURRENT_DATE)) {
                Date date = Utilities.getCurrentDate();

                // check for moving date forwards or backwards (by days)
                Pattern p = Pattern.compile(BaseConstants.DATATABLE_KEYWORD_CURRENT_DATE + "([-+])(\\d{1,3})");
                Matcher m = p.matcher(result);
                if (m.matches()) {
                    boolean positive = m.group(1).equals("+");
                    int adjustment = Integer.parseInt(m.group(2));
                    date = Utilities.moveDate(date, Calendar.DATE, (positive ? adjustment : -adjustment));
                }

                result = Utilities.formatAsDate(date);
            } else if (result.startsWith(BaseConstants.DATATABLE_KEYWORD_RANDOM)) {
                int length = Integer.valueOf(StringUtils.split(result, '_')[1]);
                result = RandomStringUtils.randomAlphanumeric(length);
            }
            else if (result.startsWith(BaseConstants.DATATABLE_KEYWORD_GLOBAL))
            {
                Globals globals = (Globals) ApplicationContextProvider.getApplicationContext().getBean("globals");
                result = globals.get(StringUtils.split(result, '_')[1]);
            }
        }

        return result;
    }

    @Override
    public Iterator<DataTable> iterator() {
        return new BaseDataTableIterator();
    }

    class BaseDataTableIterator implements Iterator<DataTable> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return BaseDataTable.this.maps.size() > currentIndex;
        }

        @Override
        public DataTable next() {
            final Map<String, String> dataRecord = BaseDataTable.this.maps.get(currentIndex);
            currentIndex++;
            return DataTable.create(Collections.singletonList(dataRecord));
        }
    }
}
