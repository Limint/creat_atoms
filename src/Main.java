import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = br.readLine().trim().split(" ");
        String[] s2 = br.readLine().trim().split(" ");
        String[] s3 = br.readLine().trim().split(" ");
        Box box = new Box(s1,s2,s3);
        String s = br.readLine();
        box.print(box.getFilename());
        if(s != null) {
            String[] s4 = s.trim().split(" ");
            int num = Integer.parseInt(s4[0]);
            double step = Double.parseDouble(s4[1]);
            while (--num > 0) {
                box.lattice.resetLattice(new Double[]{box.lattice.a + step});
                box.reset();
                box.print(box.getFilename());
            }
        }
    }
}
