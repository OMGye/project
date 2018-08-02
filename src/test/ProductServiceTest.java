
import com.dao.UserMapper;
import com.google.common.collect.Lists;
import com.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geely
 */
public class ProductServiceTest extends TestBase {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void testIProductService() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        int row = userMapper.updateItemId(list,2);
        if (row < 2)
            throw new Exception();
        System.out.println(row);
    }

}
