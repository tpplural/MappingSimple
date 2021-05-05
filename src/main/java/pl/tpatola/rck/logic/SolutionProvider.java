package pl.tpatola.rck.logic;

import java.util.List;
import java.util.Map;

public interface SolutionProvider {


    List<Integer> getDividers(Integer n);

    Integer getUpperLimit();

    Map<Integer, List<String>> getDividersWithMappings(List<Integer> values, String mapping);

}
