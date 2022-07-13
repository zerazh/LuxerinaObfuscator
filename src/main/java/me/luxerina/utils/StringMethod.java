package me.luxerina.utils;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class StringMethod implements Opcodes{

    public static String encrypt(String a){
        return new String(Base64.getEncoder().encode(a.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encrypt2(String n, int a){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < n.length(); i++){
            char c = (char) ((int)n.charAt(i) + a);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static ClassNode getDecryptClass(String na, String kk){
        ClassNode classNode = new ClassNode();
        classNode.name = (na.isEmpty() ? String.valueOf((char) new Random().nextInt()) : na);
        classNode.access = Opcodes.ACC_PUBLIC;
        classNode.version = Opcodes.V1_8;
        classNode.superName = "java/lang/Object";
        String field = String.valueOf((char) new Random().nextInt());
        classNode.visitField(ACC_PRIVATE, field, "Ljava/lang/String;", null, null);
        String field2 = String.valueOf((char) new Random().nextInt());
        classNode.visitField(ACC_PRIVATE, field2, "I", null, null);

        writeInit(classNode, field, field2);
        writeDecryptMethod(classNode, kk);
        writeDecryptMethod2(classNode, kk);
        writeToStringMethod(classNode, kk, field, field2);
        writeGetMethod(classNode);

        return classNode;
    }

    private static void writeToStringMethod(ClassNode classNode, String kk, String field, String field2){
        MethodVisitor methodVisitor = classNode.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, classNode.name, field, "Ljava/lang/String;");
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, classNode.name, field2, "I");
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classNode.name, kk + "31", "(Ljava/lang/String;I)Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ASTORE, 1);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, classNode.name, kk, "(Ljava/lang/String;)Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(3, 3);
        methodVisitor.visitEnd();
    }

    private static void writeDecryptMethod(ClassNode classNode, String kk){
        MethodVisitor methodVisitor = classNode.visitMethod(ACC_PRIVATE, kk, "(Ljava/lang/String;)Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/util/Base64", "getDecoder", "()Ljava/util/Base64$Decoder;", false);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/Base64$Decoder", "decode", "(Ljava/lang/String;)[B", false);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(4, 2);
        methodVisitor.visitEnd();
    }

    private static void writeDecryptMethod2(ClassNode classNode, String kk){
        MethodVisitor methodVisitor = classNode.visitMethod(ACC_PRIVATE, kk + "31", "(Ljava/lang/String;I)Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ISTORE, 4);
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {classNode.name, "java/lang/String", Opcodes.INTEGER, "java/lang/StringBuilder", Opcodes.INTEGER}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ILOAD, 4);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
        Label label1 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPGE, label1);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ILOAD, 4);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitInsn(ISUB);
        methodVisitor.visitInsn(I2C);
        methodVisitor.visitVarInsn(ISTORE, 5);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ILOAD, 5);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(C)Ljava/lang/StringBuilder;", false);
        methodVisitor.visitInsn(POP);
        methodVisitor.visitIincInsn(4, 1);
        methodVisitor.visitJumpInsn(GOTO, label0);
        methodVisitor.visitLabel(label1);
        methodVisitor.visitFrame(Opcodes.F_NEW, 4, new Object[] {classNode.name, "java/lang/String", Opcodes.INTEGER, "java/lang/StringBuilder"}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(2, 6);
        methodVisitor.visitEnd();
    }

    public static void writeInit(ClassNode classNode, String field, String field2){
        MethodVisitor methodVisitor = classNode.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;I)V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, classNode.name, field, "Ljava/lang/String;");
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 2);
        methodVisitor.visitFieldInsn(PUTFIELD, classNode.name, field2, "I");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    public static void writeGetMethod(ClassNode classNode){
        MethodVisitor methodVisitor = classNode.visitMethod(ACC_PUBLIC + ACC_STATIC, "get", "(Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        Label label1 = new Label();
        Label label2 = new Label();
        methodVisitor.visitTryCatchBlock(label0, label1, label2, null);
        Label label3 = new Label();
        methodVisitor.visitTryCatchBlock(label2, label3, label2, null);
        Label label4 = new Label();
        Label label5 = new Label();
        Label label6 = new Label();
        methodVisitor.visitTryCatchBlock(label4, label5, label6, "java/lang/Throwable");
        Label label7 = new Label();
        Label label8 = new Label();
        Label label9 = new Label();
        methodVisitor.visitTryCatchBlock(label7, label8, label9, "java/lang/Exception");
        Label label10 = new Label();
        Label label11 = new Label();
        methodVisitor.visitTryCatchBlock(label10, label7, label11, null);
        Label label12 = new Label();
        Label label13 = new Label();
        Label label14 = new Label();
        methodVisitor.visitTryCatchBlock(label12, label13, label14, "java/lang/Exception");
        methodVisitor.visitTryCatchBlock(label11, label12, label11, null);
        methodVisitor.visitLdcInsn(Type.getType("L"+ classNode.name +";"));
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getResourceAsStream", "(Ljava/lang/String;)Ljava/io/InputStream;", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitJumpInsn(IFNONNULL, label10);
        methodVisitor.visitTypeInsn(NEW, "java/lang/RuntimeException");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitLdcInsn("File not found");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V", false);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitLabel(label10);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B"}, 0, new Object[] {});
        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitVarInsn(ASTORE, 5);
        methodVisitor.visitLabel(label4);
        methodVisitor.visitTypeInsn(NEW, "java/io/ByteArrayOutputStream");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/ByteArrayOutputStream", "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, 6);
        methodVisitor.visitLabel(label0);
        methodVisitor.visitIntInsn(SIPUSH, 4096);
        methodVisitor.visitIntInsn(NEWARRAY, T_BYTE);
        methodVisitor.visitVarInsn(ASTORE, 7);
        Label label15 = new Label();
        methodVisitor.visitLabel(label15);
        methodVisitor.visitFrame(Opcodes.F_NEW, 8, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable", "java/io/ByteArrayOutputStream", "[B"}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitVarInsn(ALOAD, 7);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ALOAD, 7);
        methodVisitor.visitInsn(ARRAYLENGTH);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "read", "([BII)I", false);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ISTORE, 8);
        methodVisitor.visitInsn(ICONST_M1);
        Label label16 = new Label();
        methodVisitor.visitJumpInsn(IF_ICMPEQ, label16);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitVarInsn(ALOAD, 7);
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ILOAD, 8);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "write", "([BII)V", false);
        methodVisitor.visitJumpInsn(GOTO, label15);
        methodVisitor.visitLabel(label16);
        methodVisitor.visitFrame(Opcodes.F_NEW, 9, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable", "java/io/ByteArrayOutputStream", "[B", Opcodes.INTEGER}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "flush", "()V", false);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "toByteArray", "()[B", false);
        methodVisitor.visitVarInsn(ASTORE, 4);
        methodVisitor.visitLabel(label1);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitJumpInsn(IFNULL, label5);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "close", "()V", false);
        methodVisitor.visitJumpInsn(GOTO, label5);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitFrame(Opcodes.F_NEW, 7, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable", "java/io/ByteArrayOutputStream"}, 1, new Object[] {"java/lang/Throwable"});
        methodVisitor.visitVarInsn(ASTORE, 9);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(ALOAD, 6);
        Label label17 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label17);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/ByteArrayOutputStream", "close", "()V", false);
        methodVisitor.visitLabel(label17);
        methodVisitor.visitFrame(Opcodes.F_NEW, 10, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable", "java/io/ByteArrayOutputStream", Opcodes.TOP, Opcodes.TOP, "java/lang/Throwable"}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 9);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitLabel(label5);
        methodVisitor.visitFrame(Opcodes.F_NEW, 6, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable"}, 0, new Object[] {});
        methodVisitor.visitJumpInsn(GOTO, label7);
        methodVisitor.visitLabel(label6);
        methodVisitor.visitFrame(Opcodes.F_NEW, 6, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable"}, 1, new Object[] {"java/lang/Throwable"});
        methodVisitor.visitVarInsn(ASTORE, 6);
        methodVisitor.visitVarInsn(ALOAD, 5);
        Label label18 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label18);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitJumpInsn(IF_ACMPEQ, label18);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "addSuppressed", "(Ljava/lang/Throwable;)V", false);
        methodVisitor.visitLabel(label18);
        methodVisitor.visitFrame(Opcodes.F_NEW, 7, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", "java/lang/Throwable", "java/lang/Throwable"}, 0, new Object[] {});
        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitLabel(label7);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B"}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "close", "()V", false);
        methodVisitor.visitLabel(label8);
        Label label19 = new Label();
        methodVisitor.visitJumpInsn(GOTO, label19);
        methodVisitor.visitLabel(label9);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B"}, 1, new Object[] {"java/lang/Exception"});
        methodVisitor.visitVarInsn(ASTORE, 5);
        methodVisitor.visitJumpInsn(GOTO, label19);
        methodVisitor.visitLabel(label11);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B"}, 1, new Object[] {"java/lang/Throwable"});
        methodVisitor.visitVarInsn(ASTORE, 10);
        methodVisitor.visitLabel(label12);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "close", "()V", false);
        methodVisitor.visitLabel(label13);
        Label label20 = new Label();
        methodVisitor.visitJumpInsn(GOTO, label20);
        methodVisitor.visitLabel(label14);
        methodVisitor.visitFrame(Opcodes.F_NEW, 11, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, "java/lang/Throwable"}, 1, new Object[] {"java/lang/Exception"});
        methodVisitor.visitVarInsn(ASTORE, 11);
        methodVisitor.visitLabel(label20);
        methodVisitor.visitFrame(Opcodes.F_NEW, 11, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B", Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, Opcodes.TOP, "java/lang/Throwable"}, 0, new Object[] {});
        methodVisitor.visitVarInsn(ALOAD, 10);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitLabel(label19);
        methodVisitor.visitFrame(Opcodes.F_NEW, 5, new Object[] {"java/lang/String", Opcodes.INTEGER, "java/lang/String", "java/io/InputStream", "[B"}, 0, new Object[] {});
        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/util/Base64", "getDecoder", "()Ljava/util/Base64$Decoder;", false);
        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/nio/charset/StandardCharsets", "UTF_8", "Ljava/nio/charset/Charset;");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([BLjava/nio/charset/Charset;)V", false);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "split", "(Ljava/lang/String;)[Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(AALOAD);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/Base64$Decoder", "decode", "(Ljava/lang/String;)[B", false);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([B)V", false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(7, 12);
        methodVisitor.visitEnd();
    }

}