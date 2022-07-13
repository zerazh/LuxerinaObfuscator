package me.luxerina.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import me.luxerina.Obfuscator;
import me.luxerina.utils.AccessHelper;

public class BadPopTransformer extends Transformer {

    public BadPopTransformer(Obfuscator Obf) {
        super(Obf);
    }
    
	public static String s = getMassive();

	@Override
	public void visit(ClassNode classNode) {
		for (MethodNode mn : classNode.methods) {
			for (@SuppressWarnings("unused")
			AbstractInsnNode ain : mn.instructions.toArray()) {
				if (mn.name.contains("<") || AccessHelper.isAbstract(mn.access)) {
					return;
				}
				for (int i = 0; i < 3; i++) {
					mn.instructions.insert(new InsnNode(Opcodes.POP2));
					mn.instructions.insert(new LdcInsnNode(Obfuscator.name));
					mn.instructions.insert(new InsnNode(Opcodes.POP));
					mn.instructions.insert(new InsnNode(Opcodes.SWAP));
					mn.instructions.insert(new InsnNode(Opcodes.POP));
					mn.instructions.insert(new LdcInsnNode(s));
					mn.instructions.insert(new LdcInsnNode(s));
					mn.instructions.insert(new LdcInsnNode(Obfuscator.name));
				}
			}
		}

	}
	
	public static String getMassive() {
		StringBuffer sb = new StringBuffer();
		while (sb.length() < 65536 - 1) {
			sb.append(" ");
		}
		return sb.toString();
	}
}
