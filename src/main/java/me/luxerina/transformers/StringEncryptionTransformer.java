package me.luxerina.transformers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import me.luxerina.Obfuscator;
import me.luxerina.utils.StringMethod;

public class StringEncryptionTransformer extends Transformer {

	public static boolean check=false;

	public StringEncryptionTransformer(Obfuscator obfuscator) {
		super(obfuscator);
	}

	ClassNode decryptClass = StringMethod.getDecryptClass(Obfuscator.name,String.valueOf((char) new Random().nextInt(Integer.MAX_VALUE)));

	@Override
	public void visit(ClassNode classNode) {
		check=true;
        for(MethodNode methodNode : classNode.methods){
            String splitKey = "##!##";
            String name = name(String.valueOf((classNode.name + "." + methodNode.name + methodNode.desc + String.valueOf(methodNode.access)).hashCode()));
            String full = "";
            int index = 1;
            for(AbstractInsnNode insnNode : methodNode.instructions){
                if(insnNode instanceof LdcInsnNode){
                    LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
                    if(ldcInsnNode.cst instanceof String){
                        String value = (String) ldcInsnNode.cst;
                        try{
                            full += splitKey;
                            full += new String(Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8)));
                        }
                        catch (Exception ex){
                        	ex.printStackTrace();
                        }
                        InsnList block = new InsnList();
                        block.add(new LdcInsnNode("/" + name));
                        block.add(toInsnNode(index));
                        block.add(new LdcInsnNode(splitKey));
                        block.add(new MethodInsnNode(INVOKESTATIC, decryptClass.name, "get", "(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;", false));

                        methodNode.instructions.insert(insnNode, block);
                        methodNode.instructions.remove(insnNode);
                        index++;
                    }
                }
            }
            if(full.isEmpty()){
                continue;
            }
            Obfuscator.addingImages.put(name, full.getBytes(StandardCharsets.UTF_8));
        
    }
		for (MethodNode methodNode : classNode.methods) {
			for (AbstractInsnNode insnNode : methodNode.instructions) {
				if (insnNode instanceof LdcInsnNode) {
					LdcInsnNode ldcInsnNode = (LdcInsnNode) insnNode;
					if (!(ldcInsnNode.cst instanceof String)) {
						continue;
					}
					String value = (String) ldcInsnNode.cst;
					if (!value.isEmpty()) {
						int rand = 0;
						do {
							rand = new Random().nextInt(Integer.MAX_VALUE);
						} while (rand == 0);
						String encrypted = StringMethod.encrypt(value);
						String encrypted2 = StringMethod.encrypt2(encrypted, rand);
						InsnList block = new InsnList();
						block.add(new TypeInsnNode(NEW, decryptClass.name));
						block.add(new InsnNode(DUP));
						block.add(new LdcInsnNode(encrypted2));
						block.add(toInsnNode(rand));
						block.add(new MethodInsnNode(INVOKESPECIAL, decryptClass.name, "<init>","(Ljava/lang/String;I)V", false));
						block.add(new MethodInsnNode(INVOKEVIRTUAL, decryptClass.name, "toString","()Ljava/lang/String;", false));
						methodNode.instructions.insert(insnNode, block);
						methodNode.instructions.remove(insnNode);
					}
				}
			}
		}

	}
	
	public static AbstractInsnNode toInsnNode(int value) {
		if (value > 5 || value < 0) {
			LdcInsnNode ldcInsnNode = new LdcInsnNode(value);
			return ldcInsnNode;
		}
		switch (value) {
		case 0: {
			return new InsnNode(ICONST_0);
		}
		case 1: {
			return new InsnNode(ICONST_1);
		}
		case 2: {
			return new InsnNode(ICONST_2);
		}
		case 3: {
			return new InsnNode(ICONST_3);
		}
		case 4: {
			return new InsnNode(ICONST_4);
		}
		case 5: {
			return new InsnNode(ICONST_5);
		}
		case -1: {
			return new InsnNode(ICONST_M1);
		}
		}
		return null;
	}
	
	public static String name(String n) {
		return "META-INF/DATA/" + n + (new Random().nextBoolean() ? ".png" : ".jpg");
	}

	@Override
	public void after() {
		obf.addNewClass(decryptClass);
	}
}