package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class RegistroUsuarios {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroUsuarios window = new RegistroUsuarios();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegistroUsuarios() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(184, 25, 37, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(159, 50, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(159, 106, 86, 20);
		textField_1.setColumns(10);
		frame.getContentPane().add(textField_1);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(184, 81, 48, 14);
		frame.getContentPane().add(lblApellidos);
		
		textField_2 = new JTextField();
		textField_2.setBounds(159, 163, 86, 20);
		textField_2.setColumns(10);
		frame.getContentPane().add(textField_2);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaDeNacimiento.setBounds(157, 137, 98, 14);
		frame.getContentPane().add(lblFechaDeNacimiento);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(184, 194, 37, 14);
		frame.getContentPane().add(lblCorreo);
		
		textField_3 = new JTextField();
		textField_3.setBounds(161, 220, 86, 20);
		textField_3.setColumns(10);
		frame.getContentPane().add(textField_3);
	}
}
