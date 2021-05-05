package pl.tpatola.rck.logic;

import pl.tpatola.rck.common.TaskConstants;
import pl.tpatola.rck.dataaccess.DataProvider;
import pl.tpatola.rck.dataaccess.SimpleDataProviderImpl;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionProviderImpl implements SolutionProvider {

    private final Map<Integer, List<Integer>> dividers = new HashMap<>();

    private final Map<String,Map<Integer, String>> categoriesWithMapping = new HashMap<>();

    private static SolutionProviderImpl instance;

    private SolutionProviderImpl(){

    }

    public static SolutionProvider getInstance(){
        if (instance == null){
            instance = new SolutionProviderImpl();
        }
        return instance;
    }


    public List<Integer> getDividers(Integer n){
        if (n > getUpperLimit()){
            throw new InvalidParameterException("Exceeding upper limit of " + getUpperLimit());
        }
        if (!dividers.containsKey(n)) {
            dividers.put(n,calculateDivisors(n));
        }
        return dividers.get(n);

    }

    @Override
    public Integer getUpperLimit() {
        return TaskConstants.MAXIMUM_MAPPING_RANGE;
    }

    @Override
    public Map<Integer, List<String>> getDividersWithMappings(List<Integer> values, String mapping) {

        Map<Integer, List<String>> result = new HashMap<>();
        for (Integer i : values) {
            List<Integer> divisors = getDividers(i);

            if (!categoriesWithMapping.containsKey(mapping)){
                DataProvider dataProvider = new SimpleDataProviderImpl();
                categoriesWithMapping.put(mapping,dataProvider.provideMappingForKey(mapping));
            }
            List<String> mapped = divisors
                    .stream()
                    .map(divisor -> categoriesWithMapping.get(mapping).get(divisor)).collect(Collectors.toList());

            result.put(i,mapped);

        }
        return result;
    }

    private List<Integer> calculateDivisors(Integer input){
        return IntStream.rangeClosed(1, input).filter(i -> input % i == 0).boxed().collect(Collectors.toList());
    }
}
