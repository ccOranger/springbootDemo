package ldemo;

import com.example.ldemo.config.mq.DelayRabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DelayRabbitmqTests {

    @Autowired
    private AmqpTemplate rabbitAmqpTemplate;

    @Test
    public void contextLoads() {

        rabbitAmqpTemplate.convertAndSend(DelayRabbitConfig.DELAY_EXCHANGE_NAME, DelayRabbitConfig.DELAY_QUEUEB_ROUTING_KEY, "订单实体类对象信息", message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
            return message;
        });


    }

}
