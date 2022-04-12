/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gamevahnichenko;

import java.awt.Graphics;  //Вахниченко Никита Юрьевич
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author nikit
 */
public class GameVahnichenko extends JFrame { //класс Game наследует от класса JFrame
    
    private static GameVahnichenko gamevahnichenko_window; //переменная, хранящая объект окна
    private static long last_frame_time; //переменная времени
    private static Image backgr; //объявление переменной для картинки
    private static Image cowb; //объявление переменной для картинки
    private static Image plan; //объявление переменной для картинки
    private static float cowb_left = 200; //координата точки угла падающего объекта
    private static float cowb_top = -100; //координата точки угла падающего объекта
    private static float cowb_v = 200; //переменная скорости
    private static int score = 0; //переменная очков
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException { //обработка исключения
        // TODO code application logic here
        
        backgr = ImageIO.read(GameVahnichenko.class.getResourceAsStream("backgr.jpg")); //загрузка картинки
        cowb = ImageIO.read(GameVahnichenko.class.getResourceAsStream("cowb.jpg")); //загрузка картинки
        plan = ImageIO.read(GameVahnichenko.class.getResourceAsStream("plan.jpg")); //загрузка картинки
        
        gamevahnichenko_window = new GameVahnichenko(); //создание объекта окна
        gamevahnichenko_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //завершение программы при закрытии окна
        gamevahnichenko_window.setLocation(200, 50); //точка появления окна программы
        gamevahnichenko_window.setSize(900, 600); //размер окна
        gamevahnichenko_window.setResizable(false); //запрет изменение размера окна
        last_frame_time = System.nanoTime(); //присвоение значения времени переменной
        GameField game_field = new GameField(); //создание объекта класса GameField
        game_field.addMouseListener(new MouseAdapter() { //обработчик события нажатия на кнопку мыши
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = cowb_left + plan.getWidth(null); //Вахниченко
                float drop_bottom = cowb_top + plan.getHeight(null);
                boolean is_drop = x >= cowb_left && x <= drop_right && y >= cowb_top && y <= drop_bottom;
                
                if (is_drop) { //Вахниченко Н.Ю.
                    cowb_top = -100; //координата точки угла падающего объекта
                    cowb_left = (int) (Math.random() * (game_field.getWidth() - plan.getWidth(null))); //координата точки угла падающего объекта
                    cowb_v = cowb_v + 10; //увеличение переменной скорости 
                    score++; //увеличение переменной очка
                    gamevahnichenko_window.setTitle("Score: " + score); //отражение количества очков
                }
            }
        });
        gamevahnichenko_window.add(game_field); //добавление в окно
        gamevahnichenko_window.setVisible(true); //видимость окна
        
        
    }
    
    private static void onRepaint(Graphics g){ //метод для возможности рисования в окне (игровой цикл)
        long current_time = System.nanoTime();
        float delt_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        
        cowb_top = cowb_top + cowb_v * delt_time;
        g.drawImage(backgr, 0, 0, null); //Вахниченко
        g.drawImage(plan, (int) cowb_left, (int) cowb_top, null); //отрисовка картинки
        if(cowb_top > gamevahnichenko_window.getHeight()) g.drawImage(cowb, 50, -100, null); //отрисовка картинки
    }
    
    private static class GameField extends JPanel { //создания класса, наследующего от класса JPanel
        
        @Override //динамическое замещение методов
        protected void paintComponent(Graphics g) { //переопределенный метод
            super.paintComponent(g); //получение доступа к компоненту 
            onRepaint(g); //вызов метода onRepaint
            repaint(); //отрисовка
        }
    }
    
}

