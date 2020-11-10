/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mochila;

import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author JANUS
 */
public class Calculo {

    int Valmonedas[];
    int Matriz[][];
    String razon[][];

    public int[][] Calcular(ArrayList<JTextField> p, int vueltas) {
        Valmonedas = new int[p.size()];
        Matriz = new int[p.size() + 1][vueltas + 1];
        razon = new String[p.size() + 1][vueltas + 1];
        Llenarmatrices(p);
        insercion();
        crearMatriz(vueltas);
        return Matriz;
    }

    public void insercion() {
        int i, j;
        int aux, aux2;

        for (i = 1; i < Valmonedas.length; i++) {

            aux = Valmonedas[i];
            j = i - 1;
            while ((j > -1) && (aux < Valmonedas[j])) {
                Valmonedas[j + 1] = Valmonedas[j];
                j = j - 1;
            }
            Valmonedas[j + 1] = aux;
        }
    }

    private void Llenarmatrices(ArrayList<JTextField> p) {
        for (int i = 0; i < p.size(); i++) {
            Valmonedas[i] = Integer.parseInt(p.get(i).getText());
        }
    }

    private void crearMatriz(int limite) {

        for (int l = 0; l < Matriz[0].length; l++) {
            Matriz[0][l] = 50000;
            razon[0][l] = "Infinito";
        }

        for (int i = 1; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[0].length; j++) {
                if (j == 0) {
                    Matriz[i][j] = 0;
                    razon[i][j] = "0:0";
                } else {
                    Max(i, j);
                }
            }
        }
        imprimirmatriz(Matriz);
        imprimirmatriz2(razon);
    }

    private void imprimirmatriz(int[][] Mat) {
        for (int i = 0; i < Mat.length; i++) {
            for (int j = 0; j < Mat[0].length; j++) {
                System.out.print(Mat[i][j] + "    ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void imprimirmatriz2(String[][] Mat) {
        for (int i = 0; i < Mat.length; i++) {
            for (int j = 0; j < Mat[0].length; j++) {
                System.out.print(Mat[i][j] + "    ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void Max(int i, int j) {
        int valor = Valmonedas[0];
        int valori = Valmonedas[i - 1];
        if (i == 1 && j < valori) {
            Matriz[i][j] = 50000;
            razon[i][j] = "Infinito";

        } else if (i == 1) {
            Matriz[i][j] = 1 + Matriz[1][j - valor];
            if (Matriz[i][j] >= 50000) {
                razon[i][j] = "Infinito";
            } else {
                for (int k = 0; k < Matriz[i][j]; k++) {
                    if (k == 0) {
                        razon[i][j] = "1:" + valori;
                    } else {
                        razon[i][j] = razon[i][j] + " + " + " 1:" + valori;
                    }

                }

            }

        } else {

            if (j < valori) {
                Matriz[i][j] = Matriz[i - 1][j];
                razon[i][j] = razon[i - 1][j];
            } else {
                int superior = Matriz[i - 1][j];
                int otro = 1 + Matriz[i][j - valori];
                if (superior >= otro) {
                    Matriz[i][j] = otro;
                    if (Matriz[i][j] >= 50000) {
                        razon[i][j] = "Infinito";
                    } else {
                        razon[i][j] = razon[i][j - valori] + " + 1:" + valori;
                    }
                } else {
                    Matriz[i][j] = superior;
                    if (Matriz[i][j] >= 50000) {
                        razon[i][j] = "Infinito";
                    } else {
                        if (j % valori == 0) {
                            razon[i][j] = razon[i - 1][j] + " + 1:" + Valmonedas[i - 2];
                        } else {
                            razon[i][j] = razon[i - 1][j];
                        }

                    }

                }
            }
        }
    }

    public String[][] getStrings() {
        return razon;
    }

    public String[] getmon() {
        String arra[] = new String[Valmonedas.length];
        for (int i = 0; i < Valmonedas.length; i++) {
            arra[i] = Integer.toString(Valmonedas[i]);
        }

        return arra;
    }

}
