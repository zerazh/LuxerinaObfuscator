package me.luxerina.transformers;

import java.util.Random;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import me.luxerina.Obfuscator;
import me.luxerina.utils.NameGen;

public class FlowTransformer extends Transformer {
	
	public FlowTransformer(Obfuscator obf) {
		super(obf);
	}
	
	Random RANDOM = new Random();

	@Override
	public void visit(ClassNode node) {
		for (MethodNode method : node.methods) {
			if (!method.name.startsWith("<")) {
				for (AbstractInsnNode insnNode : method.instructions.toArray()) {

					if (insnNode.getOpcode() == Opcodes.DUP ||
							insnNode.getOpcode() == Opcodes.POP ||
							insnNode.getOpcode() == Opcodes.SWAP ||
							insnNode.getOpcode() == Opcodes.FSUB ||
							insnNode.getOpcode() == Opcodes.ISUB ||
							insnNode.getOpcode() == Opcodes.DSUB ||
							insnNode.getOpcode() == Opcodes.ATHROW
					) {
						for (int i = 0; i < 1 + RANDOM.nextInt(5); i++) {
							method.instructions.insertBefore(insnNode,
									new LdcInsnNode(NameGen.String(new Random().nextInt(5))));
							method.instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "length", "()I", false));
								method.instructions.insertBefore(insnNode,
										new LdcInsnNode(NameGen.String(new Random().nextInt(5))));
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP));

							if (RANDOM.nextBoolean()) {
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.DUP));
			//					for (int i2 = 0; i2 < 2 + random.nextInt(5); i2++) {
			//						method.instructions.insert(new InsnNode(Opcodes.SWAP));
			//					}
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP2));
							} else {
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.DUP));
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP));
								method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.POP));
							}
						}
					}

					// NOP
					//if (RANDOM.nextInt(144) == 0)
					//	method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
					// DUP SWAP
					if (insnNode.getOpcode() == Opcodes.DUP) {//Anti JDGUI
						//	method.instructions.insert(insnNode, new InsnNode(Opcodes.SWAP));
						method.instructions.insert(new InsnNode(Opcodes.POP2));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new InsnNode(Opcodes.POP));
						if (RANDOM.nextBoolean()) {
							for (int i = 0; i < 2 + RANDOM.nextInt(5); i++) {
								method.instructions.insert(new InsnNode(Opcodes.SWAP));
							}
						}
						//method.instructions.insert(new InsnNode(Opcodes.SWAP));
						if (RANDOM.nextBoolean()) {
							method.instructions.insert(new InsnNode(Opcodes.POP));
						} else {
							method.instructions.insert(new InsnNode(Opcodes.DUP));
							method.instructions.insert(new InsnNode(Opcodes.POP2));

						}
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));
						method.instructions.insert(new LdcInsnNode(NameGen.String(1)));

					}
				}
			}
		}
	}
}