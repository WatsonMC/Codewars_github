import code.AssemblerInterpreter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;

public class AssemblerInterpreterTest {

    @Test
    public void testMsg(){
        code.AssemblerInterpreter.Environment.setRegister("a",109);
        String pgrm = "mov a 109\n" +
                "msg  '(5+1)/2 = ', a\n"+
                "end";

        String pgrmOut = AssemblerInterpreter.interpret(pgrm);
        System.out.println(pgrmOut);
    }

    @Test
    public void loopingTest (){
        String pgrm = "mov c, 14   ; instruction mov c, 14\n" +
                "mov d, 112   ; instruction mov d, 112\n" +
                "call func\n" +
                "msg 'Random result: ', g\n" +
                "end\n" +
                "\n" +
                "func:\n" +
                "  cmp c, d\n" +
                "  jge exit\n" +
                "  mov g, c\n" +
                "  mul g, d\n" +
                "  ret\n" +
                "; Do nothing\n" +
                "exit:\n" +
                "  msg 'Do nothing'\n" +
                "\n" +
                "msg 'Random result: ', g\n" +
                "  msg 'Do nothing'";
        String pgrmOut = AssemblerInterpreter.interpret(pgrm);
        System.out.println(pgrmOut);

    }

    @Test
    public void testSimple(){
        String pgrm = "\n; My first program\nmov  a, 5\ninc  a\ncall function\nmsg  '(5+1)/2 = ', a    ; output message\nend\n\nfunction:\n    div  a, 2\n    ret\n";
        String pgrmOut = AssemblerInterpreter.interpret(pgrm);
        System.out.println(pgrmOut);

    }

    @Test
    public void sampleTests() {
        for (int i = 0 ; i < expected.length ; i++) {
            String output = code.AssemblerInterpreter.interpret(displayProg(progs[i]));
            assertEquals(expected[i], output);
        }
    }

    private static String[] progs = {
            "\n; My first program\nmov  a, 5\ninc  a\ncall function\nmsg  '(5+1)/2 = ', a    ; output message\nend\n\nfunction:\n    div  a, 2\n    ret\n",

            "\nmov   a, 5\nmov   b, a\nmov   c, a\ncall  proc_fact\ncall  print\nend\n\nproc_fact:\n    dec   b\n    mul   c, b\n    cmp   b, 1\n    jne   proc_fact\n    ret\n\nprint:\n    msg   a, '! = ', c ; output text\n    ret\n",

            "\nmov   a, 8            ; value\nmov   b, 0            ; next\nmov   c, 0            ; counter\nmov   d, 0            ; first\nmov   e, 1            ; second\ncall  proc_fib\ncall  print\nend\n\nproc_fib:\n    cmp   c, 2\n    jl    func_0\n    mov   b, d\n    add   b, e\n    mov   d, e\n    mov   e, b\n    inc   c\n    cmp   c, a\n    jle   proc_fib\n    ret\n\nfunc_0:\n    mov   b, c\n    inc   c\n    jmp   proc_fib\n\nprint:\n    msg   'Term ', a, ' of Fibonacci series is: ', b        ; output text\n    ret\n",

            "\nmov   a, 11           ; value1\nmov   b, 3            ; value2\ncall  mod_func\nmsg   'mod(', a, ', ', b, ') = ', d        ; output\nend\n\n; Mod function\nmod_func:\n    mov   c, a        ; temp1\n    div   c, b\n    mul   c, b\n    mov   d, a        ; temp2\n    sub   d, c\n    ret\n",

            "\nmov   a, 81         ; value1\nmov   b, 153        ; value2\ncall  init\ncall  proc_gcd\ncall  print\nend\n\nproc_gcd:\n    cmp   c, d\n    jne   loop\n    ret\n\nloop:\n    cmp   c, d\n    jg    a_bigger\n    jmp   b_bigger\n\na_bigger:\n    sub   c, d\n    jmp   proc_gcd\n\nb_bigger:\n    sub   d, c\n    jmp   proc_gcd\n\ninit:\n    cmp   a, 0\n    jl    a_abs\n    cmp   b, 0\n    jl    b_abs\n    mov   c, a            ; temp1\n    mov   d, b            ; temp2\n    ret\n\na_abs:\n    mul   a, -1\n    jmp   init\n\nb_abs:\n    mul   b, -1\n    jmp   init\n\nprint:\n    msg   'gcd(', a, ', ', b, ') = ', c\n    ret\n",

            "\ncall  func1\ncall  print\nend\n\nfunc1:\n    call  func2\n    ret\n\nfunc2:\n    ret\n\nprint:\n    msg 'This program should return null'\n",

            "\nmov   a, 2            ; value1\nmov   b, 10           ; value2\nmov   c, a            ; temp1\nmov   d, b            ; temp2\ncall  proc_func\ncall  print\nend\n\nproc_func:\n    cmp   d, 1\n    je    continue\n    mul   c, a\n    dec   d\n    call  proc_func\n\ncontinue:\n    ret\n\nprint:\n    msg a, '^', b, ' = ', c\n    ret\n"};

    private static String[] expected = {"(5+1)/2 = 3",
            "5! = 120",
            "Term 8 of Fibonacci series is: 21",
            "mod(11, 3) = 2",
            "gcd(81, 153) = 9",
            null,
            "2^10 = 1024"};


    private String displayProg(String p) {
        System.out.println("\n----------------------\n");
        System.out.println(p);
        return p;
    }
}