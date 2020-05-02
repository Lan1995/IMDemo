package codec;

public class AlgorithmFactory {


    public static Serialize getDefaultAlgorithm(){
        return new FastJsonSerialize();
    }

}
