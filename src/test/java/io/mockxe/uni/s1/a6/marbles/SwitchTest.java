package io.mockxe.uni.s1.a6.marbles;

import org.junit.jupiter.api.Test;

class SwitchTest {

    @Test
    void testAlternatingSuccessors() {

        Switch s2 = new Switch(null);
        Switch s3 = new Switch(null);

        Switch s1 = new Switch(s2, s3);

        Switch next;
        for (int i = 0; i < 10; i++) {
            next = s1.next();

            System.out.println(i + ": " + next);

            if (i%2 == 0) {
                assert next == s2;
            } else {
                assert next == s3;
            }
        }



    }

    @Test
    void testFindLastSimple() {
        Switch s2 = new Switch(null);
        Switch s3 = new Switch(null);

        Switch s1 = new Switch(s2, s3);

        Switch last;
        for (int i = 0; i < 10; i++) {
            last = s1.findLast();

            System.out.println(i + ": " + last);

            if (i%2 == 0) {
                assert last == s2;
            } else {
                assert last == s3;
            }
        }
    }

    @Test
    void testFindLastAdvanced() {
        Switch s4 = new Switch(null);
        Switch s5 = new Switch(null);
        Switch s6 = new Switch(null);
        Switch s7 = new Switch(null);

        Switch s2 = new Switch(s4, s5);
        Switch s3 = new Switch(s6, s7);

        Switch s1 = new Switch(s2, s3);

        /*
         *        s1
         *      /    \
         *    s2      s3
         *   /  \    /  \
         *  s4  s5  s6  s7
         *
         *  expected order:
         *  s4, s6, s5, s7, s4, s6, s5, s7
         *
         */
        assert s1.findLast() == s4;
        assert s1.findLast() == s6;
        assert s1.findLast() == s5;
        assert s1.findLast() == s7;
        assert s1.findLast() == s4;
        assert s1.findLast() == s6;
        assert s1.findLast() == s5;
        assert s1.findLast() == s7;
    }


    @Test
    void testMultiSwitch() {
        Switch sa = new Switch(null);
        Switch sb = new Switch(null);
        Switch sc = new Switch(null);

        Switch sw = new Switch(sa, sb, sc);

        /*
         *
         *
         *      sw <--\
         *     /  \   |
         *    o    o -/
         *   / \   |
         *  sa sc  sb
         */
        assert sw.findLast() == sa;
        assert sw.findLast() == sb;
        assert sw.findLast() == sc;
        assert sw.findLast() == sa;
        assert sw.findLast() == sb;
        assert sw.findLast() == sc;
        assert sw.findLast() == sa;
        assert sw.findLast() == sb;
        assert sw.findLast() == sc;
    }

    @Test
    void testDirectSuccessor() {

        Switch a = new Switch(null);
        Switch b = new Switch(null);

        Switch s00 = new Switch(null);
        Switch sa0 = new Switch(a, null);
        Switch s0b = new Switch(null, b);
        Switch sab = new Switch(a, b);

        Switch[] s00arr = s00.directSuccessors();
        assert s00arr.length == 0;

        Switch[] sa0arr = sa0.directSuccessors();
        assert sa0arr.length == 1;
        assert sa0arr[0] == a;

        Switch[] s0barr = s0b.directSuccessors();
        assert s0barr.length == 1;
        assert s0barr[0] == b;

        Switch[] sabarr = sab.directSuccessors();
        assert sabarr.length == 2;
        assert sabarr[0] == a;
        assert sabarr[1] == b;

    }

}
