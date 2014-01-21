package clashsoft.cscalc.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import clashsoft.cscalc.strings.add.StringConverterAddBeginning;
import clashsoft.cscalc.strings.add.StringConverterAddEnd;
import clashsoft.cscalc.strings.insert.StringConverterInsertAfter;
import clashsoft.cscalc.strings.insert.StringConverterInsertInfront;
import clashsoft.cscalc.strings.insert.StringConverterInsertPos;
import clashsoft.cscalc.strings.remove.StringConverterRemove;
import clashsoft.cscalc.strings.remove.StringConverterRemoveFirst;
import clashsoft.cscalc.strings.remove.StringConverterRemoveLast;
import clashsoft.cscalc.strings.replace.StringConverterReplace;
import clashsoft.cscalc.strings.replace.StringConverterReplaceFirst;
import clashsoft.cscalc.strings.replace.StringConverterReplaceLast;

public class StringConversions extends DefaultMutableTreeNode
{
	private static final long	serialVersionUID	= 4322866774371636879L;

	public StringConversions(Object userObject)
	{
		super(userObject);
		
		DefaultMutableTreeNode node_1;
		DefaultMutableTreeNode node_2;
		node_1 = new DefaultMutableTreeNode("Text");
		node_2 = new DefaultMutableTreeNode("Add");
		node_2.add(new DefaultMutableTreeNode(new StringConverterAddBeginning()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterAddEnd()));
		node_1.add(node_2);
		node_2 = new DefaultMutableTreeNode("Insert");
		node_2.add(new DefaultMutableTreeNode(new StringConverterInsertInfront()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterInsertAfter()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterInsertPos()));
		node_1.add(node_2);
		node_2 = new DefaultMutableTreeNode("Remove");
		node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveFirst()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveLast()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterRemove()));
		node_1.add(node_2);
		node_2 = new DefaultMutableTreeNode("Replace");
		node_2.add(new DefaultMutableTreeNode(new StringConverterReplaceFirst()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterReplaceLast()));
		node_2.add(new DefaultMutableTreeNode(new StringConverterReplace()));
		node_1.add(node_2);
		add(node_1);
		node_1 = new DefaultMutableTreeNode("Characters");
		node_1.add(new DefaultMutableTreeNode("Replace characters"));
		node_1.add(new DefaultMutableTreeNode("Remove characters"));
		node_1.add(new DefaultMutableTreeNode("Retain characters"));
		node_1.add(new DefaultMutableTreeNode("Remove trailing whitespaces"));
		node_1.add(new DefaultMutableTreeNode("Remove vowels"));
		node_1.add(new DefaultMutableTreeNode("Remove special characters"));
		add(node_1);
		node_1 = new DefaultMutableTreeNode("Character Positions & Ranges");
		node_1.add(new DefaultMutableTreeNode("Remove characters from beginning"));
		node_1.add(new DefaultMutableTreeNode("Remove characters from end"));
		node_1.add(new DefaultMutableTreeNode("Truncate"));
		add(node_1);
		node_1 = new DefaultMutableTreeNode("Conversions");
		node_1.add(new DefaultMutableTreeNode("lowercase"));
		node_1.add(new DefaultMutableTreeNode("Sentence case"));
		node_1.add(new DefaultMutableTreeNode("Title Case"));
		node_1.add(new DefaultMutableTreeNode("UPPERCASE"));
		node_1.add(new DefaultMutableTreeNode("Expand CamelCase"));
		add(node_1);
	}
}