import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ColorPredictor extends JFrame {

	private static JPanel contentPane;
	static NeuralNetwork nn = new NeuralNetwork(3, 3, 1);
	static rgbColor c_rgb = new rgbColor();
	static Circle circle;
	static int count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ColorPredictor frame = new ColorPredictor();
					frame.setVisible(true);

					frame.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							//마우스 우클릭을 통해 학습을 시작 좌클릭으로 성능 테스트
							if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
								Timer timer = new Timer();
								TimerTask t_task = new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (count >= 10000) {
											timer.cancel();
										}
										count++;
										trainData();
										circle.repaint();
										frame.repaint();										
									}
								};
								timer.schedule(t_task, 0, 1);

							} else {
								test();
								//frame.repaint();
							}
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub

						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ColorPredictor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		c_rgb.reset();
		contentPane.setBackground(new Color(c_rgb.r, c_rgb.g, c_rgb.b));

		double[] a_rgb = { (double) c_rgb.r / 255, (double) c_rgb.g / 255, (double) c_rgb.b / 255 };
		double[] output = nn.feedForward(a_rgb);
		String predictColor = output[0] < 0.5 ? "black" : "white";

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel black_Label = new JLabel("black");
		black_Label.setHorizontalAlignment(SwingConstants.CENTER);
		black_Label.setFont(new Font("굴림", Font.PLAIN, 38));
		black_Label.setForeground(Color.black);
		black_Label.setBounds(100, 100, 100, 30);
		contentPane.add(black_Label);

		JLabel white_Label = new JLabel("white");
		white_Label.setHorizontalAlignment(SwingConstants.CENTER);
		white_Label.setFont(new Font("굴림", Font.PLAIN, 38));
		white_Label.setForeground(Color.white);
		white_Label.setBounds(400, 100, 100, 30);
		contentPane.add(white_Label);

		circle = new Circle(predictColor);
		if (predictColor == "black")
			circle.setBounds(110, 200, 80, 80);
		else
			circle.setBounds(410, 200, 80, 80);
		contentPane.add(circle);

	}

	public static void trainData() {
		// c_rgb.reset();
		Random random = new Random();
		boolean value = random.nextBoolean();

		c_rgb.setColor(value ? 255 : 0);
		contentPane.setBackground(new Color(c_rgb.r, c_rgb.g, c_rgb.b));

		double[] a_rgb = { ((double) c_rgb.r) / 255, ((double) c_rgb.g) / 255, ((double) c_rgb.b) / 255 };
		double[] output = nn.feedForward(a_rgb);
//		System.out.println(output[0]);
		// 학습
		double grayscale = ntsc(a_rgb);
		double[] answer = new double[1]; // 0 = black, 1 = white
		if (grayscale < 128) {
			answer[0] = 0.0;
		} else {
			answer[0] = 1.0;
		}
		nn.train(a_rgb, answer);

		String predictColor = output[0] < 0.5 ? "black" : "white";

		circle.color = predictColor;
		if (predictColor == "black")
			circle.setBounds(110, 200, 80, 80);
		else
			circle.setBounds(410, 200, 80, 80);
//		System.out.println(circle.color);
	}

	public static void test() {
		c_rgb.reset();
		contentPane.setBackground(new Color(c_rgb.r, c_rgb.g, c_rgb.b));

		double[] a_rgb = { ((double) c_rgb.r) / 255, ((double) c_rgb.g) / 255, ((double) c_rgb.b) / 255 };
		double[] output = nn.feedForward(a_rgb);
		
		System.out.println(ntsc(a_rgb));
		String predictColor = output[0] < 0.5 ? "black" : "white";
		
		circle.color = predictColor;
		if (predictColor == "black")
			circle.setBounds(110, 200, 80, 80);
		else
			circle.setBounds(410, 200, 80, 80);
	}

	public static double ntsc(double[] rgb) {
		// 0 = r, 1 = g, 2 = b
		return (0.299 * (rgb[0] * 255) + 0.587 * (rgb[1] * 255) + 0.114 * (rgb[2] * 255));
	}

}
