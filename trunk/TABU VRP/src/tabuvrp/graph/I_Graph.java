/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabuvrp.graph;

/**
 *
 * @author lmolr
 */
public interface I_Graph<N, E> extends I_GraphView<N, E> {


    public void setNode(int n, N node);


    public void setEdge(int n1, int n2, E edge);


}
