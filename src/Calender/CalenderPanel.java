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


        Calendar data = Calendar.getInstance();//달력출력
        year = data.get(Calendar.YEAR);//현재 년도 출력
        month = data.get(Calendar.MONTH) + 1;//현제 달 출력


        for (int i = 0; i < 30; i++) {
            yearlist[i] = new String(Integer.toString(2000 + i));//String형(콤보박스)
        }
        for (int i = 0; i < 12; i++) {
            monthlist[i] = new String(Integer.toString(1 + i));//String형
        }


        Year = new JComboBox(yearlist);
        Year.setBackground(Color.white);
        Year.setSelectedItem(Integer.toString(year));//오늘 년도 선택
        Year.addActionListener(new yyAction());//리스너 이벤트 등록

        Month = new JComboBox(monthlist);
        Month.setBackground(Color.white);
        Month.addActionListener(new mmAction());//리스너 이벤트 등록
        Month.setSelectedItem(Integer.toString(month));//오늘 달 선택


        button = new JButton[3];
        button[0] = new JButton("◀");
        button[0].setForeground(Color.pink);
        button[0].setBackground(Color.white);
        button[0].addActionListener(new backAction());

        button[1] = new JButton("▶");
        button[1].setForeground(Color.pink);
        button[1].setBackground(Color.white);
        button[1].addActionListener(new nextAction());//리스너 이벤트 등록


        button[2] = new JButton("오늘");
        button[2].setBackground(Color.white);
        button[2].setForeground(Color.red);
        button[2].addActionListener(new todayAction());//리스너 이벤트 등록

        Day = new JPanel();
        Day.setLayout(new GridLayout(1, 7));

        day = new JButton[7];
        day[0] = new JButton("일");
        day[1] = new JButton("월");
        day[2] = new JButton("화");
        day[3] = new JButton("수");
        day[4] = new JButton("목");
        day[5] = new JButton("금");
        day[6] = new JButton("토");

        for (int i = 0; i < 7; i++) {
            Day.add(day[i]);
            day[i].setFont(new Font("HY견고딕", 0, 15));
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

        //그달의 첫번째날의 요일 받기
        int firstday = setcal.get(Calendar.DAY_OF_WEEK);

        //그달의 마지막날 받기

        int lastday = setcal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dateNow = 1;//현재 날짜를 나타낸다.

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
                monthButtons[i][j].setFont(new Font("HY견고딕", 0, 15));
                monthButtons[i][j].setBackground(Color.WHITE);
                Color color1 = new Color(0xFF9999);
                Color color2 = new Color(0x66CCFF);
                monthButtons[i][0].setBackground(color1);
                monthButtons[i][6].setBackground(color2);
                monthButtons[i][j].setHorizontalAlignment(SwingConstants.LEFT);
                monthButtons[i][j].setVerticalAlignment(SwingConstants.TOP);
                monthButtons[i][j].addActionListener(new cellAction());
                
                
                // 그림과 제목을 같이 보여줄때, 위치나 크기를 임의로 지정할 수 있어야 하므로, layout 을 null로 바꿔준다.
                monthButtons[i][j].setLayout(null);
                
                Data.add(monthButtons[i][j]);
                
                if (dateNow == 1 && j + 1 < firstday) {//1일이 일요일이 아니라면 요일까지 빈버튼.
                    
                	monthButtons[i][j].setText("");
                	
                } else if (dateNow > lastday)//날짜가 마지막 날짜보다 커지면
                	
                    monthButtons[i][j].setText("");
                
                else {
                    String today = "" + dateNow;//1부터
                    monthButtons[i][j].setText(today);
                    
                    // 해당 날짜에 메모 데이터를 읽어와서 있으면 제목을 넣어준다.
                    String memoReadData = MemoFrame.getData(year + "년 " + (month + 1) + "월 " + dateNow + "일");
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
                    // 해당 날짜에 그림판 데이터가 있으면 데이터를 불러와서 그려준다.
                    int[] getDataR = PaintFrame.getData(year + "년 " + (month + 1) + "월 " + dateNow + "일");
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


    class yyAction implements ActionListener {  //연도 콤보박스의 액션
        public void actionPerformed(ActionEvent e) {

            JComboBox cb = (JComboBox) e.getSource();
            int index = cb.getSelectedIndex();
            year = Integer.parseInt(yearlist[index]);

            ButtonSettion(year, month);


        }

    }

    class mmAction implements ActionListener {    //달 콤보박스의 액션
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            month = cb.getSelectedIndex();
            ButtonSettion(year, month);
        }
    }


    class nextAction implements ActionListener {   //화살표와 오늘 버튼의 액션 정의
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


    class cellAction implements ActionListener {  //연도 콤보박스의 액션

        public void actionPerformed(ActionEvent e) {
            // 만약, 현재 열려있는 다이얼로그가 존재하면, 해당 다이얼로그를 종료시키고, 선택 다이얼로그를 생성한다.
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
            // 그림 데이터가 존재하지않으면 종료
            if(paintInfo.length == 0){
                return;
            }
            // 그림 데이터가 존재하면, 그림 크기를 1/16 로 줄여서 그려준다.
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
                        // 흰색은 아무것도 안칠함
                    } else if (currentInfo >= 1) {
                        // 검은색 색칠
                        g.setColor(Color.BLACK);
                        g.fillOval(j, i, 3, 3);
                    }else{
                    }
                }
            }
        }
    }
}









