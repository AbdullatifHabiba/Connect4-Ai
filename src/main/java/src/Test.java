package src;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Test obj =new Test();
        GamePlay.currentState =new StateNode();
        GamePlay.setChildren(new ArrayList<>(),new ArrayList<>());
        obj.user();
    }

    public static void printMax(boolean[] arr, boolean[] played) {
        for(int i=arr.length-1;i>=0;i=i-7)
        {
            for(int j=i-6;j<=i;j++)
            {
                if(arr[j]  && played[j])
                {
                    System.out.print(1);
                }
                else if(!arr[j]  && played[j])
                {
                    System.out.print(0);
                }else{
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    public void user() {
        Scanner sc =new Scanner(System.in);
        int col =sc.nextInt();
        StateNode node = GamePlay.userTurn(col);
        printMax(node.color, node.played);
        computer();
    }

    public void computer() {
        StateNode node = GamePlay.myTurn(4,false);
        printMax(node.color, node.played);
        user();
    }
}
