package org.py.common.internals;


import com.google.common.collect.Lists;

import java.util.*;

public class ServiceBootstrap {


    public static <S> S loadFirst(Class<S> clazz) {
        List<S> list = loadAllOrdered(clazz);
        return list.get(0);
    }

    public static <S> Iterator<S> loadAll(Class<S> clazz) {
        ServiceLoader<S> loader = ServiceLoader.load(clazz);

        return loader.iterator();
    }

    public static <S> List<S> loadAllOrdered(Class<S> clazz) {
        Iterator<S> iterator = loadAll(clazz);

        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format(
                    "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
                    clazz.getName()));
        }

        List<S> candidates = Lists.newArrayList(iterator);
        Collections.sort(candidates, (o1, o2) -> {
            // the smaller order has higher priority
            Ordered o1orderd = o1.getClass().getAnnotation(Ordered.class);
            Ordered o2orderd = o2.getClass().getAnnotation(Ordered.class);
            return Integer.compare(o1orderd.value(), o2orderd.value());
        });

        return candidates;
    }

    public static <S extends Ordered> S loadPrimary(Class<S> clazz) {
        List<S> candidates = loadAllOrdered(clazz);

        return candidates.get(0);
    }

}