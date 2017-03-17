package com.epam.test;

import com.epam.rest.TestDataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestDataMap
{

    private static final String NULL_KEYWORD = "null";

    private Map<String, String> parameters;

    public TestDataMap() {
        this.parameters = new HashMap<>();
    }

    public TestDataMap(final Map<String, String> parameters) {
        this.parameters = new HashMap<>(parameters);
    }

    public void setParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void add(final String key, final String value) {
        this.parameters.put(key, value);
    }

    public String get(final String key) {
        validateParameterByName(key);
        if (parameters.get(key).equalsIgnoreCase(NULL_KEYWORD)) {
            return null;
        }
        return parameters.get(key);
    }

    public Integer getInt(final String key) {
        validateParameterByName(key);
        if (parameters.get(key).equalsIgnoreCase(NULL_KEYWORD)) {
            return null;
        }
        try {
            return Integer.parseInt(parameters.get(key));
        } catch (NumberFormatException e) {
            throw new TestDataException("Could not convert parameter with value '%s' to integer. Parameter name: '%s'.",
                    parameters.get(key), key);
        }
    }

    public Double getDouble(final String key) {
        validateParameterByName(key);
        if (parameters.get(key).equalsIgnoreCase(NULL_KEYWORD)) {
            return null;
        }
        try {
            return Double.parseDouble(parameters.get(key));
        } catch (NumberFormatException e) {
            throw new TestDataException("Could not convert parameter with value '%s' to double. Parameter name: '%s'.",
                    parameters.get(key), key);
        }
    }

    public List<String> getList(final String key) {
        validateParameterByName(key);
        return Arrays.asList(parameters.get(key).split(","));
    }

    /**
     * Table fields in Json file should have the following format:
     * expectedResultSet:{return ['Corporate_Status_Code': s$expectedCorporateStatusCode, 'Flag': 'True']}
     * @param key
     * @return mao
     * @throws ArrayIndexOutOfBoundsException
     */
    public Map<String, String> getMap(final String key) {
        validateParameterByName(key);
        Map<String, String> map = new HashMap<>();
        String[] array = parameters.get(key).replaceAll("[\\s\\{}]*", "").split("[=,]");
        if (array.length % 2 != 0) {
            throw new ArrayIndexOutOfBoundsException("Array length should be even");
        }
        for (int i = 0; i < array.length; i += 2) {
            map.put(array[i], array[i + 1]);
        }
        return map;
    }

    public List<Integer> getListInt(final String key) {
        validateParameterByName(key);
        try {
            return Arrays.asList(parameters.get(key).split(",")).stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new TestDataException(
                    "Could not convert parameter with value '%s' to list of integers. Parameter name: '%s'.",
                    parameters.get(key), key);
        }
    }

    public List<Double> getListDouble(final String key) {
        validateParameterByName(key);
        try {
            return Arrays.asList(parameters.get(key).split(",")).stream().map(Double::parseDouble)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new TestDataException(
                    "Could not convert parameter with value '%s' to list of double. Parameter name: '%s'.",
                    parameters.get(key), key);
        }
    }

    /**
     * Combines string values from a set of parameters into list
     * @param keys - parameters names, i.e. r1, r2, ..., r13
     * @return a list of string parameters values
     */
    public List<String> getList(final String... keys) {
        List<String> result = new ArrayList<>();
        for (String key : keys) {
            result.add(get(key));
        }
        return result;
    }

    /**
     * Combines integer values from a set of parameters into list
     * @param keys - parameters names, i.e. r1, r2, ..., r13
     * @return a list of integer parameters values
     */
    public List<Integer> getListInt(final String... keys) {
        List<Integer> result = new ArrayList<>();
        for (String key : keys) {
            result.add(getInt(key));
        }
        return result;
    }

    /**
     * Returns a list of example table rows, where each row is a map
     * @param key - the key which value is a file path, i.e. 'examples/OR_0000.table'
     * @return a list of maps
     */
    public List<Map<String, String>> getExampleTableRows(final String key) {
        validateParameterByName(key);
//        return ExamplesTableParser.getExampleTableRows(parameters.get(key));
        return null;
    }

    @Override
    public String toString() {
        return parameters.toString();
    }

    private void validateParameterByName(String key) {
        if (!parameters.containsKey(key)) {
            throw new TestDataException("No such parameter name: '%s'.", key);
        }
        if (parameters.get(key) == null) {
            throw new TestDataException("Value for parameter name '%s' is null.", key);
        }
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

}
