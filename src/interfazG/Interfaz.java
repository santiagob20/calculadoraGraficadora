package interfazG;

import java.awt.BorderLayout;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 *
 * @author santiagob20 & SebastianPinto
 */
public class Interfaz extends javax.swing.JFrame {

    int resultado = 0, cero = 0;
    int operacion = 0; 
    int error = 0;
    double X = 0, Y = 0, A = 0, B = 0;
    int actX = 0; 
    String funcion;
    int regresion = 0;
    int grafica = 0;
    int esFuncion=0;
    ArrayList<Double> ejeX = new ArrayList<>();
    ArrayList<Double> ejeY = new ArrayList<>();
    ArrayList<Double> ejeXY = new ArrayList<>();
    ArrayList<Double> ejeXX = new ArrayList<>();
    JPanel panel;
    int intervaloA = 0;

    public Interfaz() 
    {
        initComponents();
        setTitle("Calculadora de Funciones");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true); 
    }   
    
    public double factorial(double x) 
    {
        double factorial = 1;
        while (x != 0) 
        {
            factorial = factorial * x;
            x--;
        }
        return factorial;
    }
    
    public double exponencial(String X2, String Y2)
    {
        double A = Math.pow(Double.valueOf(X2), Double.valueOf(Y2));
        return A;
    }
    
    public void exeBtnOperadores(String n)
    {
        String numero = txtPrincipal.getText();
        if (numero.equals("0")) 
        {
            txtPrincipal.setText(n);
            txtOperaciones.setText(n);
            resultado = 0;
            operacion = 1;
        }
        else
        if (error == 0) 
        {
            numero = numero+n;
            txtPrincipal.setText(numero);
            txtOperaciones.setText(numero);
            resultado = 0;
            operacion = 1;
        }
    }
    
    public void resultadoTotal()
    {
        ScriptEngineManager mana = new ScriptEngineManager();
        ScriptEngine an = mana.getEngineByName("js");
        Object a = null;
        try 
        {
            a = an.eval(txtPrincipal.getText());
            String b = String.valueOf(a);
            txtPrincipal.setText(b);
            resultado = 1;
        } 
        catch (ScriptException ex) 
        {
            txtPrincipal.setText("Syntax Error");
            error = 1;
        }
    }
    
    public String EvaluarFuncion(String func)
    {
        ScriptEngineManager mana = new ScriptEngineManager();
        ScriptEngine an = mana.getEngineByName("js");
        String a = null;
        try 
        {
            a = String.valueOf(an.eval(func));
        } 
        catch (ScriptException ex) 
        {
            txtPrincipal.setText("Syntax Error");
            error = 1;
        }
        return a;
    }        
    
    public void exeBtn(String n)
    {
        cero = 1;
        String numero = txtPrincipal.getText();
        String [] cadenaSplit = numero.split("");
        if (resultado == 0 && error == 0) 
        {
            if (operacion == 0 && actX == 0) 
            {
                if (cadenaSplit[cadenaSplit.length-1].equals("X") || cadenaSplit[cadenaSplit.length-1].equals("Y") || cadenaSplit[cadenaSplit.length-1].equals("(") || cadenaSplit[cadenaSplit.length-1].equals(")")) 
                {
                    numero = numero+n;
                    txtPrincipal.setText(numero);
                    txtOperaciones.setText(numero);
                }
                else
                {
                    if (cadenaSplit[cadenaSplit.length-1].equals(".")) // Si existe un punto lo concateno con un 0 y el valor del Btn
                    {
                        numero = numero+n;
                        txtPrincipal.setText(numero);
                        txtOperaciones.setText(numero);
                    }
                    else
                    {
                        if (Integer.parseInt(cadenaSplit[cadenaSplit.length-1]) == 0 && cadenaSplit.length == 1) //Si existe un cero coloco el valor deseado
                        {
                            txtPrincipal.setText(n);
                            txtOperaciones.setText(n);
                        }
                        else // Si hay cualquier numero diferente de 0 o un "." lo concateno con el numero del Btn
                        {
                            numero = numero+n;
                            txtPrincipal.setText(numero);
                            txtOperaciones.setText(numero);
                        }
                        if (txtPrincipal.getText().equals("0")) 
                        {
                            numero = numero+n;
                            txtPrincipal.setText(numero);
                            txtOperaciones.setText(numero);
                        }
                    }
                }
            }
            else //Como ya existe un operador matematico en la expreison lo guardo completo en una variable para operarlo
            {
                numero = numero+n;
                txtPrincipal.setText(numero);
                txtOperaciones.setText(numero);
                operacion = 0; //controlamos que el ultimo valor de la cadena no es un operador
            }
        }
        else //como ya existe un resultado final de la operacion borramos y empezamos de nuevo
        {
            if (actX == 1 && resultado == 1) 
            {
                numero = numero+n;
                txtPrincipal.setText(numero);
                txtOperaciones.setText(numero);
                String [] cadena = numero.split("");
                String resultado = String.valueOf(exponencial(cadena[0], cadena[2]));
                txtPrincipal.setText(resultado);
                actX = 0;
            }
            else
            {
                txtPrincipal.setText(n);
                txtOperaciones.setText(n);
                resultado = 0;
                error = 0;
                //al seleccionar el = borramos resultado y operacion 
            }
        }
    }
    
    public void variables(String n)
    {
        if (txtPrincipal.getText().equals("0")) 
        {
            txtPrincipal.setText(n);
            txtOperaciones.setText(n);
        }
        else
        {
            String valor = txtPrincipal.getText();
            valor = valor + n;
            txtPrincipal.setText(valor);
            txtOperaciones.setText(valor);  
        }
//        if (cero == 1 || txtPrincipal.getText().equals("X"))
//        {
//            String valor = txtPrincipal.getText();
//            valor = valor + n;
//            txtPrincipal.setText(valor);
//            txtOperaciones.setText(valor);
//        }
//        if (cero == 0 || !"X".equals(txtPrincipal.getText()) || txtPrincipal.getText().equals("0")) 
//        {
//            txtPrincipal.setText(n);
//            txtOperaciones.setText(n);
//        }
        
    }
    
    public void funMath(String n)
    {
        try
        {
            double numero = 0; 
            if (error == 0 && resultado == 0 && operacion == 0) 
            {
                    switch (n)
                    {
                        case "sin":
                           resultadoTotal();
                           numero = Double.valueOf(txtPrincipal.getText());
                           txtOperaciones.setText("Sin("+numero+")");
                           txtPrincipal.setText(String.valueOf(Math.sin(numero)));
                        break;
                        case "cos":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("Cos("+numero+")");
                            txtPrincipal.setText(String.valueOf(Math.cos(numero)));     
                        break;
                        case "tan":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("Tan("+numero+")");
                            txtPrincipal.setText(String.valueOf(Math.tan(numero)));
                        break;
                        case "cot":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("Cot("+numero+")");
                            txtPrincipal.setText(String.valueOf(1/Math.tan(numero)));   
                        break;
                        case "sec":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("Sec("+numero+")");
                            txtPrincipal.setText(String.valueOf(1/Math.cos(numero)));    
                        break;
                        case "csc":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("Csc("+numero+")");
                            txtPrincipal.setText(String.valueOf(1/Math.sin(numero)));   
                        break;
                        case "sqrt":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("√("+numero+")");
                            txtPrincipal.setText(String.valueOf(Math.sqrt(numero)));   
                        break;
                        case "ln":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("ln("+numero+")");
                            txtPrincipal.setText(String.valueOf(Math.log(numero)));   
                        break;
                        case "log":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("log("+numero+")");
                            txtPrincipal.setText(String.valueOf(Math.log10(numero)));   
                        break;
                        case "1/X":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("1/("+numero+")");
                            txtPrincipal.setText(String.valueOf(1/numero));   
                        break;
                        case "X^2":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("("+numero+")^2");
                            txtPrincipal.setText(String.valueOf(Math.pow(numero,2)));   
                        break;
                        case "X^3":
                            resultadoTotal();
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("("+numero+")^3");
                            txtPrincipal.setText(String.valueOf(Math.pow(numero,3)));   
                        break;
                        case "X^Y":
                            actX = 1;
                            exeBtn("^");
                        break;
                        case "X!":
                            resultadoTotal();                        
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText(""+numero+"!");
                            txtPrincipal.setText(String.valueOf(factorial(numero)));   
                        break;
                        case "e^X":
                            resultadoTotal();                        
                            numero = Double.valueOf(txtPrincipal.getText());
                            txtOperaciones.setText("e^"+numero);
                            txtPrincipal.setText(String.valueOf(Math.exp(numero)));   
                        break;
                    }
            }
            resultado = 1;
        }
        catch(Exception e)
        {
            txtPrincipal.setText("Syntax Error");
            error = 1;
        }
    }
    
    public void intervalosGrafica()
    {
        A = Double.valueOf(txtPrincipal.getText());
        txtPrincipal.setText("0");
    }
    
    public void graficar(String n)
    {
        B = Double.valueOf(txtPrincipal.getText());       
        txtPrincipal.setText("0"); 
        while (A<=B) 
        {  
           ejeX.add(A);
           switch (n)
                {
                    case "sin":
                        ejeY.add(Math.sin(A));                        
                    break;
                    case "cos":
                       ejeY.add(Math.cos(A));
                    break;
                    case "ln":
                       ejeY.add(Math.log(A));
                    break;
                    case "X^2":
                        ejeY.add(Math.pow(A,2));
                    break;
                    case "X^3":
                        ejeY.add(Math.pow(A,3));
                    break;
                    case "regresion":
                        ejeY.add(A*3);//valores para la regresion
                        ejeXY.add(A*Math.sin(A));
                        ejeXX.add(Math.pow(A,2));
                    break;
                }
           A+=0.1;
        }

    }
    
    public void plot()
    {
        if (regresion == 0) 
        {
            XYSeries series = new XYSeries("Grafica funcion " + funcion);
            for (int i = 0; i < ejeX.size(); i++) 
            {
                series.add(ejeX.get(i),ejeY.get(i));
            }hhh

            XYSeriesCollection dataset  = new XYSeriesCollection();
            dataset.addSeries(series);
            JFreeChart chart  = ChartFactory.createXYLineChart("Grafica Funcion "+funcion, "X", "Y", dataset, PlotOrientation.VERTICAL, true, false, false);
            ChartPanel chartpanel = new ChartPanel(chart);
            chartpanel.setDomainZoomable(true);                    
            PanelGraficadora.setLayout(new BorderLayout());
            PanelGraficadora.add(chartpanel, BorderLayout.NORTH);
            ChartFrame frame = new ChartFrame("Grafica Funcion "+funcion, chart);
            JPanel panel = frame.getChartPanel();
            jInternalFrame1.removeAll();
            jInternalFrame1.repaint();
            panel.setSize(jInternalFrame1.getSize());
            jInternalFrame1.add(panel);
            jInternalFrame1.updateUI();
            this.update(this.getGraphics());
        }
        else
        {
            regresion = 0;       
            System.out.println("regresion");
            double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0, n = ejeX.size();
            for (int i = 0; i < ejeX.size(); i++) 
            {
                sumX = sumX+ejeX.get(i);
                sumY = sumY+ejeY.get(i);
                sumXY = sumXY+ejeXY.get(i);
                sumXX = sumXX+ejeXX.get(i);
            }
            double m = (((n)*(sumXY)-(sumX)*(sumY))/((n)*(sumXX)-Math.pow(sumX, 2)));
            System.out.println("m = "+m);            
            ArrayList<Double> regresionB = new ArrayList<Double>();                       
            for (int i = 0; i < ejeX.size(); i++) 
            {
                double b = ejeY.get(i)-m*ejeX.get(i);
                regresionB.add(b);
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();           
            for (int i = 0; i < ejeX.size(); i++) 
            {
                dataset.addValue(ejeY.get(i), "funcion", ""+ejeX.get(i));
                dataset.addValue(regresionB.get(i), "regresion", ""+ejeX.get(i));
            }    
            
            JFreeChart chart  = ChartFactory.createXYLineChart("Grafica Funcion "+funcion, "X", "Y", (XYDataset) dataset, PlotOrientation.VERTICAL, true, false, false);
            ChartPanel chartpanel = new ChartPanel(chart);
            chartpanel.setDomainZoomable(true);                    
            PanelGraficadora.setLayout(new BorderLayout());
            PanelGraficadora.add(chartpanel, BorderLayout.NORTH);
            ChartFrame frame = new ChartFrame("Grafica Funcion "+funcion, chart);
            JPanel panel = frame.getChartPanel();
            jInternalFrame1.removeAll();
            jInternalFrame1.repaint();
            panel.setSize(jInternalFrame1.getSize());
            jInternalFrame1.add(panel);
            jInternalFrame1.updateUI();
            this.update(this.getGraphics());
        }   
    }  
       
    public String GraficarPolinomio(String funcion)
    {
        JEP jep = new JEP();
        jep.addStandardConstants(); 
        jep.addStandardFunctions(); 
        jep.setImplicitMul(true); 
        jep.parseExpression(funcion);
        String a = String.valueOf(jep.getValue()); 
        return a;
        
//------------- CODIGO PARA GRAFICAR UTILIZADO ANTES DE ENCONTRA "JEP"-------------------       
//        String graph = "";        
//        DJep Graph = new DJep();
//        Graph.addStandardFunctions();
//        Graph.addStandardConstants();
//        Graph.addComplex();
//        Graph.setAllowUndeclared(true);
//        Graph.setAllowAssignment(true);
//        Graph.setImplicitMul(true);
//        Graph.addStandardDiffRules();
//        try 
//        {
//            Node node = Graph.parse(funcion);
//            Node diff = Graph.differentiate(node, "X");
//            Node sim = Graph.simplify(diff);
//            graph = Graph.toString(sim);
//        } 
//        catch (ParseException e) 
//        {
//            e.printStackTrace();
//        }
//        return graph;
    }
    
    public double valoresParaGraficar(String fun, int Xa)
    {
        String funE = ""; //Creo una variable donde voy a ir guardando el resultado
        String [] Split = fun.split("");//Hacemos un Split al String entrante
        for (int i = 0; i < Split.length; i++) //Recorro todo el arreglo resultante del Strin entrante
        {
            if ("X".equals(Split[i])) //Cuando encuenta la variable la remplaza por el valor entero entrante
            {
                Split[i] = "("+Xa+")";
            }
            funE = funE+Split[i];//Cada vuelta del for voy concatenando para armar de nuevo el String entrante
        }
        return Double.valueOf(GraficarPolinomio(funE));//Utilizo JEP para evaluar la expresion (Ejm. 3+4(2)-6) y Convierto el resultado en double
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFramePrincipal = new javax.swing.JInternalFrame();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jPanelPrincipal = new javax.swing.JPanel();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jButton54 = new javax.swing.JButton();
        graf = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnDel = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSiete = new javax.swing.JButton();
        btnOcho = new javax.swing.JButton();
        btnNueve = new javax.swing.JButton();
        btnCuatro = new javax.swing.JButton();
        btnDiv = new javax.swing.JButton();
        btnMult = new javax.swing.JButton();
        btnSeis = new javax.swing.JButton();
        btnCinco = new javax.swing.JButton();
        btnResta = new javax.swing.JButton();
        btnTres = new javax.swing.JButton();
        btnDos = new javax.swing.JButton();
        btnSuma = new javax.swing.JButton();
        btnUno = new javax.swing.JButton();
        btnCero = new javax.swing.JButton();
        btnPunto = new javax.swing.JButton();
        btnCsc = new javax.swing.JButton();
        btnCot = new javax.swing.JButton();
        btnElevx = new javax.swing.JButton();
        btnSec = new javax.swing.JButton();
        btnFrac = new javax.swing.JButton();
        btnFact = new javax.swing.JButton();
        btnTan = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        btnElevY = new javax.swing.JButton();
        btnSin = new javax.swing.JButton();
        btnRaiz = new javax.swing.JButton();
        btnLn = new javax.swing.JButton();
        btnCub = new javax.swing.JButton();
        btnRegL = new javax.swing.JButton();
        BtnIgual = new javax.swing.JButton();
        btnX = new javax.swing.JButton();
        btnY = new javax.swing.JButton();
        btnParentAp = new javax.swing.JButton();
        btnParentCie = new javax.swing.JButton();
        btnCos = new javax.swing.JButton();
        btnCuad = new javax.swing.JButton();
        btnFunc = new javax.swing.JButton();
        jInternalFrame3 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPrincipal = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOperaciones = new javax.swing.JTextArea();
        btnCuadGraf = new javax.swing.JButton();
        btnCosGraf = new javax.swing.JButton();
        btnSinGraf = new javax.swing.JButton();
        btnLnGraf = new javax.swing.JButton();
        btnCubGraf = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        PanelGraficadora = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();

        jFramePrincipal.setVisible(true);

        javax.swing.GroupLayout jFramePrincipalLayout = new javax.swing.GroupLayout(jFramePrincipal.getContentPane());
        jFramePrincipal.getContentPane().setLayout(jFramePrincipalLayout);
        jFramePrincipalLayout.setHorizontalGroup(
            jFramePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        jFramePrincipalLayout.setVerticalGroup(
            jFramePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        jButton22.setText("jButton1");

        jButton23.setText("jButton1");

        jButton24.setText("jButton1");

        jButton25.setText("jButton1");

        jButton26.setText("jButton1");

        jButton27.setText("jButton1");

        jButton28.setText("jButton1");

        jButton30.setText("jButton1");

        jButton33.setText("jButton1");

        jButton32.setText("jButton1");

        jButton3.setText("jButton1");

        jButton4.setText("jButton1");

        jButton10.setText("jButton1");

        jButton9.setText("jButton1");

        jButton19.setText("jButton1");

        jButton15.setText("jButton1");

        jButton35.setText("jButton1");

        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );

        jInternalFrame2.setVisible(true);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );

        jButton54.setText("RegL");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        graf.setText("GRAFICAR");
        graf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(119, 136, 153));

        btnDel.setBackground(new java.awt.Color(255, 102, 0));
        btnDel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btnDel.setText("DEL");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(255, 102, 0));
        btnBorrar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btnBorrar.setText("AC");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnSiete.setText("7");
        btnSiete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSieteActionPerformed(evt);
            }
        });

        btnOcho.setText("8");
        btnOcho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOchoActionPerformed(evt);
            }
        });

        btnNueve.setText("9");
        btnNueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNueveActionPerformed(evt);
            }
        });

        btnCuatro.setText("4");
        btnCuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuatroActionPerformed(evt);
            }
        });

        btnDiv.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnDiv.setText("/");
        btnDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivActionPerformed(evt);
            }
        });

        btnMult.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnMult.setText("*");
        btnMult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultActionPerformed(evt);
            }
        });

        btnSeis.setText("6");
        btnSeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeisActionPerformed(evt);
            }
        });

        btnCinco.setText("5");
        btnCinco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCincoActionPerformed(evt);
            }
        });

        btnResta.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnResta.setText("-");
        btnResta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaActionPerformed(evt);
            }
        });

        btnTres.setText("3");
        btnTres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTresActionPerformed(evt);
            }
        });

        btnDos.setText("2");
        btnDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDosActionPerformed(evt);
            }
        });

        btnSuma.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnSuma.setText("+");
        btnSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumaActionPerformed(evt);
            }
        });

        btnUno.setText("1");
        btnUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnoActionPerformed(evt);
            }
        });

        btnCero.setText("0");
        btnCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCeroActionPerformed(evt);
            }
        });

        btnPunto.setText("·");
        btnPunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntoActionPerformed(evt);
            }
        });

        btnCsc.setBackground(new java.awt.Color(51, 51, 51));
        btnCsc.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCsc.setForeground(new java.awt.Color(236, 238, 239));
        btnCsc.setText("csc");
        btnCsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCscActionPerformed(evt);
            }
        });

        btnCot.setBackground(new java.awt.Color(51, 51, 51));
        btnCot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCot.setForeground(new java.awt.Color(236, 238, 239));
        btnCot.setText("cot");
        btnCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotActionPerformed(evt);
            }
        });

        btnElevx.setBackground(new java.awt.Color(51, 51, 51));
        btnElevx.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnElevx.setForeground(new java.awt.Color(236, 238, 239));
        btnElevx.setText("e^x");
        btnElevx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElevxActionPerformed(evt);
            }
        });

        btnSec.setBackground(new java.awt.Color(51, 51, 51));
        btnSec.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSec.setForeground(new java.awt.Color(236, 238, 239));
        btnSec.setText("sec");
        btnSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSecActionPerformed(evt);
            }
        });

        btnFrac.setBackground(new java.awt.Color(51, 51, 51));
        btnFrac.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnFrac.setForeground(new java.awt.Color(236, 238, 239));
        btnFrac.setText("1/x");
        btnFrac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFracActionPerformed(evt);
            }
        });

        btnFact.setBackground(new java.awt.Color(51, 51, 51));
        btnFact.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnFact.setForeground(new java.awt.Color(236, 238, 239));
        btnFact.setText("x!");
        btnFact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFactActionPerformed(evt);
            }
        });

        btnTan.setBackground(new java.awt.Color(51, 51, 51));
        btnTan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTan.setForeground(new java.awt.Color(236, 238, 239));
        btnTan.setText("tan");
        btnTan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTanActionPerformed(evt);
            }
        });

        btnLog.setBackground(new java.awt.Color(51, 51, 51));
        btnLog.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLog.setForeground(new java.awt.Color(236, 238, 239));
        btnLog.setText("log");
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        btnElevY.setBackground(new java.awt.Color(51, 51, 51));
        btnElevY.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnElevY.setForeground(new java.awt.Color(236, 238, 239));
        btnElevY.setText("x^y");
        btnElevY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElevYActionPerformed(evt);
            }
        });

        btnSin.setBackground(new java.awt.Color(51, 51, 51));
        btnSin.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSin.setForeground(new java.awt.Color(236, 238, 239));
        btnSin.setText("sin");
        btnSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinActionPerformed(evt);
            }
        });

        btnRaiz.setBackground(new java.awt.Color(51, 51, 51));
        btnRaiz.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRaiz.setForeground(new java.awt.Color(236, 238, 239));
        btnRaiz.setText("√");
        btnRaiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRaizActionPerformed(evt);
            }
        });

        btnLn.setBackground(new java.awt.Color(51, 51, 51));
        btnLn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLn.setForeground(new java.awt.Color(236, 238, 239));
        btnLn.setText("ln");
        btnLn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLnActionPerformed(evt);
            }
        });

        btnCub.setBackground(new java.awt.Color(51, 51, 51));
        btnCub.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCub.setForeground(new java.awt.Color(236, 238, 239));
        btnCub.setText("x^3");
        btnCub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCubActionPerformed(evt);
            }
        });

        btnRegL.setBackground(new java.awt.Color(51, 51, 51));
        btnRegL.setFont(new java.awt.Font("Arial", 1, 8)); // NOI18N
        btnRegL.setForeground(new java.awt.Color(236, 238, 239));
        btnRegL.setText("REG L");
        btnRegL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegLActionPerformed(evt);
            }
        });

        BtnIgual.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        BtnIgual.setText("=");
        BtnIgual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIgualActionPerformed(evt);
            }
        });

        btnX.setBackground(new java.awt.Color(51, 51, 51));
        btnX.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnX.setForeground(new java.awt.Color(236, 238, 239));
        btnX.setText("X");
        btnX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXActionPerformed(evt);
            }
        });

        btnY.setBackground(new java.awt.Color(51, 51, 51));
        btnY.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnY.setForeground(new java.awt.Color(236, 238, 239));
        btnY.setText("Y");
        btnY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYActionPerformed(evt);
            }
        });

        btnParentAp.setBackground(new java.awt.Color(51, 51, 51));
        btnParentAp.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnParentAp.setForeground(new java.awt.Color(236, 238, 239));
        btnParentAp.setText("(");
        btnParentAp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParentApActionPerformed(evt);
            }
        });

        btnParentCie.setBackground(new java.awt.Color(51, 51, 51));
        btnParentCie.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnParentCie.setForeground(new java.awt.Color(236, 238, 239));
        btnParentCie.setText(")");
        btnParentCie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParentCieActionPerformed(evt);
            }
        });

        btnCos.setBackground(new java.awt.Color(51, 51, 51));
        btnCos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCos.setForeground(new java.awt.Color(236, 238, 239));
        btnCos.setText("cos");
        btnCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCosActionPerformed(evt);
            }
        });

        btnCuad.setBackground(new java.awt.Color(51, 51, 51));
        btnCuad.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCuad.setForeground(new java.awt.Color(236, 238, 239));
        btnCuad.setText("x^2");
        btnCuad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuadActionPerformed(evt);
            }
        });

        btnFunc.setBackground(new java.awt.Color(51, 51, 51));
        btnFunc.setFont(new java.awt.Font("Arial", 1, 8)); // NOI18N
        btnFunc.setForeground(new java.awt.Color(236, 238, 239));
        btnFunc.setText("Func");
        btnFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFuncActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRegL, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(60, 60, 60)
                                    .addComponent(btnCos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSec, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnY, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnParentAp, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnParentCie, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnLn, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCub, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnElevY, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnFrac, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCuad, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnFact, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSiete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnOcho, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCinco, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnSeis, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNueve, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnUno, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnDos, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(12, 12, 12))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(btnCero, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnPunto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTres, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btnSuma, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnMult, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnDiv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnResta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(BtnIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnSin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(66, 66, 66)
                            .addComponent(btnTan, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(63, 63, 63)
                            .addComponent(btnCot, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnCsc, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnElevx, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(73, 73, 73))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnX)
                    .addComponent(btnY)
                    .addComponent(btnParentAp)
                    .addComponent(btnParentCie)
                    .addComponent(btnFrac)
                    .addComponent(btnRaiz)
                    .addComponent(btnCuad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTan)
                        .addComponent(btnSec)
                        .addComponent(btnCot)
                        .addComponent(btnCsc)
                        .addComponent(btnElevx)
                        .addComponent(btnCos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFunc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLn)
                        .addComponent(btnLog)
                        .addComponent(btnCub)
                        .addComponent(btnElevY)
                        .addComponent(btnFact)
                        .addComponent(btnRegL, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNueve, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSeis, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnCuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCinco, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnUno, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDos, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnTres, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSiete, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOcho, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMult, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDiv, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSuma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResta, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnIgual, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPunto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCero, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame3.setVisible(true);

        txtPrincipal.setColumns(20);
        txtPrincipal.setRows(5);
        txtPrincipal.setText("0");
        jScrollPane1.setViewportView(txtPrincipal);

        txtOperaciones.setColumns(20);
        txtOperaciones.setRows(5);
        txtOperaciones.setText("0");
        jScrollPane2.setViewportView(txtOperaciones);

        btnCuadGraf.setBackground(new java.awt.Color(51, 51, 51));
        btnCuadGraf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCuadGraf.setForeground(new java.awt.Color(235, 235, 235));
        btnCuadGraf.setText("x^2");
        btnCuadGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuadGrafActionPerformed(evt);
            }
        });

        btnCosGraf.setBackground(new java.awt.Color(51, 51, 51));
        btnCosGraf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCosGraf.setForeground(new java.awt.Color(235, 235, 235));
        btnCosGraf.setText("cos");
        btnCosGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCosGrafActionPerformed(evt);
            }
        });

        btnSinGraf.setBackground(new java.awt.Color(51, 51, 51));
        btnSinGraf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSinGraf.setForeground(new java.awt.Color(235, 235, 235));
        btnSinGraf.setText("sin");
        btnSinGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinGrafActionPerformed(evt);
            }
        });

        btnLnGraf.setBackground(new java.awt.Color(51, 51, 51));
        btnLnGraf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnLnGraf.setForeground(new java.awt.Color(235, 235, 235));
        btnLnGraf.setText("ln");
        btnLnGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLnGrafActionPerformed(evt);
            }
        });

        btnCubGraf.setBackground(new java.awt.Color(51, 51, 51));
        btnCubGraf.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCubGraf.setForeground(new java.awt.Color(235, 235, 235));
        btnCubGraf.setText("x^3");
        btnCubGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCubGrafActionPerformed(evt);
            }
        });

        btnGraficar.setBackground(new java.awt.Color(255, 102, 0));
        btnGraficar.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        btnGraficar.setText("GRAF");
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnCuadGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSinGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCosGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLnGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCubGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGraficar))
                    .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCuadGraf)
                    .addComponent(btnSinGraf)
                    .addComponent(btnCosGraf)
                    .addComponent(btnLnGraf)
                    .addComponent(btnCubGraf)
                    .addComponent(btnGraficar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelGraficadoraLayout = new javax.swing.GroupLayout(PanelGraficadora);
        PanelGraficadora.setLayout(PanelGraficadoraLayout);
        PanelGraficadoraLayout.setHorizontalGroup(
            PanelGraficadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelGraficadoraLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelGraficadoraLayout.setVerticalGroup(
            PanelGraficadoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jInternalFrame3))
                .addGap(18, 18, 18)
                .addComponent(PanelGraficadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelGraficadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jInternalFrame3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntoActionPerformed
        // TODO add your handling code here:
        
        exeBtn(".");
    }//GEN-LAST:event_btnPuntoActionPerformed

    private void grafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grafActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_grafActionPerformed

    private void btnCscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCscActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("csc(");
        }else
        {
            funMath("csc");
        }
    }//GEN-LAST:event_btnCscActionPerformed

    private void btnCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("cot(");
        }else
        {
            funMath("cot");
        }
    }//GEN-LAST:event_btnCotActionPerformed

    private void btnElevxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElevxActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("exp^(");
        }else
        {
            funMath("e^X");
        }
    }//GEN-LAST:event_btnElevxActionPerformed

    private void btnSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSecActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("sec(");
        }else
        {
            funMath("sec");
        }
    }//GEN-LAST:event_btnSecActionPerformed

    private void btnFracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFracActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("1/(");
        }else
        {
            funMath("1/X");
        }
    }//GEN-LAST:event_btnFracActionPerformed

    private void btnFactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFactActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
           variables("!");
        }else
        {
            funMath("X!");
        }
    }//GEN-LAST:event_btnFactActionPerformed

    private void btnTanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTanActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("tan(");
        }
        else
        {
           funMath("tan"); 
        }
    }//GEN-LAST:event_btnTanActionPerformed

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("log10(");
        }else
        {
            funMath("log");
        }
    }//GEN-LAST:event_btnLogActionPerformed

    private void btnElevYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElevYActionPerformed
        // TODO add your handling code here:
        if(esFuncion == 1 && intervaloA == 0)
        {
            variables("^");
        }
        if(esFuncion == 0 && intervaloA == 0)
        {
            funMath("X^Y");
        }
        if (esFuncion == 1 && intervaloA == 1) 
        {
            
        }
    }//GEN-LAST:event_btnElevYActionPerformed

    private void btnSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("sin(");
        }else
        {
            funMath("sin");
        }
        
    }//GEN-LAST:event_btnSinActionPerformed

    private void btnRaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRaizActionPerformed
        // TODO add your handling code here:
        
        if(esFuncion==1)
        {
            variables("sqrt(");
        }else
        {
            funMath("sqrt");
        }
    }//GEN-LAST:event_btnRaizActionPerformed

    private void btnCuadGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuadGrafActionPerformed
        // TODO add your handling code here:
       
        intervalosGrafica();
        funcion="X^2";
        
    }//GEN-LAST:event_btnCuadGrafActionPerformed

    private void btnCosGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCosGrafActionPerformed
        // TODO add your handling code here:
        intervalosGrafica();
        funcion="cos";
        
    }//GEN-LAST:event_btnCosGrafActionPerformed

    private void btnLnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLnActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("log(");
        }else
        {
            funMath("ln");
        }
    }//GEN-LAST:event_btnLnActionPerformed

    private void btnCubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCubActionPerformed
        // TODO add your handling code here:
        if(esFuncion==1)
        {
            variables("^3");
        }else
        {
           funMath("X^3");
        }
        
    }//GEN-LAST:event_btnCubActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        // TODO add your handling code here:
        String a = txtPrincipal.getText();
        if (resultado == 0 && error == 0 && a.length() != 1) 
        {
            a = a.substring(0, a.length()-1);
            txtPrincipal.setText(a);
            txtOperaciones.setText(a);
        }
        else
        {
            txtPrincipal.setText("0");
            txtOperaciones.setText("0");
        }
        error = 0;
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        txtOperaciones.setText("0");
        txtPrincipal.setText("0");
        resultado = 0;//
        operacion = 0;//
        error = 0;//
        actX = 0;//
        regresion = 0;//
        funcion = "";//
        esFuncion = 0;
        X = 0;//
        Y = 0;//
        A = 0;//
        B = 0;//
        grafica = 0;//
        panel = null;//
        intervaloA = 0;
        ejeX = new ArrayList<>();
        ejeY = new ArrayList<>();
        ejeXY = new ArrayList<>();
        ejeXX = new ArrayList<>();
        jInternalFrame1.removeAll();
        jInternalFrame1.repaint();

    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSieteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSieteActionPerformed
        // TODO add your handling code here:
        exeBtn("7");
    }//GEN-LAST:event_btnSieteActionPerformed

    private void btnSumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumaActionPerformed
        // TODO add your handling code here:
        exeBtnOperadores("+");
    }//GEN-LAST:event_btnSumaActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton54ActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        // TODO add your handling code here:
        if (intervaloA == 1) 
        {
            B = Double.valueOf(txtPrincipal.getText());            
            txtOperaciones.setText("f(x)= "+ funcion + " \n Intervalos ["+A+" - "+B+"]");
            int a = (int)A;
            int b = (int)B;            
            for (int i = a; i <= b; i++) 
            {
                ejeX.add((double)i);
                ejeY.add(valoresParaGraficar(funcion, i));
            }
//            System.out.println("| X | \t\t | Y |");
//            for (int i = 0; i < ejeX.size(); i++) 
//            {
//                System.out.println("| "+ejeX.get(i)+" | \t | "+ejeY.get(i)+" |");
//            }
            plot();
        }
        if (esFuncion == 0 && intervaloA == 0) 
        {
            graficar(funcion);
            plot();
        }
        else
        {
            B = Double.valueOf(txtPrincipal.getText()); 
            txtOperaciones.setText("f(x)= "+ funcion + " \n Intervalos ["+A+" - "+B+"]");
            GraficarPolinomio(funcion);
        }
// bandera para cada boton y aqui lo evaluamos con un if o Switch
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnRegLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegLActionPerformed
        // TODO add your handling code here:
        intervalosGrafica();
        funcion="regresion";
        regresion = 1;
    }//GEN-LAST:event_btnRegLActionPerformed

    private void btnUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnoActionPerformed
        // TODO add your handling code here:
        exeBtn("1");
    }//GEN-LAST:event_btnUnoActionPerformed

    private void btnDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDosActionPerformed

        exeBtn("2");
    }//GEN-LAST:event_btnDosActionPerformed

    private void btnTresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTresActionPerformed
        // TODO add your handling code here:
        exeBtn("3");
    }//GEN-LAST:event_btnTresActionPerformed

    private void btnCuatroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuatroActionPerformed
        // TODO add your handling code here:
        exeBtn("4");
    }//GEN-LAST:event_btnCuatroActionPerformed

    private void btnCincoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCincoActionPerformed
        // TODO add your handling code here:
        exeBtn("5");
    }//GEN-LAST:event_btnCincoActionPerformed

    private void btnSeisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeisActionPerformed
        // TODO add your handling code here:
        exeBtn("6");
    }//GEN-LAST:event_btnSeisActionPerformed

    private void btnOchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOchoActionPerformed
        // TODO add your handling code here:
        exeBtn("8");
    }//GEN-LAST:event_btnOchoActionPerformed

    private void btnNueveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNueveActionPerformed
        // TODO add your handling code here:
        exeBtn("9");
    }//GEN-LAST:event_btnNueveActionPerformed

    private void btnCeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCeroActionPerformed
        // TODO add your handling code here:
        //operacion = 1;
        exeBtn("0");
    }//GEN-LAST:event_btnCeroActionPerformed

    private void btnRestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaActionPerformed
        // TODO add your handling code here:
        exeBtnOperadores("-");
    }//GEN-LAST:event_btnRestaActionPerformed

    private void btnMultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultActionPerformed
        // TODO add your handling code here:
        exeBtnOperadores("*");
    }//GEN-LAST:event_btnMultActionPerformed

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivActionPerformed
        // TODO add your handling code here:
        exeBtnOperadores("/");
    }//GEN-LAST:event_btnDivActionPerformed

    private void BtnIgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIgualActionPerformed
        // TODO add your handling code here:
        if (grafica==0 && esFuncion==0)
        {
           resultadoTotal();
        }
        else 
        {
            funcion = txtPrincipal.getText(); // Capturo la funcion a graficar 
            txtOperaciones.setText("f(x)= "+funcion+ " || Intervalo A = "+A+ " || Digite el Intervalo B");
            txtPrincipal.setText("0");
        }
        
    }//GEN-LAST:event_BtnIgualActionPerformed

    private void btnParentApActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParentApActionPerformed
        // TODO add your handling code here:
        exeBtn("(");
    }//GEN-LAST:event_btnParentApActionPerformed

    private void btnParentCieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParentCieActionPerformed
        // TODO add your handling code here:
        exeBtn(")");
    }//GEN-LAST:event_btnParentCieActionPerformed

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXActionPerformed
        // TODO add your handling code here:
        variables("X");
    }//GEN-LAST:event_btnXActionPerformed

    private void btnYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYActionPerformed
        // TODO add your handling code here:
        variables("Y");
    }//GEN-LAST:event_btnYActionPerformed

    private void btnSinGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinGrafActionPerformed
        // TODO add your handling code here:
        intervalosGrafica();
        funcion = "sin";

    }//GEN-LAST:event_btnSinGrafActionPerformed

    private void btnCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCosActionPerformed
        // TODO add your handling code here: 
        if(esFuncion==1)
        {
            variables("cos(");
        }else
        {
            funMath("cos");
        }
    }//GEN-LAST:event_btnCosActionPerformed

    private void btnCuadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuadActionPerformed
        // TODO add your handling code here:
        
        if(esFuncion==1)
        {
            variables("^2");
        }else
        {
            funMath("X^2");
        }
    }//GEN-LAST:event_btnCuadActionPerformed

    private void btnLnGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLnGrafActionPerformed
        // TODO add your handling code here:
        intervalosGrafica();
        funcion = "ln";
    }//GEN-LAST:event_btnLnGrafActionPerformed

    private void btnCubGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCubGrafActionPerformed
        // TODO add your handling code here:
        intervalosGrafica();
        funcion = "X^3";
    }//GEN-LAST:event_btnCubGrafActionPerformed

    private void btnFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFuncActionPerformed
        // TODO add your handling code here:
        cero = 0;
        intervaloA = 1;
        esFuncion = 1;
        intervalosGrafica(); //Capturo el valor 1 para la funcion
        txtOperaciones.setText("Intervalo A = " + A + "\t <<< Digite la Funcion >>>");
    }//GEN-LAST:event_btnFuncActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
                
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnIgual;
    public javax.swing.JPanel PanelGraficadora;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCero;
    private javax.swing.JButton btnCinco;
    private javax.swing.JButton btnCos;
    private javax.swing.JButton btnCosGraf;
    private javax.swing.JButton btnCot;
    private javax.swing.JButton btnCsc;
    private javax.swing.JButton btnCuad;
    private javax.swing.JButton btnCuadGraf;
    private javax.swing.JButton btnCuatro;
    private javax.swing.JButton btnCub;
    private javax.swing.JButton btnCubGraf;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnDos;
    private javax.swing.JButton btnElevY;
    private javax.swing.JButton btnElevx;
    private javax.swing.JButton btnFact;
    private javax.swing.JButton btnFrac;
    private javax.swing.JButton btnFunc;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnLn;
    private javax.swing.JButton btnLnGraf;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnMult;
    private javax.swing.JButton btnNueve;
    private javax.swing.JButton btnOcho;
    private javax.swing.JButton btnParentAp;
    private javax.swing.JButton btnParentCie;
    private javax.swing.JButton btnPunto;
    private javax.swing.JButton btnRaiz;
    private javax.swing.JButton btnRegL;
    private javax.swing.JButton btnResta;
    private javax.swing.JButton btnSec;
    private javax.swing.JButton btnSeis;
    private javax.swing.JButton btnSiete;
    private javax.swing.JButton btnSin;
    private javax.swing.JButton btnSinGraf;
    private javax.swing.JButton btnSuma;
    private javax.swing.JButton btnTan;
    private javax.swing.JButton btnTres;
    private javax.swing.JButton btnUno;
    private javax.swing.JButton btnX;
    private javax.swing.JButton btnY;
    private javax.swing.JButton graf;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton9;
    private javax.swing.JInternalFrame jFramePrincipal;
    public static javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtOperaciones;
    private javax.swing.JTextArea txtPrincipal;
    // End of variables declaration//GEN-END:variables
    

}
