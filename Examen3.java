/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examen3;

import java.util.LinkedList;

public class Examen3 {

    private static class regla {

        int f;
        int c;

        public regla(int i, int j) {
            f = i;
            c = j;
        }
    }

    private static int cantP = 0;
    private static int cantVA = 0;

    private static void mostrar(int[][] m) {
        for (int[] fila : m) {
            System.out.print("[");
            for (int i = 0; i < fila.length; i++) {
                System.out.printf("%3d", fila[i]);
            }
            System.out.println("]");
        }
        System.out.println("");
    }

    private static boolean posV(int[][] m, int i, int j) {
        return (i >= 0 && i < m.length && j >= 0 && j < m[i].length && m[i][j] == 0);
    }

    private static LinkedList<regla> raR(int[][] m, int i, int j) {
        LinkedList<regla> l = new LinkedList<>();
        int[] mf = {0, -1, -1, -1, 0, 1, 1, 1};
        int[] mc = {-1, -1, 0, 1, 1, 1, 0, -1};
        for (int k = 0; k < 8; k++) {
            int nf = i + mf[k];
            int nc = j + mc[k];
            if (posV(m, nf, nc)) {
                l.add(new regla(nf, nc));
            }
        }
        return l;
    }

    private static regla rPE(LinkedList<regla> l) {
        return l.removeFirst();
    }

    private static regla rM(LinkedList<regla> l) {
        int pos = 0;
        if (l.size() > 1) {
            pos = l.size() / 2;
        }
        return l.remove(pos);
    }

    private static regla rMD(LinkedList<regla> l, int ifin, int jfin) {
        int posMejor = 0;
        double distMenor = Double.MAX_VALUE;
        for (int i = 0; i < l.size(); i++) {
            regla r = l.get(i);
            double dist = Math.sqrt(Math.pow(r.f - ifin, 2) + Math.pow(r.c - jfin, 2));
            if (dist < distMenor) {
                distMenor = dist;
                posMejor = i;
            }
        }
        return l.remove(posMejor);
    }

    private static regla rMM(LinkedList<regla> l, int[][] m) {
        int minM = Integer.MAX_VALUE;
        regla mR = null;
        for (regla r : l) {
            int mP = raR(m, r.f, r.c).size();
            if (mP < minM) {
                minM = mP;
                mR = r;
            }
        }
        l.remove(mR);
        return mR;
    }

    private static boolean movRey(int[][] m, int i, int j, int ifin, int jfin, int paso, int hr) {
        cantP++;
        m[i][j] = paso;
        if (i >= ifin && j >= jfin) {
            mostrar(m);
            return true;
        }
        LinkedList<regla> l = raR(m, i, j);
        while (!l.isEmpty()) {
            regla r;
            switch (hr) {
                case 1:
                    r = rPE(l);
                    break;
                case 2:
                    r = rM(l);
                    break;
                case 3:
                    r = rMD(l, ifin, jfin);
                    break;
                case 4:
                    r = rMM(l, m);
                    break;
                default:
                    throw new AssertionError();
            }
            if (movRey(m, r.f, r.c, ifin, jfin, paso + 1, hr)) {
                return true;
            }
            cantVA++;
            m[r.f][r.c] = 0;
        }
        return false;
    }
    
    
    private static LinkedList<regla> raC(int[][] m, int i, int j) {
        LinkedList<regla> l = new LinkedList<>();
        int[] mfc = {1, -1, -2, -2, -1, 1, 2, 2};
        int[] mcc = {-2, -2, -1, 1, 2, 2, 1, -1};

        for (int k = 0; k < 8; k++) {
            int nf = i + mfc[k];
            int nc = j + mcc[k];
            if (posV(m, nf, nc)) {
                l.add(new regla(nf, nc));
            }
        }

        return l;
    }
    
    private static regla rMMC(LinkedList<regla> l, int[][] m) {
        int minM = Integer.MAX_VALUE;
        regla mR = null;
        for (regla r : l) {
            int mP = raC(m, r.f, r.c).size();
            if (mP < minM) {
                minM = mP;
                mR = r;
            }
        }
        l.remove(mR);
        return mR;
    }
    
    private static boolean movCaballo(int[][] m, int i, int j, int paso, int hr) {
        cantP++;
        if (paso > m.length * m[0].length) {
            mostrar(m);
            return true;
        }
        LinkedList<regla> l = raC(m, i, j);
        while (!l.isEmpty()) {
            regla r;
            switch (hr) {
                case 1:
                    r = rPE(l);
                    break;
                case 2:
                    r = rM(l);
                    break;
                case 3:
                    r = rMMC(l, m);
                    break;
                default:
                    throw new AssertionError();
            }
            m[r.f][r.c] = paso;
            if (movCaballo(m, r.f, r.c, paso + 1, hr)) {
                return true;
            }
            cantVA++;
            m[r.f][r.c] = 0;
        }
        return false;
    }
    
    
    
    
    private static boolean posVR(int[][] m, int i, int j) {
        return (i >= 0 && i < m.length && j >= 0 && j < m[i].length);
    }

    private static boolean posD(int[][] m, int i, int j) {
        //1
        int f = i;
        int c = j - 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c--;
        }
        // 2
        f = i - 1;
        c = j - 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c--;
            f--;
        }
        // 3
        f = i - 1;
        c = j;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            f--;
        }
        // 4
        f = i - 1;
        c = j + 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c++;
            f--;
        }
        // 5
        f = i;
        c = j + 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c++;
        }
        //6
        f = i + 1;
        c = j + 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c++;
            f++;
        }
        // 7
        f = i + 1;
        c = j;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            f++;
        }
        // 8
        f = i + 1;
        c = j - 1;
        while (posVR(m, f, c)) {
            if (m[f][c] != 0) {
                return false;
            }
            c--;
            f++;
        }

        return true;
    }

    private static LinkedList<regla> reglasAplicablesReina(int[][] m, int i) {
        LinkedList<regla> l = new LinkedList<>();
        for (int j = 0; j < m[i].length; j++) {
            if (posD(m, i, j)) {
                l.add(new regla(i, j));
            }
        }
        return l;
    }

    public static int contarConflictos(int[][] m, int i, int j) {
        int conf = 0;
        for (int k = 0; k < m.length; k++) {
            if (m[k][j] != 0) {
                conf++;
            }
            if (i - j + k >= 0 && i - j + k < m.length && m[i - j + k][k] != 0) {
                conf++;
            }
            if (i + j - k >= 0 && i + j - k < m.length && m[i + j - k][k] != 0) {
                conf++;
            }
        }
        return conf;
    }

    public static regla reglaMenorConflicto(LinkedList<regla> l1, int[][] m) {
        regla mR = null;
        int minConf = Integer.MAX_VALUE;
        int pos = 0;
        for (int i = 0; i < l1.size(); i++) {
            regla regla = l1.get(i);
            int conflictos = contarConflictos(m, regla.f, regla.c);
            if (conflictos < minConf) {
                minConf = conflictos;
                mR = regla;
                pos = i;
            }
        }
        return l1.remove(pos);
    }

    public static regla reglaPorRestriccion(LinkedList<regla> l1, int[][] m) {
        regla mR = null;
        int maxRestr = -1;
        int pos = 0;
        for (int i = 0; i < l1.size(); i++) {
            regla regla = l1.get(i);
            int restr = conRestr(m, regla.f, regla.c);
            if (restr > maxRestr) {
                maxRestr = restr;
                mR = regla;
                pos = i;
            }
        }
        return l1.remove(pos);
    }

    public static int conRestr(int[][] m, int i, int j) {
        int restricciones = 0;
        for (int k = i + 1; k < m.length; k++) {
            if (!posD(m, k, j)) {
                restricciones++;
            }
            if (!posD(m, k, j - (i - k))) {
                restricciones++;
            }
            if (!posD(m, k, j + (i - k))) {
                restricciones++;
            }
        }
        return restricciones;
    }

    private static boolean nReinas(int[][] m, int fil, int hr) {
        cantP++;
        if (fil > m.length) {
            mostrar(m);
            return true;
        }
        LinkedList<regla> lista = reglasAplicablesReina(m, fil - 1);
        while (!lista.isEmpty()) {
            regla R;
            switch (hr) {
                case 1:
                    R = rPE(lista);
                    break;
                case 2:
                    R = rM(lista);
                    break;
                case 3:
                    R = reglaMenorConflicto(lista, m);
                    break;
                case 4:
                    R = reglaPorRestriccion(lista, m);
                    break;
                default:
                    throw new AssertionError();
            }
            m[R.f][R.c] = fil;
            if (nReinas(m, fil + 1, hr)) {
                return true;
            }
            cantVA++;
            m[R.f][R.c] = 0;
        }
        return false;
    }
    
    
    
    
    
    
    private static boolean enFila(int[][] m, int i, int valor) {
        for (int j = 0; j < m[i].length; j++) {
            if (m[i][j] == valor) {
                return true;
            }
        }
        return false;
    }

    private static boolean enColumna(int[][] m, int j, int valor) {
        for(int i = 0; i < m.length; i++) {
            if (m[i][j] == valor) {
                return true;
            }
        }
        return false;
    }

    private static boolean enRegion(int[][] m, int i, int j, int valor) {
        int nFil = (int) Math.sqrt(m.length);
        int nCol = (int) Math.sqrt(m[i].length);
        int iRegion = (i / nFil) * nFil;
        int jRegion = (j / nCol) * nCol;
        for (int a = iRegion; a < iRegion + nFil; a++) {
            for (int b = jRegion; b < jRegion + nCol; b++) {
                if (m[a][b] == valor) {
                    return true;
                }
            }
        }
        return false;
    }

    private static LinkedList<Integer> raS(int[][] m, int i, int j) {
        LinkedList<Integer> l = new LinkedList<>();
        for (int valor = 1; valor <= m.length; valor++) {
            if (!enFila(m, i, valor) && !enColumna(m, j, valor) && !enRegion(m, i, j, valor)) {
                l.add(valor);
            }
        }

        return l;
    }

    private static boolean Sudoku(int[][] m, int i, int j) {
        cantP++;
        if (i >= m.length) {
            mostrar(m);
            return true;
        }
        if (j >= m[i].length) {
            return Sudoku(m, i + 1, 0);
        }
        if (m[i][j] != 0) {
            return Sudoku(m, i, j + 1);
        }
        LinkedList<Integer> lista = raS(m, i, j);
        while (!lista.isEmpty()) {
            int valor = lista.removeFirst();
            m[i][j] = valor;
            if (Sudoku(m, i, j + 1)) {
                return true;
            }
            cantVA++;
            m[i][j] = 0;
        }
        return false;
    }
    

    public static void main(String[] args) {
        int n = 6;
        int[][] tablero = new int[n][n];
        mostrar(tablero);

        System.out.println("mov rey sin h");
        if(movRey(tablero, 0, 0, n-1, n-1, 1, 1)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("mov rey con h medios");
        if(movRey(tablero, 0, 0, n-1, n-1, 1, 2)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("mov rey con h menor dist");
        if(movRey(tablero, 0, 0, n-1, n-1, 1, 3)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("mov rey con h menor mov");
        if(movRey(tablero, 0, 0, n-1, n-1, 1, 4)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        //*/
        
        System.out.println("mov caballo sin h ");
        if(movCaballo(tablero, 0, 0, 1, 1)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("mov caballo con h medios");
        if(movCaballo(tablero, 0, 0, 1, 2)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("mov caballo con h menor mov");
        if(movCaballo(tablero, 0, 0, 1, 3)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        //*/
        
        System.out.println("N Reinas sin h");
        if(nReinas(tablero, 1, 1)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("N Reinas con h de los medios");
        if(nReinas(tablero, 1, 2)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("N Reinas con h menor conflicto");
        if(nReinas(tablero, 1, 3)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        System.out.println("N Reinas con h max Restricción");
        if(nReinas(tablero, 1, 4)) {
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("no tiene solucion");
        }
        System.out.println("");
        cantP = 0;
        cantVA = 0;
        tablero = new int[n][n];
        
        //*/
        
        int[][] m = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        mostrar(m);

        System.out.println("Sudoku SIN H");
        
        if (Sudoku(m, 0, 0)){
            System.out.println("tiene solucion");
            System.out.println("cant p: " + cantP);
            System.out.println("cant VA: " + cantVA);
        } else {
            System.out.println("nO tiene solución");
        }
        //*/
    }
}

