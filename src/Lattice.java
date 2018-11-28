import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.*;

enum Style {
    none, sc, bcc, fcc, hcp, diamon, sq, sq2,hex, custom
}

public class Lattice implements Serializable {
    public double a;
    public double b;
    public double c;
    public double alpha;
    public double beta;
    public double gamma;
    public double[][] orient = new double[3][3];
    public ArrayList<Double[]> basis = new ArrayList<Double[]>();
    public Style style;

    public Lattice() {
        a = 1.0;
        b = 1.0;
        c = 1.0;
        alpha = 90;
        beta = 90;
        gamma = 90;
        setVector();
    }

    private void setVector() {
        double[] A0 = {a,0.0,0.0};
        System.arraycopy(A0,0,orient[0],0,3);
        double xy = b*cos(toRadians(alpha));
        double yy = sqrt(pow(b,2.0)-pow(xy,2.0));
        double[] B0 = {xy,yy,0};
        System.arraycopy(B0,0,orient[1],0,3);
        double xz = c*cos(toRadians(beta));
        double yz = (b*c*cos(toRadians(alpha))-xy*xz)/yy;
        double zz = sqrt(pow(c,2.0)-pow(xz,2.0)-pow(yz,2.0));
        double[] C0 = {xz,yz,zz};
        System.arraycopy(C0,0,orient[2],0,3);

    }

    public Lattice(String[] s) {
        style = Style.valueOf(s[0]);
        switch (style) {
            case sc:
                a = Double.parseDouble(s[1]);
                b = a;
                c = a;
                alpha = 90;
                beta = 90;
                gamma = 90;
                { Double[][] base1 = {{0.0,0.0,0.0}};
                basis.addAll(Arrays.asList(base1));
                }
                break;
            case bcc:
                a = Double.parseDouble(s[1]);
                b = a;
                c = a;
                alpha = 90;
                beta = 90;
                gamma = 90;
                { Double[][] base1 = {
                        {0.0,0.0,0.0},
                        {0.5,0.5,0.5}};
                basis.addAll(Arrays.asList(base1));
                }
                break;
            case fcc:
                a = Double.parseDouble(s[1]);
                b = a;
                c = a;
                alpha = 90;
                beta = 90;
                gamma = 90;
                { Double[][] base1 = {
                        {0.0, 0.0, 0.0},
                        {0.0, 0.5, 0.5},
                        {0.5, 0.0, 0.5},
                        {0.5, 0.5, 0.0}};
                basis.addAll(Arrays.asList(base1));
                }
                break;
        }
        setVector();
    }

    public void resetLattice(Double[] arg) {
        switch(style) {
            case sc:
            case bcc:
            case fcc:
                a = arg[0];
                b = a;
                c = a;
                break;
        }
        setVector();
    }

    public static void main(String[] args) {


    }
}
