package org.py.test.jvm.vm;

import org.junit.Test;

import java.util.Vector;

public class MemoryOozeTest {

    @Test
    public void testMemoryOoze() {
        Vector<Person> v = new Vector<Person>();
        int i=0;
        while (1<2)
        {
            Person person = new Person("name");
            v.add(person);
            person = null;
            i++;
            if(i % 1000 == 0) {
                try {
                    System.out.println(v.size());
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
