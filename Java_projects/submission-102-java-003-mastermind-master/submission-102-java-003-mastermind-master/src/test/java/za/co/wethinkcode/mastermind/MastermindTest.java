package za.co.wethinkcode.mastermind;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Random;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

class StudentTest
{
        @BeforeEach
    void setUp() {
    }


     @Test
     void testGenerator()
     {
        Random randomNumberMock = Mockito.mock(Random.class);
        when(randomNumberMock.nextInt(anyInt())).thenReturn(0,1,2,3);
        
        CodeGenerator generator = new CodeGenerator(randomNumberMock);

        assertEquals("1234",generator.generateCode());

     }   
     
     @Test
     void testGen()
     {
        Random randomNumberMock = Mockito.mock(Random.class);
        when(randomNumberMock.nextInt(anyInt())).thenReturn(2,7,5,3);
        
        CodeGenerator generator = new CodeGenerator(randomNumberMock);

        assertEquals("3864",generator.generateCode());

     }   
     
     @Test
     void testGene()
     {
        Random randomNumberMock = Mockito.mock(Random.class);
        when(randomNumberMock.nextInt(anyInt())).thenReturn(0,4,2,1);
        
        CodeGenerator generator = new CodeGenerator(randomNumberMock);

        assertEquals("1532",generator.generateCode());

     }          


}