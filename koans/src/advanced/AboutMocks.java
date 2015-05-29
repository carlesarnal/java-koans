package advanced;

import com.sandwich.koan.Koan;

import java.lang.Integer;
import java.lang.Override;
import java.lang.System;
import java.util.ArrayList;
import java.util.List;

import static com.sandwich.util.Assert.fail;

public class AboutMocks {

    static interface Collaborator {
        public void doBusinessStuff();
    }

    static class ExplosiveCollaborator implements Collaborator {
        public void doBusinessStuff() {
            fail("Default collaborator's behavior is complicating testing.");
        }
    }


    static class ClassUnderTest {
        Collaborator c;

        public ClassUnderTest() {
            // default is to pass a broken Collaborator, test should pass one
            // that doesn't throw exception
            this(new ExplosiveCollaborator());
        }

        public ClassUnderTest(Collaborator c) {
            this.c = c;
        }

        public boolean doSomething() {
            c.doBusinessStuff();
            return true;
        }
    }

    @Koan
    public void simpleAnonymousMock() {
        // HINT: pass a safe Collaborator implementation to constructor
        // new ClassUnderTest(new Collaborator(){... it should not be the
        // objective of this test to test that collaborator, so replace it
        new ClassUnderTest(new ExplosiveCollaborator()).doSomething();
    }


    public interface Data {
        public List calculator();
    }

    static class DataImplementation implements Data {
        public List calculator() {
            List list = new ArrayList(5);
            for (int i = 0; i < list.size(); i++) {
                fail("Default collaborator's behavior is complicating testing.");
            }
            return list;
        }

        static class ClassToTest {
            public List calculator() {
                List list = new ArrayList(5);
                List data = new DataImplementation().calculator();
                for (int i = 0; i < list.size(); i++) {
                    list.set(i, data.get(i).toString());
                }
                return list;
            }


        }


        /*This fails, but we want to test only ClassToTest, create an implementation of data that do not fail.
        * */
        @Koan
        public void dataMock() {
            ClassToTest data = new ClassToTest();
            List list = data.calculator();
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }

        }
    }
}
