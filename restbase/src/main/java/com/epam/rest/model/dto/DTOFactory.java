package com.epam.rest.model.dto;

import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.epam.rest.BaseDataTable;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cucumber.api.DataTable;

/**
 * Factory class to create DTO entities, initialized with values from
 * Cucumber data table.
 */
@Component
public class DTOFactory {

    /**
     * Creates DTO from Cucumber data table, wrapping it with base dtat table with keywords support.
     *
     * @param dataTable
     * @param dtoClass
     * @param <T>
     * @return
     */
    public static <T> T dto(DataTable dataTable, Class<T> dtoClass) {
        return dto(BaseDataTable.wrap(dataTable), dtoClass);
    }

    public static <T> T mergeDTO(DataTable dataTable, T dto) {
        return mergeDTO(BaseDataTable.wrap(dataTable), dto);
    }

    /**
     * Creates DTO from base data table, supporting keywords
     *
     * @param baseDataTable
     * @param dtoClass
     * @param <T>
     * @return
     */
    public static <T> T dto(BaseDataTable baseDataTable, Class<T> dtoClass) {
        try {
            T dto = dtoClass.newInstance();
            return mergeDTO(baseDataTable, dto);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T mergeDTO(BaseDataTable baseDataTable, T dto) {
        try {
            for (final String column : baseDataTable.headers()) {
                Method setter = setter(dto.getClass(), column);
                if (null != setter) {
                    Class<?> parameterType = Lists.newArrayList(setter.getParameterTypes()).get(0);
                    Object value = ParametersConverters.convert(baseDataTable.getValue(column), parameterType);
                    setter.invoke(dto, value);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return dto;
    }

    private static <T> Method setter(Class<T> dtoClass, String column) {
        return findMethod(dtoClass, "set", column);
    }

    private static <T> Method findMethod(final Class<T> dtoClass, final String prefix, final String column) {
        Set<Method> methodsSets = Sets.<Method>newHashSet(dtoClass.getDeclaredMethods());
        Iterable<Method> filteredMethods = Iterables.<Method>filter(methodsSets, new Predicate<Method>() {
            @Override
            public boolean apply(Method method) {
                return StringUtils.containsIgnoreCase(method.getName(),
                        new StringBuilder().append(prefix).append(StringUtils.deleteWhitespace(column).toString()));
            }
        });
        return Iterables.getFirst(filteredMethods, null);
    }

}
