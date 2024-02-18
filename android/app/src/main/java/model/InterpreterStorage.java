package model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.speech.tts.TextToSpeech;

import androidx.fragment.app.FragmentActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import data.SensorSample;
import data.SensorsSample;

public class InterpreterStorage implements TextToSpeech.OnInitListener {
    private static final int N_SAMPLES = 200;
    public static String status;
    static Interpreter interpreter;
    private static InterpreterStorage instance = null;
    private final String[] labels = {"HAND", "STILL"};
    private TextToSpeech textToSpeech;
    private float[] results;
    //private static InterpreterStorage instance = new InterpreterStorage();
    private ArrayList<SensorSample> a = new ArrayList<SensorSample>();
    private ArrayList<SensorSample> g = new ArrayList<SensorSample>();
    private ArrayList<SensorSample> m = new ArrayList<SensorSample>();
    private ArrayList<SensorSample> o = new ArrayList<SensorSample>();
    public static InterpreterStorage getInstance() {
        if (instance == null)
            instance = new InterpreterStorage();
        return instance;
    }
    public void setContext(Context _ctx) {
        System.out.println("interpreter...");
        try {
            interpreter = new Interpreter(loadModelFile(_ctx), null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private MappedByteBuffer loadModelFile(Context _ctx) throws IOException {
        AssetFileDescriptor assetFileDescriptor = _ctx.getAssets().openFd("linear.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long len = assetFileDescriptor.getLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, len);
    }
    public Interpreter getInterpreter() {
        return interpreter;
    }
    public void setCtx(FragmentActivity ctx) {
        textToSpeech = new TextToSpeech(ctx, this);
        textToSpeech.setLanguage(Locale.US);
    }
    public ArrayList<SensorSample> getA() {
        return a;
    }
    public void setA(ArrayList<SensorSample> a) {
        this.a = a;
    }
    public ArrayList<SensorSample> getG() {
        return g;
    }
    public void setG(ArrayList<SensorSample> g) {
        this.g = g;
    }
    public ArrayList<SensorSample> getM() {
        return m;
    }
    public void setM(ArrayList<SensorSample> m) {
        this.m = m;
    }
    public ArrayList<SensorSample> getO() {
        return o;
    }
    public void setO(ArrayList<SensorSample> o) {
        this.o = o;
    }
    public void predict() {
        float[][] ff = new float[N_SAMPLES][6];
        float[][][] input = new float[1][0][0];
        float[][] output = new float[1][];
        System.out.println("a..." + a.size());
        System.out.println("g..." + g.size());
        System.out.println("o..." + o.size());
        if ((a.size() < N_SAMPLES) || (o.size() < N_SAMPLES))
            return;
        //for (int j = N_SAMPLES; j-- > 0; ) {
        for (int j = 0; j < N_SAMPLES; ++j) {
            ff[j][0] = a.get(j).getX();
            ff[j][1] = a.get(j).getY();
            ff[j][2] = a.get(j).getZ();
            ff[j][3] = o.get(j).getX();
            ff[j][4] = o.get(j).getY();
            ff[j][5] = o.get(j).getZ();
            //ff[j][6] = astd.get(j).floatValue();
//
//                ff[j][0]=axstd.get(j).floatValue();
//                ff[j][1]=aystd.get(j).floatValue();
//                ff[j][2]=azstd.get(j).floatValue();
        }
        double[][] arr1 = {{-0.2861071, -0.2861071, -0.2657564, -0.2657564, -0.24301147, -0.24301147},
                {-0.21787235, -0.21787235, -0.24061728, -0.24061728, -0.25857377, -0.25857377},
                {-0.21787235, -0.21787235, -0.24061728, -0.24061728, -0.25857377, -0.25857377},
                {-0.25378537, -0.25378537, -0.2657564, -0.2657564, -0.2645593, -0.2645593},
                {-0.2705448, -0.2705448, -0.25019407, -0.25019407, -0.22864626, -0.22864626},
                {-0.22744916, -0.22744916, -0.24540567, -0.24540567, -0.29209262, -0.29209262},
                {-0.29209262, -0.29209262, -0.30885202, -0.30885202, -0.35314476, -0.35314476},
                {-0.39863458, -0.39863458, -0.40940848, -0.40940848, -0.37469256, -0.37469256},
                {-0.32920274, -0.32920274, -0.2837129, -0.2837129, -0.24301147, -0.24301147},
                {-0.24301147, -0.24301147, 8.443152, 8.443152, 8.456321, 8.456321},
                {8.455123, 8.455123, 8.455123, 8.455123, 8.471883, 8.471883},
                {8.49343, 8.49343, 8.507795, 8.507795, 8.4826565, 8.4826565},
                {8.503007, 8.503007, 8.4826565, 8.4826565, 8.532935, 8.532935},
                {8.53892, 8.53892, 8.524555, 8.524555, 8.53054, 8.53054},
                {8.532935, 8.532935, 8.532935, 8.532935, 8.523358, 8.523358},
                {8.514978, 8.514978, 8.508993, 8.508993, 8.485051, 8.485051},
                {8.50181, 8.50181, 8.512584, 8.512584, 8.4826565, 8.4826565},
                {8.461109, 8.461109, 8.461109, 8.461109, 4.969166, 4.969166},
                {4.981137, 4.981137, 4.9823337, 4.9823337, 5.0038815, 5.0038815},
                {5.019444, 5.019444, 5.055357, 5.055357, 5.09127, 5.09127},
                {5.1343656, 5.1343656, 5.1690817, 5.1690817, 5.1247888, 5.1247888},
                {5.08289, 5.08289, 5.0781016, 5.0781016, 5.057751, 5.057751},
                {5.0026846, 5.0026846, 4.9667716, 4.9667716, 4.9667716, 4.9667716},
                {4.936844, 4.936844, 4.9583917, 4.9583917, 4.9643774, 4.9643774},
                {4.9667716, 4.9667716, 4.9787426, 4.9787426, 5.011064, 5.011064},
                {5.021838, 5.021838, 4.984728, 4.984728, 4.984728, 4.984728},
                {-1.4639611, -1.4639611, -1.4641776, -1.4641776, -1.4641776, -1.4641776},
                {-1.4643911, -1.4643911, -1.4643911, -1.4643911, -1.4645777, -1.4645777},
                {-1.4645777, -1.4645777, -1.4646608, -1.4646608, -1.4647322, -1.4647322},
                {-1.4647322, -1.4647322, -1.4647626, -1.4647626, -1.4647626, -1.4647626},
                {-1.4647956, -1.4647956, -1.4647956, -1.4647956, -1.4647417, -1.4647417},
                {-1.4647417, -1.4647417, -1.4647417, -1.4647417, -1.4646329, -1.4646329},
                {-1.4644486, -1.4644486, -1.4644486, -1.4644486, -1.464252, -1.464252},
                {-1.4642525, -1.4642525, -1.4643819, -1.4643819, -1.464608, -1.464608},
                {-1.464608, -1.464608, -1.0432259, -1.0432259, -1.0430585, -1.0430585},
                {-1.0430585, -1.0430585, -1.0428566, -1.0428566, -1.0428566, -1.0428566},
                {-1.0426148, -1.0426148, -1.0426148, -1.0426148, -1.0423478, -1.0423478},
                {-1.0421093, -1.0421093, -1.0421093, -1.0421093, -1.0419519, -1.0419519},
                {-1.0419519, -1.0419519, -1.0419023, -1.0419023, -1.0419023, -1.0419023},
                {-1.0419568, -1.0419568, -1.0419568, -1.0419568, -1.0419568, -1.0419568},
                {-1.0420762, -1.0420762, -1.0421771, -1.0421771, -1.0421771, -1.0421771},
                {-1.0422447, -1.0422447, -1.0422782, -1.0422782, -1.0422823, -1.0422823},
                {-1.0422939, -1.0422939, -1.0422939, -1.0422939, 0.06037596, 0.06037596},
                {0.06022372, 0.06022372, 0.06022372, 0.06022372, 0.0600453, 0.0600453},
                {0.0600453, 0.0600453, 0.05990307, 0.05990307, 0.05990307, 0.05990307},
                {0.05986235, 0.05986235, 0.0598739, 0.0598739, 0.0598739, 0.0598739},
                {0.05991151, 0.05991151, 0.05991151, 0.05991151, 0.05990455, 0.05990455},
                {0.05990455, 0.05990455, 0.05993611, 0.05993611, 0.05993611, 0.05993611},
                {0.05993611, 0.05993611, 0.06008596, 0.06008596, 0.06039905, 0.06039905},
                {0.06039905, 0.06039905, 0.06075015, 0.06075015, 0.0609496, 0.0609496},
                {0.06095825, 0.06095825, 0.06075506, 0.06075506, 0.06075506, 0.06075506}};
        //float[][] arr2 = d2fConverter(arr1);
        input[0] = ff;
        //input[0] = arr2;
        //output[0] = new float[] {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        output[0] = new float[]{0.0f, 0.0f, 0.0f};//, 0.0f, 0.0f};
        //System.out.println("input: "  + Arrays.toString(output));
        interpreter.run(input, output);
        //System.out.println("results: "  + Arrays.toString(output));
        results = output[0];
        for (int i = 0; i < output[0].length; i++) {
            System.out.println("result: " + output[0][i]);
        }
        System.out.println("result: -------------------------------");
        System.out.println("result: " + Arrays.deepToString(ff) + "result: " + Arrays.deepToString(output) + "size:" + a.size());

    }
    @Override
    public void onInit(int status) {
        System.out.println("speak...");

    }
    public void addSample(SensorsSample sample) {
        a.add(sample.getA());
        if (a.size() > N_SAMPLES) {
            a.remove(0);
        }
        g.add(sample.getG());
        if (g.size() > N_SAMPLES) {
            g.remove(0);
        }
        m.add(sample.getM());
        if (m.size() > N_SAMPLES) {
            m.remove(0);
        }
        o.add(sample.getO());
        if (o.size() > N_SAMPLES) {
            o.remove(0);
        }

    }
    public double calculateSDX(List<SensorSample> list) {
        ArrayList<Float> ax = new ArrayList<Float>();
        for (int i = 0; i < list.size(); i++) {
            ax.add(list.get(i).getX());
        }
        Float[] arr = new Float[ax.size()];
        arr = ax.toArray(arr);
        double sum = 0.0, standardDeviation = 0.0;
        int length = arr.length;
        for (float num : arr) {
            sum += num;
        }
        double mean = sum / length;
        for (double num : arr) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
    public double calculateSDY(List<SensorSample> list) {
        ArrayList<Float> ay = new ArrayList<Float>();
        for (int i = 0; i < list.size(); i++) {
            ay.add(list.get(i).getY());
        }
        Float[] arr = new Float[ay.size()];
        arr = ay.toArray(arr);
        double sum = 0.0, standardDeviation = 0.0;
        int length = arr.length;
        for (float num : arr) {
            sum += num;
        }
        double mean = sum / length;
        for (double num : arr) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
    public double calculateSDZ(List<SensorSample> list) {
        ArrayList<Float> az = new ArrayList<Float>();
        for (int i = 0; i < list.size(); i++) {
            az.add(list.get(i).getZ());
        }
        Float[] arr = new Float[az.size()];
        arr = az.toArray(arr);
        double sum = 0.0, standardDeviation = 0.0;
        int length = arr.length;
        for (float num : arr) {
            sum += num;
        }
        double mean = sum / length;
        for (double num : arr) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation / length);
    }
    public String getStatus() {
        float max = -1;
        int idx = -1;
        if (results == null || results.length == 0) {
            return "NONE";
        }
        for (int i = 0; i < results.length; i++) {
            if (results[i] > max) {
                idx = i;
                max = results[i];
            }
        }
        return labels[idx];
    }
    public float[][] d2fConverter(double[][] _tfIdf) {
        float[][] tfIdf = new float[20][6];
        for (int w = 0; 50 > w; w++) {
            for (int h = 0; 6 > h; h++) {
                //System.out.println((float) _tfIdf[w][h]);
                tfIdf[w][h] = (float) _tfIdf[w][h];
            }
        }
        return tfIdf;
    }
}
