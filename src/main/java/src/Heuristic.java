package src;

public class Heuristic {
    private final int[] heuristicArr = new int[42];

    public int calculateHeuristic(StateNode s, StateNode current) {
        setHeuristic();
        if (s.equals(current))
            return 0;
        int[] points = pointsNextTo(s);
        int yellowAddedPoints = (int) Math.pow(s.getYellowPoints() - current.getYellowPoints(), 2);
        int redAddedPoints = (int) Math.pow(s.getRedPoints() - current.getRedPoints(), 2);
        int utl = 10000 * yellowAddedPoints - 10000 * redAddedPoints;
        if (!s.color[s.getLastIndexPlayed()]) {
            for (int i = 0;i < 4;i++){
                if (points[i] == 1) {
                    utl += feature3(s,i);
                }
                else if (points[i] == 2)
                    utl += feature2(s,i);
            }
            return utl + heuristicArr[s.getLastIndexPlayed()];
        } else {
            for (int i = 0;i < 4;i++){
                if (points[i] == 1)
                    utl -= feature3(s,i);
                else if (points[i] == 2)
                    utl -= feature2(s,i);
            }
            return  utl - heuristicArr[s.getLastIndexPlayed()];
        }
    }

    public int feature2(StateNode s , int positionOf3)
    {
      if(positionOf3==0)
      {
          if(s.getLastIndexPlayed()+7<42 && !s.played[s.getLastIndexPlayed()+7])
          {
              return 50;
          }
          else{
              return 20;
          }
      }
      else if(positionOf3 ==1) {
          int p = s.getLastIndexPlayed();
          if ((p-2)>=0 &&(p+2)<42 &&(p - 2) / 7 == (p / 7)
                  && (p + 2) / 7 == (p / 7) && !s.played[p - 2] && !s.played[p + 2]) {
              return 70;
          } else if (((p-2)>=0 && (p - 2) / 7 == (p / 7) && !s.played[p - 2]) ||
                  ((p + 2)<42 && (p + 2) / 7 == (p / 7) && !s.played[p + 2])) {
              return 50;
          } else {
              return 20;
          }
      }
      else if(positionOf3 ==2)
      {
          int p=s.getLastIndexPlayed();
          if((p-7+1)>=0 &&(p+14-2)<42 &&(p-7+1)/7==(p/7-1) && s.color[(p-7+1)]==s.color[p]
                  && (p+14-2)/7==(p/7+1) && s.color[(p+14-2)]==s.color[p] && !s.played[p+7-1])
          {
              return 70;
          }
          else{
              return 20;
          }
      }
      else if(positionOf3 ==3)
      {
          int p=s.getLastIndexPlayed();
          if((p-7-1)>=0 &&(p+14+2)<42 && (p-7-1)/7==(p/7-1) && s.color[(p-7-1)]==s.color[p]
                  && (p+14+2)/7==(p/7+2) && s.color[(p+14+2)]==s.color[p] && !s.played[p+7+1])
          {
              return 70;
          }
          else{
              return 20;
          }
      }
      return 20;
      }

    public int feature3(StateNode s , int positionOf3){
        if(positionOf3==0)
        {
            if(s.getLastIndexPlayed()+7<42 && !s.played[s.getLastIndexPlayed()+7])
            {
                return 30;
            }
            else{
                return 15;
            }
        }
        else if(positionOf3 ==1) {
            int p = s.getLastIndexPlayed();
            if ((p-2)>=0 &&(p+1)<42 &&(p - 2) / 7 == (p / 7) && (p + 1) / 7 == (p / 7) && !s.played[p - 2] && !s.played[p + 1]
            ||(p-1)>=0 &&(p+2)<42 &&(p - 1) / 7 == (p / 7) && (p + 2) / 7 == (p / 7) && !s.played[p - 1] && !s.played[p + 2]) {
                return 45;
            } else {
                return 15;
            }
        }
        return 15;
    }


    public void setHeuristic() {
        heuristicArr[0] = 3;
        heuristicArr[1] = 4;
        heuristicArr[2] = 5;
        heuristicArr[3] = 7;
        heuristicArr[4] = 5;
        heuristicArr[5] = 4;
        heuristicArr[6] = 3;
        heuristicArr[7] = 4;
        heuristicArr[8] = 6;
        heuristicArr[9] = 8;
        heuristicArr[10] = 10;
        heuristicArr[11] = 8;
        heuristicArr[12] = 6;
        heuristicArr[13] = 4;
        heuristicArr[14] = 5;
        heuristicArr[15] = 8;
        heuristicArr[16] = 11;
        heuristicArr[17] = 13;
        heuristicArr[18] = 11;
        heuristicArr[19] = 8;
        heuristicArr[20] = 5;
        heuristicArr[21] = 5;
        heuristicArr[22] = 8;
        heuristicArr[23] = 11;
        heuristicArr[24] = 13;
        heuristicArr[25] = 11;
        heuristicArr[26] = 8;
        heuristicArr[27] = 5;
        heuristicArr[28] = 4;
        heuristicArr[29] = 6;
        heuristicArr[30] = 8;
        heuristicArr[31] = 10;
        heuristicArr[32] = 8;
        heuristicArr[33] = 6;
        heuristicArr[34] = 4;
        heuristicArr[35] = 3;
        heuristicArr[36] = 4;
        heuristicArr[37] = 5;
        heuristicArr[38] = 7;
        heuristicArr[39] = 5;
        heuristicArr[40] = 4;
        heuristicArr[41] = 3;
    }

    int[] pointsNextTo(StateNode s) {
        int indexOfPlay = s.getLastIndexPlayed();
        int left = 0;
        int right = 0;
        int down = 0;
        int upLeft = 0;
        int upRight = 0;
        int downLeft = 0;
        int downRight = 0;

        // down
        int p = indexOfPlay - 7;
        int i = 0;
        while (p >= 0 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p -= 7;
            i++;
            down++;
        }
        // right
        p = indexOfPlay + 1;
        i = 0;
        while (p / 7 == indexOfPlay / 7 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p += 1;
            i++;
            right++;
        }
        p = indexOfPlay - 1;
        i = 0;
        // left
        while (p >= 0 && p / 7 == indexOfPlay / 7 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p -= 1;
            i++;
            left++;
        }
        // up left
        p = indexOfPlay + 6;
        i = 0;
        while (p < 42 && p % 7 != 6 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p += 6;
            i++;
            upLeft++;
        }
        // up right
        p = indexOfPlay + 8;
        i = 0;
        while (p < 42 && p % 7 != 0 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p += 8;
            i++;
            upRight++;
        }
        // down left
        p = indexOfPlay - 8;
        i = 0;
        while (p >= 0 && p < 42 && p % 7 != 6 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p -= 8;
            i++;
            downLeft++;
        }
        // down right
        p = indexOfPlay - 6;
        i = 0;
        while (p >= 0 && p < 42 && p % 7 != 0 && i < 3 && s.played[p] && s.color[p] == s.color[indexOfPlay]) {
            p -= 6;
            i++;
            downRight++;
        }
        return new int[]{down, right + left, upLeft + downRight, upRight + downLeft};
    }
}
