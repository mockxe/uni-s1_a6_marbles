package io.mockxe.uni.s1.a6.marbles;

public class Switch {

    private Switch succ0;

    private Switch succ1;

    private Switch nextSucc;


    // classic switch
    public Switch(Switch succ0, Switch succ1) {
        this.succ0 = succ0;
        this.succ1 = succ1;
        this.nextSucc = this.succ0;
    }

    // straight
    public Switch(Switch succ) {
        this(succ, succ);
    }

    // multi-switch
    public Switch(Switch succ0, Switch succ1, Switch succ2) {
        this(
                new Switch(succ0, succ2),
                new Switch(succ1, null)
        );
        this.succ1.succ1 = this;
    }


    public Switch next() {
        Switch ret = nextSucc;

        if (nextSucc == succ0) {
            nextSucc = succ1;
        } else {
            nextSucc = succ0;
        }

        return ret;
    }

    public Switch findLast() {
        Switch next = this.next();

        if (null == next) {
            return this; // has no next, so it must be the last
        } else {
            return next.findLast(); // find last of the next switch
        }
    }

    public Switch[] directSuccessors() {
        int i = 0;
        Switch[] src = new Switch[2];

        if (succ0 != null) {
            src[i] = succ0;
            i++;
        }
        if (succ1 != null) {
            src[i] = succ1;
            i++;
        }


        return recursiveArrayCopy(src, i);
    }

    public int countSwitches() {
        return successors(this).length;
    }

    private static Switch[] successors(Switch element) {
        return successors(element, new Switch[] { element });
    }

    private static Switch[] successors(Switch element, Switch[] arr) {
        for (Switch successor : element.directSuccessors()) {
            if (!containsElement(successor, arr)) {
                arr = addElement(successor, arr);
                arr = successors(successor, arr);
            }
        }
        return arr;
    }

    private static Switch[] addElement(Switch element, Switch[] arr) {
        Switch[] ret = recursiveArrayCopy(arr, arr.length + 1); // copy arr to ret
        ret[ret.length - 1] = element; // add element in last position

        return ret;
    }

    public static boolean containsElement(Switch element, Switch[] arr) {
        return containsElement(element, arr, 0);
    }

    private static boolean containsElement(Switch element, Switch[] arr, int i) {
        if (i < arr.length ) {
            if (element == arr[i]) {
                return true; // return true if element is in arr
            } else {
                return containsElement(element, arr, i + 1); // check next element
            }
        } else {
            return false; // return false if all elements were checked
        }
    }

    public static Switch[] recursiveArrayCopy(Switch[] arr, int size) {
        return recursiveArrayCopy(arr, new Switch[size], 0);
    }

    private static Switch[] recursiveArrayCopy(Switch[] src, Switch[] dest, int i) {
        if (i < dest.length && i < src.length) {
            dest[i] = src[i];
            return recursiveArrayCopy(src, dest, i + 1);
        } else {
            return dest;
        }
    }


    public Switch getSucc0() {
        return succ0;
    }

    public Switch getSucc1() {
        return succ1;
    }

    public void setSucc0(Switch succ0) {
        this.succ0 = succ0;
    }

    public void setSucc1(Switch succ1) {
        this.succ1 = succ1;
    }

}
