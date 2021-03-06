package Calender;

import Paint.PaintFrame;
import Scheduler.MemoFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// λ©λͺ¨λ₯? ? ?κ»?μ§?, κ·Έλ¦Ό? κ·Έλ¦΄κ±΄μ? ? ??? ?€?΄?Όλ‘κ·Έ
public class SelectFrame extends JDialog {
    JDialog currentDialog;
    public SelectFrame(Boolean hasData, int year, int month, int day, CalenderPanel instance) {
    	
        this.setSize(400, 200);
        this.setTitle("select");
        this.setLocation(400, 200);
        

        this.setContentPane(new JPanel());
        // λ©λͺ¨ λ²νΌ? λ§λ€?΄μ£Όκ³ , ? ??λ©? λ©λͺ¨ ?? ?? λ§λ€?λ‘? ??€.
        JButton memo = new JButton("MEMO");
        memo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDialog = new MemoFrame(year, month, day, instance);
                setVisible(false);
            }
        });
        
        
        // κ·Έλ¦Ό? ? ? λ²νΌ? λ§λ€κ³?, ? ??λ©? κ·Έλ¦Ό? ?? ?? λ§λ€?λ‘? ??€.
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
    
    
    // ?€?΄?Όλ‘κ·Έλ₯? μ’λ£??¨?€.
    public void dialogExit(){
        currentDialog.dispose();
    }
}



