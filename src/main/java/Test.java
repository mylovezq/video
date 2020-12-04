

import com.alibaba.fastjson.util.TypeUtils;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    public static void main(String[] args) {

        String[] valueList = "4294967296-137438953472".split("-");

        if (valueList.length != 2) {
            System.out.println("错");
        }
        BigDecimal valMin = TypeUtils.castToBigDecimal(valueList[0]);
        System.out.println(valMin);
    }
}
