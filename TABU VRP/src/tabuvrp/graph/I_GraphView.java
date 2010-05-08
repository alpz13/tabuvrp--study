/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabuvrp.graph;

/**
 *
 * @author lmolr
 */
public interface I_GraphView<N, E> {


    public N getNode(int n);

    
    public E getEdge(int n1, int n2);


    public int getNodeCount();

    
}
