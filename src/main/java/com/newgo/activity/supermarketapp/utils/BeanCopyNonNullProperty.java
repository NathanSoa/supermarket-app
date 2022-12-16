package com.newgo.activity.supermarketapp.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyNonNullProperty {

    public static void execute(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination, getNullProperties(source));
    }

    private static String[] getNullProperties(Object source) {
        Class<?> sourceClass = source.getClass();
        List<String> nullPropertiesNames = new ArrayList<>();

        nullPropertiesNames.addAll(Arrays.stream(sourceClass.getDeclaredMethods())
                .filter(each -> each.getName().contains("get"))
                .filter(each -> {
                    try {
                        return each.invoke(source) == null;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(each -> each.getName().replace("get", ""))
                .map(each -> each.substring(0, 1).toLowerCase() + each.substring(1))
                .collect(Collectors.toList()));

        if(arrayDoesNotHaveId(nullPropertiesNames))
            nullPropertiesNames.add("id");

        return nullPropertiesNames.toArray(new String[nullPropertiesNames.size()]);
    }

    private static boolean arrayDoesNotHaveId(List<String> nullPropertiesNames) {
        return !nullPropertiesNames.contains("id");
    }
}
