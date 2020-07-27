package random;

import java.util.Random;

/**
 * @author WJL
 */
public class RandomCodeUtils {

    /**
     * 随机数生成
     * @return
     */
    public static String generateValidateCode(){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i <= 6; i++){
            stringBuffer.append(random.nextInt(10));
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateValidateCode());
    }
}
