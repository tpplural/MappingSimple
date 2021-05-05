package pl.tpatola.rck.logic;

import pl.tpatola.rck.dataaccess.DataProvider;
import pl.tpatola.rck.dataaccess.SimpleDataProviderImpl;

import java.util.Map;

public class MapperImpl implements Mapper {

    private Map<String, Map<Integer, String>> mappings;

    DataProvider dataProvider = new SimpleDataProviderImpl();


    @Override
    public Map<Integer, String> getMapping(String key) {
        if (!mappings.containsKey(key)){
           mappings.put(key, dataProvider.provideMappingForKey(key)) ;
        }
        return mappings.get(key);
    }
}
