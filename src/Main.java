import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int[][] dungeon;
    static int[] dirY = {0, 0, 1, -1};
    static int[] dirX = {1, -1, 0, 0};

    static boolean blessedFlag = false;

    // 좌표 클래스
    static class Point {
        int x, y, distance;
        boolean buf;

        Point(int x, int y, int distance, boolean buf) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.buf = buf;
        }
    }

    static boolean isValidMap(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    // BFS를 이용한 최단 거리 계산 함수
    static int shortestPath() {
        Queue<Point> que = new LinkedList<>();
        boolean[][] visit = new boolean[N][M];
        que.add(new Point(0, 0, 0, false));
        visit[0][0] = true;
        while (!que.isEmpty()) {
            Point po = que.poll();

//            if (dungeon[po.y][po.x] == 0 || dungeon[po.y][po.x] == 3) continue;
            if (po.x == N-1 && po.y == M-1) {
                return po.distance;
            }


            if (dungeon[po.x][po.y] == 2) {
                // 여신의 석상을 만난 경우, 여신의 가호를 얻음
                que = new LinkedList<>();
                visit = new boolean[N][M];
                que.add(new Point(po.x, po.y, po.distance, true));
                visit[po.x][po.y] = true;
                po = que.poll();
            }
//            if (dungeon[po.y][po.x] == 0) continue;
//            if (!bufFlag && dungeon[po.y][po.x] == 3) continue;

            for (int i = 0; i < 4; i++) {
                int nx = po.x + dirX[i];
                int ny = po.y + dirY[i];

                if (!isValidMap(nx, ny)) continue;
                if (visit[nx][ny]) continue;
                if (dungeon[nx][ny] == 0) continue;
                if (!po.buf && dungeon[nx][ny] == 3) continue;


                if (dungeon[nx][ny] == 4) {
                    po.buf = false;
                    int iceStep = 1;
                    boolean procFlag = false;
                    while (dungeon[nx][ny] == 4) {
                        nx += dirX[i];
                        ny += dirY[i];
                        if (!isValidMap(nx, ny)) break;
                        if (!visit[nx][ny]) {
                            visit[nx][ny] = true;
                            procFlag = true;
                        }
                        if (dungeon[nx][ny] == 3 || dungeon[nx][ny] == 0) {
                            nx = nx - dirX[i];
                            ny = ny - dirY[i];
                            break;
                        }
                        iceStep++;
                    }
                    if (procFlag) {
                        que.add(new Point(nx, ny, po.distance + iceStep, po.buf));
                    }
                } else {
                    if (!visit[nx][ny]) {
                        visit[nx][ny] = true;
                        que.add(new Point(nx, ny, po.distance + 1, po.buf));
                    }
                }

            }

        }
        return -1;
    }

    public static void main(String[] args) throws IOException {

//        System.setIn(new java.io.FileInputStream("src/input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        // 던전의 크기 입력 받기
        N = Integer.parseInt(stinit.nextToken());
        M = Integer.parseInt(stinit.nextToken());
        dungeon = new int[N][M];

        // 던전 구성 입력 받기
        for (int i = 0; i < N; i++) {
            stinit = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < M; j++) {
                dungeon[i][j] = Integer.parseInt(stinit.nextToken());
            }
        }

        int sd = shortestPath();
        System.out.println(sd);
    }
}
