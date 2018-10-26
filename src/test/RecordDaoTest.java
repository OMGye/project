import com.dao.RecordMapper;
import com.pojo.Record;
import com.vo.RecordDecNum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by upupgogogo on 2018/10/26.上午12:20
 */
public class RecordDaoTest extends  TestBase{

    @Autowired
    private RecordMapper recordMapper;
    @Test
    public void test2(){
        BigDecimal bigDecimal = recordMapper.selectAmountPrice(null,null);
        System.out.println(bigDecimal);
    }
}
