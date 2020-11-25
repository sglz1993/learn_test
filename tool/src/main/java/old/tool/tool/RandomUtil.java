package old.tool.tool;

import java.util.Random;

/**
 * @Author pengyue.du
 * @Date 2020/5/13 6:15 下午
 * @Description
 */
public class RandomUtil {

    private static final char[] exclude4 = {'0', '1', '2', '3', '5', '6', '7', '8', '9'};

    public static String getNumExclude4(int size){
        if(size <= 1){
            return "";
        }
        Random random = new Random();
        int index = random.nextInt(8);
        StringBuilder sb = new StringBuilder();
        sb.append(exclude4[index + 1]);
        for(int i = 1; i < size; i++){
            sb.append(exclude4[random.nextInt(9)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getNumExclude4(6));
    }

}
