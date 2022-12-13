package io.mockxe.uni.s1.a6.marbles;

public class Switch {

    private Switch succ0;

    private Switch succ1;

    private Switch nextSucc;


    /**
     * Creates an instance of {@link Switch} class by defining its two successors. <br>
     * This is the default behaviour of a Switch
     *
     * <pre>
     *     new instance
     *         /  \
     *     succ0  succ1
     * </pre>
     *
     * @param succ0 first or "left" successor
     * @param succ1 second or "right successor
     */
    public Switch(Switch succ0, Switch succ1) {
        this.succ0 = succ0;
        this.succ1 = succ1;
        this.nextSucc = this.succ0;
    }

    /**
     * Creates an instance of {@link Switch} with just one successor. <br>
     * This is also called a "Straight"
     *
     * <pre>
     *     new instance
     *           |
     *         succ
     * </pre>
     *
     * @param succ
     */
    public Switch(Switch succ) {
        this(succ, succ);
    }

    /**
     * Creates an instance of {@link Switch} with three equally distributed successor. <br>
     * This is also called a "Multi-Switch"
     * <br>
     * <br>
     * Internally this works by having two switches as successor, giving out three of the outputs and routing back
     * the fourth output into the input.<br>
     * This guarantees the output order {@code succ0, succ1, succ2, succ0, succ1, succ2, ...}
     *
     * <pre>
     *        new instance <-\
     *          /      \     |
     *         o        o----/
     *       /   \      |
     *    succ0 succ2 succ1
     *
     * </pre>
     *
     *
     * @param succ0
     * @param succ1
     * @param succ2
     */
    public Switch(Switch succ0, Switch succ1, Switch succ2) {
        this.succ0 = new Switch(succ0, succ2);
        this.succ1 = new Switch(succ1, this);
        this.nextSucc = this.succ0;
    }

    /**
     * Simulates a marble rolling through the switch.<br>
     * Every time after a marble passed the switch, it switches between the successors, so it will be the other one
     * the next time.
     *
     * @return the successor the next marble will go to
     */
    public Switch next() {
        Switch ret = nextSucc;

        if (nextSucc == succ0) {
            nextSucc = succ1;
        } else {
            nextSucc = succ0;
        }

        return ret;
    }

    /**
     * Simulates the marble rolling through the switch and all its successors.<br>
     * Every time after a marble passed the switch, it switches between the successors.
     * The method will return the last switch the marble has passed, basically where it stopped.
     *
     * @return the last switch the marble has passed
     */
    public Switch findLast() {
        Switch next = this.next();

        if (null == next) {
            return this; // has no next, so it must be the last
        } else {
            return next.findLast(); // find last of the next switch
        }
    }

    /**
     * Returns an array of all direct successors of this switch that are not {@code null}
     *
     * @return an array of all direct successors of this switch that are not {@code null}
     */
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

    /**
     * Counts all possible switches (including itself) where a marble could go through,
     * starting from itself. Cycles are only counted once.
     *
     * @return the number of switches
     */
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
