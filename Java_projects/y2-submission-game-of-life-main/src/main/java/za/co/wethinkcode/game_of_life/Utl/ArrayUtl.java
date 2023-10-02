package za.co.wethinkcode.game_of_life.Utl;

public class ArrayUtl {
    public static int[][] setArray(String s){


        String[] rows = s.split("], \\[");
        for (int i = 0; i < rows.length; i++) {
            rows[i] = rows[i].replace("[[", "").replace("]]", "").replaceAll(" ", "");
        }


        int numberOfColumns = rows[0].split(",").length;

        String[][] matrix = new String[rows.length][numberOfColumns];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }


        int[][] stringToInt = new int[matrix.length][matrix[0].length];

        for (int a = 0; a < matrix.length; a++) {
            for (int b = 0; b < matrix[0].length; b++) {
                stringToInt[a][b] = Integer.parseInt(matrix[a][b]);
            }
        }

        return stringToInt;
    }

}
