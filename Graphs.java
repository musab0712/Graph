import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Graphs {

    public static void dfTraversal(int adjMatrix[][], int currVertex, boolean visited[]) {
        visited[currVertex] = true;
        System.out.print(currVertex + " ");
        for(int i = 0; i < adjMatrix.length; i++) {
            if(adjMatrix[currVertex][i] == 1 && visited[i] == false) {
                dfTraversal(adjMatrix, i, visited);
            }
        }
    }

    public static void dfTraversal(int adjMatrix[][]) {
        boolean visited[] = new boolean[adjMatrix.length]; 
        for(int i = 0; i < adjMatrix.length; i++){
            if(!visited[i])
                dfTraversal(adjMatrix, i, visited);
        }
    }

    public static void bfTraversal(int adjMatrix[][]) {
        Queue<Integer> pendingVertex = new  LinkedList<>();
        boolean visited[] = new boolean[adjMatrix.length];
        for(int i = 0; i < adjMatrix.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                pendingVertex.add(i);
        
                while(!pendingVertex.isEmpty()) {
                    int currVertex = pendingVertex.poll();
                    System.out.print(currVertex + " ");
                    for(int j = currVertex; j < adjMatrix.length; j++) {
                        if(adjMatrix[currVertex][j] == 1 && !visited[j]) {
                            pendingVertex.add(j);
                            visited[j] = true;
                        }
                    }
                } 
            }
        }
    }

    public static boolean isPath(int adjMatrix[][], int currVertex, int endVertex, boolean visited[]) {
        if(currVertex > (adjMatrix.length-1) || endVertex > (adjMatrix.length-1) ) {
            return false;
        }
        if(adjMatrix[currVertex][endVertex] == 1) {
            return true;
        }
        visited[currVertex] = true;
        for(int i = 0; i < adjMatrix.length; i++) {
            if(adjMatrix[currVertex][i] == 1 && visited[i] == false) {
                if(i == endVertex) {
                    return true;
                }else{
                    return isPath(adjMatrix, i, endVertex, visited);
                }
            }
        }
        return false;
    }

    public static boolean isPath(int adjMatrix[][], int startVertex, int endVertex){
        boolean visited[] = new boolean[adjMatrix.length];
        return isPath(adjMatrix, startVertex, endVertex, visited);
    }

    public static ArrayList<Integer> getPath(int adjMatrix[][], int sv, int ev, boolean[] visited) {
        if(sv == ev) {
            ArrayList<Integer> ans = new ArrayList<>();
            ans.add(ev);
            return ans;
        }
        visited[sv] = true;
        for(int i = 0; i < adjMatrix.length; i++) {
            if(adjMatrix[sv][i] == 1 && !visited[i]) {
                ArrayList<Integer> ans = getPath(adjMatrix, i, ev, visited);
                if(ans != null) {
                    ans.add(sv);
                    return ans;
                }
            }
        }
        return null;
    } 

    public static ArrayList<Integer> getPath(int adjMatrix[][], int sv, int ev) {
        boolean visited[] = new boolean[adjMatrix.length];
        return getPath(adjMatrix, sv, ev, visited);
    }

    public static void isConnected(int adjMatrix[][], int currVertex, boolean visited[]) {
        visited[currVertex] = true;
        for(int i = 0; i < adjMatrix.length; i++) {
            if(adjMatrix[currVertex][i] == 1 && visited[i] == false) {
                isConnected(adjMatrix, i, visited);
            }
        }
    }

    public static boolean isConnected(int adjMatrix[][]) {
        boolean visited[] = new boolean[adjMatrix.length]; 
        isConnected(adjMatrix, 0, visited);
        for(int i = 0; i < adjMatrix.length; i++){
            if(visited[i] == false){
                return false;
            }
        }
        return true;
    }

    // numbers of Island by BFS method

    public void bfsFill(char[][] grid, int row, int col, boolean[][] visited) {
        int dr[] = {1, 0, -1, 0};
        int dc[] = {0, -1, 0, 1};
        visited[row][col] = true;
        Queue<Pair> pending = new LinkedList<Pair>();
        pending.add(new Pair(row, col));
        int m = grid.length; 
        int n = grid[0].length;
        while(!pending.isEmpty()) {
            int ro = pending.peek().first;
            int co = pending.peek().second;
            pending.remove();
            
            for(int i = 0; i < 4; i++){
                int r = ro + dr[i];
                int c = co + dc[i];
                if(r >= 0 && r < m && c >= 0 && c < n && !visited[r][c] && grid[r][c] == '1') {
                        visited[r][c] = true;
                        pending.add(new Pair(r, c));
                    }
            }
        }
    }

    // numbers of Island by DFS method

    private void dfsFill(char[][] grid,int i, int j){
        if(i>=0 && j>=0 && i<grid.length && j<grid[0].length && grid[i][j]=='1'){
            grid[i][j]='0';
            dfsFill(grid, i + 1, j);
            dfsFill(grid, i - 1, j);
            dfsFill(grid, i, j + 1);
            dfsFill(grid, i, j - 1);
        }
    }
    
    // numbers of Island main method
    public int numIslands(char[][] grid) {
        int m = grid.length; // row
        int n = grid[0].length;  // coloum
        boolean[][] visited = new boolean[m][n];
        int count = 0; // for starting point
        for(int row = 0; row < m; row++) {
            for(int col = 0; col < n; col++) {
                // By DFS
                if(grid[row][col] == '1' ) {
                    count++;
                    dfsFill(grid, row, col);
                }
                // By BFS
                if(grid[row][col] == '1' && !visited[row][col]) {
                    count++;
                    bfsFill(grid, row, col, visited);
                }
            }
        }
        return count;
    }

    // numbers of province

    private void dfs(int[][] isConnected, int currVer, boolean[] visited) {
        visited[currVer] = true;
        for(int i = 0 ; i < isConnected.length; i++) {
            if(isConnected[currVer][i] == 1 && !visited[i]) {
                dfs(isConnected, i, visited);
            }
        }
    }

    public int findNumOfProvince(int[][] isConnected) {
        boolean visited[] = new boolean[isConnected.length];
        int count = 0;
        for(int i = 0 ; i < isConnected.length; i++) {
            if(!visited[i]) {
                count++;
                dfs(isConnected, i, visited);
            }
        }
        return count;
    }

    // Flood Fill

    private void dfs(int[][] image, int[][]ans, int row, int col, int color, int intialColor, 
    int[] delRow, int[] delCol) {
        ans[row][col] = color;
        int n = image.length;
        int m = image[0].length;
        for(int i = 0; i < 4; i++) {
            int adjRow = row + delRow[i];
            int adjCol = col +delCol[i];
            if(adjRow >= 0 && adjRow < n && adjCol >= 0 && adjCol < m && 
            image[adjRow][adjCol] == intialColor && ans[adjRow][adjCol] != color) {
                dfs(image, ans, adjRow, adjCol, color, intialColor, delRow, delCol);
            }
        }
    }
    

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int[][] ans = image;
        int intialColor = image[sr][sc];
        int[] delRow = {-1, 0, +1, 0};
        int[] delCol = {0, +1, 0, -1};
        dfs(image, ans, sr, sc, color, intialColor, delRow, delCol);
        return ans;
    }

    // Rotting Oranges

    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<Pair> q = new LinkedList<>();
        int[][] visited = new int[n][m];
        int countFresh = 0;
        for(int row = 0; row < n; row++) {
            for(int col = 0; col < m; col++) {
                if(grid[row][col] == 2) {
                    visited[row][col] = 2;
                    q.add(new Pair(row, col, 0));
                }
                if(grid[row][col] == 1) {
                    countFresh++;
                }
            }
        }
        int[] delRow = {-1,0,+1,0};
        int[] delCol = {0,+1,0,-1};
        int tm  = 0;
        int count = 0;
        while(!q.isEmpty()) {
            int r = q.peek().row;
            int c = q.peek().col;
            int t = q.peek().tm;
            tm = Math.max(tm, t);
            q.remove();

            for(int i = 0; i < 4; i++) {
                int ro = delRow[i] + r;
                int cl = delCol[i] + c;
                if (ro >= 0 && ro < n && cl >= 0 && cl < m &&
                visited[ro][cl] == 0 && grid[ro][cl] == 1) {
                    q.add(new Pair(ro, cl, tm + 1));
                    visited[ro][cl] = 2;
                    count++;
                }
            }
        }
        if (count != countFresh) return -1;
        return tm;
    }

    // Print Adjacency Matrix

    public static void printAdjMatrix(int adjMatrix[][]) {
        for(int i = 0; i < adjMatrix.length ; i++) {
            for(int j = 0; j < adjMatrix.length; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int e = sc.nextInt();
        int adjMatrix[][] = new int[n][n];
        for(int i = 0; i < e; i++){
            int v1 = sc.nextInt();
            int v2  = sc.nextInt();
            adjMatrix[v1][v2] = 1;
            adjMatrix[v2][v1] = 1;
        }
        System.out.println("Matrix");
        printAdjMatrix(adjMatrix);
        System.out.print("Depth First Traversla : ");
        dfTraversal(adjMatrix);
        System.out.println();
        System.out.print("Breadth First Traversal : ");
        bfTraversal(adjMatrix);
        System.out.println();
        // System.out.println("Enter the start and end vertex for the Path: ");
        // int sv = sc.nextInt();
        // int ev = sc.nextInt();
        // boolean isPath = isPath(adjMatrix, start, end);
        // ArrayList<Integer> ans = getPath(adjMatrix, sv, ev);
        // if(ans != null){
        //     System.out.print("Path b/w " + sv + " and " + ev + " : ");
        //     for(int i = 0; i < ans.size(); i++) {
        //         System.out.print(ans.get(i) + "  ");
        //     }
        // }
        // else{
        //     System.out.println(" Path Not Found");
        // }
        System.out.print("Is Connected : " + isConnected(adjMatrix));

    }
}