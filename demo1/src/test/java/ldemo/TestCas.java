package ldemo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCas {

    @Test
    public void  tt(){


        AtomicInteger atomicInteger = new AtomicInteger(0);

        atomicInteger.incrementAndGet();
    }
}
