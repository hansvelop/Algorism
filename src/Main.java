import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N, M;
    static int[][] dungeon;
    static int[] dirY = {0, 0, 1, -1};
    static int[] dirX = {1, -1, 0, 0};

    static boolean blessedFlag = false;

    // 좌표 클래스
    static class Point {
        int x, y, distance;

        Point(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }

    // BFS를 이용한 최단 거리 계산 함수
    static int shortestPath(int[] sp, int[] ep) {
        Queue<Point> que = new LinkedList<>();
        boolean[][] visit = new boolean[N][M];
        que.add(new Point(sp[0], sp[1], 0));
        visit[0][0] = true;
        while (!que.isEmpty()) {
            Point po = que.poll();

            if (po.x == ep[0] && po.y == ep[1]) {
                return po.distance;
            }

//            if (dungeon[po.y][po.x] == 0 || dungeon[po.y][po.x] == 3) continue;

            for (int i = 0; i < 4; i++) {
                int nx = po.x + dirX[i];
                int ny = po.y + dirY[i];

                if (ny < 0 || nx < 0 || nx > N - 1 || ny > M - 1) continue;
                if (visit[nx][ny] || (dungeon[nx][ny] != 1 && dungeon[nx][ny] != 4 && dungeon[nx][ny] != 2)) continue;

                visit[nx][ny] = true;
                que.add(new Point(nx, ny, po.distance + 1));

            }

        }
        return -1;
    }

    public static void main(String[] args) throws IOException {

        System.setIn(new java.io.FileInputStream("src/input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        // 던전의 크기 입력 받기
        N = Integer.parseInt(stinit.nextToken());
        M = Integer.parseInt(stinit.nextToken());
        dungeon = new int[N][M];

        int blessed[] = null;
        // 던전 구성 입력 받기
        for (int i = 0; i < N; i++) {
            stinit = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                dungeon[i][j] = Integer.parseInt(stinit.nextToken());
                if (dungeon[i][j] == 2) {
                    blessed = new int[]{i, j};
                }
            }
        }

        // BFS 실행하여 결과 출력
        int sum = 0;
        int sd = shortestPath(new int[]{0, 0}, new int[]{N - 1, M - 1});
        if (sd == -1) {
            sd = shortestPath(new int[]{0, 0}, new int[]{blessed[0], blessed[1]});
            if (sd == -1) {
                sum = -1;
            } else {
                sum = sd;
                blessedFlag = true;
                sd = shortestPath(new int[]{blessed[0], blessed[1]}, new int[]{N - 1, M - 1});
                if(sd == -1){
                    sum = -1;
                }else{
                    sum += sd;
                }

            }
        } else {
            sum = sd;
        }
        System.out.println(sum);
    }
}
