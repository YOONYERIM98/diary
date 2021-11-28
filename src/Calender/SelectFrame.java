package Calender;

import Paint.PaintFrame;
import Scheduler.MemoFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ë©”ëª¨ë¥? ? ?„ê»?ì§?, ê·¸ë¦¼?„ ê·¸ë¦´ê±´ì? ?„ ?ƒ?•˜?Š” ?‹¤?´?–¼ë¡œê·¸
public class SelectFrame extends JDialog {
    JDialog currentDialog;
    public SelectFrame(Boolean hasData, int year, int month, int day, CalenderPanel instance) {
    	
        this.setSize(400, 200);
        this.setTitle("select");
        this.setLocation(400, 200);
        

        this.setContentPane(new JPanel());
        // ë©”ëª¨ ë²„íŠ¼?„ ë§Œë“¤?–´ì£¼ê³ , ?„ ?ƒ?•˜ë©? ë©”ëª¨ ?”„? ˆ?„?„ ë§Œë“¤?„ë¡? ?•œ?‹¤.
        JButton memo = new JButton("MEMO");
        memo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDialog = new MemoFrame(year, month, day, instance);
                setVisible(false);
            }
        });
        
        
        // ê·¸ë¦¼?Œ ?„ ?ƒ ë²„íŠ¼?„ ë§Œë“¤ê³?, ?„ ?ƒ?•˜ë©? ê·¸ë¦¼?Œ ?”„? ˆ?„?„ ë§Œë“¤?„ë¡? ?•œ?‹¤.
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
    
    
    // ?‹¤?´?–¼ë¡œê·¸ë¥? ì¢…ë£Œ?‹œ?‚¨?‹¤.
    public void dialogExit(){
        currentDialog.dispose();
    }
}



