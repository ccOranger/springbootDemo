package ldemo;


import com.example.ldemo.LdemoApplication;
import com.example.ldemo.service.Dialog;
import com.example.ldemo.service.Singleton;
import com.example.ldemo.service.impl.BuildDialog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LdemoApplication.class)
public class DesignTest {

    @Test
    public void  singletonTest(){
        Singleton singleton = Singleton.getSingleton();
        Singleton singleton1 = Singleton.getSingleton();

        System.out.println(singleton.equals(singleton1));


    }


    @Test
    public void diallog(){
        Dialog dialog = new BuildDialog();
        dialog.renderWindow();
    }
}
