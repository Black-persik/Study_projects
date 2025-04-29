// Ivan Vasilev 
import java.util.Scanner;
public class ShutingYard2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] splitString = s.split(" ");
        Calculation2 calculation = new Calculation2();
        calculation.arrayInput = splitString;
        String[] ans = calculation.answer(splitString);
        int forTaskAlen = 0;
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] != null){
                forTaskAlen++;
            } else {
                break;
            }
        }
        String[] clearAns = new String[forTaskAlen];
        for (int i = 0; i < forTaskAlen; i++) {
            clearAns[i] = ans[i];
        }
        String answerTaskB = calculation.taskb(clearAns);
        System.out.println(answerTaskB);

    }
}
interface Stack2<String>{
    void push(String s, String[] array);
    void pop();
    String peek();
    boolean isEmpty();
    int size();
}
class Calculation2 implements Stack2<String>{
    String[] arrayInput = new String[200000];
    String[] stackArray = new String[200000];
    String[] outputArray = new String[200000];
    public static final String NUM = "1234567890";
    public static final String OPERATORS_STRONG = "*/";
    public static final String OPERATORS_WEAK = "+-";
    public static final String PARENTHESES = "()";
    public static final String MAXMIN = "maxmin";
    @Override
    public void push(String s, String[] array) { // что и куда
        for (int i = 0; i < array.length; i++){
            if (array[i] == null){
                array[i] = s;
                break;
            }
        }
    }
    @Override
    public void pop(){
        for (int i = 1; i < stackArray.length; i++){
            if (stackArray[i] == null){
                stackArray[i - 1] = null;
                break;
            }
        }
    }
    @Override
    public String peek(){
        for (int i = 1; i < stackArray.length; i++){
            if (stackArray[i] == null){
                return stackArray[i - 1];
            }
        }
        return "a";
    }
    @Override
    public boolean isEmpty(){
        return stackArray[0] == null;
    }
    @Override
    public int size(){
        return stackArray.length;
    }
    public int value(String s){
        if (s.equals("-") || s.equals("+")){
            return 1;
        }
        if (s.equals("*") || s.equals("/")){
            return 2;
        }
        return 0;
    }


    public String[] answer(String[] input){
        for (int i = 0; i < input.length; i++){
            if (NUM.contains(input[i])){
                push(input[i], outputArray);
            } else if (MAXMIN.contains(input[i])){
                push(input[i], stackArray);
            } else if (input[i].equals("(")) {
                push(input[i], stackArray);
            } else if (input[i].equals(")")){
                while (peek() != null && !peek().equals("(")){
                    push(peek(), outputArray);
                    pop();
                }
                pop();
                if(peek() != null && (peek().equals("max") || peek().equals("min"))) {
                    push(peek(), outputArray);
                    pop();
                }

            } else if (input[i].equals(",")){
                while (peek() != null && !peek().equals("(")){
                    push(peek(), outputArray);
                    pop();
                }
            }
            else {
                while (peek() != null && (value(peek()) >= value(input[i]))) {
                    push(peek(), outputArray);
                    pop();
                }
                push(input[i], stackArray);
            }

        }
        while (peek() != null){
            push(peek(), outputArray);
            pop();
        }
        return outputArray;
    }
    public String taskb(String[] taskA){
        for (int i = 0; i < taskA.length; i++){
            if (NUM.contains(taskA[i])) {
                push(taskA[i], stackArray);
            } else if (MAXMIN.contains(taskA[i])) {
                int first = Integer.parseInt(peek());
                pop();
                int second = Integer.parseInt(peek());
                pop();
                int min = Math.min(first, second);
                if (taskA[i].equals("min")) {
                    push(String.valueOf(min), stackArray);
                }
                if (taskA[i].equals("max")) {
                    int max = Math.max(first, second);
                    push(String.valueOf(max), stackArray);
                }
            } else if (taskA[i].equals("+") || taskA[i].equals("-") || taskA[i].equals("*") || taskA[i].equals("/")) {
                int first = Integer.parseInt(peek());
                pop();
                int second = Integer.parseInt(peek());
                pop();
                switch (taskA[i]) {
                    case "+":
                        push(String.valueOf(second + first), stackArray);
                        break;
                    case "-":
                        push(String.valueOf(second - first), stackArray);
                        break;
                    case "*":
                        push(String.valueOf(second * first), stackArray);
                        break;
                    case "/":
                        push(String.valueOf(second / first), stackArray);
                        break;
                }
            }
        }

        return peek();
    }




}
