package Calender;

import Paint.PaintFrame;
import Scheduler.MemoFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 메모�? ?��?���?�?, 그림?�� 그릴건�? ?��?��?��?�� ?��?��?��로그
public class SelectFrame extends JDialog {
    JDialog currentDialog;
    public SelectFrame(Boolean hasData, int year, int month, int day, CalenderPanel instance) {
    	
        this.setSize(400, 200);
        this.setTitle("select");
        this.setLocation(400, 200);
        

        this.setContentPane(new JPanel());
        // 메모 버튼?�� 만들?��주고, ?��?��?���? 메모 ?��?��?��?�� 만들?���? ?��?��.
        JButton memo = new JButton("MEMO");
        memo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDialog = new MemoFrame(year, month, day, instance);
                setVisible(false);
            }
        });
        
        
        // 그림?�� ?��?�� 버튼?�� 만들�?, ?��?��?���? 그림?�� ?��?��?��?�� 만들?���? ?��?��.
        JButton paint = new JButton("PAINT");
        paint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDialog = new PaintFrame(year, month, day, instance);
                setVisible(false);
            }
        });
        
        
        this.add(memo);
        this.add(paint);
        setVisible(true);
        currentDialog = this;
        
        
    }
    
    
    // ?��?��?��로그�? 종료?��?��?��.
    public void dialogExit(){
        currentDialog.dispose();
    }
}



