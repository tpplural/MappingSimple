package pl.tpatola.rck.dataaccess;

import java.util.Map;

public interface DataProvider {
    Map<Integer,String> provideMappingForKey(String key);
}
