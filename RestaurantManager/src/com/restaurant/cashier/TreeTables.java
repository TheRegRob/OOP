package com.restaurant.cashier;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class TreeTables extends JPanel {
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Ordini");
	DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
	TreeCellRenderer tcr;
	JTree tree;
	
	public TreeTables() {
		super (new GridLayout(1, 0));
		treeModel.addTreeModelListener(new TTModelListener());
		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		URL url_tblIcon = CashierEnv.class.getResource("/Table_Icon.png");
		ImageIcon tableIcon = new ImageIcon(url_tblIcon);
		URL url_dishIcon = CashierEnv.class.getResource("/Dish_Icon.png");
		ImageIcon dishIcon = new ImageIcon(url_dishIcon);
	    if (tableIcon != null && dishIcon != null) {
	    	OrderRender render = new OrderRender(tableIcon, dishIcon);
	    	tree.setCellRenderer(render);
	    }
		JScrollPane sp = new JScrollPane(tree);
		sp.setBorder(new LineBorder(Color.BLACK, 1, true));
		add(sp);
	}
	
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}
	
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (DefaultMutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}
	}
	
	public TreePath searchTable(UITable table) {
		String nodeID = getNodeID(table);
		Enumeration<TreeNode> e = rootNode.depthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
			if (node.toString().equalsIgnoreCase(nodeID)) {
				return new TreePath(node.getPath());
			}
		}
		return null;
	}
	
	public DefaultMutableTreeNode addObject(Object child) {
		if (child instanceof UITable) {
			String nodeID = getNodeID((UITable) child);
			return addObject(nodeID);
		} else {
			return addObject(child);
		}
		
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean visible) {
		DefaultMutableTreeNode nNode;
		if (child instanceof UITable) {
			String nodeID = getNodeID((UITable) child);
			nNode = new DefaultMutableTreeNode(nodeID);
		} else {
			nNode = new DefaultMutableTreeNode(child);
		}
		if (parent == null) {
			parent = rootNode;
		} 
		treeModel.insertNodeInto(nNode, parent, parent.getChildCount());
		if (visible) {
			tree.scrollPathToVisible(new TreePath(nNode.getPath()));
		}
		tree.expandRow(0);
		return nNode;
	}
	
	private String getNodeID(UITable table) {
		StringBuilder sb = new StringBuilder();
		sb.append("Tavolo " + table.tableID.charAt(0));
		for(int i = 0; i < table.chainedTbls.size(); i++) {
			sb.append(table.chainedTbls.get(i).tableID.charAt(1));
		}
		return sb.toString();
	}
}

@SuppressWarnings("serial")
class OrderRender extends DefaultTreeCellRenderer {
	Icon tableIcon;
	Icon dishIcon;
	
	public OrderRender(Icon parentIcon, Icon leafIcon) {
		tableIcon = parentIcon;
		dishIcon = leafIcon;
	}
	
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean exp,
			boolean leaf,
			int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
		if (tree.getModel().getRoot().equals(nodo)) {
			setIcon(null);
		} else if (nodo.getChildCount() > 0) {	
			setIcon(tableIcon);
		} else {
			if (nodo.getParent().equals(tree.getModel().getRoot())) {
				setIcon(tableIcon);
			} else {
				setIcon(dishIcon);
			}
		}
		return this;
	}
}

class TTModelListener implements TreeModelListener {

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		int index = e.getChildIndices()[0];
		node = (DefaultMutableTreeNode) (node.getChildAt(index));
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		int index = e.getChildIndices()[0];
		node = (DefaultMutableTreeNode) (node.getChildAt(index));
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		int index = e.getChildIndices()[0];
		node = (DefaultMutableTreeNode) (node.getChildAt(index));
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());
		int index = e.getChildIndices()[0];
		node = (DefaultMutableTreeNode) (node.getChildAt(index));
		
	}
}
