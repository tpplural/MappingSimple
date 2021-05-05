package pl.tpatola.rck.logic;

import java.util.Map;

public interface Mapper {
    Map<Integer, String> getMapping(String key);
}
