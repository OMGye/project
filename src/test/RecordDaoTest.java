import com.common.Const;
import com.common.RecordAuth;
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
       List<RecordDecNum> list = recordMapper.selectAmountMaterial(null,null, RecordAuth.MATERIAL.getCode(), Const.RecordConst.Last_CHECK);
        for (RecordDecNum recordDecNum : list)
            System.out.println(recordDecNum.getRecordDec() + "--" + recordDecNum.getTotalPrice()+ "---"+ recordDecNum.getNumber());
    }
}
