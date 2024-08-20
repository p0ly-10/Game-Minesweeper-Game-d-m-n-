package gameDoMin;

import DAO.nguoichoiDAO;
import POJO.nguoiChoi;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.Timer;

public class gameDoMin extends JFrame implements ActionListener, KeyListener {
    int BOM, dem = 0;
    int maxXY = 100;
    boolean die = false;
    Timer timer;
    boolean flag = false;
    private Color bom_cl = Color.red;
    int key_flag = KeyEvent.VK_H;
    private Color background_number_cl = Color.yellow;
    private Color background_null_cl = Color.gray;
    private Color flag_cl = Color.green;
    private int m, n;
    int M[] = {8, 15, 21};
    int N[] = {8, 19, 27};
    int Mines[] = {10, 40, 100};
    private int values[][] = new int[maxXY][maxXY];
    private JButton bt[][] = new JButton[maxXY][maxXY];
    private JLabel point_lb, hightPoint_lb, temp_lb, flag_lb, lv_lb;
    private JComboBox<String> lv = new JComboBox<>();
    private boolean tick[][] = new boolean[maxXY][maxXY];
    private JButton repeat_bt, mines_bt;
    private JPanel pn0, pn, pn2, leaderboardPanel;
    private JTable leaderboardTable;
    private Leaderboard leaderboard;
    Container cn;
    nguoiChoi nguoi;
            

    public gameDoMin(String s, int k, nguoiChoi nguoi) {
        super(s);
        this.nguoi = nguoi; // Lưu đối tượng nguoiChoi
        BOM = Mines[k];
        m = M[k];
        n = N[k];
        initBom();
        cn = this.getContentPane();
        pn = new JPanel();
        pn.setLayout(new GridLayout(m, n));
        for (int i = 0; i <= m + 1; i++)
            for (int j = 0; j <= n + 1; j++) {
                bt[i][j] = new JButton("   ");
                tick[i][j] = true;
            }
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                bt[i][j] = new JButton("   ");
                pn.add(bt[i][j]);
                bt[i][j].setActionCommand(i + " " + j);
                bt[i][j].addActionListener(this);
                bt[i][j].addKeyListener(this);
            }
        point_lb = new JLabel("00:00:00:00");
        point_lb.setFont(new Font("Arial", 1, 20));
        hightPoint_lb = new JLabel(hightPoint(k));
        hightPoint_lb.setFont(new Font("Arial", 1, 20));
        temp_lb = new JLabel("     ");
        mines_bt = new JButton(String.valueOf(BOM));
        mines_bt.setBackground(flag_cl);
        repeat_bt = new JButton("Ván mới");
        repeat_bt.addActionListener(this);
        flag_lb = new JLabel("Đang gỡ bom (h)");
        flag_lb.setForeground(bom_cl);
        flag_lb.setFont(new Font("Arial", 1, 15));
        lv_lb = new JLabel("Mức độ: ");
        lv_lb.setFont(new Font("Arial", 1, 15));
        lv.addItem("Dễ");
        lv.addItem("Trung bình");
        lv.addItem("Khó");
        lv.setSelectedIndex(k);
        pn2 = new JPanel();
        pn2.setLayout(new FlowLayout());
        pn2.add(lv_lb);
        pn2.add(lv);
        pn2.add(mines_bt);
        pn2.add(repeat_bt);
        pn2.add(flag_lb);
        pn0 = new JPanel();
        pn0.setLayout(new FlowLayout());
        pn0.add(point_lb);
        pn0.add(temp_lb);
        pn0.add(hightPoint_lb);
        cn.add(pn0, "North");
        cn.add(pn);
        cn.add(pn2, "South");

        // Initialize leaderboard
        leaderboard = new Leaderboard(k);
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BorderLayout());
        leaderboardTable = new JTable((TableModel) leaderboard);
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);
        cn.add(leaderboardPanel, "East");

        this.setVisible(true);
        this.pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                point_lb.setText(next(point_lb));
            }
        });
    }
    

    public gameDoMin(nguoiChoi nguoi) {
    // Gọi constructor mặc định với các thông số mặc định
    this("CodeLearn.io - Game Dò Mìn", 0, nguoi);
}
    

    public void initBom() {
        float ratio = (float) 0.05; // Tỉ lệ tạo mìn những ô xung quanh
        int i, j;
        for (i = 0; i <= m + 1; i++)
            for (j = 0; j <= n + 1; j++)
                values[i][j] = 0;
        while (dem < BOM) {
            // Chọn ra tọa độ chưa phải là mìn.
            do {
                i = (int) ((m - 1) * Math.random()) + 1;
                j = (int) ((n - 2) * Math.random()) + 1;
            } while (values[i][j] != 0);
            if (values[i][j] == 0)
                init(i, j, ratio);
        }
    }

    public void init(int i, int j, float ratio) {
        if (Math.random() < ratio) {
            values[i][j] = -1;
            for (int k = i - 1; k <= i + 1; k++)
                for (int h = j - 1; h <= j + 1; h++)
                    if (values[k][h] != -1) {
                        values[k][h]++;
                    }
            dem++;
            // Gọi hàm tạo bom cho những ô xung quanh.
            for (int k = i - 1; k <= i + 1; k++)
                for (int h = j - 1; h <= j + 1; h++)
                    if (k > 0 && h > 0 && values[k][h] != -1 && dem < BOM)
                        init(k, h, ratio);
        }
    }

    public String next(JLabel lb) {
        String str[] = lb.getText().split(":");
        int tt = Integer.parseInt(str[3]);
        int s = Integer.parseInt(str[2]);
        int m = Integer.parseInt(str[1]);
        int h = Integer.parseInt(str[0]);
        String kq = "";
        int sum = tt + s * 100 + m * 60 * 100 + h * 60 * 60 * 100 + 1;
        if (sum % 100 > 9)
            kq = ":" + sum % 100 + kq;
        else
            kq = ":0" + sum % 100 + kq;
        sum /= 100;

        if (sum % 60 > 9)
            kq = ":" + sum % 60 + kq;
        else
            kq = ":0" + sum % 60 + kq;
        sum /= 60;

        if (sum % 60 > 9)
            kq = ":" + sum % 60 + kq;
        else
            kq = ":0" + sum % 60 + kq;
        sum /= 60;
        if (sum > 9)
            kq = sum + kq;
        else
            kq = "0" + sum + kq;
        return kq;
    }

    public void open(int i, int j) {
        if (tick[i][j] && values[i][j] != -1) {
            bt[i][j].setText(String.valueOf(values[i][j]));
            bt[i][j].setBackground(background_number_cl);
            tick[i][j] = false;
            checkWin();
        }
    }

    public void openEmpty(int i, int j) {
        if (tick[i][j]) {
            tick[i][j] = false;
            bt[i][j].setBackground(background_null_cl);
            checkWin();
            for (int h = i - 1; h <= i + 1; h++)
                for (int k = j - 1; k <= j + 1; k++)
                    if (h >= 0 && h <= m && k >= 0 && k <= n) {
                        if (values[h][k] == 0 && tick[h][k])
                            openEmpty(h, k);
                        else
                            open(h, k);
                    }
        }
    }

    public void addFlag(int i, int j) {
        if (bt[i][j].getBackground() == flag_cl) {
            tick[i][j] = true;
            bt[i][j].setBackground(null);
            mines_bt.setText(String.valueOf(Integer.parseInt(mines_bt.getText()) + 1));
        } else if (tick[i][j] && Integer.parseInt(mines_bt.getText()) > 0) {
            tick[i][j] = false;
            bt[i][j].setBackground(flag_cl);
            mines_bt.setText(String.valueOf(Integer.parseInt(mines_bt.getText()) - 1));
        }
        checkWin();
    }

    public void checkWin() {
    // Kiểm tra xem người chơi đã chiến thắng chưa
    boolean win = true;
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (tick[i][j] && values[i][j] != -1) {
                win = false;
                break;
            }
        }
    }

    if (win) {
        timer.stop();
        try {
            // Cập nhật thông tin thắng vào SQL
            updateWinToSQL();
            checkPoint(point_lb.getText(), lv.getSelectedIndex());
                leaderboard.updateLeaderboard(point_lb.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        die = true;
        JOptionPane.showMessageDialog(null, "Bạn đã chiến thắng!");
    }
}


    public void checkPoint(String s, int k) throws IOException {
        String file = "point.txt";
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String str[] = {"99:99:99:99", "99:99:99:99", "99:99:99:99"};
        for (int i = 0; i <= 2; i++)
            str[i] = br.readLine();
        fr.close();
        if (s.compareTo(str[k]) < 0) {
            str[k] = s;
            FileWriter f = new FileWriter(file);
            f.write(str[0] + "\n");
            f.write(str[1] + "\n");
            f.write(str[2] + "\n");
            f.flush();
            f.close();
        }
    }

    String hightPoint(int k) {
        String file = "point.txt";
        String str = "";
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                for (int i = 0; i <= k; i++)
                    str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            String maxTime = "99:99:99:99";
            FileWriter f;
            try {
                f = new FileWriter(file);
                f.write(maxTime + "\n");
                f.write(maxTime + "\n");
                f.write(maxTime + "\n");
                f.flush();
                f.close();
                return maxTime;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return str;
    }

    public void loss() {
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (values[i][j] == -1) {
                    bt[i][j].setBackground(bom_cl);
                    bt[i][j].setText("X");
                }
    // Người chơi thua, chỉ cần cập nhật số trận đã chơi
    try {
        // Cập nhật thông tin thua vào SQL
        updateLossToSQL();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}

    private void updateWinToSQL() throws IOException {
    // Tăng số trận đã chơi và số trận thắng
    nguoi.setSoTranDaChoi(nguoi.getSoTranDaChoi() + 1);
    nguoi.setSoTranThang(nguoi.getSoTranThang() + 1);

    // Cập nhật vào cơ sở dữ liệu
    nguoichoiDAO.capNhatSoTran(nguoi);
    nguoichoiDAO.capNhatSoTranThang(nguoi);
}

private void updateLossToSQL() throws IOException {
    // Chỉ tăng số trận đã chơi
    nguoi.setSoTranDaChoi(nguoi.getSoTranDaChoi() + 1);

    // Cập nhật vào cơ sở dữ liệu
    nguoichoiDAO.capNhatSoTran(nguoi);
}
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(repeat_bt.getText())) {
            new gameDoMin("CodeLearn.io - Game Dò Mìn", lv.getSelectedIndex(), nguoi); // Chuyển đối tượng nguoi
            this.dispose();
        } else if (!die) {
            timer.start();
            int i = 0, j = 0;
            String s = e.getActionCommand();
            int k = s.indexOf(32);
            i = Integer.parseInt(s.substring(0, k));
            j = Integer.parseInt(s.substring(k + 1, s.length()));
            if (!flag) {
                if (values[i][j] == -1) {
                    if (bt[i][j].getBackground() != flag_cl) {
                        bt[i][j].setBackground(bom_cl);
                        bt[i][j].setText("X");
                        timer.stop();
                        loss();
                        JOptionPane.showMessageDialog(null, "Trúng Bom, bạn đã thua!");
                        die = true;
                    }
                } else if (values[i][j] == 0) {
                    openEmpty(i, j);
                } else {
                    open(i, j);
                }
            } else {
                addFlag(i, j);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == key_flag) {
            flag = !flag;
        }
        if (flag) {
            flag_lb.setText("Đang cắm cờ (h)");
            flag_lb.setForeground(flag_cl);
        } else {
            flag_lb.setText("Đang gỡ bom (h)");
            flag_lb.setForeground(bom_cl);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

   public static void main(String[] args) {
    nguoiChoi player = new nguoiChoi(); // Tạo đối tượng nguoiChoi, bạn có thể tùy chỉnh thông tin người chơi
    new gameDoMin("CodeLearn.io - Game Dò Mìn", 0, player); // Truyền đối tượng nguoiChoi vào constructor
}
}


class Leaderboard extends AbstractTableModel {
    private static final String[] columnNames = {"Xếp hạng", "Thời gian"};
    private ArrayList<String> times;
    private int level;

    public Leaderboard(int level) {
        this.level = level;
        times = new ArrayList<>();
        loadLeaderboard();
    }

    public void updateLeaderboard(String newTime) {
        times.add(newTime);
        Collections.sort(times);
        if (times.size() > 10) {
            times.remove(10);
        }
        saveLeaderboard();
        fireTableDataChanged();
    }

    private void loadLeaderboard() {
        try {
            File file = new File(getLeaderboardFileName());
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    times.add(line);
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLeaderboard() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getLeaderboardFileName()));
            for (String time : times) {
                writer.write(time);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLeaderboardFileName() {
        return "leaderboard_level_" + level + ".txt";
    }

    public int getRowCount() {
        return times.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return times.get(rowIndex);
        }
        return null;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }
    
}

