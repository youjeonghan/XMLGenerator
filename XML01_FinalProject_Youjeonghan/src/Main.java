import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Serializer;
import org.apache.xml.serialize.SerializerFactory;
import org.apache.xml.serialize.XMLSerializer;
import java.io.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.*;

public class Main extends JFrame {
	public static Document doc;
	public static int check_root;
	public static int check_attribute;
	public boolean ccc;
	Color mousein = new Color(50, 188, 223);
	Color backgroundC = new Color(70, 70, 70);
	Color ComponentC = new Color(150, 150, 150);
	JFrame UI = new JFrame();
	JPanel manager;
	JLabel a[];
	JTextArea manResult;
	JButton MBs[];
	File file;
	MyMouseListener listener;
	public static Node temp1;
	public static NodeList temp2;

	Font font = new Font("Serit", Font.BOLD, 18);
	Font font2 = new Font("Serit", Font.BOLD, 13);
	GridBagConstraints gbM1;
	GridBagLayout layoutM, layoutU;

	public Main() {
		GridLayout ad = new GridLayout(1, 2);
		UI.setLayout(ad);
		UI.setTitle("MiniProject1-유정한-16011019");
		UI.setSize(1500, 900);
		UI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Manager();
		UI.add(manager);
		UI.setVisible(true);
		addmouselistener();
	}

	public void addmouselistener() {
		listener = new MyMouseListener();
		for (int i = 0; i < 9; i++) {
			MBs[i].addMouseListener(listener);
		}
	}

	public void Manager() {
		manager = new JPanel();
		manager.setBackground(backgroundC);
		manager.setSize(700, 400);
		manager.setBorder(BorderFactory.createLineBorder(backgroundC, 10));
		gbM1 = new GridBagConstraints();
		layoutM = new GridBagLayout();

		MBs = new JButton[9];
		a = new JLabel[1];

		manager.setLayout(layoutM);
		gbM1.fill = GridBagConstraints.BOTH;
		gbM1.weightx = 1.0;
		gbM1.weighty = 1.0;
		MBInit();
		labeling();
		MBInsert(MBs);
		MBs[0].addActionListener(new ActionListenerLoad());
		MBs[1].addActionListener(new ActionListenerMake());
		MBs[2].addActionListener(new ActionListenerFind());
		MBs[3].addActionListener(new ActionListenerSave());
		MBs[4].addActionListener(new ActionListenerPrint());
		MBs[5].addActionListener(new ActionListenerInsert());
		MBs[6].addActionListener(new ActionListenerUpdate());
		MBs[7].addActionListener(new ActionListenerDelete());
		MBs[8].addActionListener(new ActionListenerExit());
	}

	public void labeling() {
		String title[] = { "                                load파일 >> 없음                                " };
		for (int r = 0; r < 1; r++) {
			a[r] = new JLabel(title[r]);
			a[r].setBackground(ComponentC);
			a[r].setOpaque(true);
			a[r].setBorder(BorderFactory.createLineBorder(backgroundC, 2));
			a[r].setFont(font);
			a[r].setHorizontalAlignment(SwingConstants.CENTER);
			makeM(a[r], 0, r + 1, 1, 1);
			manager.add(a[r]);
		}
	}

	public void MBInsert(JButton MB[]) {
		String[] text = { "Load", "Make", "Find", "Save", "Print", "Insert", "Update", "Delete", "Exit" };

		for (int i = 0; i < 9; i++) {
			MB[i] = new JButton(text[i]);
			MB[i].setFont(font);
			makeM(MB[i], i + 1, 1, 1, 1);
			manager.add(MB[i]);
			MB[i].setBorder(BorderFactory.createLineBorder(backgroundC, 2));
			MB[i].setBackground(ComponentC);
		}
	}

	public void MBInit() {
		manResult = new JTextArea();
		manResult.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(manResult);
		makeMScroll(scrollPane, 0, 3, 12, 6);
		scrollPane.setBorder(BorderFactory.createLineBorder(backgroundC, 2));
		manager.add(scrollPane);

	}

	public void makeM(JComponent c, int x, int y, int w, int h) {
		gbM1.gridx = x;
		gbM1.gridy = y;
		gbM1.gridwidth = w;
		gbM1.gridheight = h;
		layoutM.setConstraints(c, gbM1);
	}

	public void makeMScroll(JComponent c, int x, int y, int w, int h) {
		gbM1.gridx = x;
		gbM1.gridy = y;
		gbM1.gridwidth = w;
		gbM1.gridheight = h;
		gbM1.weighty = 50;
		layoutM.setConstraints(c, gbM1);
		gbM1.weighty = 1;
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setBackground(mousein);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setBackground(ComponentC);
		}

	}

	////////// input 창 class//////
	public class Input implements ActionListener {
		int size;
		String table = "";
		String schema[];
		JFrame input = new JFrame();
		JButton In = new JButton("확인");
		JLabel[] labels;
		JTextArea[] textarea;
		JPanel[] pls;
		String[] results;

		public Input(String table, int size, String schema[]) {
			this.size = size;
			this.schema = schema;
			this.table = table;
			labels = new JLabel[size];
			textarea = new JTextArea[size];
			pls = new JPanel[size];
			results = new String[size];

			init();
			input.setVisible(true);
			input.setLayout(new FlowLayout());
			input.setBounds(700, 100, 800, 200);

		}

		public void init() {
			input.setTitle("입력하세요");

			for (int i = 0; i < size; i++) {
				labels[i] = new JLabel(schema[i]);
				textarea[i] = new JTextArea(1, 50);
				pls[i] = new JPanel();
				pls[i].add(labels[i]);
				pls[i].add(textarea[i]);
				input.add(pls[i]);

			}

			In.addActionListener(this);
			input.add(In);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < size; i++) {
				results[i] = textarea[i].getText();
				System.out.println(results[i]);
			}
			input.dispose();
		}
	}

	public class makeInput implements ActionListener {
		int size;
		String table = "";
		String schema[];
		JFrame input = new JFrame();
		JButton In = new JButton("확인");
		JLabel[] labels;
		JTextArea[] textarea;
		JPanel[] pls;
		String[] results;

		int count = 0;
		String check_num = "-1";

		public makeInput(String table, int size, String schema[]) {
			this.size = size;
			this.schema = schema;
			this.table = table;
			labels = new JLabel[size];
			textarea = new JTextArea[size];
			pls = new JPanel[size];
			results = new String[size];

			init();
			input.setVisible(true);
			input.setLayout(new FlowLayout());
			input.setBounds(700, 100, 900, 300); // 시작점 가로, 세로, 입력창 가로, 세로

		}

		public void init() {
			input.setTitle("Make");
			pls[0] = new JPanel();
			labels[0] = new JLabel("삽입할 노드타입 1: element   2: attribute   3: comment   4: text");
			pls[0].add(labels[0]);
			input.add(pls[0]);
			for (int i = 0; i < size; i++) {
				labels[i] = new JLabel(schema[i]);
				textarea[i] = new JTextArea(1, 50);
				pls[i] = new JPanel();
				pls[i].add(labels[i]);
				pls[i].add(textarea[i]);
				input.add(pls[i]);

			}

			In.addActionListener(this);
			input.add(In);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < size; i++) {
				results[i] = textarea[i].getText();
			}
			input.dispose();
			try {
				String saveNodetype = results[0];
				String findNode = results[1];
				if (results[2].equals("")) {
					results[2] = "0";
				}
				String nodeOrder = results[2];
				String nodeNmae = results[3];
				String exit = results[4];
				if (results[5].equals("")) {
					results[5] = "0";
				}
				String attribute_value = results[5];

				elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num),
						Integer.parseInt(nodeOrder), nodeNmae, saveNodetype, 1, attribute_value);
				if (!doc.getDocumentElement().getNodeName().equals(findNode)) {

				}
				if (saveNodetype.equals("1") || saveNodetype.equals("4"))
					check_num = JOptionPane.showInputDialog("자식으로 삽일할 부모노드 번호 선택");
				else if (saveNodetype.equals("2"))
					check_num = JOptionPane.showInputDialog("속성을 삽일할 노드 번호 선택");
				else if (saveNodetype.equals("3"))
					check_num = JOptionPane.showInputDialog("주석을 삽일할 노드 번호 선택");
				count = 0;
				check_root = 0;
				manResult.setText("");
				elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num),
						Integer.parseInt(nodeOrder), nodeNmae, saveNodetype, 0, attribute_value);

				if (exit.equals("1")) {
					ccc = false;
					manResult.setText("make 완료");
					return;
				}

			} catch (FactoryConfigurationError e1) {
				// unable to get a document builder factory
				e1.printStackTrace(System.err);
			}
			manResult.setText(" ");
			traverse(doc.getDocumentElement(), "    ");
			if (ccc == true) {
				String schema[] = { "저장할 노드타입 (숫자 입력)", "어떤 노드자식에 추가할지", "몇번째에 추가할지(0부터 시작 / Attribute, Text는 입력 무관)",
						"    저장명    ", "더 이상 추가 안할시 1입력", "Attribute 밸류값(속성 입력외는 무관)" };
				makeInput Doc = new makeInput("Make", 6, schema);
			}
		}

		public void traverse(Node node, String indent) {
			if (node == null)
				return;

			int type = node.getNodeType();
			switch (type) {
			case Node.DOCUMENT_NODE:
				manResult.append(indent + "[Document] " + node.getNodeName() + "\n");
				break;

			case Node.ENTITY_NODE:
				manResult.append(indent + "[ENTITY] " + node.getNodeName() + "\n");
				break;

			case Node.ELEMENT_NODE:
				manResult.append(indent + "[Element");
				if (node.hasAttributes()) {
					NamedNodeMap attr = node.getAttributes();
					for (int i = 0; i < attr.getLength(); i++) {
						manResult.append(
								" " + attr.item(i).getNodeName() + "=" + "\"" + attr.item(i).getNodeValue() + "\"");
					}
				}
				manResult.append("] " + node.getNodeName() + "\n");
				break;

			case Node.ENTITY_REFERENCE_NODE:
				manResult.append(indent + "[ENTITY_REFERENCE] " + node.getNodeName());
				break;

			case Node.CDATA_SECTION_NODE:
				manResult.append(indent + "[CDATA_SECTION] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;

			case Node.COMMENT_NODE:
				manResult.append(indent + "[COMMENT] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;

			case Node.TEXT_NODE:
				manResult.append(indent + "[TEXT] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;
			}

			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++)
					traverse(children.item(i), indent + "    ");
			}
		}

		public void elementNodeFind(Node node, String eleName, int check_num, int nodeOrder, String nodeNmae,
				String saveNodetype, int show, String attribute_value) {
			if (node == null)
				return;
			NodeList children = node.getChildNodes();

			// 루트노드의 자식에 삽입할 경우
			if (doc.getDocumentElement().getNodeName().equals(eleName)) {
				if (check_root++ == 0) {
					manResult.append((count) + ": [루트 노드] " + doc.getDocumentElement().getNodeName());
				}

				Node root_node = doc.getDocumentElement();
				// save타입이 element
				if (check_num == count && saveNodetype.equals("1")) {
					NodeList children1 = root_node.getChildNodes();
					Element item = doc.createElement(nodeNmae);

					if (nodeOrder > children1.getLength()) {
						manResult.setText(children1.getLength() + "번째 노드가 마지막노드입니다. (0부터 시작)");
					} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE
							&& children1.getLength() == 1) {
						root_node.appendChild(item);
						root_node.appendChild(doc.createTextNode(""));
						manResult.setText("삽입 성공");
					} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
						root_node.insertBefore(item, children1.item(0));
						root_node.insertBefore(doc.createTextNode(""), children1.item(0));
						manResult.setText("삽입 성공");
					} else if (nodeOrder == 0 || children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
						root_node.insertBefore(item, children1.item(nodeOrder));
						manResult.setText("삽입 성공");
						root_node.insertBefore(doc.createTextNode(""), children1.item(nodeOrder));
					} else if (children1.item(nodeOrder).getNodeType() == Node.ELEMENT_NODE) {
						root_node.insertBefore(item, children1.item(nodeOrder));
						manResult.setText("삽입 성공");
						root_node.insertBefore(doc.createTextNode(""), children1.item(nodeOrder + 1));
					}
				}
				return;
			}

			// 루트노드 이외의 삽입
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {

					if (check_num == count && saveNodetype.equals("1")) {
						// save타입이 element
						NodeList children1 = child.getChildNodes();
						Element item = doc.createElement(nodeNmae);

						if (nodeOrder > children1.getLength()) {
							manResult.setText(children1.getLength() + "번째 노드가 마지막노드입니다. (0부터 시작)");
						} else if (nodeOrder == 0 && children.item(nodeOrder).getNodeType() == Node.TEXT_NODE && children1.getLength() == 1) {
							child.appendChild(item);
							child.appendChild(doc.createTextNode(""));
							manResult.setText("삽입 성공");
						} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
							child.insertBefore(item, children1.item(0));
							child.insertBefore(doc.createTextNode(""), children1.item(0));
							manResult.setText("삽입 성공");
						} else if (nodeOrder == 0 || children.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
							child.insertBefore(item, children1.item(nodeOrder));
							manResult.setText("삽입 성공");
							child.insertBefore(doc.createTextNode(""), children1.item(nodeOrder));
						} else if (children.item(nodeOrder).getNodeType() == Node.ELEMENT_NODE) {
							child.insertBefore(item, children1.item(nodeOrder));
							manResult.setText("삽입 성공");
							child.insertBefore(doc.createTextNode(""), children1.item(nodeOrder + 1));
						}
						return;
					}

					else if (check_num == count && saveNodetype.equals("2")) {
						// save타입이 attribute
						Attr item = doc.createAttribute(nodeNmae);
						item.setNodeValue(attribute_value);

						((Element) child).setAttributeNode(item);

						return;
					}

					else if (check_num == count && saveNodetype.equals("3")) {
						// save타입이 comment
						NodeList children1 = node.getChildNodes();
						Comment item = doc.createComment(nodeNmae);

						node.insertBefore(item, children1.item(0));
						node.insertBefore(doc.createTextNode(""), children1.item(0));

						return;
					}

					else if (check_num == count && saveNodetype.equals("4")) {
						// save타입이 text
						NodeList children1 = child.getChildNodes();
						Text item = doc.createTextNode(nodeNmae);

						child.appendChild(item);

						return;
					}
					if (show == 1) {
						manResult.append((count++) + ": [" + getDepth(child) + ", ");
						manResult.append(getElementSiblingIndex(child) + "] ");
						manResult.append(child.getNodeName() + "\n");
					}
				}
				elementNodeFind(child, eleName, check_num, nodeOrder, nodeNmae, saveNodetype, show, attribute_value);
			}
		}

		public void commentNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {
					manResult.append("[" + getDepth(child) + ", ");
					manResult.append(getCommentSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}

				commentNodeFind(child, eleName);
			}
		}

		public void textNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {
					manResult.append("[ " + "부모노드 : " + "[" + getDepth(child) + ", " + getCommentSiblingIndex(child)
							+ "]" + node.getNodeName() + " ] ");
					manResult.append(child.getNodeValue() + "\n");
				}

				textNodeFind(child, eleName);
			}
		}

		public int getDepth(Node node) {
			int index = 0;
			while ((node = node.getParentNode()) != null)
				index++;
			return index;
		}

		protected int getElementSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}

		protected int getCommentSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.ELEMENT_NODE)
					index++;

			return index;
		}

		protected int getTextSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}
	}

	public class insertInput implements ActionListener {
		int size;
		String table = "";
		String schema[];
		JFrame input = new JFrame();
		JButton In = new JButton("확인");
		JLabel[] labels;
		JTextArea[] textarea;
		JPanel[] pls;
		String[] results;

		int count = 0;
		String check_num = "-1";

		public insertInput(String table, int size, String schema[]) {
			this.size = size;
			this.schema = schema;
			this.table = table;
			labels = new JLabel[size];
			textarea = new JTextArea[size];
			pls = new JPanel[size];
			results = new String[size];

			init();
			input.setVisible(true);
			input.setLayout(new FlowLayout());
			input.setBounds(700, 100, 900, 300);

		}

		public void init() {
			input.setTitle("Insert");
			pls[0] = new JPanel();
			labels[0] = new JLabel("삽입할 노드타입 1: element   2: attribute   3: comment   4: text");
			pls[0].add(labels[0]);
			input.add(pls[0]);
			for (int i = 0; i < size; i++) {
				labels[i] = new JLabel(schema[i]);
				textarea[i] = new JTextArea(1, 50);
				pls[i] = new JPanel();
				pls[i].add(labels[i]);
				pls[i].add(textarea[i]);
				input.add(pls[i]);

			}

			In.addActionListener(this);
			input.add(In);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < size; i++) {
				results[i] = textarea[i].getText();
			}
			input.dispose();
			try {
				String saveNodetype = results[0];
				String findNode = results[1];
				if (results[2].equals("")) {
					results[2] = "0";
				}
				String nodeOrder = results[2];
				String nodeNmae = results[3];
				if (results[4].equals("")) {
					results[4] = "0";
				}
				String attribute_value = results[4];

				elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num),
						Integer.parseInt(nodeOrder), nodeNmae, saveNodetype, 1, attribute_value);

				if (saveNodetype.equals("1") || saveNodetype.equals("4"))
					check_num = JOptionPane.showInputDialog("자식으로 삽일할 부모노드 번호 선택");
				else if (saveNodetype.equals("2"))
					check_num = JOptionPane.showInputDialog("속성을 삽일할 노드 번호 선택");
				else if (saveNodetype.equals("3"))
					check_num = JOptionPane.showInputDialog("주석을 삽일할 노드 번호 선택");
				count = 0;
				manResult.setText("");
				elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num),
						Integer.parseInt(nodeOrder), nodeNmae, saveNodetype, 0, attribute_value);

			} catch (FactoryConfigurationError e1) {
				// unable to get a document builder factory
				e1.printStackTrace(System.err);
			}
		}

		public void elementNodeFind(Node node, String eleName, int check_num, int nodeOrder, String nodeNmae,
				String saveNodetype, int show, String attribute_value) {
			if (node == null)
				return;
			NodeList children = node.getChildNodes();

			// 루트노드의 자식에 삽입할 경우
			if (doc.getDocumentElement().getNodeName().equals(eleName)) {
				manResult.append((count) + ": [루트 노드] " + doc.getDocumentElement().getNodeName());

				Node root_node = doc.getDocumentElement();
				// save타입이 element
				if (check_num == count && saveNodetype.equals("1")) {
					NodeList children1 = root_node.getChildNodes();
					Element item = doc.createElement(nodeNmae);

					if (nodeOrder > children1.getLength()) {
						manResult.setText(children1.getLength() + "번째 노드가 마지막노드입니다. (0부터 시작)");
					} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE
							&& children1.getLength() == 1) {
						root_node.appendChild(item);
						root_node.appendChild(doc.createTextNode(""));
						manResult.setText("삽입 성공");
					} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
						root_node.insertBefore(item, children1.item(0));
						root_node.insertBefore(doc.createTextNode(""), children1.item(0));
						manResult.setText("삽입 성공");
					} else if (nodeOrder == 0 || children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
						root_node.insertBefore(item, children1.item(nodeOrder));
						manResult.setText("삽입 성공");
						root_node.insertBefore(doc.createTextNode(""), children1.item(nodeOrder));
					} else if (children1.item(nodeOrder).getNodeType() == Node.ELEMENT_NODE) {
						root_node.insertBefore(item, children1.item(nodeOrder));
						manResult.setText("삽입 성공");
						root_node.insertBefore(doc.createTextNode(""), children1.item(nodeOrder + 1));
					}
					return;
				}
			}

			// 루트노드 이외의 삽입
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {

					// save타입이 element
					if (check_num == count && saveNodetype.equals("1")) {
						NodeList children1 = child.getChildNodes();
						Element item = doc.createElement(nodeNmae);

						if (nodeOrder > children1.getLength()) {
							manResult.setText(children1.getLength() + "번째 노드가 마지막노드입니다. (0부터 시작)");
						} else if (nodeOrder == 0 && children.item(nodeOrder).getNodeType() == Node.TEXT_NODE && children1.getLength() == 1) {
							child.appendChild(item);
							child.appendChild(doc.createTextNode(""));
							manResult.setText("삽입 성공");
						} else if (nodeOrder == 0 && children1.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
							child.insertBefore(item, children1.item(0));
							child.insertBefore(doc.createTextNode(""), children1.item(0));
							manResult.setText("삽입 성공");
						} else if (nodeOrder == 0 || children.item(nodeOrder).getNodeType() == Node.TEXT_NODE) {
							child.insertBefore(item, children1.item(nodeOrder));
							manResult.setText("삽입 성공");
							child.insertBefore(doc.createTextNode(""), children1.item(nodeOrder));
						} else if (children.item(nodeOrder).getNodeType() == Node.ELEMENT_NODE) {
							child.insertBefore(item, children1.item(nodeOrder));
							manResult.setText("삽입 성공");
							child.insertBefore(doc.createTextNode(""), children1.item(nodeOrder + 1));
						}
						return;
					}

					else if (check_num == count && saveNodetype.equals("2")) {
						// save타입이 attribute
						Attr item = doc.createAttribute(nodeNmae);
						item.setNodeValue(attribute_value);

						((Element) child).setAttributeNode(item);

						manResult.setText("삽입 성공");
						return;
					}

					else if (check_num == count && saveNodetype.equals("3")) {
						// save타입이 comment
						NodeList children1 = node.getChildNodes();
						Comment item = doc.createComment(nodeNmae);

						node.insertBefore(item, children1.item(0));
						node.insertBefore(doc.createTextNode(""), children1.item(0));

						manResult.setText("삽입 성공");
						return;
					}

					else if (check_num == count && saveNodetype.equals("4")) {
						// save타입이 text
						NodeList children1 = child.getChildNodes();
						Text item = doc.createTextNode(nodeNmae);

						child.appendChild(item);

						manResult.setText("삽입 성공");
						return;
					}
					if (show == 1) {
						manResult.append((count++) + ": [" + getDepth(child) + ", ");
						manResult.append(getElementSiblingIndex(child) + "] ");
						manResult.append(child.getNodeName() + "\n");
					}
				}

				elementNodeFind(child, eleName, check_num, nodeOrder, nodeNmae, saveNodetype, show, attribute_value);
			}
		}

		public void commentNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {
					manResult.append("[" + getDepth(child) + ", ");
					manResult.append(getCommentSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}

				commentNodeFind(child, eleName);
			}
		}

		public void textNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {
					manResult.append("[ " + "부모노드 : " + "[" + getDepth(child) + ", " + getCommentSiblingIndex(child)
							+ "]" + node.getNodeName() + " ] ");
					manResult.append(child.getNodeValue() + "\n");
				}

				textNodeFind(child, eleName);
			}
		}

		public int getDepth(Node node) {
			int index = 0;
			while ((node = node.getParentNode()) != null)
				index++;
			return index;
		}

		protected int getElementSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}

		protected int getCommentSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.ELEMENT_NODE)
					index++;

			return index;
		}

		protected int getTextSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}
	}

	public class updateInput implements ActionListener {
		int size;
		String table = "";
		String schema[];
		JFrame input = new JFrame();
		JButton In = new JButton("확인");
		JLabel[] labels;
		JTextArea[] textarea;
		JPanel[] pls;
		String[] results;

		int count = 0;
		String check_num = "-1";

		public updateInput(String table, int size, String schema[]) {
			this.size = size;
			this.schema = schema;
			this.table = table;
			labels = new JLabel[size];
			textarea = new JTextArea[size];
			pls = new JPanel[size];
			results = new String[size];

			init();
			input.setVisible(true);
			input.setLayout(new FlowLayout());
			input.setBounds(700, 100, 800, 300);

		}

		public void init() {
			input.setTitle("Insert");
			pls[0] = new JPanel();
			labels[0] = new JLabel("변경할 노드타입 1: element   2: attribute   3: comment   4: text");
			pls[0].add(labels[0]);
			input.add(pls[0]);
			for (int i = 0; i < size; i++) {
				labels[i] = new JLabel(schema[i]);
				textarea[i] = new JTextArea(1, 50);
				pls[i] = new JPanel();
				pls[i].add(labels[i]);
				pls[i].add(textarea[i]);
				input.add(pls[i]);

			}

			In.addActionListener(this);
			input.add(In);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < size; i++) {
				results[i] = textarea[i].getText();
			}
			input.dispose();
			try {
				String updateNodetype = results[0];
				String findNode = results[1];
				String updateName = results[2];
//				if (results[3].equals("")) {
//					results[3] = "0";
//				}
				String attribute_value = results[3];

				if (updateNodetype.equals("1")) {
					manResult.setText("");
					check_attribute = 0;
					elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName);
					
					check_num = JOptionPane.showInputDialog("변경할 노드 번호 선택");
					count = 0;
					elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName);
					check_attribute = 0;
				} else if (updateNodetype.equals("2")) {
					manResult.setText("");
					check_attribute = 0;
					attributeNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName, attribute_value);
					
					check_num = JOptionPane.showInputDialog("변경할 노드 번호 선택");
					count = 0;
					attributeNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName, attribute_value);
					check_attribute = 0;
				} else if (updateNodetype.equals("3")) {
					manResult.setText("");
					check_attribute = 0;
					commentNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName);
					
					check_num = JOptionPane.showInputDialog("변경할 노드 번호 선택");
					count = 0;
					commentNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode,
							updateName);
					check_attribute = 0;
				} else if (updateNodetype.equals("4")) {
					manResult.setText("");
					check_attribute = 0;
					textNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode, updateName);
					
					check_num = JOptionPane.showInputDialog("변경할 노드 번호 선택");
					count = 0;
					textNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num), findNode, updateName);
					check_attribute = 0;
				}

			} catch (FactoryConfigurationError e1) {
				// unable to get a document builder factory
				e1.printStackTrace(System.err);
			}

		}

		// element 노드 찾는 함수
		public void elementNodeFind(Node node, String eleName, int check_num, String findNode, String updateName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {

					if (check_attribute == 0 && check_num == count) {
						Node new_temp = doc.createElement(updateName);
						NodeList children1 = child.getChildNodes();
						for (int j = 0; j < children1.getLength(); j++) {
							new_temp.appendChild(children1.item(j));
						}
						if (child.hasAttributes()) {
							NamedNodeMap attr = child.getAttributes();
							for (int j = 0; j < attr.getLength(); j++) {
								String attrName = attr.item(j).getNodeName();
								String attrValue = attr.item(j).getNodeValue();
								Attr item;
								item = doc.createAttribute(attrName);
								item.setNodeValue(attrValue);

								((Element) new_temp).setAttributeNode(item);
							}
						}
						node.replaceChild(new_temp, child);
						manResult.append("수정완료" + "\n");
						check_attribute = 1;
						return;
					}
					manResult.append((count++) + ": [" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}

				elementNodeFind(child, eleName, check_num, findNode, updateName);
			}
		}

		// attribute 노드 찾는 함수
		public void attributeNodeFind(Node node, String eleName, int check_num, String findNode, String updateName,
				String attribute_value) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.hasAttributes()) {
					NamedNodeMap attr = child.getAttributes();
					for (int j = 0; j < attr.getLength(); j++) {

						if (check_attribute == 0 && attr.item(j).getNodeName().equals(eleName)) {
							if (check_num == count) {
								String attrName = attr.item(j).getNodeName();
								String attrValue = attr.item(j).getNodeValue();

								// 해당 속성삭제
								((Element) child).removeAttributeNode((Attr) attr.item(j));

								// 하고 다시 추가
								Attr item;
								if (attribute_value.equals(""))
									item = doc.createAttribute(attrName);
								else
									item = doc.createAttribute(updateName);

								if (attribute_value.equals(""))
									item.setNodeValue(attrValue);
								else
									item.setNodeValue(attribute_value);

								((Element) child).setAttributeNode(item);
								manResult.append("수정완료" + "\n");
								check_attribute = 1;
								return;
							}
							manResult.append((count++) + ": [" + getDepth(child) + ", ");
							manResult.append(getElementSiblingIndex(child) + "] ");
							manResult.append(child.getNodeName() + "\n");
						}
					}
				}
				attributeNodeFind(child, eleName, check_num, findNode, updateName, attribute_value);
			}
		}

		public void commentNodeFind(Node node, String eleName, int check_num, String findNode, String updateName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)
						&& child.getNodeType() == Node.COMMENT_NODE) {

					if (check_attribute == 0 && check_num == count) {
						child.setNodeValue(updateName);
						manResult.append("수정완료" + "\n");
						check_attribute = 1;
						return;
					}
					manResult.append((count++) + ": [" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}
				commentNodeFind(child, eleName, check_num, findNode, updateName);
			}
		}

		public void textNodeFind(Node node, String eleName, int check_num, String findNode, String updateName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {
					if (check_attribute == 0 && check_num == count) {
						child.setNodeValue(updateName);
						check_attribute = 1;
						return;
					}

					manResult.append((count++) + ": [" + "부모노드 : " + "[" + getDepth(child) + ", "
							+ getCommentSiblingIndex(child) + "]" + node.getNodeName() + " ] ");
					manResult.append(child.getNodeValue() + "\n");
				}

				textNodeFind(child, eleName, check_num, findNode, updateName);
			}
		}

		public int getDepth(Node node) {
			int index = 0;
			while ((node = node.getParentNode()) != null)
				index++;
			return index;
		}

		protected int getElementSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}

		protected int getAttributeSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ELEMENT_NODE)
					index++;

			return index;
		}

		protected int getCommentSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.ELEMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}

		protected int getTextSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}

		public void AddElement(Node node, int nodeOrder, String nodeNmae, String nodeValue) {
			if (node == null)
				return;
			NodeList children = node.getChildNodes();
			Element item = doc.createElement(nodeNmae);
			item.appendChild(doc.createTextNode(nodeValue));
			node.insertBefore(item, children.item(nodeOrder));

			children = node.getChildNodes();

			Node selected_Node = children.item(nodeOrder);
			manResult.setText(selected_Node.getNodeName() + "삽입 성공");

		}
	}

	public class deleteInput implements ActionListener {
		int size;
		String table = "";
		String schema[];
		JFrame input = new JFrame();
		JButton In = new JButton("확인");
		JLabel[] labels;
		JTextArea[] textarea;
		JPanel[] pls;
		String[] results;

		int count = 0;
		String check_num = "-1";

		public deleteInput(String table, int size, String schema[]) {
			this.size = size;
			this.schema = schema;
			this.table = table;
			labels = new JLabel[size];
			textarea = new JTextArea[size];
			pls = new JPanel[size];
			results = new String[size];

			init();
			input.setVisible(true);
			input.setLayout(new FlowLayout());
			input.setBounds(700, 100, 800, 300);

		}

		public void init() {
			input.setTitle("Delete");
			pls[0] = new JPanel();
			labels[0] = new JLabel("삭제할 노드타입 1: element   2: attribute   3: comment   4: text");
			pls[0].add(labels[0]);
			input.add(pls[0]);
			for (int i = 0; i < size; i++) {
				labels[i] = new JLabel(schema[i]);
				textarea[i] = new JTextArea(1, 50);
				pls[i] = new JPanel();
				pls[i].add(labels[i]);
				pls[i].add(textarea[i]);
				input.add(pls[i]);

			}

			In.addActionListener(this);
			input.add(In);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < size; i++) {
				results[i] = textarea[i].getText();
			}
			input.dispose();
			try {
				String deleteNodetype = results[0];
				String findNode = results[1];

				if (deleteNodetype.equals("1"))
					elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("2"))
					attributeNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("3"))
					commentNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("4"))
					textNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				check_num = JOptionPane.showInputDialog("삭제할 노드 번호 선택");
				count = 0;
				if (deleteNodetype.equals("1"))
					elementNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("2"))
					attributeNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("3"))
					commentNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));
				else if (deleteNodetype.equals("4"))
					textNodeFind(doc.getDocumentElement(), findNode, Integer.parseInt(check_num));

			} catch (FactoryConfigurationError e1) {
				// unable to get a document builder factory
				e1.printStackTrace(System.err);
			}
		}

		public void elementNodeFind(Node node, String eleName, int check_num) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {

					if (check_num == count) {
						manResult.setText("");
						manResult.append("[ELEMENT] " + child.getNodeName() + "  삭제 완료" + "\n");
						if (children.item(i - 1).getNodeType() == Node.TEXT_NODE)
							node.removeChild(children.item(i - 1));
						node.removeChild(child);

						return;
					}
					manResult.append((count++) + ": [" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}
				elementNodeFind(child, eleName, check_num);
			}
		}

		public void attributeNodeFind(Node node, String eleName, int check_num) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.hasAttributes()) {
					NamedNodeMap attr = child.getAttributes();
					for (int j = 0; j < attr.getLength(); j++) {

						if (attr.item(j).getNodeName().equals(eleName)) {
							System.out.println(attr.item(j).getNodeName());
							if (check_num == count) {
								manResult.setText("");
								manResult.append("[ATTRIBUTE] " + attr.item(j).getNodeName() + "=" + "\""
										+ attr.item(j).getNodeValue() + "\"" + "  삭제 완료" + "\n");
								// 해당 속성삭제
								((Element) child).removeAttributeNode((Attr) attr.item(j));

								return;
							}
							manResult.append((count++) + ": [" + getDepth(child) + ", ");
							manResult.append(getElementSiblingIndex(child) + "] ");
							manResult.append(child.getNodeName() + "\n");
						}
					}
				}

				attributeNodeFind(child, eleName, check_num);
			}
		}

		public void commentNodeFind(Node node, String eleName, int check_num) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {

					if (check_num == count) {
						manResult.setText("");
						manResult.append("[COMMENT] " + child.getNodeValue() + "  삭제 완료" + "\n");
						if (children.item(i - 1).getNodeType() == Node.TEXT_NODE)
							node.removeChild(children.item(i - 1));
						node.removeChild(child);

						return;
					}
					manResult.append((count++) + ": [" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}
				commentNodeFind(child, eleName, check_num);
			}
		}

		public void textNodeFind(Node node, String eleName, int check_num) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {

					if (check_num == count) {
						manResult.setText("");
						manResult.append("[TEXT] " + child.getNodeValue() + "  삭제 완료" + "\n");
						if (children.item(i - 1) != null && children.item(i - 1).getNodeType() == Node.TEXT_NODE)
							node.removeChild(children.item(i - 1));
						node.removeChild(child);

						return;
					}
					manResult.append((count++) + ": [" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}
				textNodeFind(child, eleName, check_num);
			}
		}

		public int getDepth(Node node) {
			int index = 0;
			while ((node = node.getParentNode()) != null)
				index++;
			return index;
		}

		protected int getElementSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}

		protected int getCommentSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.ELEMENT_NODE)
					index++;

			return index;
		}

		protected int getTextSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE)
					index++;

			return index;
		}
	}

	private class ActionListenerLoad implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Load 실행");
			manResult.setText("Load 실행\n\n");

			String input = JOptionPane.showInputDialog(manager, "load할 파일 이름 입력");

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(input);
				file = new File("./" + input);

				if (file.exists()) {
					System.out.println("Load 성공");
					manResult.setText("Load 성공\n" + input + "\n");
					a[0].setText("load파일 >> " + input);
				}

			} catch (FileNotFoundException e) {
				manResult.setText("존재하지 않는 파일입니다.\n");
				JOptionPane.showMessageDialog(null, "존재하지 않는 파일입니다.");
				a[0].setText("                                load파일 >> 없음                                ");
				file = null;
			} catch (FactoryConfigurationError e) {
				// unable to get a document builder factory
				e.printStackTrace(System.err);
			} catch (ParserConfigurationException e) {
				// parser was unable to be configured
				e.printStackTrace(System.err);
			} catch (SAXException e) {
				// parsing error
				e.printStackTrace(System.err);
			} catch (IOException e) {
				// i/o error
				e.printStackTrace(System.err);
			}
		}
	}

	private class ActionListenerMake implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Make 실행");
			manResult.setText("Make 실행\n\n");

			// load한거 save하고 진행
			if (file != null) {
				try {
					OutputFormat format = new OutputFormat(doc);
					format.setEncoding("UTF-8");
					StringWriter stringOut = new StringWriter();
					XMLSerializer serial = new XMLSerializer(stringOut, format);
					serial.asDOMSerializer();

					serial.serialize(doc.getDocumentElement());

					String input = JOptionPane.showInputDialog(manager, "저장할 파일명");

					FileWriter fw = new FileWriter(input);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(stringOut.toString());
					bw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			a[0].setText("load파일 >> " + "make 파일");

			String rootName = JOptionPane.showInputDialog(manager, "Make: 생성할 루트노드 명");
			file = new File("./" + rootName);
			doc = new DocumentImpl();
			Element root = doc.createElement(rootName);
			root.appendChild(doc.createTextNode(""));
			doc.appendChild(root);

			String schema[] = { "저장할 노드타입 (숫자 입력)", "어떤 노드자식에 추가할지", "몇번째에 추가할지(0부터 시작 / Attribute, Text는 입력 무관)",
					"    저장명    ", "더 이상 추가 안할시 1입력", "Attribute 밸류값(속성 입력외는 무관)" };
			makeInput Doc;
			manResult.setText(" ");
			ccc = true;

			Doc = new makeInput("Make", 6, schema);
		}
	}

	private class ActionListenerFind implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (file == null) {
				manResult.setText("Load된 파일이 없습니다.\n");
				JOptionPane.showMessageDialog(null, "load or make한 xml파일이 없습니다. xml 파일을 먼저 입력하십시오.");
				return;
			}
			manResult.setText("");
			try {
				String findNodetype = JOptionPane.showInputDialog(manager,
						"찾을 노드타입 (숫자 입력)\n1 : element\n2 : attribute\n3 : comment\n4 : text");
				String findNode = JOptionPane.showInputDialog(manager, "찾을 노드이름 입력");
				switch (findNodetype) {
				case "1":
					elementNodeFind(doc.getDocumentElement(), findNode);
					break;
				case "2":
					attributeNodeFind(doc.getDocumentElement(), findNode);
					break;
				case "3":
					commentNodeFind(doc.getDocumentElement(), findNode);
					break;
				case "4":
					textNodeFind(doc.getDocumentElement(), findNode);
					break;
				}

			} catch (FactoryConfigurationError e) {
				// unable to get a document builder factory
				e.printStackTrace(System.err);
			}

		}

		public void elementNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeName().equals(eleName)) {
					manResult.append("[" + getDepth(child) + ", ");
					manResult.append(getElementSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}

				elementNodeFind(child, eleName);
			}
		}

		public void attributeNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.hasAttributes()) {
					NamedNodeMap attr = child.getAttributes();

					for (int j = 0; j < attr.getLength(); j++) {
						if (attr.item(j)!=null && attr.item(j).getNodeName().equals(eleName)) {
							manResult.append("[ " + "해당노드 : " + "[" + getDepth(child) + ", "
									+ getCommentSiblingIndex(child) + "] " + child.getNodeName() + " 의 속성 " + " ] ");
							manResult.append(attr.item(j).getNodeName() + "=" + "\"" + attr.item(j).getNodeValue()
									+ "\"" + "\n");
						}
					}
				}
				attributeNodeFind(child, eleName);
			}
		}

		public void commentNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);

				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {
					manResult.append("[" + getDepth(child) + ", ");
					manResult.append(getCommentSiblingIndex(child) + "] ");
					manResult.append(child.getNodeName() + "\n");
				}

				commentNodeFind(child, eleName);
			}
		}

		public void textNodeFind(Node node, String eleName) {
			if (node == null)
				return;

			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeValue() != null && child.getNodeValue().equals(eleName)) {
					manResult.append("[ " + "부모노드 : " + "[" + getDepth(child) + ", " + getCommentSiblingIndex(child)
							+ "]" + node.getNodeName() + " ] ");
					manResult.append(child.getNodeValue() + "\n");
				}

				textNodeFind(child, eleName);
			}
		}

		public int getDepth(Node node) {
			int index = 0;
			while ((node = node.getParentNode()) != null)
				index++;
			return index;
		}

		protected int getElementSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}

		protected int getAttributeSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ELEMENT_NODE)
					index++;

			return index;
		}

		protected int getCommentSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.ELEMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}

		protected int getTextSiblingIndex(Node node) {
			int index = 1;

			while ((node = node.getPreviousSibling()) != null)
				if (node.getNodeType() != Node.ELEMENT_NODE && node.getNodeType() != Node.COMMENT_NODE
						&& node.getNodeType() != Node.ATTRIBUTE_NODE)
					index++;

			return index;
		}
	}

	private class ActionListenerSave implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Save 실행");
			manResult.setText("Save 실행\n\n");
			if (file == null) {
				manResult.append("Load된 파일이 없습니다.\n");
			} else {
				try {
					OutputFormat format = new OutputFormat(doc);
					format.setEncoding("UTF-8");
					StringWriter stringOut = new StringWriter();
					XMLSerializer serial = new XMLSerializer(stringOut, format);
					serial.asDOMSerializer();

					serial.serialize(doc.getDocumentElement());

					String input = JOptionPane.showInputDialog(manager, "저장할 파일명");

					FileWriter fw = new FileWriter(input);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(stringOut.toString());
					bw.close();

					manResult.append("Save 완료\\n\\n");

					// save후 load자동
					input = JOptionPane.showInputDialog(manager, "load할 파일 이름 입력");

					try {
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						doc = builder.parse(input);
						file = new File("./" + input);

						if (file.exists()) {
							System.out.println("Load 성공");
							manResult.setText("Load 성공\n" + input + "\n");
							a[0].setText("load파일 >> " + input);
						}

					} catch (FileNotFoundException e) {
						System.out.println("Load 실패");
						manResult.setText("Load 실패\n존재하지 않는 파일입니다.\n\n");
						a[0].setText("                                load파일 >> 없음                                ");
					} catch (FactoryConfigurationError e) {
						// unable to get a document builder factory
						e.printStackTrace(System.err);
					} catch (ParserConfigurationException e) {
						// parser was unable to be configured
						e.printStackTrace(System.err);
					} catch (SAXException e) {
						// parsing error
						e.printStackTrace(System.err);
					} catch (IOException e) {
						// i/o error
						e.printStackTrace(System.err);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private class ActionListenerPrint implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Print 실행");
			manResult.setText("Print 실행\n\n");

			try {
				if (file == null) {
					manResult.setText("Load된 파일이 없습니다.\n");
					JOptionPane.showMessageDialog(null, "load or make한 xml파일이 없습니다. xml 파일을 먼저 입력하십시오.");
				} else {
					traverse(doc.getDocumentElement(), "    ");
				}
			} catch (FactoryConfigurationError e) {
				// unable to get a document builder factory
				e.printStackTrace(System.err);
			}
		}

		public void traverse(Node node, String indent) {
			if (node == null)
				return;

			int type = node.getNodeType();
			switch (type) {
			case Node.DOCUMENT_NODE:
				manResult.append(indent + "[Document] " + node.getNodeName() + "\n");
				break;

			case Node.ENTITY_NODE:
				manResult.append(indent + "[ENTITY] " + node.getNodeName() + "\n");
				break;

			case Node.ELEMENT_NODE:
				manResult.append(indent + "[Element");
				if (node.hasAttributes()) {
					NamedNodeMap attr = node.getAttributes();
					for (int i = 0; i < attr.getLength(); i++) {
						manResult.append(
								" " + attr.item(i).getNodeName() + "=" + "\"" + attr.item(i).getNodeValue() + "\"");
					}
				}
				manResult.append("] " + node.getNodeName() + "\n");
				break;

			case Node.ENTITY_REFERENCE_NODE:
				manResult.append(indent + "[ENTITY_REFERENCE] " + node.getNodeName());
				break;

			case Node.CDATA_SECTION_NODE:
				manResult.append(indent + "[CDATA_SECTION] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;

			case Node.COMMENT_NODE:
				manResult.append(indent + "[COMMENT] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;

			case Node.TEXT_NODE:
				manResult.append(indent + "[TEXT] ");
				manResult.append(node.getNodeName());
				manResult.append("  " + node.getNodeValue() + "\n");
				break;
			}

			NodeList children = node.getChildNodes();
			if (children != null) {
				int len = children.getLength();
				for (int i = 0; i < len; i++)
					traverse(children.item(i), indent + "    ");
			}
		}

	}

	private class ActionListenerInsert implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (file == null) {
				manResult.setText("Load된 파일이 없습니다.\n");
				JOptionPane.showMessageDialog(null, "load or make한 xml파일이 없습니다. xml 파일을 먼저 입력하십시오.");
				return;
			}
			String schema[] = { "저장할 노드타입 (숫자 입력)", "어떤 노드자식에 추가할지", "몇번째에 추가할지(0부터 시작 / Attribute, Text는 입력 무관)",
					"    저장명    ", "Attribute 밸류값(속성 입력외는 무관)" };
			insertInput Doc = new insertInput("Insert", 5, schema);
			manResult.setText("");
		}
	}

	private class ActionListenerUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (file == null) {
				manResult.setText("Load된 파일이 없습니다.\n");
				JOptionPane.showMessageDialog(null, "load or make한 xml파일이 없습니다. xml 파일을 먼저 입력하십시오.");
				return;
			}
			manResult.setText("");
			String schema[] = { "변경할 노드타입 (숫자 입력)", "변경할 노드 이름", "    변경명    ", "Attribute 밸류값(속성값 변경시 입력)" };
			updateInput Doc = new updateInput("Update", 4, schema);
		}
	}

	private class ActionListenerDelete implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (file == null) {
				manResult.setText("Load된 파일이 없습니다.\n");
				JOptionPane.showMessageDialog(null, "load or make한 xml파일이 없습니다. xml 파일을 먼저 입력하십시오.");
				return;
			}
			manResult.setText("");
			String schema[] = { "삭제할 노드타입 (숫자 입력)", "삭제할 노드 이름" };
			deleteInput Doc = new deleteInput("Update", 2, schema);
		}
	}

	private class ActionListenerExit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (file == null) {
				manResult.append("Load된 파일이 없습니다.\n");
			} else {
				try {
					OutputFormat format = new OutputFormat(doc);
					format.setEncoding("UTF-8");
					StringWriter stringOut = new StringWriter();
					XMLSerializer serial = new XMLSerializer(stringOut, format);
					serial.asDOMSerializer();

					serial.serialize(doc.getDocumentElement());

					String input = JOptionPane.showInputDialog(manager, "저장할 파일명");

					FileWriter fw = new FileWriter(input);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(stringOut.toString());
					bw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("종료");
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		Main BLS = new Main();
		BLS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		BLS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}