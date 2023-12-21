import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.awt.*;
public class RSAGeneratorGUI extends JFrame {
    final static BigInteger one=new BigInteger("1");
    final static BigInteger zero=new BigInteger("0");
    BigInteger p;
    BigInteger q;
    BigInteger exponent;

    private JPanel panel=new JPanel();
    private JButton button1=new JButton("Find modulus(n)");
    private JButton button2=new JButton("Generate");
    JTextField textField1=new JTextField(10);
    JTextField textField2=new JTextField(10);
    private JTextArea textArea1=new JTextArea(1,10);
    private JTextArea textArea2=new JTextArea(1,10);
    private JTextArea textArea3=new JTextArea(1,10);
    JTextField textField3=new JTextField(10);
    private JLabel label1=new JLabel("Prime1");
    private JLabel label2=new JLabel("Prime2");
    public RSAGeneratorGUI(){
        super("RSA Key Set Generator");
        setSize(400,400);
        panel.add(Box.createHorizontalStrut(15));
        panel.add(label1);
        panel.add(textField1);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(label2);
        panel.add(textField2);
        panel.add(Box.createHorizontalStrut(50));
        ActionListener listener1=new findListener();
        button1.addActionListener(listener1);
        panel.add(button1,BorderLayout.CENTER);
        panel.add(new JLabel("modulus(n):"));
        panel.add(textArea3);
        panel.add(Box.createHorizontalStrut(300));
        panel.add(new JLabel("Enter Exponent:"));
        panel.add(textField3);
        ActionListener listener2=new generateListener();
        button2.addActionListener(listener2);
        panel.add(button2,BorderLayout.CENTER);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(new JLabel("Public Key Set"),BorderLayout.BEFORE_LINE_BEGINS);
        panel.add(textArea1);
        panel.add(new JLabel("Private key Set"));
        panel.add(textArea2);
        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public class findListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                p=new BigInteger(textField1.getText());
                q=new BigInteger(textField2.getText());
                if(!isPrime(p)){
                    textArea3.setText("Need prime integer for Prime1");
                }else if(!isPrime(q)){
                    textArea3.setText("Need prime integer for Prime2");
                }else {
                    BigInteger n = findModulus(p, q);
                    textArea3.setText(n.toString());
                }
        }
    }
    public class generateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            p=new BigInteger(textField1.getText());
            q=new BigInteger(textField2.getText());
            exponent=new BigInteger(textField3.getText());
            BigInteger n=new BigInteger(findModulus(p,q).toString());
            BigInteger l=findTotient(p,q);
            if(!gcd(exponent,l).equals(one)){
                textArea2.setText("invalid exponent, find another one");
                textArea1.setText("gcd(exponent,totient) need be one");
            }else {
                textArea2.setText("(" + n + ", " + findSecretKey(exponent, l).toString() + ")");
                textArea1.setText("(" + n + ", " + exponent.toString() + ")");
            }
        }
    }

    public static void main(String[] args) {
        RSAGeneratorGUI gui=new RSAGeneratorGUI();




    }
    public static BigInteger findSecretKey(BigInteger e,BigInteger l){
        for(BigInteger i=one;i.compareTo(l)<0;i=i.add(one)){
            if(e.multiply(i).mod(l).equals(one)) {
                return i;
            }
        }
        return zero;
    }

    public static BigInteger findModulus(BigInteger p, BigInteger q){
        return p.multiply(q);
    }
    public static BigInteger findTotient (BigInteger p, BigInteger q){
        return p.subtract(one).multiply(q.subtract(one));
    }
    public static BigInteger gcd(BigInteger a,BigInteger b){
        for(BigInteger i=a;i.compareTo(one)>=0;i=i.subtract(one)){
            if(a.mod(i).equals(zero)&&b.mod(i).equals(zero))
                return i;
        }
        return zero;
    }
    public static Boolean isPrime(BigInteger x){
        for(BigInteger i=x.subtract(one);i.compareTo(one)>0;i=i.subtract(one)){
            if(x.mod(i).equals(zero))
                return false;
        }
        return true;
    }
    public static BigInteger encrypt(BigInteger n, BigInteger e,BigInteger m){
        return m.modPow(e,n);
    }
}