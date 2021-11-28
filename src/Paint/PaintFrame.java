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
    	
        final String name = year + "년 " + month + "월 " + day + "일";

        this.setSize(500, 500);
        this.setTitle("Paint");
        this.setLocation(400, 200);
        // 기본적으로 그려줄 데이터를 가져오는데, 만약 데이터가 없으면 새로 만들어준다.
        int[] getDataR = getData(name);
        
        if(getDataR == null) {

        	paintInfo = new int [PAINT_WIDTH*PAINT_HEIGHT];

        	} else {

        	paintInfo = getDataR;

        	}
        
        

        contentPanel = new Panel();
        contentPanel.setLayout(null);
        this.setContentPane(contentPanel);
        

        // 그림을 그릴 패널에 이벤트를 추가한다.
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
        

        // 검은색으로 바꿔주는 버튼을 만든다.
        blackColorButton = new JButton("");
        blackColorButton.setBackground(Color.BLACK);
        blackColorButton.setBounds(20, 360, 20, 20);
        blackColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.BLACK;
            }
        });
        

        // 흰색으로 색깔을 바꿔주는 버튼을 만든다.
        whiteColorButton = new JButton("");
        whiteColorButton.setBackground(Color.WHITE);
        whiteColorButton.setBounds(50, 360, 20, 20);
        whiteColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.WHITE;
            }
        });


        // 저장 버튼을 만들고, 이벤트를 추가한다.
        JButton saveButton = new JButton("저장");
        FontMetrics fontMetrics = saveButton.getFontMetrics(saveButton.getFont());
        saveButton.setBounds(200, 320,100 ,30);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setData(name);
                instance.ButtonSettion(year, month - 1);
            }
        });
        

        // 각각의 버튼과 그림 패널을 추가시킨다.
        contentPanel.add(drawingPanel);
        contentPanel.add(blackColorButton);
        contentPanel.add(whiteColorButton);
        contentPanel.add(saveButton);

        this.setVisible(true);
    }

    
    // 데이터를 읽어준다.
    public static int[] getData(String name) {
        try {
            // 파일을 byte array 로 읽어옴.
            file = new File("./data/" + name + ".bin");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            //! ( 스택오버플로 출처 ) 읽어온 파일 바이트 어레이를 int 배열로 바꿔주는 알고리즘
            IntBuffer intBuf =
                    ByteBuffer.wrap(fileContent)
                            .order(ByteOrder.BIG_ENDIAN)
                            .asIntBuffer();
            int[] array = new int[intBuf.remaining()];
            intBuf.get(array);
            return array;
        } catch (Exception ex) {
            // 저장된 파일이 없으면 null값 반환
            return null;
        }
    }
    

    // 데이터를 파일로 저장한다. 저장할 때, byte 로 저장시킨다.
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
    

    // 그림패널 클래스를 선언한다.
    class DrawingPanel extends Panel {
        // 그려지는 색깔을 확인하여, 그림판에 그림을 그린다.
        public void paint(Graphics g) {
            for (int i = 0; i < PAINT_HEIGHT; i++) {
                for (int j = 0; j < PAINT_WIDTH; j++) {
                    int currentInfo = paintInfo[(i * 400) + j];
                    if (currentInfo == 0) {
                        // 흰색은 아무것도 안칠함
                    } else if (currentInfo == 1) {
                        // 검은색 색칠
                        g.setColor(Color.BLACK);
                        g.fillOval(j, i, 3, 3);
                    }
                }
            }
        }
    }
    

    // paint info 를 byte array로 변환시켜준다.
    private byte[] paintInfoToByteArr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(paintInfo.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(paintInfo);
        return byteBuffer.array();
    }
}

