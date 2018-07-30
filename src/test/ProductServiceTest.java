
import com.dao.UserMapper;
import com.google.common.collect.Lists;
import com.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;

import java.util.List;

/**
 * Created by geely
 */
public class ProductServiceTest extends TestBase {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testIProductService() throws ParseException {
        User user = userMapper.selectByUserNameAndPassword("","");
        System.out.println(user);
    }

}
