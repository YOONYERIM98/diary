package Scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;

import Calender.CalenderPanel;

public class MemoFrame extends JDialog {

    JPanel panel;
    JLabel memoDateLabel;
    JLabel memoTimeLabel;
    JLabel memoTitleLabel;
    JLabel memoContentLabel;
    JButton saveButton;
    JTextArea contentTitle, contentArea, timeArea;
    int year, month, day, labelStringWidth, labelStringHeight;
    FontMetrics fontMetrics;
    String y, m, d, x;
    CalenderPanel cp;
    static File folder = new File("./data");
    static File file;

    public MemoFrame(final int year, final int month, int day, final CalenderPanel cp) {
    	
        this.cp = cp;
        this.setSize(500, 430);
        this.setTitle("일정 관리" + year + month + day);
        this.setLocation(400, 200);
        
        
        final String currentDate = year + "년 " + month + "월 " + day + "일";
        
        
        // 읽는 날짜의 데이터를 가져오고, 만약 없으면 빈문자열이 들어있는 크기 3짜리 배열로 초기화한다.
        // 만약 데이터가 있으면 / 를 기준으로 split 해준다.
        String data = getData(currentDate);//데이타 읽기 함수
        
     // 읽는 날짜의 데이터를 가져오고, 만약 없으면 빈문자열이 들어있는 크기 3짜리 배열로 초기화한다.
        // 만약 데이터가 있으면 / 를 기준으로 split 해준다.
        String datas[];
        
        if("".equals(data)) {

        	datas = new String[]{"","",""};

        } 
        else {

        	datas = data.split("/");

        }
        
        panel = new JPanel();
        panel.setLayout(null);//구체적인 위치를 조정하기 위해 setBounds로 지정
        this.add(panel);

        
        Font font = new Font("San Serif", 0, 17);
        memoDateLabel = new JLabel(currentDate);//년월일(일정 날짜)
        fontMetrics = memoDateLabel.getFontMetrics(font);
        memoDateLabel.setFont(font);
        memoDateLabel.setBounds(200,20,300,30);

        
        // 일정 시간에 및 입력 필드
        memoTimeLabel = new JLabel("시간 : ");//시간
        fontMetrics = memoTimeLabel.getFontMetrics(font);
        memoTimeLabel.setFont(font);
        memoTimeLabel.setBounds(40, 50,50 ,30);

        timeArea = new JTextArea("", 20, 40);
        timeArea.setBackground(Color.WHITE);
        timeArea.setText(datas[0]);
        timeArea.setFont(font);
        timeArea.setBounds(100, 50,350 ,25);

        // 제목 라벨 및 입력 필드
        memoTitleLabel = new JLabel("제목 : ");//제목
        fontMetrics = memoTitleLabel.getFontMetrics(font);
        memoTitleLabel.setFont(font);
        memoTitleLabel.setBounds(40, 80,50 ,30);

        contentTitle = new JTextArea("", 20, 40);
        contentTitle.setBackground(Color.WHITE);
        contentTitle.setText(datas[1]);
        contentTitle.setFont(font);
        contentTitle.setBounds(100, 81,350 ,25);
        
        // 일정 내용 및 입력 필드
        memoContentLabel = new JLabel("내용 : ");//내용
        fontMetrics = memoContentLabel.getFontMetrics(font);
        memoContentLabel.setFont(font);
        memoContentLabel.setBounds(40, 110,50 ,30);

        contentArea = new JTextArea("", 20, 40);
        contentArea.setText(datas[2]);
        contentArea.setBackground(Color.WHITE);
        contentArea.setFont(font);
        contentArea.setBounds(100, 112,350 ,200);
        
        // 저장버튼 크기 및 폰트를 지정해주고, 액션 리스너를 추가한다.// 버튼이 눌릴 때마다 데이터가 저장되고, 메인 스케쥴을 다시 그려준다.
        saveButton = new JButton("저장");
        saveButton.setBounds(210, 320,100 ,30);
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // 데이터를 저장할때 각각 시간/제목/내용 의 형태로 저장한다.
                setData(currentDate, timeArea.getText() +" / "+ contentTitle.getText() + " / " 
                + contentArea.getText()+ "/");
                cp.ButtonSettion(year, month - 1);
            }
        });

        // 각각의 요소들을 현재 컨테이너에 추가해준다.
        panel.add(memoDateLabel);
        panel.add(memoTimeLabel);
        panel.add(memoTitleLabel);
        panel.add(memoContentLabel);
        panel.add(contentTitle);
        panel.add(contentArea);
        panel.add(timeArea);
        panel.add(saveButton);

        setVisible(true);
    }

    // 데이터를 읽어온다.
    public static String getData(String name) {
        try {
            String ret = "";
            file = new File("./data/" + name + ".txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {//sc가 읽어오 파일에서 '다음에 읽어올 정수가 존재하는지?'를 조건으로 건다.
                ret += sc.nextLine();
            }
            sc.close();
            return ret;
        } catch (Exception ex) {
            return "";
        }
    }

    // 데이터를 저장한다.
    public void setData(String name, String data) {
        try {
            file = new File("./data/" + name + ".txt");
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, false);//출럭 스트림 생성, 덮어씀
            writer.write(data);
            writer.close();
        } catch (Exception ex) {
            return;
        }
    }

}