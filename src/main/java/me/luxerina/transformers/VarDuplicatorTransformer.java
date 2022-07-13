package me.luxerina.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import me.luxerina.Obfuscator;

public class VarDuplicatorTransformer extends Transformer {

    public VarDuplicatorTransformer(Obfuscator Obf) {
        super(Obf);
    }

	@Override
	public void visit(ClassNode classNode) {
		for (MethodNode mn : classNode.methods) {
	        for (AbstractInsnNode ain : mn.instructions.toArray()) {
	            if (ain.getType() == AbstractInsnNode.VAR_INSN) {
	                VarInsnNode vin = (VarInsnNode) ain;
	                if (vin.getOpcode() == Opcodes.ASTORE) {
	                    mn.instructions.insertBefore(vin, new InsnNode(Opcodes.DUP));
	                    mn.instructions.insertBefore(vin, new InsnNode(Opcodes.ACONST_NULL));
	                    mn.instructions.insertBefore(vin, new InsnNode(Opcodes.SWAP));
	                    AbstractInsnNode next = vin.getNext();

	                    mn.instructions.insertBefore(next, new InsnNode(Opcodes.POP));
	                    mn.instructions.insertBefore(next, new VarInsnNode(Opcodes.ASTORE, vin.var));
	                }
	            }
	        }
		}
	}
}