import java.util.Random;

public class NoiseGenerator{

    private double[] noise;
    private double[] rand;

    public NoiseGenerator(int size){
        noise = new double[size];
        rand = random(size);

        int n = size;

        double div = 0;

        while(n >= 1){
            double s = n / (double)size;
            div += s;
            for(int i = 0; i < size; i++){
                double sample = sample(i, n);
                noise[i] += s * sample;
            }
            n = n/2;

        }

        for(int i = 0; i < noise.length; i++){
            noise[i] = noise[i] / div;
        }
    }

    private double sample(int x, int pitch){
        double s = 0.0;
        int lower = x;
        while(lower%pitch > 0){
            lower--;
        }
        lower = (x / pitch) * pitch;
        int upper = lower + pitch;
        if(upper >= rand.length){
            upper = upper - rand.length;
        }
        upper = (lower + pitch) % rand.length;

        double blend = (double)(x - lower) / (double)pitch;

        s = (1.0 - blend) * rand[lower] + blend * rand[upper];
        
        return s;
    }

    private double[] random(int size){
        double[] rand = new double[size];
        for(int i = 0; i < size; i++){
            rand[i] = randDouble();
        }
        return rand;
    }

    private double randDouble(){
        return new Random().nextDouble();
    }

    public double[] getNoise(){
        return noise;
    }

    public double[] getRand(){
        return rand;
    }
}
