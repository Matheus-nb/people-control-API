package com.api.peoplecontrol.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class ConvertionUtils {

    private ConvertionUtils(){}

    public static <T> T convert(Class<T> clazz, Object objectCopiar) {
        T objectCopiado = null;

        if(clazz != null &&
                objectCopiar != null) {

            objectCopiado = BeanUtils.instantiateClass(clazz);

            BeanUtils.copyProperties(objectCopiar, objectCopiado);
        }

        return objectCopiado;
    }

}
