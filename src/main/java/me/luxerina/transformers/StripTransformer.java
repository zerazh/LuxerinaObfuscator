package me.luxerina.transformers;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;

import me.luxerina.Obfuscator;

public class StripTransformer extends Transformer {
	
    public StripTransformer(Obfuscator obf) {
        super(obf);
    }
    @Override
	public void after() {
        for (ClassNode classNode : obf.getClasses()) {
            for (MethodNode method : classNode.methods) {
                method.localVariables = null;
                method.parameters = null;
                method.signature = null;
                for (AbstractInsnNode instruction : method.instructions) {
                    if (instruction instanceof LineNumberNode) {
                        method.instructions.remove(instruction);
                    }
                }
            }
            for (FieldNode field : classNode.fields)
            field.signature = null;
            classNode.signature = null;
            classNode.innerClasses.clear();
            classNode.sourceFile = null;
            classNode.sourceDebug = null;
        }
    }
    
	@Override
	public void visit(ClassNode classNode) {
		
	}
}