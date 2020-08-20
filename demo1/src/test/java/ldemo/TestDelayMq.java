package ldemo;


import com.example.ldemo.config.mq.DelayQueueConfig;
import com.example.ldemo.config.mq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDelayMq {

    @Autowired
    private Sender sender;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Test
    public void  tt(){


        for (int i=0;i<3;i++){

            amqpTemplate.convertAndSend(DelayQueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME,"测试==="+i);
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
