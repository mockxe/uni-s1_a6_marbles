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


        Switch[] ret = new Switch[i];

        for (int j = 0; j < i; j++) {
            ret[j] = src[j];
        }

        return ret;
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
