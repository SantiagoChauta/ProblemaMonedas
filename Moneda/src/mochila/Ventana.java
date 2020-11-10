package mochila;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Ventana extends JFrame implements ActionListener {

    JFrame v = new JFrame();
    JPanel panel1, panel2;
    JLabel lvalorvueltas, lnummonedas, lmonedas;
    JTextField tfvalorvueltas, tfnummonedas;
    JButton bCrearcampos, bCalcular, bAtras;
    ArrayList<JTextField> arraymonedas;
    ArrayList<JLabel> Arraysol, arrayval, tablita, arraycolval;
    Calculo c;
    JScrollPane scroll;
    int matriz[][], inres;

    Ventana() {
        iniciarComponentes();
        CrearVentana();
    }

    private void iniciarComponentes() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel1.setLayout(null);
        panel2.setLayout(null);
        scroll = new JScrollPane();
        lvalorvueltas = new JLabel("Ingrese el numero de vueltas:");
        lnummonedas = new JLabel("Ingrese el numero de monedas:");
        lmonedas = new JLabel("Ingrese los valores de las monedas");
        tfvalorvueltas = new JTextField();
        tfnummonedas = new JTextField();
        bCrearcampos = new JButton("Crear Campos");
        bCalcular = new JButton("Calcular");
        bAtras = new JButton("Volver");
        bCalcular.addActionListener(this);
        bCrearcampos.addActionListener(this);
        bAtras.addActionListener(this);
        arraymonedas = new ArrayList();
        Arraysol = new ArrayList();
        arrayval = new ArrayList();
        tablita = new ArrayList();
        arraycolval = new ArrayList();
        lvalorvueltas.setBounds(20, 20, 180, 20);
        tfvalorvueltas.setBounds(200, 20, 20, 20);
        lnummonedas.setBounds(20, 60, 180, 20);
        tfnummonedas.setBounds(200, 60, 20, 20);
        bCrearcampos.setBounds(20, 100, 120, 20);
        lmonedas.setBounds(20, 140, 200, 20);
        bCalcular.setBounds(20, 280, 100, 20);
        c = new Calculo();
        panel1.add(lvalorvueltas);
        panel1.add(tfvalorvueltas);
        panel1.add(lnummonedas);
        panel1.add(tfnummonedas);
        panel1.add(bCrearcampos);
    }

    private void CrearVentana() {
        v = new JFrame();
        v.setTitle("Mochila");
        v.setSize(250, 180);
        v.setLocationRelativeTo(null);
        v.setResizable(false);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.add(panel1);
        v.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        int peso = Integer.parseInt(tfvalorvueltas.getText());
        int numarticulos = Integer.parseInt(tfnummonedas.getText());
        boolean bopeso = false;
        boolean bonumarticulos = false;
        if (peso > 0 && peso <= 30) {
            bopeso = true;
        } else {
            bopeso = false;
            if (peso <= 0) {
                JOptionPane.showMessageDialog(null, "El peso minimo es 1");
            } else {
                JOptionPane.showMessageDialog(null, "El peso maximo es 30");
            }
        }
        if (numarticulos > 0 && numarticulos < 9) {
            bonumarticulos = true;
        } else {
            bonumarticulos = false;
            if (numarticulos <= 0) {
                JOptionPane.showMessageDialog(null, "Minimo ingrese 1 articulo");
            } else {
                JOptionPane.showMessageDialog(null, "Maximo ingrese 8 articulos");
            }
        }
        if (bopeso && bonumarticulos) {
            if (ae.getSource().equals(bCrearcampos)) {
                añadircampos(numarticulos);
            }
            if (ae.getSource().equals(bCalcular)) {
                matriz = c.Calcular(arraymonedas, peso);
                if (infito(matriz)) {
                    JOptionPane.showMessageDialog(null, "No se puede dar el cambio");
                    v.setVisible(false);
                    remover();
                    v.setVisible(true);
                } else {
                    DibujarMatriz();
                    v.setVisible(false);
                    v.remove(panel1);
                    v.setSize(500, 500);
                    v.setLocationRelativeTo(null);
                    v.add(scroll);
                    v.setVisible(true);
                }
            }
        }
        if (ae.getSource().equals(bAtras)) {
            remover();
            cambioPaneles(panel2, panel1, 250, 180);
        }

    }

    private void remover() {
        panel1.removeAll();
        panel2.removeAll();
        tfnummonedas.setText("");
        tfvalorvueltas.setText("");
        panel1.add(lvalorvueltas);
        panel1.add(tfvalorvueltas);
        panel1.add(lnummonedas);
        panel1.add(tfnummonedas);
        panel1.add(bCrearcampos);
        arraymonedas.clear();
        Arraysol.clear();
    }

    private void añadircampos(int numarticulos) {
        v.setVisible(false);

        for (int i = 0; i < numarticulos; i++) {
            arraymonedas.add(new JTextField());
            arraymonedas.get(i).setBounds(20 + (i * 35), 180, 30, 20);
            panel1.add(arraymonedas.get(i));
        }
        panel1.add(lmonedas);
        panel1.add(bCalcular);
        v.setSize(310, 340);
        v.setVisible(true);

    }

    private void DibujarMatriz() {
        int index = 0;
        String valor[][] = c.getStrings();

        int cadena = valor[0][0].length();
        int a = matriz.length - 1, b = matriz[0].length - 1;
        for (int k = 0; k < valor.length; k++) {
            for (int l = 0; l < valor[0].length; l++) {
                int aux = valor[k][l].length();
                if (aux > cadena) {
                    cadena = aux;
                    a = k;
                    b = l;
                }
            }
        }
        String espacio[] = valor[a][b].split(":");
        String respuesta[] = valor[valor.length - 1][valor[0].length - 1].split(" ");
        String tabla[] = sacarRespuesta(respuesta);
        for (int k = 0; k < valor.length; k++) {
            for (int l = 0; l < valor[0].length; l++) {
                arrayval.add(new JLabel(valor[k][l]));
                arrayval.get(index).setBounds(100 + (l * (60 + 20 * espacio.length)), 80 + (60 * k), 30 * espacio.length, 30);
                panel2.add(arrayval.get(index));
                index++;

            }
        }
        index = 0;
        String[] valmon = c.getmon();
        arraycolval.add(new JLabel("Col valores"));
        arraycolval.get(index).setBounds(20, 70 + (60 * -1), 50, 40);
        panel2.add(arraycolval.get(index));
        index++;
        for (int i = 0; i < valmon.length; i++) {
            arraycolval.add(new JLabel("(valor " + valmon[i] + ")"));
            arraycolval.get(index).setBounds(20, 120 + (70 * (i)), 60, 40);
            panel2.add(arraycolval.get(index));
            index++;
        }
        index = 0;
        for (int i = -1; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (i == -1) {
                    Arraysol.add(new JLabel(Integer.toString(j)));

                } else {
                    if (matriz[i][j] >= 50000) {
                        Arraysol.add(new JLabel(""));
                    } else {
                        Arraysol.add(new JLabel(Integer.toString(matriz[i][j])));
                    }

                }
                Arraysol.get(index).setBounds(100 + (j * (60 + 20 * espacio.length)), 70 + (60 * i), 50, 20);
                panel2.add(Arraysol.get(index));
                index++;
            }
        }
        index = 0;
        for (int i = 0; i < inres; i++) {
            tablita.add(new JLabel(tabla[i]));
            tablita.get(index).setBounds(40, 120 + (55 * (matriz.length) + (i * 30)), 150, 20);
            panel2.add(tablita.get(index));
            index++;
        }

        //bAtras.setBounds(40, 30 + (50 * (matriz.length + inres*2)), 100, 20);
        panel2.setPreferredSize(new Dimension(matriz[0].length * (60 + 25 * espacio.length) + 30, (matriz.length + tabla.length) * 60));
        //panel2.add(bAtras);
        scroll.setBounds(5, 10, 100, 100);
        scroll.setViewportView(panel2);
    }

    private void cambioPaneles(JPanel panelviejo, JPanel panelnuevo, int x, int y) {
        v.setVisible(false);
        v.remove(panelviejo);
        v.add(panelnuevo);
        v.setSize(x, y);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }

    private String[] sacarRespuesta(String[] respuesta) {
        String separado[];
        String[] mon = c.getmon();
        int tablilla[][];
        if (respuesta.length % 2 == 0) {
            tablilla = new int[respuesta.length / 2 + 1][2];
        } else {
            tablilla = new int[respuesta.length / 2 + 2][2];
        }
        int a = 0;
        int indexa;
        for (int i = 0; i < respuesta.length; i ++) {
            System.out.println("respuesta es " +respuesta[i]);
            separado = respuesta[i].split(":");
            /*for (int h = 0; h < separado.length; h++) {
                System.out.println(separado[h] + " ");
            }*/
            //System.out.println(separado.length + "longitud 1");
            try {
                indexa = Integer.parseInt(separado[1]);

                if (indexa != 0) {
                    tablilla[a][0] = indexa;
                    tablilla[a][1] = 1;
                    a++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

        }
        String[] resul = comparar(tablilla);

        for (int i = 0; i < tablilla.length; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(tablilla[i][j] + " ");
            }
            System.out.println("");
        }
        return resul;
    }

    private String[] comparar(int[][] tablilla) {
        int aux = tablilla[0][0];
        int numero = 1;
        String[] res = new String[tablilla.length];
        int index = 0;
        for (int i = 1; i < res.length; i++) {
            if (aux == tablilla[i][0]) {
                numero++;
            } else {
                res[index] = numero + " moneda(s) de $" + aux;
                numero=1;
                index++;
                aux = tablilla[i][0];
            }
        }

        for (int j = 0; j < res.length - index; j++) {
            System.out.println(res[j] + " ");
        }
        inres = index;
        return res;
    }

    private boolean infito(int[][] val) {

        boolean inf = true;

        for (int i = 0; i < val.length; i++) {
            if (val[i][val[0].length - 1] >= 50000) {
                inf = true;
            } else {
                inf = false;
                break;
            }
        }
        return inf;
    }

}
