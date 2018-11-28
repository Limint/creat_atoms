import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class RandomAtoms {
    public int Atoms;
    public int[] Partition;
    public int[] AtomsPart;
    public int[] randomAtoms;
    private int max;

    public RandomAtoms(int atoms, int[] partition) {
        Atoms = atoms;
        Partition = partition;
        max = 0;
        init();
    }

    private void init() {
        AtomsPart = new int[Partition.length];
        randomAtoms = new int[Atoms];
        int sum = 0,sum1 = 0;
        for (int i : Partition) {sum += i;}
        for (int i = 0; i < Partition.length-1; i++) {
            int temp = Atoms*Partition[i];
            AtomsPart[i] = temp/sum + (temp%sum*2/sum);
            int sum2 = sum1;
            sum1 += AtomsPart[i];
            for (int j = sum2; j < sum1; j++) {
                randomAtoms[j] = i+1;
            }
            max = Math.max(max, AtomsPart[i]);
        }
        AtomsPart[AtomsPart.length-1] = Atoms - sum1;
        for (int i = sum1; i < Atoms; i++) {
            randomAtoms[i] = Partition.length;
        }
        random();
    }

    public void random() {
        Random rand = new Random();
        for (int i = Atoms-1; i >= max; i--) {
            int random = rand.nextInt(i);
            if (random != i && randomAtoms[random] != randomAtoms[i]) {
                int temp = randomAtoms[i];
                randomAtoms[i] = randomAtoms[random];
                randomAtoms[random] = temp;
            }
        }
    }

    public void reset(int atoms, int[] partition) {
        Atoms = atoms;
        Partition = partition;
        init();
    }

    public void printToFile(String filename) {
        try{
            File f = new File(filename);
            FileOutputStream fop = new FileOutputStream(f);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            for(int i : randomAtoms) {
                writer.append(String.format("%d\r\n",i));
            }
            writer.close();
            fop.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int atoms = Integer.parseInt(args[0]);
        int[] part = new int[Integer.parseInt(args[1])];
        for(int i = 0; i < part.length; i++) {
            part[i] = Integer.parseInt(args[i+2]);
        }
        RandomAtoms randomatoms = new RandomAtoms(atoms, part);
        int n = (args.length > Integer.parseInt(args[1])+2)? Integer.parseInt(args[args.length-1]) : 1;

        for (int i = 0; i < n; i++) {
            randomatoms.printToFile(String.format("randomatoms%d.dat",i));
            randomatoms.random();
        }
    }

}
