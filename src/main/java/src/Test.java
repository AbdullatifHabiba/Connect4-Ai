package src;


import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        Test obj =new Test();
        GamePlay.currentState =new StateNode();
        GamePlay.setChildrens(new ArrayList<>());
        obj.user();

    }
    public void user()
    {
        Scanner src =new Scanner(System.in);
        int col =src.nextInt();
        StateNode node =GamePlay.userTurn(6,col);

        printMax(node.color, node.played);
        computer();
    }
    public void computer()
    {
        StateNode node =GamePlay.myTurn(6);
        printMax(node.color, node.played);
        user();
    }
    public static void printMax(boolean[] arr,boolean[] played)
    {
        for(int i=arr.length-1;i>=0;i--)
        {
            if(arr[i]  && played[i])
            {
                System.out.print(1);
            }
            else if(!arr[i]  && played[i])
            {
                System.out.print(0);
            }else{
                System.out.print("-");
            }
            if(i%7==0)
            {
                System.out.println();
            }

        }
    }
}