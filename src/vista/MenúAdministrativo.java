package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MenúAdministrativo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenúAdministrativo window = new MenúAdministrativo();
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
	public MenúAdministrativo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 339);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Menú de opciones");
		lblNewLabel.setBounds(167, 32, 86, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Consultar Libros");
		btnNewButton.setBounds(149, 57, 131, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnConsultarUsuario = new JButton("Consultar Prestamos");
		btnConsultarUsuario.setBounds(149, 91, 131, 23);
		frame.getContentPane().add(btnConsultarUsuario);
		
		JButton btnNewButton_2 = new JButton("Consultar Sanciones");
		btnNewButton_2.setBounds(149, 126, 131, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Registrar Libro");
		btnNewButton_3.setBounds(149, 164, 126, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Consultar Libros");
		btnNewButton_4.setBounds(149, 199, 126, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_4_1 = new JButton("Registrar autor");
		btnNewButton_4_1.setBounds(149, 235, 126, 23);
		frame.getContentPane().add(btnNewButton_4_1);
	}
}
