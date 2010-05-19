package tabuvrp.vrp;


import java.io.FileReader;
import java.io.BufferedReader;

public class VRPFactory {

    public VRP newVRPFromFile(String path) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(path));
        VRP vrp = null;

        return vrp;
    }
}
