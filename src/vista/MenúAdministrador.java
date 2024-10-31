package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MenúAdministrador {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenúAdministrador window = new MenúAdministrador();
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
	public MenúAdministrador() {
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
		
		JLabel lblNewLabel = new JLabel("Menú de opciones");
		lblNewLabel.setBounds(167, 32, 86, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Registrar Usuario");
		btnNewButton.setBounds(149, 57, 126, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnConsultarUsuario = new JButton("Registrar Socio");
		btnConsultarUsuario.setBounds(149, 91, 126, 23);
		frame.getContentPane().add(btnConsultarUsuario);
		
		JButton btnNewButton_2 = new JButton("Modificar Localidad Socio");
		btnNewButton_2.setBounds(138, 125, 151, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Reestablecer Contraseña");
		btnNewButton_3.setBounds(138, 165, 155, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Registrar Socio");
		btnNewButton_4.setBounds(149, 199, 126, 23);
		frame.getContentPane().add(btnNewButton_4);
	}
}
