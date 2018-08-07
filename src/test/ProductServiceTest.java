
import com.dao.MaterialStockMapper;
import com.dao.UserMapper;
import com.google.common.collect.Lists;
import com.pojo.MaterialStock;
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
    @Autowired
    private MaterialStockMapper materialStockMapper;
    @Test
    @Transactional
    public void testIProductService() throws Exception {
        MaterialStock materialStock = materialStockMapper.selectByCategoryName("11");
        System.out.println(materialStock);
    }

}
