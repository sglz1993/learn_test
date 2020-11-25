package old.tool.java_base;

/**
 * @author: pengyue.du
 * @time: 2020/5/26 4:31 下午
 */
public abstract class EnumTest {

    String name = "Enum";

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        MethodEnum.A.print();
        MethodEnum.A.print2();
    }

    public static enum MethodEnum{
        A{
            @Override
            public void print() {
                System.out.println("a");
            }
            public void print3() {
                System.out.println("3a");
            }
        },
        ;
        public void print(){
            System.out.println("all");
        }
        public void print2(){
            print();
        }
    }

}
