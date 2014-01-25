package clashsoft.cscalc.gui;

import javax.swing.tree.DefaultMutableTreeNode;

import clashsoft.cscalc.strings.add.StringConverterAddBeginning;
import clashsoft.cscalc.strings.add.StringConverterAddEnd;
import clashsoft.cscalc.strings.character.*;
import clashsoft.cscalc.strings.conversion.*;
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
		{
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
		}
		add(node_1);
		
		node_1 = new DefaultMutableTreeNode("Characters");
		{
			node_1.add(new DefaultMutableTreeNode(new StringConverterTrim()));
			
			node_2 = new DefaultMutableTreeNode("Replace");
			{
				node_2.add(new DefaultMutableTreeNode(new StringConverterReplaceCharacters()));
				node_2.add(new DefaultMutableTreeNode(new StringConverterRetainCharacters()));
			}
			node_1.add(node_2);
			
			node_2 = new DefaultMutableTreeNode("Remove");
			{
				node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveChars()));
				node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveVowels()));
				node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveSpecial()));
				node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveCharsBeginning()));
				node_2.add(new DefaultMutableTreeNode(new StringConverterRemoveCharsEnd()));
				node_1.add(node_2);
			}
		}
		add(node_1);
		
		node_1 = new DefaultMutableTreeNode("Conversions");
		{
			node_1.add(new DefaultMutableTreeNode(new StringConverterLowercase()));
			node_1.add(new DefaultMutableTreeNode(new StringConverterSentenceCase()));
			node_1.add(new DefaultMutableTreeNode(new StringConverterTitleCase()));
			node_1.add(new DefaultMutableTreeNode(new StringConverterUppercase()));
			node_1.add(new DefaultMutableTreeNode(new StringConverterExpandCamelCase()));
		}
		add(node_1);
	}
}