package com.cloudsea.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EnumUtil {

    private static final String VALUES = "ENUM$VALUES";

    public static <T extends Enum<?>> LinkedHashMap<String, LinkedHashMap<String, Object>> readToMap(Class<T> enumClass)
            throws Exception {

        if (!enumClass.isEnum()) {
            throw new Exception(enumClass.getName() + "不是枚举类型");
        }

        LinkedHashMap<String, LinkedHashMap<String, Object>> map = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
        Enum<?>[] enums = enumClass.getEnumConstants();

        if (enums == null || enums.length == 0) {
            return map;
        }

        Field[] fields = enumClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (field.getType() != enumClass && !VALUES.equals(fieldName)) {
                fieldList.add(field);
            }
        }

        for (Enum<?> e : enums) {
            LinkedHashMap<String, Object> fmap = new LinkedHashMap<String, Object>();
            for (Field field : fieldList) {
                field.setAccessible(true);
                Object value = field.get(e);
                fmap.put(field.getName(), value);
            }

            map.put(e.name(), fmap);
        }

        return map;
    }
}
