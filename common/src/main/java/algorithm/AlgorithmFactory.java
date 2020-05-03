package algorithm;

import algorithm.Serialize;
import codec.FastJsonSerialize;

public class AlgorithmFactory {


    public static Serialize getDefaultAlgorithm(){
        return new FastJsonSerialize();
    }

}
