import javax.swing.*;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;
public class Homework1 extends JPanel
{
		implements TreeSelectionListener	
		{
			private JEditorPane htmlPane;
			private JTree Tree;
			
			private static boolean playWithLineStyle = false;
			private static String lineStyle = "Horizontal";
			private static boolean useSystemLookAndFeel = false;
		public Homework1() 
		{
			super(new GridLayout(1,0));
			//Create the nodes.
			DefaultMutableTreeNode top = new DefaultMutableTreeNode(tree);
			CreateNode(top,tree);

			//Create a tree that allows one selection at a time.
			Tree = new JTree(top);
			Tree.getSelectionModel().setSelectionMode
					(TreeSelectionModel.SINGLE_TREE_SELECTION);

			//Listen for when the selection changes.
			Tree.addTreeSelectionListener(this);

			if (playWithLineStyle) 
			{
				System.out.println("line style = " + lineStyle);
				Tree.putClientProperty("JTree.lineStyle", lineStyle);
			}

			//Create the scroll pane and add the tree to it.
			JScrollPane treeView = new JScrollPane(Tree);

			//Create the HTML viewing pane.
			htmlPane = new JEditorPane();
			htmlPane.setEditable(false);
			JScrollPane htmlView = new JScrollPane(htmlPane);

			//Add the scroll panes to a split pane.
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent(treeView);
			splitPane.setBottomComponent(htmlView);

			Dimension minimumSize = new Dimension(100, 50);
			htmlView.setMinimumSize(minimumSize);
			treeView.setMinimumSize(minimumSize);
			splitPane.setDividerLocation(100);
			splitPane.setPreferredSize(new Dimension(500, 300));

			ImageIcon leafIcon=new ImageIcon(Homework1.class.getResource("middle.gif"));
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setClosedIcon(leafIcon);
			renderer.setOpenIcon(leafIcon);
			Tree.setCellRenderer(renderer);
			add(splitPane);
		}
		public static boolean IsLeaf=false;
		/** Required by TreeSelectionListener interface. */
		public void valueChanged(TreeSelectionEvent e) 
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)
					Tree.getLastSelectedPathComponent();

			if (node == null) return;
			IsLeaf=node.isLeaf();
			Object nodeInfo = node.getUserObject();
			DisplayNode((node)nodeInfo);
		}
		public void  CreateNode(DefaultMutableTreeNode top,node n)
		{
			if(n.left!=null)
			{
				DefaultMutableTreeNode left=new DefaultMutableTreeNode(n.left);
				top.add(left);
				CreateNode(left,n.left);
			}
			if(n.right!=null)
			{
				DefaultMutableTreeNode Right=new DefaultMutableTreeNode(n.right);
				top.add(Right);
				CreateNode(Right,n.right);
			}
		}
		public void DisplayNode(node n)
		{
			infix(n);
			if(!IsLeaf)
			{
				Screen=Screen+"="+calculate(n);
			}
			htmlPane.setText(Screen);
		}
		private static void createAndShowGUI() {
			if (useSystemLookAndFeel) {
				try 
				{
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) 
				{
					System.err.println("Couldn't use system look and feel.");
				}
			}

			JFrame frame = new JFrame("Binary Tree Calculator");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//Add content to the window.
			frame.add(new Homework1());

			//Display the window.
			frame.pack();
			frame.setVisible(true);
		}

		
	public static node tree;
	public static Stack <Character>Bank = new Stack<Character>();
	public static String Screen;
	public static void main(String[] args) 
	{
		// Begin of arguments input sample
		String input ="251-*32*+";
		if (args.length > 0) 
		{
			input = args[0];
			if (input.equalsIgnoreCase("251-*32*+")) 
			{
				System.out.println("(2*(5-1))+(3*2)=14");
			}
		}
		for (int i = 0; i <input.length(); i++) {
			Bank.push(input.charAt(i));
		}
		for(int i=0;i<input.length();i++) 
		{
			Bank.add(input.charAt(i));
		}
		tree = new node(Bank.pop());
		infix(tree);
		inorder(tree);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		// End of arguments input sample		
		// TODO: Implement your project here
	}
	public static Boolean star(Character s)
	{
		if("+-*/".indexOf(s)!=-1)
		{
			return true;
		}
		return false;
	}
	public static Boolean IsNumber(Character s)
	{
		if("0123456789".indexOf(s)!=-1)
		{
			return true;
		}
		return false;
	}
	public static void inorder(node a) 
	{
		if(star(a.Key)) 
		{
			a.right=new node(Bank.pop());
			inorder(a.right);
			a.left=new node(Bank.pop());
			inorder(a.left);
		}
	}
	public static void infix(node a) 
	{
		Screen="";
		if(IsNumber(a.Key))
		{
			Screen+=a.Key;
			System.out.print(a.Key);
		}else if(star(a.Key))
		{
			infix(a.left);
			Screen+=a.Key;
			System.out.print(a.Key);
			infix(a.right);
		}
		System.out.println("="+calculate(a));
	}
	public static int calculate(node a)
	{
		if(a.Key=='+')
		{
			return calculate(a.left) + calculate(a.right);
		}
		if(a.Key=='-')
		{
			return calculate(a.left) - calculate(a.right);
		}
		if(a.Key=='*')
		{
			return calculate(a.left) * calculate(a.right);
		}
		else return Integer.parseInt(a.Key.toString());		
	}
}
}
