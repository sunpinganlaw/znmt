package org.gxz.znrl.activiti.tools;

/**
 * Created by admin on 2014/7/15.
 */
public class Edge extends GraphElement {
    private Node src;
    private Node dest;

    public Node getSrc() {
        return src;
    }

    public void setSrc(Node src) {
        this.src = src;
    }

    public Node getDest() {
        return dest;
    }

    public void setDest(Node dest) {
        this.dest = dest;
    }
}
