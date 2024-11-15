/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package demodagameconran;

/**
 *
 * @author 03062
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class WindowPanel extends JPanel implements ActionListener, KeyListener
{
    public static final int M_TILE   = 25;
    public static final int M_WIDTH  = M_TILE * 25;
    public static final int M_HEIGHT = M_TILE * 25;
    public static final int M_FPS    = 15;
    public static final int M_DELAY  = 1000 / M_FPS;
    private Timer m_timer = new Timer( M_DELAY, this );
    private Font  m_font  = new Font( "MS Gothic", Font.PLAIN, 100 );
    private Font  score_font  = new Font( "MS Gothic", Font.PLAIN, 25 );
    public  int score = 0;
    private int m_iFruitX, m_iFruitY;
    private int m_iSnakeLength = 4;
    private char m_cDir = 'r';
    public boolean m_bGameOver = false;
    private int[] m_iSnakeX, m_iSnakeY;
   
    @Override public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        // Con rắn      
        g.setColor( Color.ORANGE ); //Màu sắc con rắn
        for ( int i = 0; i < m_iSnakeLength; i++ )
            g.fillRect( m_iSnakeX[ i ], m_iSnakeY[ i ], M_TILE, M_TILE ); // Tô màu thân con rắn tại m_iSnakeX,m_iSnakeY
        // Trái cây
        g.setColor( Color.RED );//Màu của trái cây
        g.fillRect( m_iFruitX, m_iFruitY, M_TILE, M_TILE );//Tô màu trái cây
        // Lưới
        g.setColor( Color.WHITE );//Màu của Viền ô vuông
        for ( int i = 0; i < M_WIDTH; i += M_TILE )
            for ( int j = 25; j < M_HEIGHT; j += M_TILE )
                g.drawRect( i, j, M_TILE, M_TILE );//Viền xung quanh hình
        g.setColor( Color.BLACK );
        g.setFont(score_font);
        g.drawString( "Score:"+score, 10, 20 );
        // Game Over
        if ( m_bGameOver ) {
            g.setColor( Color.YELLOW );//Màu chữ
            g.setFont( m_font );//Tùy chỉnh kiểu chữ
            g.drawString( "GAME OVER", M_WIDTH / 2 - 233, M_HEIGHT / 2 );//Vẽ Chữ ở tọa độ tương ứng
        }
        g.dispose();
    }
 
    @Override public void actionPerformed( ActionEvent e )
    {
        if ( !m_bGameOver ) {
            //Cập nhật vị ví con rắn, Tạo hiệu ứng di chuyển
            for ( int i = m_iSnakeLength; i > 0; i-- ) {
                m_iSnakeX[ i ] = m_iSnakeX[ i - 1 ];
                m_iSnakeY[ i ] = m_iSnakeY[ i - 1 ];}
            //Hướng di chuyển của con rắn
            if ( m_cDir == 'r' ) m_iSnakeX[ 0 ] += M_TILE;
            if ( m_cDir == 'l' ) m_iSnakeX[ 0 ] -= M_TILE;
            if ( m_cDir == 'u' ) m_iSnakeY[ 0 ] -= M_TILE;
            if ( m_cDir == 'd' ) m_iSnakeY[ 0 ] += M_TILE;
            if ( m_iSnakeX[ 0 ] < 0 )
                m_iSnakeX[ 0 ] = M_WIDTH;
            if ( m_iSnakeY[ 0 ] < 25 )
                m_iSnakeY[ 0 ] = M_HEIGHT;
            if ( m_iSnakeX[ 0 ] > M_WIDTH )
                m_iSnakeX[ 0 ] = 0;
            if ( m_iSnakeY[ 0 ] > M_HEIGHT )
                m_iSnakeY[ 0 ] = 25;
            if ( m_iSnakeX[ 0 ] == m_iFruitX &&
                 m_iSnakeY[ 0 ] == m_iFruitY ) {
                m_iSnakeLength++;
                score++;
                //Tạo lại trái cây khi tạo độ của con rắn bằng với tạo độ của trái cây
                m_iFruitX = new java.util.Random().nextInt( M_WIDTH  / M_TILE );
                m_iFruitY = new java.util.Random().nextInt( M_HEIGHT / M_TILE );
                m_iFruitX *= M_TILE;
                m_iFruitY *= M_TILE; }
            for ( int i = m_iSnakeLength; i > 0; i-- ) {
                if ( m_iSnakeX[ 0 ] == m_iSnakeX[ i ] &&
                     m_iSnakeY[ 0 ] == m_iSnakeY[ i ] )
                    m_bGameOver = true; 
                GameOver gameover = new GameOver();
                gameover.setVisible(m_bGameOver);
                this.setVisible(!m_bGameOver);
            }
        }
        // kích hoạt lại phương thức paintComponent( Graphics g ) để vẽ lại giao diện
        repaint();
    }
 
    @Override public void keyPressed( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_RIGHT )
            if ( m_cDir != 'l' )
                m_cDir = 'r';
        if ( e.getKeyCode() == KeyEvent.VK_LEFT )
            if ( m_cDir != 'r' )
                m_cDir = 'l';
        if ( e.getKeyCode() == KeyEvent.VK_UP )
            if ( m_cDir != 'd' )
                m_cDir = 'u';
        if ( e.getKeyCode() == KeyEvent.VK_DOWN )
            if ( m_cDir != 'u' )
                m_cDir = 'd';
    }
 
    @Override public void keyTyped( KeyEvent e ) {}
    @Override public void keyReleased( KeyEvent e ) {}
   
    public WindowPanel()
    {
        this.setFocusable( true );
        this.setDoubleBuffered( true );
        this.setPreferredSize( new Dimension( M_WIDTH, M_HEIGHT ) );
        this.setBackground( Color.CYAN );
        this.addKeyListener( this );
        m_iFruitX = new java.util.Random().nextInt( M_WIDTH  / M_TILE );
        m_iFruitY = new java.util.Random().nextInt( M_HEIGHT / M_TILE );
        m_iFruitX *= M_TILE;
        m_iFruitY *= M_TILE;
        m_iSnakeX = new int[ 200 ];
        m_iSnakeY = new int[ 200 ];
        m_timer.start();
    }
}

public class Main extends JFrame {

    /**
     * @param args the command line arguments
     */
     public Main()
    {
        this.add( new WindowPanel() );
        this.pack();
        this.setTitle( "Game Con Rắn" );
        this.setResizable( false );
        this.setLocationRelativeTo( null );
        this.setVisible( true );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
 
    public static void main( String[] args )
    {
        new Main();
    }
    
}
