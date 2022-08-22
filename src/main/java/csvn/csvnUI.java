package staj1;

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

public class staj1whole extends JFrame {

	private JPanel contentPane;
	private JTextField rcrdtxt;
	private JTextField rplytxt;
	
	Color dpanel = new Color(170,170,170);
	Color dspanel = new Color(140,140,140);
	Color dcomponent = new Color(130,130,130);
	
	DefaultTableModel modeldst = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {                
            return false;               
    };
	};
	DefaultTableModel modelrcrd = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {                
            return false;               
    };
	};
	DefaultTableModel modelrply = new DefaultTableModel() {
		public boolean isCellEditable(int row, int column) {                
            return false;               
    };
	};
	
	private JTable dsttable =new JTable(modeldst);
	private JTable rcrdtable=new JTable(modelrcrd);
	private JTable rplytable=new JTable(modelrply);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staj1whole frame = new staj1whole();
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
	public staj1whole() {
		colors colors= new colors();

		
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
				
				JButton mainbtn9 = new JButton("STORAGE %0");
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
				rcrdcombo1.setModel(new DefaultComboBoxModel(new String[] {"30", "45", "60"}));
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
			if(maintglbtn.isSelected()==true) {
				maintglbtn.setIcon(new ImageIcon(imgsun));
				dpanel = colors.lightpanel;
				dspanel = colors.lightsubpanel;
				dcomponent = colors.lightcomponents;
				
			}
			else {
				maintglbtn.setIcon(new ImageIcon(imgmoon));
				dpanel = colors.darkpanel;
				dspanel = colors.darksubpanel;
				dcomponent = colors.darkcomponents;
				
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
			             if(!(Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE || Character.isDigit(c))) {
			                 e.consume();  // ignore the event if it's not an alphabet
			             }
			         }
			      });
				rcrdtxt.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if(!(Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE || Character.isDigit(c))) {
			                 e.consume();  // ignore the event if it's not an alphabet
			             }
			         }
			      });
	
				String rplyheader[] = new String[] {"Records"};
				modelrply.setColumnIdentifiers(rplyheader);
				String rcrdheader[] = new String[] {"Source", "Status", "Record Name", "REC Start Time"};
				modelrcrd.setColumnIdentifiers(rcrdheader);
				String dstheader[] = new String[] { "Source", ">>>>","Destination" };
				modeldst.setColumnIdentifiers(dstheader);
				
				rplytable.setModel(modelrply);
				rcrdtable.setModel(modelrcrd);
				dsttable.setModel(modeldst);
				dsttable.getColumnModel().getColumn(0).setPreferredWidth(190);
				dsttable.getColumnModel().getColumn(1).setPreferredWidth(15);
				dsttable.getColumnModel().getColumn(2).setPreferredWidth(190);

		{  
			try   
			{  
				File xmlfile = new File("XMLFile.xml");  
				DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();   
				DocumentBuilder dbbuild = dbfac.newDocumentBuilder();  
				Document xmldoc = dbbuild.parse(xmlfile);  
				xmldoc.getDocumentElement().normalize();  
				NodeList nodeList = xmldoc.getElementsByTagName("module");  
				for (int i = 0; i < nodeList.getLength(); i++)   
				{  
				Node node = nodeList.item(i);  
				if (node.getNodeType() == Node.ELEMENT_NODE)   
				{  
				Element eElement = (Element) node;  
				rplycombo.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());
				dstcombo1.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());
				dstcombo2.addItem(eElement.getElementsByTagName("id").item(0).getTextContent());
				

				modelrcrd.addRow(new Object[] { 
						eElement.getElementsByTagName("id").item(0).getTextContent(),
						"Available",
						"recordname",
						"recordtime"});
				}  
				}  
				}   
			
			catch (Exception f)  
			{  
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
				File folder = new File("C:\\Users\\Taha\\workspace\\staj1\\src\\staj1");
				File[] filelist = folder.listFiles();
				
				for (int l = modelrply.getRowCount()-1; l>=0;l--) {
				    modelrply.removeRow(l);
				}
	
				for(int k = 0;k<filelist.length;k++) {
				modelrply.addRow(new Object[] {filelist[k].getName()});
				}
			}
		});
		
		rplybtn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File folder2 = new File("C:\\Users\\Taha\\workspace\\staj1\\src\\staj1");
				File[] filelist2 = folder2.listFiles();
				
				rplytxt.setText(null);
				
				for (int l = modelrply.getRowCount()-1; l>=0;l--) {
				    modelrply.removeRow(l);
				}
	
				for(int k = 0;k<filelist2.length;k++) {
				modelrply.addRow(new Object[] {filelist2[k].getName()});
				}
				
			}
		});
		
		  
		  rplybtn_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String text = rplytxt.getText();
					File folder3 = new File("C:\\Users\\Taha\\workspace\\staj1\\src\\staj1");
					File[] filelist3 = folder3.listFiles();
					for (int l = modelrply.getRowCount()-1; l>=0;l--) {
					    modelrply.removeRow(l);
					}
					for(int g = 0;g<filelist3.length;g++) {
						boolean index = filelist3[g].getName().contains(text);
						if(index) {
						modelrply.addRow(new Object[] {filelist3[g].getName()});
						}
					}
				}
			});
		
		dstbtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				modeldst.addRow(new Object[] { 
						dstcombo1.getSelectedItem().toString(),
						"        >>>>",
						dstcombo2.getSelectedItem().toString()});
			}
		});
		
		
		dstbtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			modeldst.removeRow(dsttable.getSelectedRow());	
			}
		});
	}
}