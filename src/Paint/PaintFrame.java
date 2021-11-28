package Paint;

import Calender.CalenderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Files;

public class PaintFrame extends JDialog {
    private static File file;
    static File folder = new File("./data");

    int PAINT_WIDTH = 400;
    int PAINT_HEIGHT = 240;
    Panel contentPanel;
    DrawingPanel drawingPanel;
    JButton whiteColorButton;
    JButton blackColorButton;
    Color currentColor = Color.BLACK;
    int mouseX, mouseY = 0;

    // 340 * 460
    int paintInfo[];
    

    public PaintFrame(int year, int month, int day, CalenderPanel instance) {
    	
        final String name = year + "�� " + month + "�� " + day + "��";

        this.setSize(500, 500);
        this.setTitle("Paint");
        this.setLocation(400, 200);
        // �⺻������ �׷��� �����͸� �������µ�, ���� �����Ͱ� ������ ���� ������ش�.
        int[] getDataR = getData(name);
        
        if(getDataR == null) {

        	paintInfo = new int [PAINT_WIDTH*PAINT_HEIGHT];

        	} else {

        	paintInfo = getDataR;

        	}
        
        

        contentPanel = new Panel();
        contentPanel.setLayout(null);
        this.setContentPane(contentPanel);
        

        // �׸��� �׸� �гο� �̺�Ʈ�� �߰��Ѵ�.
        drawingPanel = new DrawingPanel();
        drawingPanel.setLayout(null);
        drawingPanel.setBounds(10, 10, PAINT_WIDTH, PAINT_HEIGHT);
        drawingPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                int point = (mouseY * PAINT_WIDTH) + mouseX;
                if(point < paintInfo.length && point > 0) {
                    if (currentColor == Color.WHITE) {
                        paintInfo[point] = 0;
                    } else {
                        paintInfo[point] = 1;
                    }
                    drawingPanel.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        
        
        contentPanel.add(drawingPanel);
        

        // ���������� �ٲ��ִ� ��ư�� �����.
        blackColorButton = new JButton("");
        blackColorButton.setBackground(Color.BLACK);
        blackColorButton.setBounds(20, 360, 20, 20);
        blackColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.BLACK;
            }
        });
        

        // ������� ������ �ٲ��ִ� ��ư�� �����.
        whiteColorButton = new JButton("");
        whiteColorButton.setBackground(Color.WHITE);
        whiteColorButton.setBounds(50, 360, 20, 20);
        whiteColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.WHITE;
            }
        });


        // ���� ��ư�� �����, �̺�Ʈ�� �߰��Ѵ�.
        JButton saveButton = new JButton("����");
        FontMetrics fontMetrics = saveButton.getFontMetrics(saveButton.getFont());
        saveButton.setBounds(200, 320,100 ,30);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setData(name);
                instance.ButtonSettion(year, month - 1);
            }
        });
        

        // ������ ��ư�� �׸� �г��� �߰���Ų��.
        contentPanel.add(drawingPanel);
        contentPanel.add(blackColorButton);
        contentPanel.add(whiteColorButton);
        contentPanel.add(saveButton);

        this.setVisible(true);
    }

    
    // �����͸� �о��ش�.
    public static int[] getData(String name) {
        try {
            // ������ byte array �� �о��.
            file = new File("./data/" + name + ".bin");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            //! ( ���ÿ����÷� ��ó ) �о�� ���� ����Ʈ ��̸� int �迭�� �ٲ��ִ� �˰���
            IntBuffer intBuf =
                    ByteBuffer.wrap(fileContent)
                            .order(ByteOrder.BIG_ENDIAN)
                            .asIntBuffer();
            int[] array = new int[intBuf.remaining()];
            intBuf.get(array);
            return array;
        } catch (Exception ex) {
            // ����� ������ ������ null�� ��ȯ
            return null;
        }
    }
    

    // �����͸� ���Ϸ� �����Ѵ�. ������ ��, byte �� �����Ų��.
    public void setData(String name) {
        try {
            file = new File("./data/" + name + ".bin");
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] datas = paintInfoToByteArr();
            fos.write(datas);
            fos.flush();
            fos.close();
        } catch (Exception ex) {
            return;
        }
    }
    

    // �׸��г� Ŭ������ �����Ѵ�.
    class DrawingPanel extends Panel {
        // �׷����� ������ Ȯ���Ͽ�, �׸��ǿ� �׸��� �׸���.
        public void paint(Graphics g) {
            for (int i = 0; i < PAINT_HEIGHT; i++) {
                for (int j = 0; j < PAINT_WIDTH; j++) {
                    int currentInfo = paintInfo[(i * 400) + j];
                    if (currentInfo == 0) {
                        // ����� �ƹ��͵� ��ĥ��
                    } else if (currentInfo == 1) {
                        // ������ ��ĥ
                        g.setColor(Color.BLACK);
                        g.fillOval(j, i, 3, 3);
                    }
                }
            }
        }
    }
    

    // paint info �� byte array�� ��ȯ�����ش�.
    private byte[] paintInfoToByteArr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(paintInfo.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(paintInfo);
        return byteBuffer.array();
    }
}

