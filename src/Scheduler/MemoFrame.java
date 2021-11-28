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
        this.setTitle("���� ����" + year + month + day);
        this.setLocation(400, 200);
        
        
        final String currentDate = year + "�� " + month + "�� " + day + "��";
        
        
        // �д� ��¥�� �����͸� ��������, ���� ������ ���ڿ��� ����ִ� ũ�� 3¥�� �迭�� �ʱ�ȭ�Ѵ�.
        // ���� �����Ͱ� ������ / �� �������� split ���ش�.
        String data = getData(currentDate);//����Ÿ �б� �Լ�
        
     // �д� ��¥�� �����͸� ��������, ���� ������ ���ڿ��� ����ִ� ũ�� 3¥�� �迭�� �ʱ�ȭ�Ѵ�.
        // ���� �����Ͱ� ������ / �� �������� split ���ش�.
        String datas[];
        
        if("".equals(data)) {

        	datas = new String[]{"","",""};

        } 
        else {

        	datas = data.split("/");

        }
        
        panel = new JPanel();
        panel.setLayout(null);//��ü���� ��ġ�� �����ϱ� ���� setBounds�� ����
        this.add(panel);

        
        Font font = new Font("San Serif", 0, 17);
        memoDateLabel = new JLabel(currentDate);//�����(���� ��¥)
        fontMetrics = memoDateLabel.getFontMetrics(font);
        memoDateLabel.setFont(font);
        memoDateLabel.setBounds(200,20,300,30);

        
        // ���� �ð��� �� �Է� �ʵ�
        memoTimeLabel = new JLabel("�ð� : ");//�ð�
        fontMetrics = memoTimeLabel.getFontMetrics(font);
        memoTimeLabel.setFont(font);
        memoTimeLabel.setBounds(40, 50,50 ,30);

        timeArea = new JTextArea("", 20, 40);
        timeArea.setBackground(Color.WHITE);
        timeArea.setText(datas[0]);
        timeArea.setFont(font);
        timeArea.setBounds(100, 50,350 ,25);

        // ���� �� �� �Է� �ʵ�
        memoTitleLabel = new JLabel("���� : ");//����
        fontMetrics = memoTitleLabel.getFontMetrics(font);
        memoTitleLabel.setFont(font);
        memoTitleLabel.setBounds(40, 80,50 ,30);

        contentTitle = new JTextArea("", 20, 40);
        contentTitle.setBackground(Color.WHITE);
        contentTitle.setText(datas[1]);
        contentTitle.setFont(font);
        contentTitle.setBounds(100, 81,350 ,25);
        
        // ���� ���� �� �Է� �ʵ�
        memoContentLabel = new JLabel("���� : ");//����
        fontMetrics = memoContentLabel.getFontMetrics(font);
        memoContentLabel.setFont(font);
        memoContentLabel.setBounds(40, 110,50 ,30);

        contentArea = new JTextArea("", 20, 40);
        contentArea.setText(datas[2]);
        contentArea.setBackground(Color.WHITE);
        contentArea.setFont(font);
        contentArea.setBounds(100, 112,350 ,200);
        
        // �����ư ũ�� �� ��Ʈ�� �������ְ�, �׼� �����ʸ� �߰��Ѵ�.// ��ư�� ���� ������ �����Ͱ� ����ǰ�, ���� �������� �ٽ� �׷��ش�.
        saveButton = new JButton("����");
        saveButton.setBounds(210, 320,100 ,30);
        saveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // �����͸� �����Ҷ� ���� �ð�/����/���� �� ���·� �����Ѵ�.
                setData(currentDate, timeArea.getText() +" / "+ contentTitle.getText() + " / " 
                + contentArea.getText()+ "/");
                cp.ButtonSettion(year, month - 1);
            }
        });

        // ������ ��ҵ��� ���� �����̳ʿ� �߰����ش�.
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

    // �����͸� �о�´�.
    public static String getData(String name) {
        try {
            String ret = "";
            file = new File("./data/" + name + ".txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {//sc�� �о�� ���Ͽ��� '������ �о�� ������ �����ϴ���?'�� �������� �Ǵ�.
                ret += sc.nextLine();
            }
            sc.close();
            return ret;
        } catch (Exception ex) {
            return "";
        }
    }

    // �����͸� �����Ѵ�.
    public void setData(String name, String data) {
        try {
            file = new File("./data/" + name + ".txt");
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, false);//�ⷰ ��Ʈ�� ����, ���
            writer.write(data);
            writer.close();
        } catch (Exception ex) {
            return;
        }
    }

}