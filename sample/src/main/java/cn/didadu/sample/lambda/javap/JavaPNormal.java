package cn.didadu.sample.lambda.javap;

/**
 * Created by admin on 15/10/22.
 */
public class JavaPNormal {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override public void run() {
                System.out.println("Hello World!");
            }
        });
        t.start();
    }
}

/**
 Last modified 2015-10-22; size 659 bytes
 MD5 checksum 08ea6de74a12448505c146efdaf93892
 Compiled from "JavaPNormal.java"
 public class cn.didadu.sample.lambda.javap.JavaPNormal
 minor version: 0
 major version: 52
 flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
 #1 = Methodref          #8.#25         // java/lang/Object."<init>":()V
 #2 = Class              #26            // java/lang/Thread
 #3 = Class              #27            // cn/didadu/sample/lambda/javap/JavaPNormal$1
 #4 = Methodref          #3.#25         // cn/didadu/sample/lambda/javap/JavaPNormal$1."<init>":()V
 #5 = Methodref          #2.#28         // java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
 #6 = Methodref          #2.#29         // java/lang/Thread.start:()V
 #7 = Class              #30            // cn/didadu/sample/lambda/javap/JavaPNormal
 #8 = Class              #31            // java/lang/Object
 #9 = Utf8               InnerClasses
 #10 = Utf8               <init>
 #11 = Utf8               ()V
 #12 = Utf8               Code
 #13 = Utf8               LineNumberTable
 #14 = Utf8               LocalVariableTable
 #15 = Utf8               this
 #16 = Utf8               Lcn/didadu/sample/lambda/javap/JavaPNormal;
 #17 = Utf8               main
 #18 = Utf8               ([Ljava/lang/String;)V
 #19 = Utf8               args
 #20 = Utf8               [Ljava/lang/String;
 #21 = Utf8               t
 #22 = Utf8               Ljava/lang/Thread;
 #23 = Utf8               SourceFile
 #24 = Utf8               JavaPNormal.java
 #25 = NameAndType        #10:#11        // "<init>":()V
 #26 = Utf8               java/lang/Thread
 #27 = Utf8               cn/didadu/sample/lambda/javap/JavaPNormal$1
 #28 = NameAndType        #10:#32        // "<init>":(Ljava/lang/Runnable;)V
 #29 = NameAndType        #33:#11        // start:()V
 #30 = Utf8               cn/didadu/sample/lambda/javap/JavaPNormal
 #31 = Utf8               java/lang/Object
 #32 = Utf8               (Ljava/lang/Runnable;)V
 #33 = Utf8               start
 {
 public cn.didadu.sample.lambda.javap.JavaPNormal();
 descriptor: ()V
 flags: ACC_PUBLIC
 Code:
 stack=1, locals=1, args_size=1
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return
 LineNumberTable:
 line 6: 0
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0       5     0  this   Lcn/didadu/sample/lambda/javap/JavaPNormal;

 public static void main(java.lang.String[]);
 descriptor: ([Ljava/lang/String;)V
 flags: ACC_PUBLIC, ACC_STATIC
 Code:
 stack=4, locals=2, args_size=1
 0: new           #2                  // class java/lang/Thread
 3: dup
 4: new           #3                  // class cn/didadu/sample/lambda/javap/JavaPNormal$1
 7: dup
 8: invokespecial #4                  // Method cn/didadu/sample/lambda/javap/JavaPNormal$1."<init>":()V
 11: invokespecial #5                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
 14: astore_1
 15: aload_1
 16: invokevirtual #6                  // Method java/lang/Thread.start:()V
 19: return
 LineNumberTable:
 line 8: 0
 line 13: 15
 line 14: 19
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      20     0  args   [Ljava/lang/String;
 15       5     1     t   Ljava/lang/Thread;
 }
 SourceFile: "JavaPNormal.java"
 InnerClasses:
 static #3; //class cn/didadu/sample/lambda/javap/JavaPNormal$1
 */


