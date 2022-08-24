package csvn;

import core.kafka.communication.types.Record;
import core.kafka.communication.types.Status;
import core.models.DateConverter;
import core.models.ObjectConverter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import core.themes.Colors;
import csvn.pubsub.ActionProducer;
import csvn.pubsub.StatusProducer;
import java.lang.reflect.InvocationTargetException;
import static java.util.Objects.nonNull;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class csvnUI extends JFrame {

    private JPanel contentPane;
    private JTextField rcrdtxt;
    private JTextField rplytxt;
    private static Status statusModel;
    private JButton mainbtn9;
    static JButton[] statusencoderbutton;
    static JButton[] statusbutton;
    static JLabel[] statuslabel;
    private JLabel mainlbl1;

    Color dpanel = new Color(170, 170, 170);
    Color dspanel = new Color(140, 140, 140);
    Color dcomponent = new Color(130, 130, 130);

    public void guncelle(Status status, JButton btnvoid) {
        try {
            statusModel = status;
            long totalsize = Long.valueOf(status.getDiskSize().toString());
            long usabledisk = Long.valueOf(status.getUsableDiskPartition().toString());

            if (nonNull(totalsize) && nonNull(usabledisk)) {
                long a = (100 * (totalsize - usabledisk) / totalsize);
                if (a > 80) {

                    mainbtn9.setBackground(Color.RED);
                } else if (a > 50) {
                    mainbtn9.setBackground(Color.YELLOW);
                } else {
                    mainbtn9.setBackground(Color.GREEN);
                }
                btnvoid.setText("Storage %" + a);
                btnvoid.repaint();

                //System.out.println(a);
            } else {

                mainbtn9.setBackground(Color.RED);
                btnvoid.setText("CANNOT GET DISK INFO");
                btnvoid.repaint();
            }
        } catch (Exception e) {
            mainbtn9.setBackground(Color.RED);
            btnvoid.setText("ERROR DISK INFO");
            btnvoid.repaint();
        }

    }

    DefaultTableModel modeldst = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    ;
    };
	DefaultTableModel modelrcrd = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    ;
    };
	DefaultTableModel modelrply = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    ;
    };
	
	private JTable dsttable = new JTable(modeldst);
    private JTable rcrdtable = new JTable(modelrcrd);
    private JTable rplytable = new JTable(modelrply);

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        EventQueue.invokeAndWait(new Runnable() {
            public void run() {
                try {
                    csvnUI frame = new csvnUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public csvnUI() {
        Colors colors = new Colors();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 862, 510);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel mainpanel = new JPanel();
        contentPane.add(mainpanel);
        mainpanel.setBounds(0, 37, 862, 398);
        mainpanel.setLayout(null);

        JPanel uppanel = new JPanel();
        uppanel.setBounds(0, 0, 862, 37);
        contentPane.add(uppanel);
        uppanel.setLayout(null);
        uppanel.setBackground(Color.DARK_GRAY);

        JToggleButton maintglbtn = new JToggleButton("");
        Image imgmoon = new ImageIcon(this.getClass().getResource("/moon.png")).getImage();
        Image imgsun = new ImageIcon(this.getClass().getResource("/sun.png")).getImage();
        maintglbtn.setIcon(new ImageIcon(imgmoon));
        maintglbtn.setBounds(751, 3, 73, 32);
        uppanel.add(maintglbtn);

        JButton mainbtn10 = new JButton("");
        Image imgsettings = new ImageIcon(this.getClass().getResource("/settings.png")).getImage();
        mainbtn10.setIcon(new ImageIcon(imgsettings));
        mainbtn10.setBounds(751, 3, 73, 32);
        uppanel.add(mainbtn10);

        JButton mainbtn7 = new JButton("NETWORK STATUS");
        mainbtn7.setBackground(Color.GREEN);
        mainbtn7.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn7.setBounds(117, 3, 181, 32);
        uppanel.add(mainbtn7);

        JButton mainbtn5 = new JButton("SERVER STATUS");
        mainbtn5.setBackground(Color.GREEN);
        mainbtn5.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn5.setBounds(327, 3, 181, 32);
        uppanel.add(mainbtn5);

        mainbtn9 = new JButton("123");
        mainbtn9.setText("Storage %0");
        mainbtn9.setBackground(Color.GREEN);
        mainbtn9.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn9.setBounds(537, 3, 181, 32);
        uppanel.add(mainbtn9);

        JButton mainbtn8 = new JButton("");
        Image imghome = new ImageIcon(this.getClass().getResource("/home.png")).getImage();
        mainbtn8.setIcon(new ImageIcon(imghome));
        mainbtn8.setBounds(10, 3, 73, 32);
        uppanel.add(mainbtn8);

        JPanel downpanel = new JPanel();
        contentPane.add(downpanel);
        downpanel.setBounds(0, 435, 862, 37);
        downpanel.setLayout(null);
        downpanel.setBackground(Color.DARK_GRAY);

        JButton mainbtn6 = new JButton("QUIT");
        mainbtn6.setForeground(Color.WHITE);
        mainbtn6.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn6.setBounds(751, 3, 80, 32);
        downpanel.add(mainbtn6);

        JPanel submainpanel_1 = new JPanel();
        submainpanel_1.setBounds(10, 74, 814, 216);
        mainpanel.add(submainpanel_1);
        submainpanel_1.setLayout(null);

        JButton mainbtn2 = new JButton("RECORD");
        mainbtn2.setForeground(Color.WHITE);
        mainbtn2.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn2.setBounds(10, 60, 242, 145);
        submainpanel_1.add(mainbtn2);

        JButton mainbtn3 = new JButton("LIVE DISTRUBITION");
        mainbtn3.setForeground(Color.WHITE);
        mainbtn3.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn3.setBounds(279, 60, 242, 145);
        submainpanel_1.add(mainbtn3);

        JButton mainbtn4 = new JButton("REPLAY");
        mainbtn4.setForeground(Color.WHITE);
        mainbtn4.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        mainbtn4.setBounds(562, 60, 242, 145);
        submainpanel_1.add(mainbtn4);

        JLabel mainlbl1 = new JLabel("SYSTEM OPERATIONS");
        mainlbl1.setForeground(Color.WHITE);
        mainlbl1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        mainlbl1.setHorizontalAlignment(SwingConstants.CENTER);
        mainlbl1.setBounds(10, 11, 193, 38);
        submainpanel_1.add(mainlbl1);

        JPanel rcrdpanel = new JPanel();
        rcrdpanel.setLayout(null);
        rcrdpanel.setBounds(0, 37, 862, 398);
        contentPane.add(rcrdpanel);
        rcrdpanel.setLayout(null);

        JPanel subrcrdpanel1 = new JPanel();
        subrcrdpanel1.setLayout(null);
        subrcrdpanel1.setBounds(10, 320, 825, 81);
        rcrdpanel.add(subrcrdpanel1);

        JLabel rcrdlbl1 = new JLabel("?? time");
        rcrdlbl1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        rcrdlbl1.setForeground(Color.WHITE);
        rcrdlbl1.setHorizontalAlignment(SwingConstants.CENTER);
        rcrdlbl1.setBounds(10, 5, 78, 25);
        subrcrdpanel1.add(rcrdlbl1);

        JLabel rcrdlbl2 = new JLabel("Record Name");
        rcrdlbl2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        rcrdlbl2.setForeground(Color.WHITE);
        rcrdlbl2.setHorizontalAlignment(SwingConstants.CENTER);
        rcrdlbl2.setBounds(230, 5, 100, 25);
        subrcrdpanel1.add(rcrdlbl2);

        JButton rcrdbtn1 = new JButton("Start");
        rcrdbtn1.setBounds(475, 30, 162, 36);
        rcrdbtn1.setForeground(Color.WHITE);
        subrcrdpanel1.add(rcrdbtn1);

        JComboBox rcrdcombo1 = new JComboBox();
        rcrdcombo1.setBounds(10, 30, 78, 36);
        rcrdcombo1.setForeground(Color.WHITE);
        rcrdcombo1.setModel(new DefaultComboBoxModel(new String[]{"30", "45", "60"}));
        subrcrdpanel1.add(rcrdcombo1);

        JButton rcrdbtn2 = new JButton("Stop");
        rcrdbtn2.setBounds(655, 30, 162, 36);
        rcrdbtn2.setForeground(Color.WHITE);
        subrcrdpanel1.add(rcrdbtn2);

        rcrdtxt = new JTextField();
        rcrdtxt.setColumns(10);
        rcrdtxt.setBounds(102, 30, 360, 36);
        rcrdtxt.setForeground(Color.white);

        subrcrdpanel1.add(rcrdtxt);

        JLabel rcrdlbl3 = new JLabel("RECORDING STATUS");
        rcrdlbl3.setHorizontalAlignment(SwingConstants.CENTER);
        rcrdlbl3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rcrdlbl3.setForeground(Color.WHITE);
        rcrdlbl3.setBounds(10, 11, 794, 25);
        rcrdpanel.add(rcrdlbl3);

        JScrollPane rcrdscroll = new JScrollPane();
        rcrdscroll.setBounds(10, 36, 825, 280);
        rcrdpanel.add(rcrdscroll);
        rcrdtable = new JTable();
        rcrdscroll.setViewportView(rcrdtable);

        /*Replay panel
		 * 
         */
        JPanel rplypanel = new JPanel();
        rplypanel.setBounds(0, 37, 862, 398);
        contentPane.add(rplypanel);
        rplypanel.setLayout(null);

        JButton rplybtn_1 = new JButton("Search");
        rplybtn_1.setForeground(Color.WHITE);
        rplybtn_1.setBounds(500, 40, 150, 38);
        rplypanel.add(rplybtn_1);

        JButton rplybtn_2 = new JButton("Refresh");
        rplybtn_2.setForeground(Color.WHITE);
        rplybtn_2.setBounds(665, 40, 150, 38);
        rplypanel.add(rplybtn_2);

        rplytxt = new JTextField();
        rplytxt.setBackground(Color.LIGHT_GRAY);
        rplytxt.setForeground(Color.WHITE);
        rplytxt.setColumns(10);
        rplytxt.setBounds(10, 40, 475, 38);
        rplypanel.add(rplytxt);

        JPanel subrplypanel_1 = new JPanel();
        subrplypanel_1.setLayout(null);
        subrplypanel_1.setBounds(10, 325, 825, 80);
        rplypanel.add(subrplypanel_1);

        JButton rplybtn_3 = new JButton("MARK");
        rplybtn_3.setForeground(Color.WHITE);
        rplybtn_3.setBounds(670, 30, 145, 38);
        subrplypanel_1.add(rplybtn_3);

        JButton rplybtn_4 = new JButton("START");
        rplybtn_4.setForeground(Color.WHITE);
        rplybtn_4.setBounds(350, 30, 145, 38);
        subrplypanel_1.add(rplybtn_4);

        JButton rplybtn_5 = new JButton("STOP");
        rplybtn_5.setForeground(Color.WHITE);
        rplybtn_5.setBounds(510, 30, 145, 38);
        subrplypanel_1.add(rplybtn_5);

        JComboBox rplycombo = new JComboBox();
        rplycombo.setBounds(10, 30, 320, 38);
        subrplypanel_1.add(rplycombo);

        JLabel rplylbl1 = new JLabel("REPLAY PANEL");
        rplylbl1.setHorizontalAlignment(SwingConstants.CENTER);
        rplylbl1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rplylbl1.setForeground(Color.WHITE);
        rplylbl1.setBounds(320, 9, 180, 39);
        rplypanel.add(rplylbl1);

        JLabel rplylbl = new JLabel("Destination");
        rplylbl.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        rplylbl.setForeground(Color.WHITE);
        rplylbl.setHorizontalAlignment(SwingConstants.CENTER);
        rplylbl.setBounds(120, 5, 100, 25);
        subrplypanel_1.add(rplylbl);

        JScrollPane rplyscroll = new JScrollPane();
        rplyscroll.setBounds(10, 85, 825, 235);
        rplypanel.add(rplyscroll);
        rplytable = new JTable();
        rplyscroll.setViewportView(rplytable);

        /*
		 * distrubition panel
         */
        JPanel dstpanel = new JPanel();
        dstpanel.setBounds(0, 37, 862, 398);
        contentPane.add(dstpanel);
        dstpanel.setLayout(null);

        JScrollPane dstscroll = new JScrollPane();
        dstscroll.setBounds(10, 42, 582, 359);
        dstpanel.add(dstscroll);

        dsttable = new JTable();
        dstscroll.setViewportView(dsttable);

        JComboBox dstcombo1 = new JComboBox();
        dstcombo1.setBounds(610, 66, 214, 44);
        dstpanel.add(dstcombo1);

        JComboBox dstcombo2 = new JComboBox();
        dstcombo2.setBounds(610, 162, 214, 44);
        dstpanel.add(dstcombo2);

        JLabel dstlbl1 = new JLabel("Source");
        dstlbl1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dstlbl1.setForeground(Color.WHITE);
        dstlbl1.setHorizontalAlignment(SwingConstants.CENTER);
        dstlbl1.setBounds(610, 27, 220, 38);
        dstpanel.add(dstlbl1);

        JLabel dstlbl2 = new JLabel("Destination");
        dstlbl2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dstlbl2.setForeground(Color.WHITE);
        dstlbl2.setHorizontalAlignment(SwingConstants.CENTER);
        dstlbl2.setBounds(610, 121, 214, 38);
        dstpanel.add(dstlbl2);

        JButton dstbtn1 = new JButton("Start");
        dstbtn1.setBounds(610, 227, 214, 44);
        dstbtn1.setForeground(Color.WHITE);
        dstpanel.add(dstbtn1);

        JButton dstbtn2 = new JButton("Stop");
        dstbtn2.setBounds(610, 282, 214, 44);
        dstbtn2.setForeground(Color.WHITE);
        dstpanel.add(dstbtn2);

        JLabel dstlbl3 = new JLabel("DISTRUBITION PANEL");
        dstlbl3.setHorizontalAlignment(SwingConstants.CENTER);
        dstlbl3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        dstlbl3.setForeground(Color.WHITE);
        dstlbl3.setBounds(206, 9, 180, 39);
        dstpanel.add(dstlbl3);
        JPanel sttngpanel = new JPanel();
        sttngpanel.setBounds(0, 37, 862, 398);
        contentPane.add(sttngpanel);
        sttngpanel.setLayout(null);

        JPanel statuspanel = new JPanel();
        statuspanel.setBounds(0, 37, 862, 398);
        contentPane.add(statuspanel);
        statuspanel.setLayout(null);

        JPanel bospanel = new JPanel();
        bospanel.setBounds(0, 0, 862, 37);
        contentPane.add(bospanel);
        bospanel.setLayout(null);
        mainpanel.setBackground(dpanel);
        submainpanel_1.setBackground(dpanel);
        mainbtn2.setBackground(dcomponent);
        mainbtn3.setBackground(dcomponent);
        rplypanel.setBackground(dpanel);
        rplybtn_1.setBackground(dcomponent);
        rplybtn_2.setBackground(dcomponent);
        subrplypanel_1.setBackground(dspanel);
        rplybtn_3.setBackground(dcomponent);
        rplybtn_4.setBackground(dcomponent);
        rplybtn_5.setBackground(dcomponent);
        dstpanel.setBackground(dpanel);
        dstbtn1.setBackground(dcomponent);
        dstbtn2.setBackground(dcomponent);
        maintglbtn.setBackground(dcomponent);
        mainbtn8.setBackground(dcomponent);
        rcrdpanel.setBackground(dpanel);
        subrcrdpanel1.setBackground(dspanel);
        rcrdbtn1.setBackground(dcomponent);
        rcrdcombo1.setBackground(dcomponent);
        rcrdbtn2.setBackground(dcomponent);
        rcrdtxt.setBackground(dcomponent);
        mainbtn4.setBackground(dcomponent);
        mainbtn6.setBackground(dcomponent);
        rplytxt.setBackground(dcomponent);
        mainbtn10.setBackground(dcomponent);
        sttngpanel.setBackground(dpanel);
        statuspanel.setBackground(dpanel);

        maintglbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (maintglbtn.isSelected() == true) {
                    maintglbtn.setIcon(new ImageIcon(imgsun));

                    dpanel = Colors.lightpanel;
                    dspanel = Colors.lightsubpanel;
                    dcomponent = Colors.lightcomponents;

                } else {
                    maintglbtn.setIcon(new ImageIcon(imgmoon));
                    dpanel = Colors.darkpanel;
                    dspanel = Colors.darksubpanel;
                    dcomponent = Colors.darkcomponents;

                }
                mainpanel.setBackground(dpanel);
                submainpanel_1.setBackground(dpanel);
                dstbtn2.setBackground(dcomponent);
                mainbtn2.setBackground(dcomponent);
                maintglbtn.setBackground(dcomponent);
                mainbtn3.setBackground(dcomponent);
                mainbtn4.setBackground(dcomponent);
                mainbtn6.setBackground(dcomponent);
                rcrdpanel.setBackground(dpanel);
                subrcrdpanel1.setBackground(dspanel);
                rcrdbtn1.setBackground(dcomponent);
                rcrdcombo1.setBackground(dcomponent);
                rcrdbtn2.setBackground(dcomponent);
                rcrdtxt.setBackground(dcomponent);
                subrcrdpanel1.setBackground(dspanel);
                rplypanel.setBackground(dpanel);
                rplybtn_1.setBackground(dcomponent);
                rplybtn_2.setBackground(dcomponent);
                subrplypanel_1.setBackground(dspanel);
                rplybtn_3.setBackground(dcomponent);
                rplybtn_4.setBackground(dcomponent);
                rplybtn_5.setBackground(dcomponent);
                dstpanel.setBackground(dpanel);
                dstbtn1.setBackground(dcomponent);
                mainbtn8.setBackground(dcomponent);
                rplytxt.setBackground(dcomponent);
                mainbtn10.setBackground(dcomponent);
                sttngpanel.setBackground(dpanel);
                statuspanel.setBackground(dpanel);
            }
        });

        rcrdpanel.setVisible(false);
        rplypanel.setVisible(false);
        dstpanel.setVisible(false);
        bospanel.setVisible(false);
        mainbtn10.setVisible(false);
        maintglbtn.setVisible(true);
        sttngpanel.setVisible(false);
        statuspanel.setVisible(false);

        mainbtn8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(true);
                rcrdpanel.setVisible(false);
                rplypanel.setVisible(false);
                dstpanel.setVisible(false);
                sttngpanel.setVisible(false);
                statuspanel.setVisible(false);
            }
        });
        mainbtn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(false);
                rcrdpanel.setVisible(false);
                rplypanel.setVisible(false);
                dstpanel.setVisible(true);
                sttngpanel.setVisible(false);
                statuspanel.setVisible(false);
            }
        });

        mainbtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(false);
                rcrdpanel.setVisible(true);
                rplypanel.setVisible(false);
                dstpanel.setVisible(false);

                sttngpanel.setVisible(false);
                statuspanel.setVisible(false);

            }

        });

        mainbtn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        mainbtn10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(false);
                rcrdpanel.setVisible(false);
                rplypanel.setVisible(false);
                dstpanel.setVisible(false);
                sttngpanel.setVisible(true);
                statuspanel.setVisible(false);
            }
        });

        mainbtn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(false);
                rcrdpanel.setVisible(false);
                rplypanel.setVisible(false);
                dstpanel.setVisible(false);
                sttngpanel.setVisible(false);
                statuspanel.setVisible(true);
            }
        });

        rplytxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isAlphabetic(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || Character.isDigit(c))) {
                    e.consume();  // ignore the event if it's not an alphabet
                }
            }
        });
        rcrdtxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isAlphabetic(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE || Character.isDigit(c))) {
                    e.consume();  // ignore the event if it's not an alphabet
                }
            }
        });

        String rplyheader[] = new String[]{"Records"};
        modelrply.setColumnIdentifiers(rplyheader);
        String rcrdheader[] = new String[]{"Source", "Status", "Record Name", "REC Start Time", "Availability"};
        modelrcrd.setColumnIdentifiers(rcrdheader);
        String dstheader[] = new String[]{"Source", ">>>>", "Destination"};
        modeldst.setColumnIdentifiers(dstheader);

        rplytable.setModel(modelrply);
        rcrdtable.setModel(modelrcrd);
        dsttable.setModel(modeldst);
        dsttable.getColumnModel().getColumn(0).setPreferredWidth(190);
        dsttable.getColumnModel().getColumn(1).setPreferredWidth(15);
        dsttable.getColumnModel().getColumn(2).setPreferredWidth(190);

        {
            try {
                File xmlfile = new File("XMLFile.xml");
                DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
                DocumentBuilder dbbuild = dbfac.newDocumentBuilder();
                Document xmldoc = dbbuild.parse(xmlfile);
                xmldoc.getDocumentElement().normalize();
                NodeList nodeList = xmldoc.getElementsByTagName("module");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        rplycombo.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());
                        dstcombo1.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());
                        dstcombo2.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());

                        modelrcrd.addRow(new Object[]{
                            eElement.getElementsByTagName("id").item(0).getTextContent(),
                            "Available",
                            "recordname",
                            "recordtime", "availability"});
                    }
                }
            } catch (Exception f) {
            }
        }

        mainbtn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainpanel.setVisible(false);
                rcrdpanel.setVisible(false);
                rplypanel.setVisible(true);
                dstpanel.setVisible(false);
                sttngpanel.setVisible(false);
                statuspanel.setVisible(false);

                for (int l = modelrply.getRowCount() - 1; l >= 0; l--) {
                    modelrply.removeRow(l);
                }
                List<String> liste = statusModel.getReplayFiles();
                for (int k = 0; k < liste.size(); k++) {
                    modelrply.addRow(new Object[]{liste.get(k)});
                }
            }
        });

        rplybtn_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File folder2 = new File("/var/tmp/videos/");
                File[] filelist2 = folder2.listFiles();

                rplytxt.setText(null);

                for (int l = modelrply.getRowCount() - 1; l >= 0; l--) {
                    modelrply.removeRow(l);
                }

                for (int k = 0; k < filelist2.length; k++) {
                    modelrply.addRow(new Object[]{filelist2[k].getName()});
                }

            }
        });

        rplybtn_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = rplytxt.getText();
                File folder3 = new File("/var/tmp/videos/");
                File[] filelist3 = folder3.listFiles();
                for (int l = modelrply.getRowCount() - 1; l >= 0; l--) {
                    modelrply.removeRow(l);
                }
                for (int g = 0; g < filelist3.length; g++) {
                    boolean index = filelist3[g].getName().contains(text);
                    if (index) {
                        modelrply.addRow(new Object[]{filelist3[g].getName()});
                    }
                }
            }
        });

        dstbtn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                if (dsttable.getRowCount() > 0) {
                    for (i = 0; i < dsttable.getRowCount(); i++) {
                        if (dstcombo1.getSelectedItem().toString().equals(dsttable.getValueAt(i, 0)) && dstcombo2.getSelectedItem().toString().equals(dsttable.getValueAt(i, 2))) {
                            break;
                        }
                    }
                }

                if (i == dsttable.getRowCount()) {
                    modeldst.addRow(new Object[]{
                        dstcombo1.getSelectedItem().toString(),
                        "        >>>>",
                        dstcombo2.getSelectedItem().toString()});
                    Map<String, String> properties = new HashMap<String, String>();
                    properties.put("FROM", dstcombo1.getSelectedItem().toString());
                    properties.put("TO", dstcombo2.getSelectedItem().toString());
                    properties.put("MULTICASTIP", "127.0.0.1");
                    properties.put("MULTICASTPORT", "1234");
                    try {
                        ActionProducer.Send("STREAM", "START", properties);
                    } catch (Exception ex) {
                        Logger.getLogger(csvnUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ScheduledThreadPoolExecutor statusThread = new ScheduledThreadPoolExecutor(1);
        statusThread.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    guncelle(App.vericek(), mainbtn9);
                    try {
                        List<Record> liste = statusModel.getOpconRecordStatus();
                        List<Boolean> durumliste = statusModel.getOpconPingStatus();
                        for (int i = 0; i < liste.size(); i++) {
                            modelrcrd.setValueAt(liste.get(i).getSource(), i, 0);
                            modelrcrd.setValueAt(liste.get(i).getStatus() ? "Available" : "Recording", i, 1);
                            modelrcrd.setValueAt(liste.get(i).getName(), i, 2);
                            modelrcrd.setValueAt(nonNull(liste.get(i).getStartTime()) ? DateConverter.longToStringDate(Long.valueOf(String.valueOf(liste.get(i).getStartTime()))) : "", i, 3);
                            modelrcrd.setValueAt(durumliste.get(i) ? "Connected" : "Not Connected",i,4);

                        }
                        for (int i = 0; i < statusbutton.length; i++) {
                            statusbutton[i].setBackground(statusModel.getOpconPingStatus().get(i) ? Color.GREEN : Color.RED);
                        }

                    } catch (Exception f) {
                    }
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1500, TimeUnit.MILLISECONDS);

        dstbtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, String> properties = new HashMap<String, String>();
                int rownum = dsttable.getSelectedRow();

                properties.put("FROM", dsttable.getValueAt(rownum, 0).toString());
                properties.put("TO", dsttable.getValueAt(rownum, 2).toString());
                properties.put("MULTICASTIP", "127.0.0.1");
                properties.put("MULTICASTPORT", "1234");
                try {
                    ActionProducer.Send("STREAM", "STOP", properties);
                    modeldst.removeRow(rownum);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
        mainbtn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File xmlfile1 = new File("XMLFile.xml");
                    DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dbbuild = dbfac.newDocumentBuilder();
                    Document xmldoc = dbbuild.parse(xmlfile1);
                    xmldoc.getDocumentElement().normalize();
                    NodeList nodeList = xmldoc.getElementsByTagName("module");
                    statusencoderbutton = new JButton[nodeList.getLength()];
                    statusbutton = new JButton[nodeList.getLength()];
                    statuslabel = new JLabel[nodeList.getLength()];
                    int sayac1 = 0;
                    int sayac2 = 0;
                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Node node = nodeList.item(i);
                        Element eElement = (Element) node;

                        statusbutton[i] = new JButton(eElement.getElementsByTagName("id").item(0).getTextContent());
                        statusbutton[i].setBounds(40 + sayac2 * 420, 40 + sayac1 * 60, 100, 50);
                        statusbutton[i].setBackground(dcomponent);
                        statuspanel.add(statusbutton[i]);
                        statusencoderbutton[i] = new JButton("ENCODER/DECODER");
                        statusencoderbutton[i].setBounds(190 + sayac2 * 420, 40 + sayac1 * 60, 200, 50);
                        statusencoderbutton[i].setBackground(dcomponent);
                        statuspanel.add(statusencoderbutton[i]);
                        statuslabel[i] = new JLabel("----");
                        statuslabel[i].setBounds(150 + sayac2 * 420, 40 + sayac1 * 60, 30, 50);
                        statuslabel[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        statuslabel[i].setHorizontalAlignment(SwingConstants.CENTER);
                        statuspanel.add(statuslabel[i]);
                        statuspanel.repaint();
                        sayac1++;
                        if (sayac1 > 5) {
                            sayac1 = 0;
                            sayac2 = 1;
                        }
                    }

                } catch (Exception f) {
                }
            }
        });
        rcrdtable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String rcrdstring = null;
                rcrdstring = rcrdtable.getValueAt(rcrdtable.getSelectedRow(), 1).toString();
                if (rcrdstring.equals("Available")) {
                    rcrdbtn1.setEnabled(true);
                    rcrdbtn2.setEnabled(false);
                } else {
                    rcrdbtn1.setEnabled(true);
                    rcrdbtn2.setEnabled(true);
                }

            }
        });

        rcrdbtn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, String> properties = new HashMap<String, String>();
                int rownum = rcrdtable.getSelectedRow();

                properties.put("FROM", rcrdtable.getValueAt(rownum, 0).toString());
                properties.put("MULTICASTIP", "127.0.0.1");
                properties.put("MULTICASTPORT", "1234");
                properties.put("PERIOD",rcrdcombo1.getSelectedItem().toString());
                properties.put("PRIORITY","NORMAL");
                properties.put("NAME",rcrdtxt.getText());
                try {
                    ActionProducer.Send("RECORD", "START", properties);
                    
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        rcrdbtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, String> properties = new HashMap<String, String>();
                int rownum = rcrdtable.getSelectedRow();

                properties.put("FROM", rcrdtable.getValueAt(rownum, 0).toString());
                properties.put("MULTICASTIP", "127.0.0.1");
                properties.put("MULTICASTPORT", "1234");
                //properties.put("PERIOD",rcrdcombo1.getSelectedItem().toString());
                properties.put("PRIORITY","NORMAL");
                properties.put("NAME",rcrdtable.getValueAt(rownum, 2).toString());
                try {
                    ActionProducer.Send("RECORD", "STOP", properties);
                    
                    
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        rplybtn_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String destination = rplycombo.getSelectedItem().toString();
                String vidFileName = rplytable.getValueAt(rplytable.getSelectedRow(), 0).toString();
                Map<String, String> properties = new HashMap<String, String>();
                properties.put("FILE", vidFileName);
                properties.put("TO", destination);
                properties.put("MULTICASTIP", "127.0.0.1");
                properties.put("MULTICASTPORT", "1234");
                
                try {
                    ActionProducer.Send("REPLAY", "START", properties);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        rplybtn_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String destination = rplycombo.getSelectedItem().toString();
                String vidFileName = rplytable.getValueAt(rplytable.getSelectedRow(), 0).toString();
                Map<String, String> properties = new HashMap<String, String>();
                properties.put("FILE", vidFileName);
                properties.put("TO", destination);
                properties.put("MULTICASTIP", "127.0.0.1");
                properties.put("MULTICASTPORT", "1234");
                try {
                    ActionProducer.Send("REPLAY", "STOP", properties);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
    }

    /**
     *
     * @param status
     * @param mainbtn9
     */
}
