package Calender;

import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;


import Paint.PaintFrame;
import Scheduler.MemoFrame;


public class CalenderPanel extends JPanel {

	
	JPanel panel, topPanel;
    JButton[] button;
    int year, month;
    JComboBox Year, Month;
    String[] yearlist = new String[30];
    String[] monthlist = new String[12];
    JPanel Day, Data;
    JButton day[];
    final CalenderPanel instance;
    JButton[][] monthButtons = new JButton[6][7];
    GregorianCalendar cal, cal2, cal3;
    int currentYear, currentMonth, currentDay;
    JLabel[][] label = new JLabel[6][7];
    SelectFrame currentDialogFrame;

    public CalenderPanel() {
        instance = this;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                monthButtons[i][j] = new JButton();
                label[i][j] = new JLabel();
            }
        }
        this.setLayout(new BorderLayout());

        topPanel = new JPanel();
        this.add(topPanel, BorderLayout.NORTH);


        Calendar data = Calendar.getInstance();//�޷����
        year = data.get(Calendar.YEAR);//���� �⵵ ���
        month = data.get(Calendar.MONTH) + 1;//���� �� ���


        for (int i = 0; i < 30; i++) {
            yearlist[i] = new String(Integer.toString(2000 + i));//String��(�޺��ڽ�)
        }
        for (int i = 0; i < 12; i++) {
            monthlist[i] = new String(Integer.toString(1 + i));//String��
        }


        Year = new JComboBox(yearlist);
        Year.setBackground(Color.white);
        Year.setSelectedItem(Integer.toString(year));//���� �⵵ ����
        Year.addActionListener(new yyAction());//������ �̺�Ʈ ���

        Month = new JComboBox(monthlist);
        Month.setBackground(Color.white);
        Month.addActionListener(new mmAction());//������ �̺�Ʈ ���
        Month.setSelectedItem(Integer.toString(month));//���� �� ����


        button = new JButton[3];
        button[0] = new JButton("��");
        button[0].setForeground(Color.pink);
        button[0].setBackground(Color.white);
        button[0].addActionListener(new backAction());

        button[1] = new JButton("��");
        button[1].setForeground(Color.pink);
        button[1].setBackground(Color.white);
        button[1].addActionListener(new nextAction());//������ �̺�Ʈ ���


        button[2] = new JButton("����");
        button[2].setBackground(Color.white);
        button[2].setForeground(Color.red);
        button[2].addActionListener(new todayAction());//������ �̺�Ʈ ���

        Day = new JPanel();
        Day.setLayout(new GridLayout(1, 7));

        day = new JButton[7];
        day[0] = new JButton("��");
        day[1] = new JButton("��");
        day[2] = new JButton("ȭ");
        day[3] = new JButton("��");
        day[4] = new JButton("��");
        day[5] = new JButton("��");
        day[6] = new JButton("��");

        for (int i = 0; i < 7; i++) {
            Day.add(day[i]);
            day[i].setFont(new Font("HY�߰��", 0, 15));
            day[i].setBackground(Color.WHITE);

        }

        day[0].setForeground(Color.RED);
        day[6].setForeground(Color.BLUE);
        
        
        Data = new JPanel();
        Data.setLayout(new GridLayout(6, 7));

        panel = new JPanel(new BorderLayout());
        
        topPanel.setBackground(Color.WHITE);
        topPanel.add(button[0]);
        topPanel.add(Year);
        topPanel.add(Month);
        topPanel.add(button[1]);
        topPanel.add(button[2]);

        
        panel.add(Day, BorderLayout.NORTH);
        panel.add(Data, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);
        

      
        
        ButtonSettion(year, month);


    }


    public void ButtonSettion(int year, int month) {


        currentYear = year;
        currentMonth = month;
        Calendar setcal = Calendar.getInstance();
        setcal.set(currentYear, currentMonth, 1);

        //�״��� ù��°���� ���� �ޱ�
        int firstday = setcal.get(Calendar.DAY_OF_WEEK);

        //�״��� �������� �ޱ�

        int lastday = setcal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dateNow = 1;//���� ��¥�� ��Ÿ����.

        try {
            panel.remove(Data);
        } catch (Exception e) {
        }
        
        Data = new JPanel();
        Data.setLayout(new GridLayout(6, 7));
        if (panel != null)
            panel.add(Data);
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
            	
                monthButtons[i][j] = new JButton();
                monthButtons[i][j].setFont(new Font("HY�߰��", 0, 15));
                monthButtons[i][j].setBackground(Color.WHITE);
                Color color1 = new Color(0xFF9999);
                Color color2 = new Color(0x66CCFF);
                monthButtons[i][0].setBackground(color1);
                monthButtons[i][6].setBackground(color2);
                monthButtons[i][j].setHorizontalAlignment(SwingConstants.LEFT);
                monthButtons[i][j].setVerticalAlignment(SwingConstants.TOP);
                monthButtons[i][j].addActionListener(new cellAction());
                
                
                // �׸��� ������ ���� �����ٶ�, ��ġ�� ũ�⸦ ���Ƿ� ������ �� �־�� �ϹǷ�, layout �� null�� �ٲ��ش�.
                monthButtons[i][j].setLayout(null);
                
                Data.add(monthButtons[i][j]);
                
                if (dateNow == 1 && j + 1 < firstday) {//1���� �Ͽ����� �ƴ϶�� ���ϱ��� ���ư.
                    
                	monthButtons[i][j].setText("");
                	
                } else if (dateNow > lastday)//��¥�� ������ ��¥���� Ŀ����
                	
                    monthButtons[i][j].setText("");
                
                else {
                    String today = "" + dateNow;//1����
                    monthButtons[i][j].setText(today);
                    
                    // �ش� ��¥�� �޸� �����͸� �о�ͼ� ������ ������ �־��ش�.
                    String memoReadData = MemoFrame.getData(year + "�� " + (month + 1) + "�� " + dateNow + "��");
                    String title = "";
                    if("".equals(memoReadData) ){
                        title = memoReadData.split("/")[0];
                    }else{
                        title = memoReadData.split("/")[1];
                    }
                    JLabel data = new JLabel(title);

                    label[i][j] = data;
                    label[i][j].setBounds(50,0,80,20);
                    monthButtons[i][j].add(label[i][j]);
                    monthButtons[i][j].setName(title);
                    // �ش� ��¥�� �׸��� �����Ͱ� ������ �����͸� �ҷ��ͼ� �׷��ش�.
                    int[] getDataR = PaintFrame.getData(year + "�� " + (month + 1) + "�� " + dateNow + "��");
                    if(getDataR != null){
                        PaintPanel p = new PaintPanel(getDataR);
                        p.setBounds(20,30, 100,60);
                        monthButtons[i][j].add(p);
                    }
                    dateNow++;
                }
            }
      
        }


    }


    class yyAction implements ActionListener {  //���� �޺��ڽ��� �׼�
        public void actionPerformed(ActionEvent e) {

            JComboBox cb = (JComboBox) e.getSource();
            int index = cb.getSelectedIndex();
            year = Integer.parseInt(yearlist[index]);

            ButtonSettion(year, month);


        }

    }

    class mmAction implements ActionListener {    //�� �޺��ڽ��� �׼�
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            month = cb.getSelectedIndex();
            ButtonSettion(year, month);
        }
    }


    class nextAction implements ActionListener {   //ȭ��ǥ�� ���� ��ư�� �׼� ����
        public void actionPerformed(ActionEvent e) {
            if (month >= 11) {
                month = 0;
                year += 1;
            } else {
                month++;
            }
            Year.setSelectedIndex(year - 2000);
            Month.setSelectedIndex(month);
        }
    }

    class todayAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Calendar data = Calendar.getInstance();
            Year.setSelectedIndex(data.get(Calendar.YEAR) - 2000);
            Month.setSelectedIndex(data.get(Calendar.MONTH));
        }
    }

    class backAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (month <= 0) {
                month = 11;
                year -= 1;
            } else {
                month--;
            }
            Year.setSelectedIndex(year - 2000);
            Month.setSelectedIndex(month);

        }
    }


    class cellAction implements ActionListener {  //���� �޺��ڽ��� �׼�

        public void actionPerformed(ActionEvent e) {
            // ����, ���� �����ִ� ���̾�αװ� �����ϸ�, �ش� ���̾�α׸� �����Ű��, ���� ���̾�α׸� �����Ѵ�.
            if (currentDialogFrame != null) {
                currentDialogFrame.dialogExit();
            }
            if (e.getActionCommand() != "") {
                currentDialogFrame = new SelectFrame(!"".equals(((JButton) e.getSource()).getName()), year, month + 1, Integer.parseInt(e.getActionCommand()), instance);
            }
        }
    }

    class PaintPanel extends Panel {
        int[] paintInfo;

        public PaintPanel(int[] paintInfo) {
            this.paintInfo = paintInfo;
        }

        public void paint(Graphics g) {
            // �׸� �����Ͱ� �������������� ����
            if(paintInfo.length == 0){
                return;
            }
            // �׸� �����Ͱ� �����ϸ�, �׸� ũ�⸦ 1/16 �� �ٿ��� �׷��ش�.
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 100; j++) {
                    int x = j * 4;
                    int y = i * 4;
                    int position = (y * 400) + x;
                    int currentInfo = (paintInfo[position + 3] + paintInfo[position + 2] + paintInfo[position + 1] + paintInfo[position]);
                    position = (y + 1) * 400 + x;
                    currentInfo += (paintInfo[position + 3] + paintInfo[position + 2] + paintInfo[position + 1] + paintInfo[position]);
                    position = (y + 2) * 400 + x;
                    currentInfo += (paintInfo[position + 3] + paintInfo[position + 2] + paintInfo[position + 1] + paintInfo[position]);
                    position = (y + 3) * 400 + x;
                    currentInfo += (paintInfo[position + 3] + paintInfo[position + 2] + paintInfo[position + 1] + paintInfo[position]);
                    if (currentInfo == 0) {
                        // ����� �ƹ��͵� ��ĥ��
                    } else if (currentInfo >= 1) {
                        // ������ ��ĥ
                        g.setColor(Color.BLACK);
                        g.fillOval(j, i, 3, 3);
                    }else{
                    }
                }
            }
        }
    }
}









