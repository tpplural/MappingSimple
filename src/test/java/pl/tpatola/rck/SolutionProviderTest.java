package pl.tpatola.rck;

import pl.tpatola.rck.logic.SolutionProviderImpl;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolutionProviderTest {
    SolutionProviderImpl instance = (SolutionProviderImpl) SolutionProviderImpl.getInstance();


    @org.junit.jupiter.api.Test
    void testResultForInput5() {
        //given input = 5
        Integer input = 5;
        //when
        List<Integer> result = instance.getDividers(input);
       //then
        assertEquals(result.size(),2);
        assertTrue(result.contains(Integer.valueOf(5)));
    }
    @org.junit.jupiter.api.Test
    void overTheLimit(){
        //given input = 25
        Integer input = 25;
        //when
        //then
        assertThrows(InvalidParameterException.class, () ->{
            instance.getDividers(input);
        });

    }
}
