package me.luxerina.transformers;

import java.util.ListIterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

import me.luxerina.Obfuscator;

public class TryCatchTransformer extends Transformer {

    public TryCatchTransformer(Obfuscator Obf) {
        super(Obf);
    }
	
	LabelNode tryStart,
			tryFinalStart,
			tryCatchStart,
			tryCatchEnd;
	
	@Override
	public void visit(ClassNode classNode) {
		for (MethodNode mn : classNode.methods) {
			
			ListIterator<AbstractInsnNode> iterator = mn.instructions.iterator();
			AbstractInsnNode next;
			while(iterator.hasNext()) {
				next = iterator.next();
				
				if(next.getOpcode() != GOTO && (next instanceof MethodInsnNode)) {
					tryStart = new LabelNode();
					tryFinalStart = new LabelNode();
					tryCatchStart = new LabelNode();
					tryCatchEnd = new LabelNode();
					
					iterator.previous();
					iterator.add(tryStart);
					iterator.next();
					iterator.add(tryFinalStart);
					{
					iterator.add (
						new JumpInsnNode (
							GOTO,
							tryCatchEnd
						)
					);
					}
					iterator.add(tryCatchStart);
					iterator.add (
						new InsnNode (
							ATHROW
						)
					);
					iterator.add(tryCatchEnd);
					mn.tryCatchBlocks.add(new TryCatchBlockNode(tryStart, tryFinalStart, tryCatchStart, "java/lang/Exception"));
				}
			}
		}
	}
	
}