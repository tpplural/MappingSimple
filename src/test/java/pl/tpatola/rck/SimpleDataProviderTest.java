package pl.tpatola.rck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.tpatola.rck.common.TaskConstants;
import pl.tpatola.rck.dataaccess.DataProvider;
import pl.tpatola.rck.dataaccess.SimpleDataProviderImpl;

import java.security.InvalidParameterException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleDataProviderTest {
    DataProvider instance;

    @BeforeEach
    void init(){
        instance = new SimpleDataProviderImpl();
    }

    @Test
    void testResultForValidInput() {
        //given input = 5
        String key = TaskConstants.SUPPORTED_COLLECTIONS.get(0);
        //when
        Map<Integer, String> result = instance.provideMappingForKey(key);
       //then
        assertEquals(result.size(),10);
            assertTrue(result.get(1).contains(key));
        result.values().stream().forEach(System.out::println);
    }
    @Test
    void testResultForInvalidKey() {
        //given input = 5
        String key = "NON_EXISTING";
        //when
        //then
        assertThrows(InvalidParameterException.class, () ->{
            instance.provideMappingForKey(key);
        });

    }

}
