package cn.didadu.sample.lambda.javap;

/**
 * Created by admin on 15/10/22.
 */
public class JavaPLambda {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("Hello World!"));
        t.start();
    }
}
/**
 Last modified 2015-10-22; size 1273 bytes
 MD5 checksum 2779605d156036ff7ef7c21063f6f527
 Compiled from "JavaPLambda.java"
 public class cn.didadu.sample.lambda.javap.JavaPLambda
 minor version: 0
 major version: 52
 flags: ACC_PUBLIC, ACC_SUPER
 Constant pool:
 #1 = Methodref          #10.#27        // java/lang/Object."<init>":()V
 #2 = Class              #28            // java/lang/Thread
 #3 = InvokeDynamic      #0:#33         // #0:run:()Ljava/lang/Runnable;
 #4 = Methodref          #2.#34         // java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
 #5 = Methodref          #2.#35         // java/lang/Thread.start:()V
 #6 = Fieldref           #36.#37        // java/lang/System.out:Ljava/io/PrintStream;
 #7 = String             #38            // Hello World!
 #8 = Methodref          #39.#40        // java/io/PrintStream.println:(Ljava/lang/String;)V
 #9 = Class              #41            // cn/didadu/sample/lambda/javap/JavaPLambda
 #10 = Class              #42            // java/lang/Object
 #11 = Utf8               <init>
 #12 = Utf8               ()V
 #13 = Utf8               Code
 #14 = Utf8               LineNumberTable
 #15 = Utf8               LocalVariableTable
 #16 = Utf8               this
 #17 = Utf8               Lcn/didadu/sample/lambda/javap/JavaPLambda;
 #18 = Utf8               main
 #19 = Utf8               ([Ljava/lang/String;)V
 #20 = Utf8               args
 #21 = Utf8               [Ljava/lang/String;
 #22 = Utf8               t
 #23 = Utf8               Ljava/lang/Thread;
 #24 = Utf8               lambda$main$0
 #25 = Utf8               SourceFile
 #26 = Utf8               JavaPLambda.java
 #27 = NameAndType        #11:#12        // "<init>":()V
 #28 = Utf8               java/lang/Thread
 #29 = Utf8               BootstrapMethods
 #30 = MethodHandle       #6:#43         // invokestatic java/lang/invoke/LambdaMetafactory.metafactory:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
 #31 = MethodType         #12            //  ()V
 #32 = MethodHandle       #6:#44         // invokestatic cn/didadu/sample/lambda/javap/JavaPLambda.lambda$main$0:()V
 #33 = NameAndType        #45:#46        // run:()Ljava/lang/Runnable;
 #34 = NameAndType        #11:#47        // "<init>":(Ljava/lang/Runnable;)V
 #35 = NameAndType        #48:#12        // start:()V
 #36 = Class              #49            // java/lang/System
 #37 = NameAndType        #50:#51        // out:Ljava/io/PrintStream;
 #38 = Utf8               Hello World!
 #39 = Class              #52            // java/io/PrintStream
 #40 = NameAndType        #53:#54        // println:(Ljava/lang/String;)V
 #41 = Utf8               cn/didadu/sample/lambda/javap/JavaPLambda
 #42 = Utf8               java/lang/Object
 #43 = Methodref          #55.#56        // java/lang/invoke/LambdaMetafactory.metafactory:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
 #44 = Methodref          #9.#57         // cn/didadu/sample/lambda/javap/JavaPLambda.lambda$main$0:()V
 #45 = Utf8               run
 #46 = Utf8               ()Ljava/lang/Runnable;
 #47 = Utf8               (Ljava/lang/Runnable;)V
 #48 = Utf8               start
 #49 = Utf8               java/lang/System
 #50 = Utf8               out
 #51 = Utf8               Ljava/io/PrintStream;
 #52 = Utf8               java/io/PrintStream
 #53 = Utf8               println
 #54 = Utf8               (Ljava/lang/String;)V
 #55 = Class              #58            // java/lang/invoke/LambdaMetafactory
 #56 = NameAndType        #59:#63        // metafactory:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
 #57 = NameAndType        #24:#12        // lambda$main$0:()V
 #58 = Utf8               java/lang/invoke/LambdaMetafactory
 #59 = Utf8               metafactory
 #60 = Class              #65            // java/lang/invoke/MethodHandles$Lookup
 #61 = Utf8               Lookup
 #62 = Utf8               InnerClasses
 #63 = Utf8               (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
 #64 = Class              #66            // java/lang/invoke/MethodHandles
 #65 = Utf8               java/lang/invoke/MethodHandles$Lookup
 #66 = Utf8               java/lang/invoke/MethodHandles
 {
 public cn.didadu.sample.lambda.javap.JavaPLambda();
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
 0       5     0  this   Lcn/didadu/sample/lambda/javap/JavaPLambda;

 public static void main(java.lang.String[]);
 descriptor: ([Ljava/lang/String;)V
 flags: ACC_PUBLIC, ACC_STATIC
 Code:
 stack=3, locals=2, args_size=1
 0: new           #2                  // class java/lang/Thread
 3: dup
 4: invokedynamic #3,  0              // InvokeDynamic #0:run:()Ljava/lang/Runnable;
 9: invokespecial #4                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
 12: astore_1
 13: aload_1
 14: invokevirtual #5                  // Method java/lang/Thread.start:()V
 17: return
 LineNumberTable:
 line 8: 0
 line 9: 13
 line 10: 17
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      18     0  args   [Ljava/lang/String;
 13       5     1     t   Ljava/lang/Thread;
 }
 SourceFile: "JavaPLambda.java"
 InnerClasses:
 public static final #61= #60 of #64; //Lookup=class java/lang/invoke/MethodHandles$Lookup of class java/lang/invoke/MethodHandles
 BootstrapMethods:
 0: #30 invokestatic java/lang/invoke/LambdaMetafactory.metafactory:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
 Method arguments:
 #31 ()V
 #32 invokestatic cn/didadu/sample/lambda/javap/JavaPLambda.lambda$main$0:()V
 #31 ()V
 */

