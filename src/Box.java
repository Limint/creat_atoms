import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

public class Box {
    public Lattice lattice;
    public RandomAtoms rand;
    public int x;
    public int y;
    public int z;
    public double xlo;
    public double xhi;
    public double ylo;
    public double yhi;
    public double zlo;
    public double zhi;
    public double xy;
    public double xz;
    public double yz;

    public Box(String[] s1,String[] s2,String[] s3) {
        lattice = new Lattice(s1);
        x = Integer.parseInt(s2[0]);
        y = Integer.parseInt(s2[1]);
        z = Integer.parseInt(s2[2]);
        int[] part = new int[s3.length];
        for (int i = 0; i < part.length; i++) {
            part[i] = Integer.parseInt(s3[i]);
        }
        rand = new RandomAtoms(x*y*z*lattice.basis.size(),part);
        xlo = 0.0;
        ylo = 0.0;
        zlo = 0.0;
        reset();
    }

    public void reset() {
        xhi = x*lattice.orient[0][0];
        yhi = y*lattice.orient[1][1];
        zhi = z*lattice.orient[2][2];
        xy = y*lattice.orient[1][0];
        xz = z*lattice.orient[2][0];
        yz = z*lattice.orient[2][1];
    }

    public void print(String filename) {
        StringBuilder out = new StringBuilder();
        out.append("converted from tim\r\n\n");
        out.append(String.format("%13d atoms\r\n",rand.Atoms));
        out.append(String.format("%13d atom types\r\n",rand.Partition.length));
        out.append(String.format("%10.6f %10.6f xlo xhi\r\n",xlo,xhi));
        out.append(String.format("%10.6f %10.6f ylo yhi\r\n",ylo,yhi));
        out.append(String.format("%10.6f %10.6f zlo zhi\r\n",zlo,zhi));

        if (!String.format("%.10f",xy).equals("0.0000000000") ||
                !String.format("%.10f",xz).equals("0.0000000000") ||
                !String.format("%.10f",yz).equals("0.0000000000") ) {
            out.append(String.format("%12.6f %12.6f %12.6f xy xz yz\r\n",xy,xz,yz));
        }

        out.append("\nAtoms\r\n\n");

        int n = 1;
        for (Double[] l : lattice.basis) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        double xs = (i+l[0])*lattice.orient[0][0]+(j+l[1])*lattice.orient[1][0]+(k+l[2])*lattice.orient[2][0];
                        double ys = (j+l[1])*lattice.orient[1][1]+(k+l[2])*lattice.orient[2][1];
                        double zs = (k+l[2])*lattice.orient[2][2];
                        out.append(String.format("%5d %5d %14.8f %14.8f %14.8f\r\n", n, rand.randomAtoms[n - 1], xs, ys, zs));
                        n++;
                    }
                }
            }
        }

        try {
            File f = new File(filename);
            FileOutputStream fop = new FileOutputStream(f);
            OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
            writer.append(out);
            writer.close();
            fop.close();
        } catch (IOException e) {
            System.out.println("exception");
        }
    }

    public String getFilename() {
        StringBuilder filename = new StringBuilder();
        filename.append("rand_");
        for (int i : rand.Partition) {filename.append(String.format("%d_",i));}
        filename.append(lattice.style.toString());
        filename.append("_");
        DecimalFormat format = new DecimalFormat("####.############");
        filename.append(format.format(lattice.a));
        filename.append(".dat");
        return filename.toString();
    }



}
