package me.luxerina.transformers;

import java.util.Random;

import org.objectweb.asm.tree.ClassNode;

import me.luxerina.Obfuscator;

import org.objectweb.asm.Opcodes;

public abstract class Transformer implements Opcodes {

    protected final Obfuscator obf;
    protected final Random random;

    public Transformer(Obfuscator obf) {
        this.obf = obf;
        this.random = obf.getRandom();
    }

    public abstract void visit(ClassNode classNode);

    public void after() {
    }
}
